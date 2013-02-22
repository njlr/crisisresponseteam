package crisisresponseteam.simulation.events;

public strictfp final class AmbulancePositionUpdatedEvent extends Event {
	
	private static final long serialVersionUID = 3239601997276981432L;
	
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
	
	@Override
	public String toString() {
		
		return "AmbulancePositionUpdateEvent[x: " + this.x + ", y: " + this.y + "]";
	}
}
