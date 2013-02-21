package crisisresponseteam.simulation;

import org.newdawn.slick.geom.Vector2f;

public strictfp final class CrisisSite {
	
	private final Vector2f position;
	private final String name;
	
	public Vector2f getPosition() {
		
		return this.position;
	}
	
	public String getName() {
		
		return this.name;
	}
	
	public CrisisSite(final Vector2f position, final String name) {
		
		super();
		
		this.position = position;
		this.name = name;
	}
}
