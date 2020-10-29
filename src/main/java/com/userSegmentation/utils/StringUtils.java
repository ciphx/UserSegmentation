package com.userSegmentation.utils;

import java.util.Map;

public class StringUtils {

    private static boolean isParsableAsLong(String s) {
        try {
            Long.valueOf(s);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean isParsableAsInt(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    private static boolean isParsableAsDouble(String s) {
        try {
            Double.valueOf(s);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    /**
     * evaluates less than equal to
     * @param a
     * @param b
     * @return
     */
    public static Boolean evaluateLE(String a, String b){
        if(isParsableAsLong(a) && isParsableAsLong(b))
            return Long.valueOf(b) <= Long.valueOf(a);
        else if(isParsableAsDouble(a) && isParsableAsDouble(b))
            return Double.valueOf(a) <= Double.valueOf(b);
        else
            return false;
    }

    /**
     * evaluates greater than equal to
     * @param a
     * @param b
     * @return
     */
    public static Boolean evaluateGE(String a, String b){
        if(isParsableAsLong(a) && isParsableAsLong(b))
            return Long.valueOf(b) >= Long.valueOf(a);
        else if(isParsableAsDouble(a) && isParsableAsDouble(b))
            return Double.valueOf(a) >= Double.valueOf(b);
        else
            return false;
    }

    /**
     * evaluates less than
     * @param a
     * @param b
     * @return
     */
    public static Boolean evaluateLessThan(String a, String b){
        if(isParsableAsLong(a) && isParsableAsLong(b))
            return Long.valueOf(b) < Long.valueOf(a);
        else if(isParsableAsDouble(a) && isParsableAsDouble(b))
            return Double.valueOf(a) < Double.valueOf(b);
        else
            return false;
    }

    /**
     * evaluates greater than
     * @param a
     * @param b
     * @return
     */
    public static Boolean evaluateGreaterThan(String a, String b){
        if(isParsableAsLong(a) && isParsableAsLong(b))
            return Long.valueOf(b) > Long.valueOf(a);
        else if(isParsableAsDouble(a) && isParsableAsDouble(b))
            return Double.valueOf(a) > Double.valueOf(b);
        else
            return false;
    }

    /**
     * evaluates equal to
     * @param a
     * @param b
     * @return
     */
    public static Boolean equals(String a , String b){
        if(isParsableAsLong(a) && isParsableAsLong(b))
            return Long.valueOf(b).equals(Long.valueOf(a));
        else if(isParsableAsDouble(a) && isParsableAsDouble(b))
            return Double.valueOf(a).equals(Double.valueOf(b));
        else
            return a.equalsIgnoreCase(b);
    }

    /**
     * evaluates not equal to
     * @param a
     * @param b
     * @return
     */
    public static Boolean notEquals(String a , String b){
        return !equals(a,b);
    }

    public static String formatMap(Map<String, Boolean> map){
        String result = "[\n";
        for(String ruleIdentifier : map.keySet()) {
            result += (ruleIdentifier+": "+ map.get(ruleIdentifier)+"\n");
        }
        result += "]";
        return result;
    }
}


