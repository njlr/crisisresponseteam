package crisisresponseteam.simulation;

import nlib.components.BasicComponentRenderable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.eventbus.Subscribe;

import crisisresponseteam.simulation.events.AmbulancePositionUpdatedEvent;
import crisisresponseteam.simulation.events.CrisisSiteSetEvent;
import crisisresponseteam.simulation.events.TimeLeftEvent;

public strictfp final class NavigatorMap extends BasicComponentRenderable {
	
	private final String ref;
	
	private TiledMap map;
	
	private Vector2f ambulancePosition;
	private CrisisSite currentCrisisSite;
	
	private int timeLeft;
	
	public int getTimeLeft() {
		return timeLeft / 1000;
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_MAP;
	}
	
	public float getWidth() {
		
		return this.map.getWidth() * this.map.getTileWidth();
	}
	
	public float getHeight() {
		
		return this.map.getHeight() * this.map.getTileHeight();
	}
	
	public int getTileWidth() {
		
		return this.map.getTileWidth();
	}
	
	public int getTileHeight() {
		
		return this.map.getTileHeight();
	}
	
	public Vector2f getAmbulancePosition() {
		
		return this.ambulancePosition;
	}
	
	public CrisisSite getCurrentCrisisSite() {
		
		return this.currentCrisisSite;
	}
	
	public NavigatorMap(final long id, final String ref) {
		
		super(id);
		
		this.ref = ref;
		
		this.ambulancePosition = new Vector2f(0f, 0f);
		this.currentCrisisSite = null;
		
		this.timeLeft = 0;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.map = new TiledMap(ref);
		
		this.timeLeft = CrisisManager.TIME_START;
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.timeLeft > 0) { 
			
			this.timeLeft -= delta;
			
			if (this.timeLeft < 0) {
				
				this.timeLeft = 0;
			}
		}
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		this.map.render(0, 0);
	}
	
	@Subscribe
	public void handleAmbulancePositionUpdatedEvent(final AmbulancePositionUpdatedEvent e) {
		
		this.ambulancePosition.set(e.getX(), e.getY());
	}
	
	@Subscribe
	public void handleCrisisSetEvent(final CrisisSiteSetEvent e) {
		
		this.currentCrisisSite = e.getCrisisSite();
	}
	
	@Subscribe
	public void handleTimeLeftEvent(final TimeLeftEvent e) {
		
		this.timeLeft = e.getTimeLeft();
	}
}
