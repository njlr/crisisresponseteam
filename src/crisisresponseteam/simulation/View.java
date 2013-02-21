package crisisresponseteam.simulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import nlib.components.BasicComponent;

public strictfp final class View extends BasicComponent {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private final Map map;
	private final Ambulance ambulance;
	
	private float x;
	private float y;
	
	public float getX() {
		
		return this.x;
	}
	
	public float getY() {
		
		return this.y;
	}
	
	public View(final long id, final Map map, final Ambulance ambulance) {
		
		super(id);
		
		this.map = map;
		this.ambulance = ambulance;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.trackAmbulance(); 
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.trackAmbulance();
	}
	
	private void trackAmbulance() {
		
		this.x = this.ambulance.getX() - WIDTH / 2f;
		this.y = this.ambulance.getY() - HEIGHT / 2f;
		
		if (this.x < 0f) {
			
			this.x = 0f;
		}
		
		if (this.y < 0f) {
			
			this.y = 0f;
		}
		
		if (this.x + WIDTH > this.map.getWidth()) {
			
			this.x = this.map.getWidth() - WIDTH;
		}
		
		if (this.y + HEIGHT > this.map.getHeight()) {
			
			this.y = this.map.getHeight() - HEIGHT;
		}
	}
}
