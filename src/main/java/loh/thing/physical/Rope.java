package thing.physical;

import java.util.*;

import exception.MoveOutOfLocationException;
import inter.AbleBeAttracted;
import inter.Attachable;
import inter.Dragable;

public class Rope extends PhysicalThing implements AbleBeAttracted, Attachable {
	
	public Rope(int length, Location attachEnd, Location justEnd) { 
		this.length = length;
		this.weight = (int) (length * 0.25);
		this.attachEnd = attachEnd;
		this.justEnd = justEnd;
		this.setLocation(attachEnd);
		attachEnd.addThing(this);
		previousattachEnd = attachEnd;
	}
	
	public Rope(int length, Location firsEnd, Location secondEnd, PhysicalThing thing ) { 
		this(length,firsEnd,secondEnd);
		attach(thing);
	}
	
	private int length;
	private int weight;
	private int appliedPower;
	private List<Dragable> dragThings = new ArrayList<Dragable>();
	private PhysicalThing attachThing;
	private Location attachEnd;
	private Location justEnd;
	private Location previousattachEnd;
	private Location dragThingsLocation;
	
	@Override
	public void beAttracted(Dragable dragThing) {
		dragThings.add(dragThing);
		appliedPower += dragThing.getPower();
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
				
		if(obj == null || getClass() != obj.getClass()) return false;

		return false;
	}
	@Override
	public String toString() {
		return "Веревка длиной " + length + " у.е. прикрепленная на " + attachThing.toString();
	}
	public void process() {
		for(Dragable thing : dragThings) { System.out.printf("%s, ", thing.toString()); }
		if(appliedPower>=weight) {
	        System.out.printf("подтягивают %s.\n", this.toString());
	        changeLocation(justEnd);
	        attachThing.changeLocation(justEnd);
	    }
		else {
			System.out.printf("не смогил подтянуть %s.", this.toString());
		}
		dragThings.clear();
	}	
	@Override
	public void bePushed(Location location) {
		System.out.print(this.toString() + " была оттолкнута в локацию " + location.toString());
		changeLocation(location);
	}
	@Override
	public void attach(PhysicalThing thing) {
		this.attachThing = thing;
		this.weight += thing.getWeight();
		thing.beAttach(this);
	}
	@Override
	public void move(Location location) {	
		System.out.println("Привязанный конец " + this.toString() + " перемещается в " + location.toString());
		if(!attachEnd.equals(justEnd)) {
			justEnd = previousattachEnd;
			System.out.println("Второй конец " + this.toString() + " перемещается в " + justEnd.toString());
		}
		attachEnd = location;
		previousattachEnd = attachEnd;
	}
	@Override
	public void changeLocation(Location location) {
		if(location.isNearLocation(getLocation())) {
			throw new MoveOutOfLocationException();
		}
		attachEnd.deleteThing(this);
		if(location.isSublocation() && (!getLocation().isSublocation() ||!getLocation().getSuperlocation().equals(location.getSuperlocation()))) {
			System.out.println(this.toString() + " перемещается в " + location.getSuperlocation().toString() );
			System.out.println(this.toString() + " перемещается в " + location.toString());
			attachEnd = location;
			justEnd = location;
			previousattachEnd = location;
			setLocation(location);
		}
		else if(location.getSublocation().isEmpty()) {
			System.out.println(this.toString() + "перемещается в " + location.toString());
			attachEnd = location;
			justEnd = location;
			previousattachEnd = location;
			setLocation(location);			
		}
		else {
			System.out.println(this.toString() + " перемещается в " + location.toString());
			System.out.println(this.toString() + " перемещается в " + location.getSublocation().get(1).toString());
			attachEnd = location.getSublocation().get(1);
			justEnd = location.getSublocation().get(1);
			previousattachEnd = location.getSublocation().get(1);
			setLocation(location.getSublocation().get(1));
		}
		attachEnd.addThing(this);
	}

}
