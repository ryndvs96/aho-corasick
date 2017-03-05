import java.util.*;
import java.io.*;

public class WordSearchSolve {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    // size of word list
    int k = scan.nextInt();
    // word list
    ArrayList<ArrayList<Character>> words = new ArrayList<>();
    for (int i = 0; i < k; i++) {
      String str = scan.next();
    
      ArrayList<Character> arr = new ArrayList<>();
      for (char c : str.toCharArray())
        arr.add(c);
      words.add(arr);
    }

    // alphabet
    HashSet<Character> sigma = new HashSet<>();

    // n by m matrix
    int n = scan.nextInt();
    int m = scan.nextInt();
    // characters
    Character[][] search = new Character[n][m];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        char c = scan.next().charAt(0);
        search[i][j] = c;
        sigma.add(c);
      }
    }

    // line seperator
    char sep = ' ';
    sigma.add(sep);

    // grid for iterator
    Grid<Character> ws = new Grid(search, sep);
    
    // aho-corasick object
    AC<Character> ac = new AC<Character>(words, sigma);
    //System.out.println(ac);

    ArrayList<Match> matches = ac.match(grid.iterator());

  }
}
