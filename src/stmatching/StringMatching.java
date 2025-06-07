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

        int ans = 0;

        for (int i =0 ; i < str.length(); i++){
            int j = 0;
            while (j < pattern.length() && pattern.charAt(j) == str.charAt(i+j)) j++;
            if (j == pattern.length()) ans++;
        }

        return ans;
    }

    public static int boyerMoore(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();

        if (!check(n, m))  return -1;

        // ascii-code
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


}
