package inter;
import thing.physical.Location;
import thing.physical.PhysicalThing;

public interface Pushable {
	void push(PhysicalThing thing, Location location);
}
