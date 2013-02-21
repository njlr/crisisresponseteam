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
	private final String ref;
	
	private TiledMap map;
	
	public Map(final long id, final ComponentManager<Component> componentManager, final String ref) {
		
		super(id);
		
		this.componentManager = componentManager;
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
							new Vector2f(x, y), 
							interval);
					
					this.componentManager.addComponent(pedestrianSpawn);
					
					System.out.println("SPAWN");
				}
				else if (type.equals("CrisisSite")) {
					
					
				}
			}
		}
	}
	
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
