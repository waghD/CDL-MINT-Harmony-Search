package main;

public enum StreamCount {
	SINGLE, THREE, FIVE;
	
	public int getSize() {
		switch(this) {
		case FIVE:
			return 5;
		case THREE:
			return 3;
		case SINGLE:
		default:
			return 1;
		}
	}
}
