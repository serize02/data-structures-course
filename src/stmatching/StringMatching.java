package stmatching;

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

    public static int BoyerMoore(String str, String pattern) {

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

}
