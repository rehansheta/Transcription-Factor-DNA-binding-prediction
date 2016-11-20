/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rehan
 */
public class MemeMastCmdGen {

    public static void main(String[] args) {
        MemeMastCmdGen mmcd = new MemeMastCmdGen();
        mmcd.generateCmd(100);
        mmcd.generateCmd(500);
        mmcd.generateCmd(1000);
    }

    private void generateCmd(int ev) {

        // practice
        for (int TF_NO = 1; TF_NO < 11; TF_NO++) {
            FileWriter fw = null;
            try {
                File f = new File("cmd\\TF_" + TF_NO + ".sh");
                if (ev == 100) {
                    fw = new FileWriter(f, false);

                    fw.write("#!/bin/bash");
                    fw.write("\n\n");

                    fw.write("PATH=/home/meme/bin:${PATH}");
                    fw.write("\n");
                    fw.write("export PATH");
                    fw.write("\n\n");
                } else {
                    fw = new FileWriter(f, true);
                }

//                fw.write("psp-gen -pos TF_" + TF_NO + "_data_1_pos.fasta -neg TF_" + TF_NO + "_data_1_neg.fasta > TF_" + TF_NO + "_data_1.psp");
//                fw.write("\n");
//                
//                /************/
//                fw.write("#");
//                
//                fw.write("meme TF_" + TF_NO + "_data_1.fasta -dna -minw 6 -maxw 15 -minsites 10 -mod zoops -nmotifs 5 -maxsize 200000 -psp TF_" + TF_NO + "_data_1.psp -text > TF_" + TF_NO + "_data_1_psp.meme");
//                fw.write("\n");
                for (int m = 1; m < 6; m++) {
                    fw.write("mast TF_" + TF_NO + "_data_1_psp.meme TF_" + TF_NO + "_data_1.fasta -norc -m " + m + " -ev " + ev + " -mt 0.01 -oc TF_" + TF_NO + "_data_1_mast_m" + m + "_ev" + ev + "");
                    fw.write("\n");
                }
                for (int m = 1; m < 6; m++) {
                    fw.write("mast TF_" + TF_NO + "_data_1_psp.meme TF_" + TF_NO + "_data_2.fasta -norc -m " + m + " -ev " + ev + " -mt 0.01 -oc TF_" + TF_NO + "_data_2_mast_m" + m + "_ev" + ev + "");
                    fw.write("\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(MemeMastCmdGen.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(MemeMastCmdGen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // challenge
        for (int TF_NO = 11; TF_NO < 16; TF_NO++) {
            FileWriter fw = null;
            try {
                File f = new File("cmd\\TF_" + TF_NO + ".sh");
                if (ev == 100) {
                    fw = new FileWriter(f, false);

                    fw.write("#!/bin/bash");
                    fw.write("\n\n");

                    fw.write("PATH=/home/meme/bin:${PATH}");
                    fw.write("\n");
                    fw.write("export PATH");
                    fw.write("\n\n");
                } else {
                    fw = new FileWriter(f, true);
                }

//                fw.write("psp-gen -pos TF_" + TF_NO + "_data_1_pos.fasta -neg TF_" + TF_NO + "_data_1_neg.fasta > TF_" + TF_NO + "_data_1.psp");
//                fw.write("\n");
//                
//                /************/
//                fw.write("#");
//                
//                fw.write("meme TF_" + TF_NO + "_data_1.fasta -dna -minw 6 -maxw 15 -minsites 10 -mod zoops -nmotifs 5 -maxsize 200000 -psp TF_" + TF_NO + "_data_1.psp -text > TF_" + TF_NO + "_data_1_psp.meme");
//                fw.write("\n");
                for (int m = 1; m < 6; m++) {
                    fw.write("mast TF_" + TF_NO + "_data_1_psp.meme TF_" + TF_NO + "_data_1.fasta -norc -m " + m + " -ev " + ev + " -mt 0.01 -oc TF_" + TF_NO + "_data_1_mast_m" + m + "_ev" + ev + "");
                    fw.write("\n");
                }
                for (int m = 1; m < 6; m++) {
                    fw.write("mast TF_" + TF_NO + "_data_1_psp.meme TF_" + TF_NO + "_data_2_forPred.fasta -norc -m " + m + " -ev " + ev + " -mt 0.01 -oc TF_" + TF_NO + "_data_2_forPred_mast_m" + m + "_ev" + ev + "");
                    fw.write("\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(MemeMastCmdGen.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(MemeMastCmdGen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // challenge
        for (int TF_NO = 16; TF_NO < 21; TF_NO++) {
            FileWriter fw = null;
            try {
                File f = new File("cmd\\TF_" + TF_NO + ".sh");
                if (ev == 100) {
                    fw = new FileWriter(f, false);

                    fw.write("#!/bin/bash");
                    fw.write("\n\n");

                    fw.write("PATH=/home/meme/bin:${PATH}");
                    fw.write("\n");
                    fw.write("export PATH");
                    fw.write("\n\n");
                } else {
                    fw = new FileWriter(f, true);
                }

//                fw.write("psp-gen -pos TF_" + TF_NO + "_data_2_pos.fasta -neg TF_" + TF_NO + "_data_2_neg.fasta > TF_" + TF_NO + "_data_2.psp");
//                fw.write("\n");
//
//                /************/
//                fw.write("#");
//
//                fw.write("meme TF_" + TF_NO + "_data_2.fasta -dna -minw 6 -maxw 15 -minsites 10 -mod zoops -nmotifs 5 -maxsize 200000 -psp TF_" + TF_NO + "_data_2.psp -text > TF_" + TF_NO + "_data_2_psp.meme");
//                fw.write("\n");
                for (int m = 1; m < 6; m++) {
                    fw.write("mast TF_" + TF_NO + "_data_2_psp.meme TF_" + TF_NO + "_data_2.fasta -norc -m " + m + " -ev " + ev + " -mt 0.01 -oc TF_" + TF_NO + "_data_2_mast_m" + m + "_ev" + ev + "");
                    fw.write("\n");
                }
                for (int m = 1; m < 6; m++) {
                    fw.write("mast TF_" + TF_NO + "_data_2_psp.meme TF_" + TF_NO + "_data_1_forPred.fasta -norc -m " + m + " -ev " + ev + " -mt 0.01 -oc TF_" + TF_NO + "_data_1_forPred_mast_m" + m + "_ev" + ev + "");
                    fw.write("\n");
                }
            } catch (IOException ex) {
                Logger.getLogger(MemeMastCmdGen.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(MemeMastCmdGen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
