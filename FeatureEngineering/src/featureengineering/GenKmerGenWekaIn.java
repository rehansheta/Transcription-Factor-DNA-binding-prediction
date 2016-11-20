/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Rehan
 */
public class GenKmerGenWekaIn {

    String chars = "ATGC";
    int NUM_K_MERS = 256;

    public void generateKmerFeaturedWekaInput() {
        FileWriter fw = null;
        String[] merCollection = new String[NUM_K_MERS];

//        String inFolderName = "practice";
        String inFolderName = "challenge";

        File inFolder = new File(inFolderName);
        String outFolderName = inFolderName + "Weka_256-4mer_1-0";
        File outFolder = new File(inFolder.getAbsolutePath().replaceAll(inFolderName, "") + "\\" + outFolderName);

        outFolder.mkdir();

        File[] inFiles = inFolder.listFiles();

        for (int fileNo = 0; fileNo < inFiles.length; fileNo++) {
            int kMer = 0;
            int nSeq = 1;

            try {
                Scanner s = new Scanner(inFiles[fileNo]);
                String outFileName = inFiles[fileNo].getAbsolutePath();
                outFileName = outFileName.replaceAll(inFolderName, outFolderName);
                outFileName = outFileName.substring(0, outFileName.indexOf(".")) + ".arff";
                fw = new FileWriter(outFileName);

                fw.write("@relation experiment\n\n");
                fw.write("@attribute sqnc string\n");
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l < 4; l++) {
                                merCollection[kMer] = chars.substring(i, i + 1) + chars.substring(j, j + 1) + chars.substring(k, k + 1) + chars.substring(l, l + 1);
                                fw.write("@attribute " + merCollection[kMer] + " INTEGER\n");
                                kMer++;
                            }
                        }
                    }
                }
//                if (!inFiles[fileNo].getName().contains("forPred")) {
                fw.write("@attribute class {n,p}\n");
//                }
                fw.write("\n@data\n");

                while (s.hasNext()) {
                    String line = s.nextLine();
                    String sqnc = line.split(" ")[0];
                    String pred = "p"; // default
                    if (!inFiles[fileNo].getName().contains("forPred")) {
                        pred = line.split(" ")[1];
                    }
//                    System.out.println((nSeq++) + ":\t" + sqnc + " " + pred);

                    fw.write("'" + sqnc + "',");
                    if (sqnc.contains(merCollection[0])) {
                        fw.write("1");
                    } else {
                        fw.write("0");
                    }
                    for (int i = 1; i < NUM_K_MERS; i++) {
                        if (sqnc.contains(merCollection[i])) {
                            fw.write(", 1");
                        } else {
                            fw.write(", 0");
                        }
                    }
//                    if (!inFiles[fileNo].getName().contains("forPred")) {
                    fw.write(", " + pred);
//                    }
                    fw.write("\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        GenKmerGenWekaIn dg = new GenKmerGenWekaIn();
        dg.generateKmerFeaturedWekaInput();
    }
}
