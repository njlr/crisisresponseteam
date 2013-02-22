package crisisresponseteam.simulation.events;

public strictfp final class TimeLeftEvent extends Event {
	
	private static final long serialVersionUID = -5045218734622439799L;
	
	private final int timeLeft;
	
	public int getTimeLeft() {
		
		return this.timeLeft;
	}
	
	public TimeLeftEvent(final int timeLeft) {
		
		super();
		
		this.timeLeft = timeLeft;
	}
}
