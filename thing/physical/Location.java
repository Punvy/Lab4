package thing.physical;

import java.util.ArrayList;
import java.util.List;

public class Location {
	
	public Location(String name, MoveInLocation move) {
		this.name = name;
		this.move = move;
	}
	public Location(String name, MoveInLocation move, MoveInLocation moveWithConditions) {
		this.name = name;
		this.move = move;
		this.moveWithConditions = moveWithConditions;
		haveMoveWithConditions = true;
	}
	
	public Location(String name, MoveInLocation move, Location ... nearLocations) {
		this(name,move);
		for(Location i : nearLocations) {
			i.addNearLocation(this);
		}
	}
	
	public Location(String name, MoveInLocation move, MoveInLocation moveWithConditions, Location ... nearLocations) {
		this(name,move,moveWithConditions);
		this.name = name;
		this.move = move;
	}

	private String name;
	private MoveInLocation move;
	private MoveInLocation moveWithConditions;
	private boolean haveMoveWithConditions = false;
	private List<Location> nearLocations = new ArrayList<Location>();
	private List<Location> sublocations  = new ArrayList<Location>();;
	private List<PhysicalThing> things  = new ArrayList<PhysicalThing>();
	private boolean sublocation = false; 
	private Location superlocation;
	
	
	public boolean isHaveMoveWithConditions() {
		return haveMoveWithConditions;
	}
	
	
	public List<Location> getSublocation() {
		return sublocations;
	}
	
	public void addSublocation(Location location) {
		if(!sublocations.contains(location)) 
		{
			sublocations.add(location);
			for(Location sublocation : this.getSublocation()) { location.addNearLocation(location); }
			location.sublocation = true;
			location.superlocation = this;
		}
	}
	public void addNearLocation(Location location) {
		if(!nearLocations.contains(location)) 
		{
			nearLocations.add(location);
			location.addNearLocation(this);
		}
	}

	public boolean isNearLocation(Location location) {
		return location.getSublocation().contains(this) ||
				location.getSuperlocation() != null && location.getSuperlocation().equals(this) ||
				this.getSuperlocation() != null && this.getSuperlocation().equals(location);
	}

	public void addThing(PhysicalThing ... things) {
		for(PhysicalThing i : things) {
			if(!this.things.contains(i)) {
				this.things.add(i);
				if(isSublocation()) {
					superlocation.addThing(things);
				}
			}
		}
	}
	public void deleteThing(PhysicalThing ... things) {
		for(PhysicalThing i : things) {
			if(this.things.contains(i)) {
				this.things.remove(i);
				if(isSublocation()) {
					superlocation.deleteThing(things);
				}
			}
		}
	}
	@Override
	public String toString() {
		return name;
	}
	public String getMove() {
		return move.toString();
	}

	public boolean isSublocation() {
		return sublocation;
	}

	public Location getSuperlocation() {
		return superlocation;
	}
	public MoveInLocation getMoveWithConditions() {
		return moveWithConditions;
	}

}