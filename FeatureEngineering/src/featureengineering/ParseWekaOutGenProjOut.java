/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rehan
 */
public class ParseWekaOutGenProjOut {

    public static void main(String[] args) {
        ParseWekaOutGenProjOut wop = new ParseWekaOutGenProjOut();

//        String folderName = "practiceWeka_256-ranked-4mer_1-0";
//         for (int i = 1; i <= 10; i++) {
//            wop.parseWekaPred(folderName, i, "2_pred");
//            wop.parseWekaStat(folderName, i, "2_stat", folderName + "_test_stat.csv", false);
//        }

        String folderName = "practiceWeka\\ev_500";
        for (int i = 1; i < 6; i++) {
            wop.parseWekaPred(folderName, i, "1_cv_pred");
            wop.parseWekaStat(folderName, i, "1_cv_stat", "ev_500" + "_cv_stat.csv", false);
            wop.parseWekaPred(folderName, i, "2_pred");
            wop.parseWekaStat(folderName, i, "2_stat", "ev_500" + "_test_stat.csv", false);
        }
        for (int i = 6; i < 11; i++) {
            wop.parseWekaPred(folderName, i, "2_cv_pred");
            wop.parseWekaStat(folderName, i, "2_cv_stat", "ev_500" + "_cv_stat.csv", false);
            wop.parseWekaPred(folderName, i, "1_pred");
            wop.parseWekaStat(folderName, i, "1_stat", "ev_500" + "_test_stat.csv", false);
        }

        
//        String folderName = "challengeWeka\\ev_1000";
//        for (int i = 11; i < 16; i++) {
//            wop.parseWekaPred(folderName, i, "1_cv_pred");
//            wop.parseWekaPred(folderName, i, "2_pred");
//            wop.parseWekaStat(folderName, i, "1_cv_stat", "ev_1000" + "_cv_stat.csv", false);
//        }
//
//        for (int i = 16; i < 21; i++) {
//            wop.parseWekaPred(folderName, i, "2_cv_pred");
//            wop.parseWekaPred(folderName, i, "1_pred");
//            wop.parseWekaStat(folderName, i, "2_cv_stat", "ev_1000" + "_cv_stat.csv", false);
//        }
    }

    private void parseWekaPred(String folderName, int TFNo, String dataSetNo) {

        int totalPos = 0;

        FileWriter fw = null;
        try {
            String wekaOutFileName = folderName + "\\TF_" + TFNo + "_data_" + dataSetNo + ".weka";
            String projOutFileName = folderName + "\\TF_" + TFNo + "_data_" + dataSetNo + ".txt";
            File inputFile = new File(wekaOutFileName);
            Scanner scanner = new Scanner(inputFile);
            fw = new FileWriter(projOutFileName);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("inst#")) {
                    break;
                }
            }
            while (scanner.hasNext()) {
                String line = scanner.nextLine().trim();
                if (line.trim().length() < 1) {
                    break;
                }

                String temp[] = line.split("\\s+");
                //                System.out.println(line);
                String predicted = temp[2].split(":")[1];
                String distribution = temp[temp.length - 2].split(",")[1];
                if (distribution.startsWith("*")) {
                    distribution = distribution.substring(1);
                }
                String sqnc = temp[temp.length - 1];
                sqnc = sqnc.substring(1, sqnc.length() - 1);

                fw.write(sqnc + "\t" + predicted + "\t" + distribution + "\n");

                // statistics
                if (predicted.startsWith("p")) {
                    totalPos++;
                }
            }
            // statistics continue
            //System.out.println("pos = " + totalPos + " neg = " + (4000 - totalPos));
            System.out.println(wekaOutFileName + " processed");
        } catch (IOException ex) {
            Logger.getLogger(ParseWekaOutGenProjOut.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ParseWekaOutGenProjOut.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void parseWekaStat(String folderName, int TFNo, String dataSetNo, String statFileName, boolean onTrainingSet) {

        FileWriter fw = null;
        try {
            String wekaOutFileName = folderName + "\\TF_" + TFNo + "_data_" + dataSetNo + ".weka";
            String projOutFileName = folderName + "\\" + statFileName;
            File inputFile = new File(wekaOutFileName);
            Scanner scanner = new Scanner(inputFile);
            fw = new FileWriter(projOutFileName, true);

            boolean kappaFound = false;
            boolean accuracyFound = false;
            boolean weightedFound = false;
            double kappa = -1.0;
            double accuracy = -1.0;
            double ROC_Area = -1.0;

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.contains("Kappa statistic")) {
                    if (!onTrainingSet || (onTrainingSet && !kappaFound)) {
                        kappa = Double.parseDouble(line.replaceAll("Kappa statistic", "").trim());
                        kappaFound = true;
                    }
                }
                if (line.contains("Correctly Classified Instances")) {
                    if (!onTrainingSet || (onTrainingSet && !accuracyFound)) {
                        line = line.replaceAll("Correctly Classified Instances", "").trim();
                        String[] temp = line.split("\\s+");
                        accuracy = Double.parseDouble(temp[1]);
                        accuracyFound = true;
                    }
                }
                if (line.contains("Weighted Avg.")) {
                    if (!onTrainingSet || (onTrainingSet && !weightedFound)) {
                        line = line.replaceAll("Weighted Avg.", "").trim();
                        String[] temp = line.split("\\s+");
                        ROC_Area = Double.parseDouble(temp[5]);
                        weightedFound = true;
                    }
                }
            }
            fw.write(TFNo + ", " + accuracy + ", " + kappa + ", " + ROC_Area + "\n");
            System.out.println(wekaOutFileName + " processed");
        } catch (IOException ex) {
            Logger.getLogger(ParseWekaOutGenProjOut.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ParseWekaOutGenProjOut.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
