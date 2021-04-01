	package thing.physical;

public enum MoveInLocation {
	CLIMBUP("вскарабкивается по"),
	GO("идет по"),
	RUN("бежит по"),
	FLY("летит по"),
	FALL("упал в");
	
	String move;
	public String toString() {
		return move;
	}
	
	private MoveInLocation(String move) {
		this.move = move;
	}
}
