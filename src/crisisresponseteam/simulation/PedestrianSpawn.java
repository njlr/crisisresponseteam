package crisisresponseteam.simulation;

import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponent;
import nlib.components.Component;
import nlib.components.ComponentManager;

public strictfp final class PedestrianSpawn extends BasicComponent {
	
	private final ComponentManager<Component> componentManager;
	private final World world;
	
	private final Vector2f position;
	
	private final int interval;
	
	private int timeTillSpawn;
	
	public PedestrianSpawn(final long id, final ComponentManager<Component> componentManager, final World world, final Vector2f position, final int interval) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.world = world;
		
		this.position = position;
		
		this.interval = interval;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.timeTillSpawn = 0;
	}
	
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.timeTillSpawn -= delta;
		
		while (this.timeTillSpawn <= 0) {
			
			this.timeTillSpawn += this.interval;
			
			this.spawn();
		}
	}
	
	private void spawn() {
		
		final Pedestrian pedestrian = new Pedestrian(
				this.componentManager.takeId(), 
				this.world, 
				this.position.getX(),
				this.position.getY());
		
		this.componentManager.addComponent(pedestrian);
	}
}
