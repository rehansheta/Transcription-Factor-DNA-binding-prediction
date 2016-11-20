/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Rehan
 */
public class Chromosome implements Comparable {

    // chromosome is not a RTP, but a solution (collection of RTP's in our problem)
    
    public KMer left;       // part1
    public KMer right;      // part2
    double fitness;         // p-value from ttest using X1[] and X2[]
    double[] X1;            // min(count of triplet i left & right) in positive sequences
    double[] X2;            // min(count of triplet i left & right) in negative sequences
    
    public Chromosome() {
    }

    public Chromosome(KMer left, KMer right) {
        this.left = left;
        this.right = right;
        
        // initialize X1 and X2
        this.X1 = new double[left.getPosFrequencyArray().length];
        for (int i = 0; i < left.getPosFrequencyArray().length; i++) {
            this.X1[i] = Math.min(left.getPosFrequencyArray()[i], right.getPosFrequencyArray()[i]);
        }
        this.X2 = new double[left.getNegFrequencyArray().length];
        for (int i = 0; i < left.getNegFrequencyArray().length; i++) {
            this.X2[i] = Math.min(left.getNegFrequencyArray()[i], right.getNegFrequencyArray()[i]);
        }
        
        System.out.println("X1.length = " + X1.length + " X2.length = " + X2.length);
        
        // set fitness
        TTest tTest = new TTest();
        this.fitness = tTest.tTest(X1, X2);
    }

    @Override
    public int compareTo(Object o) {
        Chromosome c = (Chromosome) o;
        return this.getFitness() > c.getFitness() ? 1 : -1;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chromosome) {
            Chromosome c = (Chromosome) o;
            return this.left.equals(c.left) && this.right.equals(c.right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (left.getMotif()+right.getMotif()).hashCode();
    }

    @Override
    public String toString() {
        return left.getMotif() + "__" + right.getMotif();
    }

    public static void main(String[] args) {
        Chromosome c = new Chromosome(new KMer("ATG", 0, 0, 0.0), new KMer("TGV", 0, 0, 0.0));
        System.out.println(c.getFitness());
    }
}
