package crisisresponseteam.simulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import nlib.components.BasicComponentRenderable;
import nlib.components.Component;
import nlib.components.ComponentManager;

public strictfp final class Map extends BasicComponentRenderable {
	
	private final ComponentManager<Component> componentManager;
	private final GoreManager goreManager;
	private final String ref;
	
	private TiledMap map;
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_MAP;
	}
	
	public Map(final long id, final ComponentManager<Component> componentManager, final GoreManager goreManager, final String ref) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.goreManager = goreManager;
		
		this.ref = ref;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.map = new TiledMap(this.ref);
		
		for (int g = 0; g < this.map.getObjectGroupCount(); g++) {
			
			for (int o = 0; o < this.map.getObjectCount(g); o++) {
				
				final String type = this.map.getObjectType(g, o);
				
				if (type.equals("PedestrianSpawn")) {
					
					final float x = this.map.getObjectX(g, o);
					final float y = this.map.getObjectY(g, o);
					
					final int interval = Integer.parseInt(this.map.getObjectProperty(g, o, "Interval", "10000"));
					
					final PedestrianSpawn pedestrianSpawn = new PedestrianSpawn(
							this.componentManager.takeId(), 
							this.componentManager, 
							this.goreManager, 
							new Vector2f(x, y), 
							interval);
					
					this.componentManager.addComponent(pedestrianSpawn);
				}
				else if (type.equals("CrisisSite")) {
					
					final float x = this.map.getObjectX(g, o);
					final float y = this.map.getObjectY(g, o);
					
					final String name = this.map.getObjectProperty(g, o, "Name", "???");
					
					final CrisisSite crisisSite = new CrisisSite(
							this.componentManager.takeId(), 
							new Vector2f(x, y), 
							name);
					
					this.componentManager.addComponent(crisisSite);
				}
			}
		}
	}
	public static final float DEPTH_GORE = -1f;
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		this.map.render(0, 0);
	}
}
