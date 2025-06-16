package stmatching;

import java.util.Arrays;

public class StringMatching {

    private static boolean check(int n, int m){
        return n >= m;
    }

    public static int bruteForce(String str, String pattern) {

        int n = str.length();
        int m = pattern.length();

        if (!check(n, m))  return -1;

        for (int i =0 ; i < str.length(); i++){
            int j = 0;
            while (j < pattern.length() && pattern.charAt(j) == str.charAt(i+j)) j++;
            if (j == pattern.length()) return i;
        }

        return -1;
    }

    public static int boyerMoore(String str, String pattern) {

        int n = str.length();
        int m = pattern.length();

        if (!check(n, m))  return -1;

        int i = m - 1;
        int j = m - 1;

        do {
            if (pattern.charAt(j) == str.charAt(i)) {
                if (j == 0) return i;
                else {
                    j--;
                    i--;
                }
            }
            else {
                i += m - Math.min(j, 1+ last(str.charAt(i), pattern));
                j = m - 1;
            }
        } while(i <= n-1);

        return -1;
    }

    private static int last(char c, String pattern) {
        for (int i = pattern.length() - 1; i >= 0; i--) {
            if (pattern.charAt(i) == c) return i;
        }
        return -1;
    }

    public static int bruteForceAll(String str, String pattern) {

        int n = str.length();
        int m = pattern.length();

        if (!check(n, m))  return -1;

        int ans = 0;

        for (int i =0 ; i < str.length(); i++){
            int j = 0;
            while (j < pattern.length() && pattern.charAt(j) == str.charAt(i+j)) j++;
            if (j == pattern.length()) ans++;
        }

        return ans;
    }

    public static int boyerMooreAll(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();

        if (!check(n, m))  return -1;

        // ascii-code alphabet-version
        int[] last = new int[256];
        Arrays.fill(last, -1);
        for (int i = 0; i < m; i++) last[pattern.charAt(i)] = i;


        int ans = 0;
        int i = 0;

        do {
            int j = m - 1;
            while (j >= 0 && pattern.charAt(j) == str.charAt(i + j)) j--;
            if (j < 0) {
                ans++;
                i++;
            } else {
                int shift = j - last[str.charAt(i + j)];
                i += Math.max(1, shift);
            }
        } while (i <= n - m);

        return ans;
    }

    private static int [] failureFunctionKMP(String pattern) {
        int n = pattern.length();
        int function [] = new int[n];
        function[0] = 0;
        int i = 1, j = 0;
        while (i < n) {
            if (pattern.charAt(j) == pattern.charAt(i)) {
                function[i] = j+1;
                j++;
                i++;
            }
            else {
                if (j > 0) j = function[j-1];
                else {
                    function[i] = 0;
                    i++;
                }
            }
        }
        return function;
    }

    public static int KMP(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        if (!check(n, m))  return -1;
        int i = 0, j = 0;
        int failure[] = failureFunctionKMP(pattern);
        while (i < n) {
            if (pattern.charAt(j) == str.charAt(i)) {
                if (j == m-1) return i-m+1;
                i++;
                j++;
            }
            else {
                if (j > 0) j = failure[j-1];
                else i++;
            }
        }
        return -1;
    }
}
