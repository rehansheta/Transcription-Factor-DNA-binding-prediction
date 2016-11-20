/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author Rehan
 */
public class GA {

    /*GLOBAL GA PARAMETERS*/
    int GENERATION_SIZE = 1000;
    int REPRODUCTION_SIZE = 500;
    int MAX_POPULATION_SIZE = 1000;
    int MAX_CHILDREN_LIMIT = 500;
    double CROSSOVER_PROBABILITY = 0.8;
    double MUTATION_PROBABILITY = 0.05;
    int K = 3;
    ArrayList<KMer> kmerList = new ArrayList<KMer>();
    ArrayList<String> posSeq;
    ArrayList<String> negSeq;

    public GA(ArrayList posSeq, ArrayList negSeq) {
        this.posSeq = posSeq;
        this.negSeq = negSeq;
        GenKmer gk = new GenKmer();
        kmerList = gk.getPermutations("ATGC", K);

        /* initialize the counter of kmers in positive and negative sequences */
        ListIterator itr = kmerList.listIterator();
        while (itr.hasNext()) {
            KMer kmer = (KMer) itr.next();
            kmer.setPosFrequencyArray(Utils.arrayMatcher(kmer.getMotif(), posSeq));
            kmer.setNegFrequencyArray(Utils.arrayMatcher(kmer.getMotif(), negSeq));
        }

        // generate initial population
        generateRandomPopulation();
    }

    private void generateRandomPopulation() {
    }
}
