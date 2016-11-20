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
public class ParseProjInGenFasta {

    public static void main(String[] args) {
        ParseProjInGenFasta fasta = new ParseProjInGenFasta();
        fasta.generateFastaFormat("practice");
        fasta.generateFastaFormat("challenge");
    }

    private void generateFastaFormat(String inFolderName) {
        FileWriter fw = null;
        FileWriter fwPos = null;
        FileWriter fwNeg = null;

        File inFolder = new File(inFolderName);
        String outFolderName = inFolderName + "Fasta";
        File outFolder = new File(inFolder.getAbsolutePath().replaceAll(inFolderName, "") + "\\" + outFolderName);

        outFolder.mkdir();

        File[] inFiles = inFolder.listFiles();

        for (int fileNo = 0; fileNo < inFiles.length; fileNo++) {
            int nSeq = 1;
            int totalCharacter = 0;

            try {
                Scanner s = new Scanner(inFiles[fileNo]);
                String outFileName = inFiles[fileNo].getAbsolutePath();
                outFileName = outFileName.replaceAll(inFolderName, outFolderName);
                outFileName = outFileName.substring(0, outFileName.indexOf("."));

                String outPosFileName = outFileName + "_pos" + ".fasta";
                String outNegFileName = outFileName + "_neg" + ".fasta";
                outFileName = outFileName + ".fasta";

                fw = new FileWriter(outFileName);

                // no pos-neg fasta file for forPred data files
                if (!inFiles[fileNo].getName().contains("forPred")) {
                    fwPos = new FileWriter(outPosFileName);
                    fwNeg = new FileWriter(outNegFileName);
                }

                while (s.hasNext()) {
                    String line = s.nextLine();
                    String sqnc = line.split(" ")[0];
                    String pred = "p"; // default

                    String out = ">seq" + nSeq + "\n" + sqnc + "\n";
                    fw.write(out);

                    if (!inFiles[fileNo].getName().contains("forPred")) {
                        pred = line.split(" ")[1];

                        if (pred.equalsIgnoreCase("p")) {
                            fwPos.write(out);
                        } else {
                            fwNeg.write(out);
                        }
                    }
                    totalCharacter += out.length();
                    nSeq++;
                }
                System.out.println(totalCharacter);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fw.close();
                    if (!inFiles[fileNo].getName().contains("forPred")) {
                        fwPos.close();
                        fwNeg.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
