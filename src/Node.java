import java.util.*;

public class Node<T> {
  private int id;
  private Node failure;
  private List<T> word;
  private HashMap<T, Node> children;
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
  public List<T> getWord() {
    return this.word;
  }
  public void setWord(List<T> word) {
    this.word = word;
  }

  // go tos (aka children)
  public boolean hasChild(T c) {
    return this.children.containsKey(c);
  }
  public Node<T> getChild(T c) {
    return this.children.get(c);
  }
  public Set<T> getChildren() {
    return this.children.keySet();
  }
  public void addChild(T c, Node<T> n) {
    this.children.put(c, n);
  }

  // failure function node
  public Node<T> getFailure() {
    return this.failure;
  }
  public void setFailure(Node<T> n) {
    this.failure = n;
  }

  public String toString() {
    return "" + this.id;
  }
}
