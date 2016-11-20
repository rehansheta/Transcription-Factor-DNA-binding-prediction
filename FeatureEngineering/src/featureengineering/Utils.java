/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureengineering;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rehan
 */
public class Utils {

    public static int matcher3(String REGEX, String INPUT) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT);
        int count = 0;
        while (m.find()) {
            count++;
        }
        return count;
    }

    public static int matcher2(String searchFor, String base) {
        int len = searchFor.length();
        int result = 0;
        if (len > 0) {
            int start = base.indexOf(searchFor);
            while (start != -1) {
                result++;
                start = base.indexOf(searchFor, start + len);
            }
        }
        return result;
    }

    public static int matcher(String searchFor, String base) {
        int baseLen = base.length();
        int searchForLen = searchFor.length();
        int count = 0;
        for (int i = 0; i <= baseLen - searchForLen; i++) {
            if (searchFor.equalsIgnoreCase(base.substring(i, i + searchForLen))) {
                count++;
            }
        }
        return count;
    }

    public static double[] arrayMatcher(String motif, ArrayList seqArray) {
        ListIterator itr = seqArray.listIterator();
        double[] count = new double[seqArray.size()];
        int i = 0;
        while (itr.hasNext()) {
            String seq = (String) itr.next();
            count[i] = matcher2(motif, seq);
            i++;
        }
        return count;
    }
}
