package crisisresponseteam.simulation.events;

public strictfp final class AmbulancePositionUpdatedEvent {
	
	private final float x;
	private final float y;
	
	public float getX() {
		
		return this.x;
	}
	
	public float getY() {
		
		return this.y;
	}
	
	public AmbulancePositionUpdatedEvent(final float x, final float y) {
		
		super();
		
		this.x = x;
		this.y = y;
	}
}
