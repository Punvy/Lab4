package thing.mystical;

import inter.Pushable;
import thing.physical.Location;
import thing.physical.PhysicalThing;

public class Wind implements Pushable{
	int power;	
	
	public Wind(int power) {
		this.power = power;
	}
	
	@Override
	public String toString() {
		return "Ветер с силой " + power + " у.е.";
	}
	
	@Override
	public void push(PhysicalThing thing, Location location) {
		if(power >= thing.getWeight()) {
			System.out.println(this.toString() + " сдувает " + thing.toString());
			thing.move(location);
		}
	}
	
	public void carry(PhysicalThing thing) {
		if(power >= thing.getWeight()) {
			System.out.println(this.toString() + " поддерживает в воздухе " + thing.toString());
			thing.setBeCarried(true);
		}
	}
}
