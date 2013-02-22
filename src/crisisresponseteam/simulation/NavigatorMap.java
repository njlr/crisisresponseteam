package crisisresponseteam.simulation;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;

import com.google.common.eventbus.Subscribe;

import crisisresponseteam.simulation.events.AmbulancePositionUpdatedEvent;

import nlib.components.BasicComponentRenderable;

public strictfp final class NavigatorMap extends BasicComponentRenderable {
	
	private final String ref;
	
	private TiledMap map;
	
	private Vector2f ambulancePosition;
	private CrisisSite currentCrisisSite;
	
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
	
	public NavigatorMap(final long id, final String ref) {
		
		super(id);
		
		this.ref = ref;
		
		this.ambulancePosition = new Vector2f(0f, 0f);
		this.currentCrisisSite = null;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.map = new TiledMap(ref);
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		this.map.render(0, 0);
		
		graphics.setColor(Color.blue);
		
		graphics.fillOval(
				this.ambulancePosition.getX() - 4f, 
				this.ambulancePosition.getY() - 4f, 
				16f, 
				16f);
		
		if (this.currentCrisisSite != null) {
			
			graphics.setColor(Color.red);
			
			graphics.fillOval(
					this.currentCrisisSite.getPosition().getX() - 4f, 
					this.currentCrisisSite.getPosition().getY() - 4f, 
					16f, 
					16f);
		}
		
		graphics.scale(0f, 0f);
	}
	
	@Subscribe
	public void handleAmbulancePositionUpdatedEvent(AmbulancePositionUpdatedEvent e) {
		
		this.ambulancePosition.set(e.getX(), e.getY());
	}
}
