/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vio.service.rest;

import java.util.Arrays;

/**
 *
 * @author moroz
 */
public class AbstractRS {

    public static final int DEF_RESULTS_COUNT = 15;
    public static final int[] DEF_RESULTS_RANGE = {0, DEF_RESULTS_COUNT};

    public int[] getRangeFromHeader(String header) {
        if (header==null){
            return Arrays.copyOf(DEF_RESULTS_RANGE, DEF_RESULTS_RANGE.length);
        }
        String[] strs = header.split("-");
        if (strs.length != 2) {
            return Arrays.copyOf(DEF_RESULTS_RANGE, DEF_RESULTS_RANGE.length);
        }

        int[] result = new int[2];
        try {
            result[0] = Integer.parseInt(strs[0]);
            result[1] = Integer.parseInt(strs[1]);
        } catch (NumberFormatException e) {
            return Arrays.copyOf(DEF_RESULTS_RANGE, DEF_RESULTS_RANGE.length);
        }
        return result;
    }
}
