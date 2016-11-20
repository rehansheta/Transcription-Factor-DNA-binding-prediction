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
public class WekaCmdGen {

    public static void main(String[] args) {
        WekaCmdGen wcg = new WekaCmdGen();
        wcg.generateCmd();
    }

    private void generateCmd() {
        FileWriter fw = null;
        try {
            String folderName = "challengeWeka";
//            String folderName = "practiceWeka";
            
            String configName = "meme_psp_m5_ev1000";
//            String configName = "256-4mer_1-0";
            
            String classifier = "weka.classifiers.trees.J48";
            
            File f = new File("cmd\\wekaCLI.bat");
            fw = new FileWriter(f);

            for (int TF_NO = 11; TF_NO < 16; TF_NO++) {
                String cmd = "java -cp weka.jar weka.classifiers.meta.FilteredClassifier "
                        + "-F \"weka.filters.unsupervised.attribute.RemoveType "
                        + "-T string\" "
                        + "-W " + classifier + " "
                        + "-t " + folderName + "\\" + configName + "\\TF_" + TF_NO + "_data_1.arff "
                        + "-T " + folderName + "\\" + configName + "\\TF_" + TF_NO + "_data_2_forPred.arff "
                        + "-p 1 -distribution "
                        + "-- -C 0.1 "
                        + "> " + folderName + "\\" + configName + "\\TF_" + TF_NO + "_data_2_forPred.weka";
                
                fw.write(cmd);
                fw.write("\n\n");
            }
            for (int TF_NO = 16; TF_NO < 21; TF_NO++) {
                String cmd = "java -cp weka.jar weka.classifiers.meta.FilteredClassifier "
                        + "-F \"weka.filters.unsupervised.attribute.RemoveType "
                        + "-T string\" "
                        + "-W " + classifier + " "
                        + "-t " + folderName + "\\" + configName + "\\TF_" + TF_NO + "_data_2.arff "
                        + "-T " + folderName + "\\" + configName + "\\TF_" + TF_NO + "_data_1_forPred.arff "
                        + "-p 1 -distribution "
                        + "-- -C 0.1 "
                        + "> " + folderName + "\\" + configName + "\\TF_" + TF_NO + "_data_1_forPred.weka";
                
                fw.write(cmd);
                fw.write("\n\n");
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
