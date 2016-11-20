/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;


/**
 *
 * @author Rehan
 */
public class KMer implements Comparable<KMer> {
    private String motif;
    private int posFrequency;       // total in all sequences
    private int negFrequency;       // total in all sequences
    private double normFreqDifference;
    
    private double[] posFrequencyArray;       // count in each sequence
    private double[] negFrequencyArray;       // count in each sequence
    private double pValue;

    public KMer(String motif, int posFrequency, int negFrequency, double normFreqDifference) {
        this.motif = motif;
        this.posFrequency = posFrequency;
        this.negFrequency = negFrequency;
        this.normFreqDifference = normFreqDifference;
    }

    public double getpValue() {
        return pValue;
    }

    public void setpValue(double pValue) {
        this.pValue = pValue;
    }

    public double[] getNegFrequencyArray() {
        return negFrequencyArray;
    }

    public void setNegFrequencyArray(double[] negFrequencyArray) {
        this.negFrequencyArray = negFrequencyArray;
    }

    public double[] getPosFrequencyArray() {
        return posFrequencyArray;
    }

    public void setPosFrequencyArray(double[] posFrequencyArray) {
        this.posFrequencyArray = posFrequencyArray;
    }

    public double getNormFreqDifference() {
        return normFreqDifference;
    }

    public void setNormFreqDifference(double normFreqDifference) {
        this.normFreqDifference = normFreqDifference;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getNegFrequency() {
        return negFrequency;
    }

    public void setNegFrequency(int negFrequency) {
        this.negFrequency = negFrequency;
    }

    public int getPosFrequency() {
        return posFrequency;
    }

    public void setPosFrequency(int posFrequency) {
        this.posFrequency = posFrequency;
    }

    @Override
    public int compareTo(KMer o) {
        // sort in increasing order according to pValue
        if (this.getpValue() > 1.0 || o.getpValue() > 1) {
            System.out.println("possible error");
        }
        return this.getpValue() > o.getpValue() ? 1 : -1;
        
        // sort in decreasing order according to normFreqDifference
        //return this.getNormFreqDifference() < o.getNormFreqDifference() ? 1 : -1;
    }
    
}
