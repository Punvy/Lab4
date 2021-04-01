package thing.physical;
import inter.AbleBeAttracted;
import inter.Attachable;
import inter.Dragable;
import inter.Moveable;
import inter.Pushable;

import java.util.ArrayList;

public class Human extends PhysicalThing implements Dragable, Moveable,Pushable{
	
	public Human(String name, int power, int weight, Location location) {
		this.name = name;
		this.power = power;
		this.weight = weight;
		this.setLocation(location);
		location.addThing(this);
		legs.add(new Leg("Правая"));
		legs.add(new Leg("Левая"));
		hands.add(new Hand("Правая"));
		hands.add(new Hand("Левая"));
	}
	
	private String name;
	private int power;
	private ArrayList<Hand> hands = new ArrayList<>();
	private ArrayList<Leg>  legs = new ArrayList<>();

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	private State state;

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
	public void move(Location location, boolean wishMoveWithCondition) {
		moveInLocation(wishMoveWithCondition);
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
	public void raiseAllHands(){
		for(Hand hand : hands) {
			hand.raise();
		}
	}
	public void doStepAllLegs(){
		for (Leg leg : legs){
			try {
				leg.doStep();
			}catch (Exception e){
				System.err.println("Действие невозможно! СЛИШКОМ БОЛЬНО!");
			}
		}
	}
	public void moveInLocation(boolean wishMoveWithCondition) {
		if(getLocation().isHaveMoveWithConditions()) {
			if(getLocation().getMoveWithConditions().equals(Location.MoveInLocation.FLY) && isBeCarried() && wishMoveWithCondition) {
				System.out.printf("%s %s %s\n", this.toString(), getLocation().getMoveWithConditions(), getLocation().toString());
			}
			else if(getLocation().getMoveWithConditions().equals(Location.MoveInLocation.JUMP) && wishMoveWithCondition && legs.size() >= 2) {
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
	@Override
	public void move(Location location) {
		moveInLocation(false);
		changeLocation(location);
	}
	public static enum State {
		Normal("с легкостью"),
		Weakened("с трудом"),
		TerriblePain("с ужасной болью");

		@Override
		public String toString() {
			return adjectiveActionWithState;
		}

		String adjectiveActionWithState;

		private State(String adjectiveActionWithState) {
			this.adjectiveActionWithState = adjectiveActionWithState;
		}
	}
	public class Leg {

		String name;

		@Override
		public String toString() {
			return name;
		}

		public void doStep() throws PainActionException {
			if(Human.this.state.equals(State.TerriblePain)) throw new PainActionException();
			System.out.printf("%s %s %s %s%n",  Human.this , getState().toString(), "поднимает", this.toString());
		}

		private Leg(String whatLeg) {
			this.name = String.format("%s %s", whatLeg, "нога");
		}
	}
	public class Hand {
		String name;

		@Override
		public String toString() {
			return name;
		}

		public void raise(){
			System.out.printf("%s %s %s %s%n",  Human.this , getState().toString(), "делает шаг", this.toString());
		}

		private Hand(String whatHand){
			this.name = String.format("%s %s", whatHand, "нога");
		}
	}
}


