package thing.physical;

import java.util.ArrayList;
import java.util.List;
import inter.*;


public abstract class PhysicalThing {
	int weight;
	private Location location;
	private List<Attachable> attachThings = new ArrayList<Attachable>();
	private boolean beCarried;
	
	public int getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return "Предмет весом " + weight + " у.е.";
	}
	
	public abstract void bePushed(Location location);
	
	public void beAttach(Attachable ...attachThings) {
		for (Attachable i : attachThings) {
			this.getAttachThings().add(i);
		}
	}
	public abstract void changeLocation(Location location);
	
	public boolean isBeCarried() {
		return beCarried;
	}
	public void setBeCarried(boolean beCarried) {
		this.beCarried = beCarried;
	}
	public List<Attachable> getAttachThings() {
		return attachThings;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Location getLocation() {
		return location;
	}
	public abstract void move(Location location);
}
