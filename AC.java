import java.util.*;

/**
 * Aho-Corasick State Automaton
 * based on lecture notes: http://www.cs.uku.fi/~kilpelai/BSA05/lectures/slides04.pdf
 *
 * Start state is root:
 *  children are forward edges
 *  failures are back or cross edges
 */
public class AC {
  // node count, used for labeling
  private int nodes;
  // dictionary
  private ArrayList<String> words;
  // alphabet
  private char[] sigma;
  private Node root;

  public AC(ArrayList<String> words, char[] sigma) {
    this.nodes = 0;
    this.sigma = sigma;
    this.words = words;
    this.root = null;

    process();
  }

  // returns arraylist of matched strings
  public ArrayList<String> match(String str) {
    return match(str.toCharArray());
  }
  public ArrayList<String> match(char[] arr) {
    ArrayList<String> matched = new ArrayList<String>();
    Node curr = this.root;
    
    // step character by character through the automaton
    // add words when end states "isWord" are reached
    for (char c : arr) {
      while (!curr.hasChild(c)) { 
        curr = curr.getFailure(); 
      }

      curr = curr.getChild(c);
      if (curr.isWord()) {
        matched.add(curr.getWord());
      }
    }
    return matched;
  }

  // build the automaton
  private void process() {
    this.root = new Node(this.nodes);
    this.nodes++;
    
    /*
     * Phase 1, add words to key word trie
     */ 
    for (String word : words) {
      add(word);
    }
    
    /*
     * Pahse 2, complete gotos for the root
     */
    for (char c : this.sigma) {
      if (!this.root.hasChild(c)) {
        this.root.addChild(c, this.root);
      }
    }

    /*
     * Phase 3, build failure functions via BFS
     */
    buildFailures();
  }

  private void buildFailures() {
    // queue
    LinkedList<Node> q = new LinkedList<>();

    // base cases root children
    for (char c : this.root.getChildren()) {
      Node child = this.root.getChild(c);
      if (child == this.root) {
        continue;
      }
      child.setFailure(this.root);
      q.addLast(child);
    }

    // recursive bfs cases
    while (!q.isEmpty()) {
      Node p = q.removeFirst();
      for (char c : p.getChildren()) {
        Node child = p.getChild(c);
        q.addLast(child);

        // backtrack on failures if no child
        Node v = p.getFailure();
        while (!v.hasChild(c)) {
          v = v.getFailure();
        }

        child.setFailure(v.getChild(c));
      }
    }
  }

  // add a word to the trie
  private void add(String word) {
    char[] arr = word.toCharArray();
    int n = arr.length;

    // add word to keyword tree
    Node p = this.root;
    for (int i = 0; i < n; i++) {
      char c = arr[i];
      Node curr = null;
      if (p.hasChild(c)) {
        curr = p.getChild(c);
      } else {
        curr = new Node(this.nodes);
        this.nodes++;
        p.addChild(c, curr);
      }

      if (i == n - 1)
        curr.setWord(word);
      p = curr;
    }
  }

  // preorder traversal
  private StringBuffer str = null;
  public String toString() {
    str = new StringBuffer();
    preOrder(this.root, '*');
    String copy = str.toString(); 
    str = null;
    return copy;
  }

  private void preOrder(Node p, char c) {
    Set<Character> children = p.getChildren();
    int n = children.size();
    if (n == 0) {
      str.append(String.format("%s:%c:%s", p, c, p.getFailure()));
      return;
    }

    str.append(String.format("%s:%c:%s(", p, c, p.getFailure())); 
    int i = 0;
    for (char child : children) {  
      if (p.getChild(child) == this.root)
        continue;
      preOrder(p.getChild(child), child);
      if (i != n - 1)
        str.append(",");
      i++;
    }
    str.append(")");
  }
}
