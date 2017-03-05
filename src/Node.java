import java.util.*;

public class Node {
  private int id;
  private Node failure;
  private String word;
  private HashMap<Character, Node> children;
  public Node(int id) {
    this.id = id;
    this.failure = null;
    this.word = null;
    this.children = new HashMap<>();
  }

  public int getID() {
    return this.id;
  }

  // if end state, contains word
  public boolean isWord() {
    return this.word != null;
  }
  public String getWord() {
    return this.word;
  }
  public void setWord(String word) {
    this.word = word;
  }

  // go tos (aka children)
  public boolean hasChild(char c) {
    return this.children.containsKey(c);
  }
  public Node getChild(char c) {
    return this.children.get(c);
  }
  public Set<Character> getChildren() {
    return this.children.keySet();
  }
  public void addChild(char c, Node n) {
    this.children.put(c, n);
  }

  // failure function node
  public Node getFailure() {
    return this.failure;
  }
  public void setFailure(Node n) {
    this.failure = n;
  }

  public String toString() {
    return "" + this.id;
  }
}
