import inter.Pushable;
import thing.mystical.Wind;
import thing.physical.Human;
import thing.physical.Location;
import thing.physical.PhysicalThing;
import thing.physical.Rope;

import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		Location startLocation = new Location("Стартовая локация", Location.MoveInLocation.GO, Location.MoveInLocation.JUMP);
		Location villageHouse = new Location("Беседка", Location.MoveInLocation.GO, Location.MoveInLocation.JUMP, startLocation);
		Location meadow = new Location("поле", Location.MoveInLocation.GO);
		Location house = new Location("дом", Location.MoveInLocation.GO, meadow, startLocation);
		Location downpipe = new Location("водосточная труба", Location.MoveInLocation.CLIMBUP);
		Location roof = new Location("крыша", Location.MoveInLocation.GO, downpipe);
		Location sky = new Location("небо", Location.MoveInLocation.FALL, Location.MoveInLocation.FLY, meadow , roof);
		house.addSublocation(roof);
		house.addSublocation(downpipe);
		
		List<Human> shorties = new ArrayList<>();
		
		shorties.add(new Human("Тюбик", 2, 1, house));
		shorties.add(new Human("Торобыжка", 3, 1, house));
		shorties.add(new Human("Незнайка", 5, 1, house));
		
		Human znayka = new Human("Знайка",5,5, startLocation);
		Rope rope = new Rope(20, startLocation, house, znayka);

		znayka.move(villageHouse,true);
		for(Human i : shorties) {
			i.drag(rope);
		}
		rope.process();
		
		znayka.move(roof);
		Wind wind = new Wind(10);
		Pushable power = new Pushable(){
			@Override
			public void push(PhysicalThing thing, Location location) {
				if(10 >= thing.getWeight()) {
					System.out.println(this.toString() + " потянула " + thing.toString());
					thing.move(location);
					thing.setBeCarried(false);
					if(thing instanceof Human) {
						((Human) thing).setState(Human.State.Weakened);
					}
				}
			}

			@Override
			public String toString() {
				return "Мистическая сила";
			}
		};
		
		wind.push(znayka, sky);
		wind.carry(znayka);
		
		znayka.moveInLocation(true);
		power.push(znayka, meadow);
		znayka.raiseAllHands();
		znayka.doStepAllLegs();
	}
}
