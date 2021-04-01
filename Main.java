import java.util.ArrayList;

import java.util.List;

import thing.physical.Human;
import thing.physical.Rope;
import thing.mystical.MysticalPower;
import thing.mystical.Wind;
import thing.physical.*;

public class Main {
	public static void main(String[] args) {
		Location meadow = new Location("поле", MoveInLocation.GO);
		Location house = new Location("дом", MoveInLocation.GO, meadow);
		Location downpipe = new Location("водосточная труба", MoveInLocation.CLIMBUP);
		Location roof = new Location("крыша", MoveInLocation.GO, downpipe);
		Location sky = new Location("небо", MoveInLocation.FALL, MoveInLocation.FLY, meadow , roof);
		house.addSublocation(roof);
		house.addSublocation(downpipe);
		
		List<Human> shorties = new ArrayList<>();
		
		shorties.add(new Human("Тюбик", 2, 1, house));
		shorties.add(new Human("Торобыжка", 3, 1, house));
		shorties.add(new Human("Незнайка", 5, 1, house));
		
		Human znayka = new Human("Знайка",5,5, meadow);
		Rope rope = new Rope(20, meadow, house, znayka);
		
		for(Human i : shorties) {
			i.drag(rope);
		}
		rope.process();
		
		znayka.move(roof);
		Wind wind = new Wind(10);
		MysticalPower power = new MysticalPower(10,"Мистическая");
		
		wind.push(znayka, sky);
		wind.carry(znayka);
		
		znayka.moveInLocation();
		power.push(znayka, meadow);
	}
}
