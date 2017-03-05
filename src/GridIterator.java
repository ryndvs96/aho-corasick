import java.util.Iterator;

public interface GridIterator<E> extends Iterator<E> {
  public abstract int geti();
  public abstract int getj();
  public abstract int direction();
}
