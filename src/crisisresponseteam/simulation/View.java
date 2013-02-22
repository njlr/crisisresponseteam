package crisisresponseteam.simulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import nlib.components.BasicComponent;
import nlib.components.BasicComponentRenderable;

public strictfp final class View extends BasicComponentRenderable {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private final Map map;
	private final Ambulance ambulance;
	private final CrisisManager crisisManager;
	
	private float x;
	private float y;
	
	public float getX() {
		
		return this.x;
	}
	
	public float getY() {
		
		return this.y;
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_VIEW;
	}
	
	public View(final long id, final Map map, final Ambulance ambulance, final CrisisManager crisisManager) {
		
		super(id);
		
		this.map = map;
		this.ambulance = ambulance;
		
		this.crisisManager = crisisManager;
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
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawString("TIME LEFT: " + this.crisisManager.getTimeLeft(), this.getX() + 4, this.getY() + 4);
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
