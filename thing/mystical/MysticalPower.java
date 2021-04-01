package thing.mystical;

import inter.Pushable;
import thing.physical.Location;
import thing.physical.PhysicalThing;

public class MysticalPower implements Pushable{
	
	public MysticalPower(int power, String whatPower) {
		this.power = power;
		this.whatPower = whatPower;
	}
	
	String whatPower;
	int power;

	
	@Override
	public String toString() {
		return whatPower + " cила";
	}
	
	
	@Override
	public void push(PhysicalThing thing, Location location) {
		if(power >= thing.getWeight()) {
			System.out.println(this.toString() + " потянула " + thing.toString());
			thing.move(location);
			thing.setBeCarried(false);
		}
	}
}