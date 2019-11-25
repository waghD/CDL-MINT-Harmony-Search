package main;

public class Boundaries<L,U> {

	  private final L lower;
	  private final U upper;

	  public Boundaries(L lower, U upper) {
	    this.lower = lower;
	    this.upper = upper;
	  }

	  public L getLeft() { return lower; }
	  public U getRight() { return upper; }

	  @Override
	  public int hashCode() { return lower.hashCode() ^ upper.hashCode(); }

	  @Override
	  public boolean equals(Object o) {
	    if (!(o instanceof Boundaries)) return false;
	    Boundaries boundaries = (Boundaries) o;
	    return this.lower.equals(boundaries.getLeft()) &&
	           this.upper.equals(boundaries.getRight());
	  }

	}