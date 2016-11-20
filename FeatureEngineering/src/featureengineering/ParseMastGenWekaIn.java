/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rehan
 */
public class ParseMastGenWekaIn {

    double[][] eVal = new double[4000][5];
    String[] sequence = new String[4000];
    private int NUM_POS_SEQ = 1000;
    private int NUM_NEG_SEQ = 3000;

    public static void main(String[] args) {
        ParseMastGenWekaIn mast = new ParseMastGenWekaIn();
//        mast.processChallenge(100);
//        mast.processChallenge(500);
//        mast.processChallenge(1000);
        mast.processPractice(100);
        mast.processPractice(500);
        mast.processPractice(1000);
    }

    private void processPractice(int ev) {
        for (int i = 1; i < 11; i++) {
            readSeqFromFasta("practiceFasta", i, "1");
            parseMast("practiceMemeMast", i, "1", ev);
            generateWekaInput("practiceWeka\\ev_" + ev + "", i, "1");

            readSeqFromFasta("practiceFasta", i, "2");
            parseMast("practiceMemeMast", i, "2", ev);
            generateWekaInput("practiceWeka\\ev_" + ev + "", i, "2");
        }
    }

    private void processChallenge(int ev) {
        for (int i = 11; i < 16; i++) {
            readSeqFromFasta("challengeFasta", i, "1");
            parseMast("challengeMemeMast", i, "1", ev);
            generateWekaInput("challengeWeka\\ev_" + ev + "", i, "1");

            readSeqFromFasta("challengeFasta", i, "2_forPred");
            parseMast("challengeMemeMast", i, "2_forPred", ev);
            generateWekaInput("challengeWeka\\ev_" + ev + "", i, "2_forPred");
        }

        for (int i = 16; i <= 20; i++) {
            readSeqFromFasta("challengeFasta", i, "2");
            parseMast("challengeMemeMast", i, "2", ev);
            generateWekaInput("challengeWeka\\ev_" + ev + "", i, "2");

            readSeqFromFasta("challengeFasta", i, "1_forPred");
            parseMast("challengeMemeMast", i, "1_forPred", ev);
            generateWekaInput("challengeWeka\\ev_" + ev + "", i, "1_forPred");
        }
    }

    private void parseMast(String folderName, int TFNo, String dataSetNo, int ev) {

        for (int i = 0; i < 5; i++) {
            String fileName = folderName + "\\TF_" + TFNo + "_data_" + dataSetNo + "_mast_m" + (i + 1) + "_ev" + ev + "\\mast.txt";

            File file = new File(fileName);
            try {
                Scanner scanner = new Scanner(file);

                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (line.equalsIgnoreCase("SEQUENCE NAME                      DESCRIPTION                   E-VALUE  LENGTH")) {
                        line = scanner.nextLine();
                        line = scanner.nextLine();
                        while (line.length() > 0) {
                            String[] temp = line.split("\\s+");

                            int seqNo = Integer.parseInt(temp[0].substring(3));
                            double value = Double.parseDouble(temp[1]);
                            eVal[seqNo - 1][i] = value;

                            line = scanner.nextLine();
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ParseMastGenWekaIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void generateWekaInput(String folderName, int TFNo, String dataSetNo) {
        FileWriter fw = null;
        try {
            String outFileName = folderName + "\\TF_" + TFNo + "_data_" + dataSetNo + ".arff";
            File outFile = new File(folderName);
            outFile.mkdirs();
            fw = new FileWriter(outFileName);

            fw.write("@relation experiment\n\n");
            fw.write("@attribute sqnc string\n");
            for (int i = 0; i < 5; i++) {
                fw.write("@attribute " + "MOTIF_" + (i + 1) + " REAL\n");
            }
            fw.write("@attribute class {n,p}\n");
            fw.write("\n@data\n");

            // have to fix
            for (int i = 0; i < 4000; i++) {
                fw.write("'" + sequence[i] + "', ");
                fw.write("" + eVal[i][0]);
                for (int j = 1; j < 5; j++) {
                    fw.write(", " + eVal[i][j]);
                }
                if (i < NUM_POS_SEQ) {
                    fw.write(", p\n");
                } else {
                    fw.write(", n\n");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ParseMastGenWekaIn.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ParseMastGenWekaIn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void readSeqFromFasta(String folderName, int TFNo, String dataSetNo) {
        try {
            String fastaFileName = folderName + "\\TF_" + TFNo + "_data_" + dataSetNo + ".fasta";
            File fastaFile = new File(fastaFileName);
            Scanner scanner = new Scanner(fastaFile);

            int seqNo = 0;
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                sequence[seqNo] = scanner.nextLine();
                seqNo++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseMastGenWekaIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
