package inter;
import thing.physical.Location;

public interface Moveable {
	void move(Location location);
	void moveAttachThing(Location location);
}
