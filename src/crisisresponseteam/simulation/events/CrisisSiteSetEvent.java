package crisisresponseteam.simulation.events;

import crisisresponseteam.simulation.CrisisSite;

public strictfp final class CrisisSiteSetEvent extends Event {
	
	private static final long serialVersionUID = 2514006052336772359L;
	
	private final CrisisSite crisisSite;
	
	public CrisisSite getCrisisSite() {
		
		return this.crisisSite;
	}
	
	public CrisisSiteSetEvent(final CrisisSite crisisSite) {
		
		super();
		
		this.crisisSite = crisisSite;
	}
}
