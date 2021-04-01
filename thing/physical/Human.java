package thing.physical;
import inter.AbleBeAttracted;
import inter.Attachable;
import inter.Dragable;
import inter.Moveable;
import inter.Pushable;

public class Human extends PhysicalThing implements Dragable, Moveable,Pushable{
	
	public Human(String name, int power, int weight, Location location) {
		this.name = name;
		this.power = power;
		this.weight = weight;
		this.setLocation(location);
		location.addThing(this);
	}
	
	private String name;
	private int power;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int getPower() {
		return power;
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public void drag(AbleBeAttracted attractedThing) {
		attractedThing.beAttracted(this);
	}	
	@Override
	public void moveAttachThing(Location location) {
		for(Attachable i : getAttachThings()){
			i.move(location);
		}
	}
	@Override
	public void move(Location location) {
		System.out.printf("%s %s %s\n", this.toString(), getLocation().getMove(), getLocation().toString());
		changeLocation(location);
	}
	@Override
	public void bePushed(Location location) {
		System.out.print(this.toString() + "был оттолкнут в " + location.toString());
		changeLocation(location);
	}
	@Override
	public void push(PhysicalThing thing, Location location) {
		
	}
	
	
	public void moveInLocation() {
		if(getLocation().isHaveMoveWithConditions()) {
			if(getLocation().getMoveWithConditions().equals(MoveInLocation.FLY) && isBeCarried()) {
				System.out.printf("%s %s %s\n", this.toString(), getLocation().getMoveWithConditions(), getLocation().toString());
			}
			else {
				System.out.printf("%s %s %s\n", this.toString(), getLocation().getMove(), getLocation().toString());
			}
		}
		else {
			System.out.printf("%s %s %s\n", this.toString(), getLocation().getMove(), getLocation().toString());
		}
	}
	@Override
	public void changeLocation(Location location) {		
		if(location.isNearLocation(getLocation())) {
			throw new MoveOutOfLocationException();
		}
		getLocation().deleteThing(this);
		if(location.isSublocation() && (!getLocation().isSublocation()  || !getLocation().getSuperlocation().equals(location.getSuperlocation()) )) {
			System.out.println(this.toString() + " переместился в " + location.getSuperlocation().toString() );
			System.out.println(this.toString() + " переместился в " + location.toString());
			setLocation(location);
		}
		else if(location.getSublocation().isEmpty()) {
			System.out.println(this.toString() + " переместился в " + location.toString());
			setLocation(location);			
		}
		else {
			System.out.println(this.toString() + "переместился в " + location.toString());
			System.out.println(this.toString() + "переместился в " + location.getSublocation().get(1).toString());
			setLocation(location.getSublocation().get(1));
		}
		getLocation().addThing(this);
		if(!getAttachThings().isEmpty()) {
			moveAttachThing(location);
		}
	}
}


