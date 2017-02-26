import java.util.*;
import java.io.*;

public class Solution {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    int k = scan.nextInt();
    ArrayList<String> words = new ArrayList<String>();
    for (int i = 0; i < k; i++) {
      words.add(scan.next());
    }

    // alphabet
    HashSet<Character> set = new HashSet<>();
    int n = scan.nextInt();
    char[][] search = new char[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        search[i][j] = scan.next().charAt(0);
        set.add(search[i][j]);
      }
    }
    set.add(' ');
    char[] sigma = new char[set.size()];
    int j = 0;
    for (char c : set) {
      sigma[j] = c;
      j++;
    }

    AC ac = new AC(words, sigma);
    System.out.println(ac);
    ArrayList<String> matches = new ArrayList<>();

    String[] strs = new String[8];
    strs[0] = scan(search, 1, 0, 0, 0);
    strs[1] = new StringBuilder(strs[0]).reverse().toString();
    strs[2] = scan(search, 0, 1, 0, 0);
    strs[3] = new StringBuilder(strs[2]).reverse().toString();
    strs[4] = scan(search, -1, 1, 0, 0);
    strs[5] = new StringBuilder(strs[4]).reverse().toString();
    strs[6] = scan(search, 1, 1, 0, n - 1);
    strs[7] = new StringBuilder(strs[6]).reverse().toString();

    for (String str : strs) {
      ArrayList<String> m = ac.match(str);
      matches.addAll(m);
    } 

    System.out.println("matched:");
    for (String str : matches) {
      System.out.println(str);
    }
  }

  // parse word search in 8 cardinal directions
  public static String scan(char[][] grid, int ioff, int joff, int is, int js) {
    int scanned = 0;
    int n = grid.length * grid[0].length;

    StringBuffer str = new StringBuffer();
    int i = is;
    int j = js;
    while (scanned < n) {
      str.append(grid[i][j]);

      i += ioff;
      j += joff;
      if (i < 0) {
        if (ioff == -1 && joff == 1) {
          i = j;
          j = 0;
          if (i >= grid.length) {
            i--;
            j++;
          }
          str.append(' ');
        }
      } else if (i >= grid.length) {
        if (ioff == 1 && joff == 1) {
          i = grid.length - j + 1;
          j = 0;
          str.append(' ');
        } else if (ioff == 1 && joff == 0) {
          j++;
          i = 0;
          str.append(' ');
        }
      } else if (j >= grid[0].length) {
        if (ioff == 0 && joff == 1) {
          i++;
          j = 0;
          str.append(' ');
        } else if (ioff == -1 && joff == 1) {
          j = i + 2;
          i = grid.length - 1;
          str.append(' ');
        } else if (ioff == 1 && joff == 1) {
          j = grid.length - i - 1;
          i = 0;
          str.append(' ');
        }
      }

      scanned++;
    }
    return str.toString();
  }
}
