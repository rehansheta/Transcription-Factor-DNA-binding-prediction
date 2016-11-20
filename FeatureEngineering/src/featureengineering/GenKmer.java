/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.util.ArrayList;

/**
 *
 * @author Rehan
 */
public class GenKmer {

    int K = 6;
    ArrayList<KMer> kmerList;
    StringBuilder b;

    ArrayList<KMer> getPermutations(String input, int K) {
        this.K = K;
        kmerList = new ArrayList<KMer>();
        b = new StringBuilder();
        permutations(input, 0);
        return kmerList;
    }
    
    void permutations(String input, int index) {
        
        if (index == K) {
            KMer kmer = new KMer(b.toString(), 0, 0, 0.0);
            kmerList.add(kmer);
            return;
        }

        for (int i = 0; i < input.length(); i++) {
            b.append(input.charAt(i));
            permutations(input, index + 1);
            b.deleteCharAt(b.length() - 1);
        }
    }
}
