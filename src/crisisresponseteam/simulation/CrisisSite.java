package crisisresponseteam.simulation;

import java.io.Serializable;

import org.newdawn.slick.geom.Vector2f;

public strictfp final class CrisisSite implements Serializable {
	
	private static final long serialVersionUID = 4566243679293369431L;
	
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
	
	@Override
	public boolean equals(final Object obj) {
		
		if (obj instanceof CrisisSite) {
			
			final CrisisSite crisisSite = (CrisisSite) obj;
			
			return this.name.equals(crisisSite.getName()) && this.position.equals(crisisSite.getPosition());
		}
		else {
			
			return false;
		}
	}
}
