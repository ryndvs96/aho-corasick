import java.util.*;

public class Grid<T> implements Iterable<T> {
  private T[][] grid;
  private T sep;
  private int w;
  private int h;
  public Grid(T[][] grid, T seperator) {
    this.grid = grid;
    this.h = grid.length;
    this.w = grid[0].length;
    this.sep = seperator;
  }

  public GridIterator<T> iterator() {
    GridIterator<T> it = new GridIterator<T>() {
      int dir = 0;
      GridIterator<T> it = null; 

      @Override
      public boolean hasNext() {
        return dir < 4;
      }

      @Override
      public T next() {
        T c = null;
        if (it == null) {
          it = iterator(dir);
        }
        if (!it.hasNext()) {
          it = null;
          dir++;
          c = sep;
        } else {
          c = it.next();
        }
        return c;
      }

      @Override
      public int geti() {
        return it.geti();
      }
      @Override
      public int getj() {
        return it.getj();
      }
      @Override
      public int direction() {
        return dir;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
    return it;
  
  
  }
  
  private GridIterator<T> iterator(int dir) {
    switch (dir) {
      case 0: return south();
      case 1: return reverse(south());
      case 2: return east();
      case 3: return reverse(east());
      default: return null;
    }
  }

  private GridIterator<T> south() {
    GridIterator<T> it = new GridIterator<T>() {
      private int i = -1;
      private int j = 0;

      @Override
      public boolean hasNext() {
        return !(j == w - 1 && i == h - 1);
      }

      @Override
      public T next() {
        i++;
        T c = null;
        if (i == h) {
          j++;
          i = -1;
          c = sep;
        } else {
          c = grid[i][j];
        }
        return c;
      }

      @Override
      public int geti() {
        return this.i;
      }
      @Override
      public int getj() {
        return this.j;
      }
      
      @Override 
      public int direction() {
        throw new UnsupportedOperationException();
      }
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
    return it;
  }

  private GridIterator<T> east() {
    GridIterator<T> it = new GridIterator<T>() {
      private int i = 0;
      private int j = -1;

      @Override
      public boolean hasNext() {
        return !(j == w - 1 && i == h - 1);
      }

      @Override
      public T next() {
        j++;
        T c = null;
        if (j == w) {
          i++;
          j = -1;
          c = sep;
        } else {
          c = grid[i][j];
        }
        return c;
      }

      @Override
      public int geti() {
        return this.i;
      }
      @Override
      public int getj() {
        return this.j;
      }
      
      @Override 
      public int direction() {
        throw new UnsupportedOperationException();
      }
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
    return it;
  }

  private GridIterator<T> reverse(GridIterator<T> it) {
    LinkedList<T> arr = new LinkedList<>();
    LinkedList<Integer> is = new LinkedList<>();
    LinkedList<Integer> js = new LinkedList<>();

    while (it.hasNext()) {
      arr.addFirst(it.next());
      is.addFirst(it.geti());
      js.addFirst(it.getj());
    }
    
    GridIterator<T> itrev = new GridIterator<T>() {
      private Iterator<T> arrIt = arr.iterator();
      private Iterator<Integer> isIt = is.iterator();
      private Iterator<Integer> jsIt = js.iterator();
      private T c = sep;
      private int i = 0;
      private int j = 0;

      @Override
      public boolean hasNext() {
        return arrIt.hasNext();
      }

      @Override
      public T next() {
        c = arrIt.next();
        i = isIt.next();
        j = jsIt.next();

        return c;
      }

      @Override
      public int geti() {
        return this.i;
      }
      @Override
      public int getj() {
        return this.j;
      }
      
      @Override 
      public int direction() {
        throw new UnsupportedOperationException();
      }
      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
    return itrev;
  }
}
