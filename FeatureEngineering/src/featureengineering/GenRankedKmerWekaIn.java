/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Rehan
 */
public class GenRankedKmerWekaIn {

    int K = 6;
    int NUM_CONSIDERABLE_K_MERS = 1024;
    String featureMethod = "ttest";     //ranked, ttest, ga
    String featureValue = "pval";           //1-0, count, pval
    ArrayList<KMer> kmerList = new ArrayList<KMer>();
    int NUM_POS_SEQ = 1000;
    int NUM_NEG_SEQ = 3000;
    ArrayList<String> posSeq;
    ArrayList<String> negSeq;
    public static TTest studentTTest = new TTest();

    public void generateRankedKmer() {
        FileWriter trainingfw = null;
        FileWriter testingfw = null;

        String trainFolderName = "practice\\trainingData";
        String testFolderName = "practice\\testingData";
        String outFolderName = "practice" + "Weka_" + NUM_CONSIDERABLE_K_MERS + "-" + featureMethod + "-" + K + "mer_" + featureValue;
//        String trainFolderName = "challenge\\trainingData";
//        String testFolderName = "challenge\\testingData";
//        String outFolderName = "challenge" + "Weka_" + NUM_CONSIDERABLE_K_MERS + "-" + featureMethod + "-" + K + "mer_" + featureValue;

        File trainingFolder = new File(trainFolderName);
        File testingFolder = new File(testFolderName);
        File outFolder = new File(outFolderName);
        outFolder.mkdir();

        File[] trainingFiles = trainingFolder.listFiles();
        File[] testingFiles = testingFolder.listFiles();

        // trainingFiles.length & testingFiles.length should be same
        for (int fileNo = 0; fileNo < trainingFiles.length; fileNo++) {
            try {
                // scan training file
                Scanner trainingScanner = new Scanner(trainingFiles[fileNo]);
                String trainingWekaFileName = trainingFiles[fileNo].getName();
                trainingWekaFileName = outFolderName + "\\" + trainingWekaFileName;
                trainingWekaFileName = trainingWekaFileName.substring(0, trainingWekaFileName.indexOf(".")) + ".arff";
                trainingfw = new FileWriter(trainingWekaFileName);

                posSeq = new ArrayList<String>();
                negSeq = new ArrayList<String>();

                while (trainingScanner.hasNext()) {
                    String line = trainingScanner.nextLine();
                    String seq = line.split(" ")[0];
                    String label = "p"; // default
                    if (!trainingFiles[fileNo].getName().contains("forPred")) {
                        label = line.split(" ")[1];
                    }
//                    System.out.println(seq + " " + label);

                    if (label.equalsIgnoreCase("p")) {
                        posSeq.add(seq);
                    } else if (label.equalsIgnoreCase("n")) {
                        negSeq.add(seq);
                    }

                    ListIterator itr = kmerList.listIterator();
                    while (itr.hasNext()) {
                        KMer kmer = (KMer) itr.next();
                        if (label.equalsIgnoreCase("p")) {
                            kmer.setPosFrequency(kmer.getPosFrequency() + Utils.matcher2(kmer.getMotif(), seq));
                        } else if (label.equalsIgnoreCase("n")) {
                            kmer.setNegFrequency(kmer.getNegFrequency() + Utils.matcher2(kmer.getMotif(), seq));
                        }
                    }
                }

                // normalized frequency difference calculation
                ListIterator itr = kmerList.listIterator();
                while (itr.hasNext()) {
                    KMer kmer = (KMer) itr.next();
                    kmer.setNormFreqDifference(Math.abs((double) kmer.getPosFrequency() / (double) NUM_POS_SEQ - (double) kmer.getNegFrequency() / (double) NUM_NEG_SEQ));
                }

                // frequence array for ttest and pValue from ttest
                itr = kmerList.listIterator();
                while (itr.hasNext()) {
                    KMer kmer = (KMer) itr.next();
                    double[] posFrequencyArray = Utils.arrayMatcher(kmer.getMotif(), posSeq);
                    double[] negFrequencyArray = Utils.arrayMatcher(kmer.getMotif(), negSeq);
                    kmer.setPosFrequencyArray(posFrequencyArray);
                    kmer.setNegFrequencyArray(negFrequencyArray);
                    kmer.setpValue(studentTTest.tTest(posFrequencyArray, negFrequencyArray));
                    if ((kmer.getpValue() + "").equalsIgnoreCase("nan")) {
                        System.out.println("pval: " + kmer.getpValue());
                        kmer.setpValue(0.5);
                    }
                }

                Collections.sort(kmerList);

                // prepare corresponding testing file
                String testingWekaFileName = testingFiles[fileNo].getName();
                testingWekaFileName = outFolderName + "\\" + testingWekaFileName;
                testingWekaFileName = testingWekaFileName.substring(0, testingWekaFileName.indexOf(".")) + ".arff";
                testingfw = new FileWriter(testingWekaFileName);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            writeRankedKmerFeaturedWekaInput(trainingFiles[fileNo], trainingfw);
            writeRankedKmerFeaturedWekaInput(testingFiles[fileNo], testingfw);

            resetKmerCounters();
        }
    }

    public void resetKmerCounters() {
        ListIterator itr = kmerList.listIterator();
        while (itr.hasNext()) {
            KMer kmer = (KMer) itr.next();
            kmer.setNegFrequency(0);
            kmer.setPosFrequency(0);
            kmer.setNormFreqDifference(0.0);
        }
    }

    public void writeNormFeqDifferences() {
        FileWriter testfw = null;
        try {
            File f = new File("test.csv");
            testfw = new FileWriter(f);

            ListIterator itr = kmerList.listIterator();
            while (itr.hasNext()) {
                KMer kmer = (KMer) itr.next();
                testfw.write(kmer.getMotif() + "," + kmer.getNormFreqDifference() + "," + kmer.getPosFrequency() + "," + kmer.getNegFrequency());
                testfw.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(GenRankedKmerWekaIn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                testfw.close();
            } catch (IOException ex) {
                Logger.getLogger(GenRankedKmerWekaIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeRankedKmerFeaturedWekaInput(File inputFile, FileWriter fw) {
        try {

            Scanner s = new Scanner(inputFile);

            fw.write("@relation experiment\n\n");
            fw.write("@attribute sqnc string\n");
            for (int i = 0; i < NUM_CONSIDERABLE_K_MERS; i++) {
                fw.write("@attribute " + kmerList.get(i).getMotif() + " INTEGER\n");
            }

            fw.write("@attribute class {n,p}\n");
            fw.write("\n@data\n");

            while (s.hasNext()) {
                String line = s.nextLine();
                String sqnc = line.split(" ")[0];
                String pred = "p"; // default
                if (!inputFile.getName().contains("forPred")) {
                    pred = line.split(" ")[1];
                }
                //System.out.println((nSeq++) + ":\t" + sqnc + " " + pred);

                fw.write("'" + sqnc + "',");
                if (featureValue.equalsIgnoreCase("1-0")) {
                    if (sqnc.contains(((KMer) kmerList.get(0)).getMotif())) {
                        fw.write("1");
                    } else {
                        fw.write("0");
                    }
                } else if (featureValue.equalsIgnoreCase("pval")) {
                    fw.write("" + ((KMer) kmerList.get(0)).getpValue());
                }
                for (int i = 1; i < NUM_CONSIDERABLE_K_MERS; i++) {
                    if (featureValue.equalsIgnoreCase("1-0")) {
                        if (sqnc.contains(((KMer) kmerList.get(i)).getMotif())) {
                            fw.write(", 1");
                        } else {
                            fw.write(", 0");
                        }
                    } else if (featureValue.equalsIgnoreCase("pval")) {
                        fw.write(", " + ((KMer) kmerList.get(i)).getpValue());
                    }
                }
                fw.write(", " + pred);
                fw.write("\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(GenRankedKmerWekaIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void main(String[] args) {
        GenRankedKmerWekaIn dg = new GenRankedKmerWekaIn();
        GenKmer gk = new GenKmer();
        dg.kmerList = gk.getPermutations("ATGC", dg.K);
        dg.generateRankedKmer();
    }
}
