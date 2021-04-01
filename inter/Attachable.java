package inter;
import thing.physical.Location;
import thing.physical.PhysicalThing;

public interface Attachable {
	void attach(PhysicalThing thing);

	void move(Location location);
}
