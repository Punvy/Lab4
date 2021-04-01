package inter;
import thing.physical.Location;

public interface Dragable {
	void drag(AbleBeAttracted attractedThing);
	int getPower();
	Location getLocation();
}
