import java.util.*;

/**
 * Aho-Corasick State Automaton
 * based on lecture notes: http://www.cs.uku.fi/~kilpelai/BSA05/lectures/slides04.pdf
 *
 * Start state is root:
 *  children are forward edges
 *  failures are back or cross edges
 */
public class AC<T> {
  // node count, used for labeling
  private int nodes;
  // dictionary
  private ArrayList<ArrayList<T>> words;
  // alphabet
  private Set<T> sigma;
  private Node<T> root;

  public AC(ArrayList<ArrayList<T>> words, Set<T> sigma) {
    this.nodes = 0;
    this.sigma = sigma;
    this.words = words;
    this.root = null;

    process();
  }

  // returns arraylist of Matches
  public ArrayList<Match> match(GridIterator<T> it) {
    ArrayList<Match> matches = new ArrayList<>();
    Node<T> curr = this.root;
    
    // step character by character through the automaton
    // add words when end states "isWord" are reached
    while (it.hasNext()) {
      T c = it.next();
      
      while (!curr.hasChild(c)) { 
        curr = curr.getFailure(); 
      }

      curr = curr.getChild(c);
      if (curr.isWord()) {
        List<T> word = curr.getWord();
        matches.add(new Match(word, String.format("(%d, %d)", it.geti(), it.getj())));
      }
    }
    return matches;
  }

  // build the automaton
  private void process() {
    this.root = new Node<T>(this.nodes);
    this.nodes++;
    
    /*
     * Phase 1, add words to key word trie
     */ 
    for (ArrayList<T> word : words) {
      add(word);
    }
    
    /*
     * Pahse 2, complete gotos for the root
     */
    for (T c : this.sigma) {
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
    LinkedList<Node<T>> q = new LinkedList<>();

    // base cases root children
    for (T c : this.root.getChildren()) {
      Node<T> child = this.root.getChild(c);
      if (child == this.root) {
        continue;
      }
      child.setFailure(this.root);
      q.addLast(child);
    }

    // recursive bfs cases
    while (!q.isEmpty()) {
      Node<T> p = q.removeFirst();
      for (T c : p.getChildren()) {
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
  private void add(ArrayList<T> word) {
    int n = word.size();

    // add word to keyword tree
    Node<T> p = this.root;
    for (int i = 0; i < n; i++) {
      T c = word.get(i);
      Node<T> curr = null;
      if (p.hasChild(c)) {
        curr = p.getChild(c);
      } else {
        curr = new Node<T>(this.nodes);
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
