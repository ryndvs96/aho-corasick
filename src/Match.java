import java.util.List;
public class Match<T> {
  public List<T> str;
  public String loc;
  public Match(List<T> str, String loc) {
    this.str = str;
    this.loc = loc;
  }
  public String toString() {
    StringBuffer b = new StringBuffer();
    for (T c : this.str) {
      b.append(c.toString());
    }
    b.append(String.format(": at location %s", this.loc));
    return b.toString();
  }
}
