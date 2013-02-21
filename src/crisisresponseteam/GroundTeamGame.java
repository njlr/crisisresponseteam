package crisisresponseteam;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import uk.ac.ed.gamedevsoc.collisions.Collider;
import uk.ac.ed.gamedevsoc.collisions.Collision;
import uk.ac.ed.gamedevsoc.collisions.CollisionManager;

import crisisresponseteam.simulation.Ambulance;
import crisisresponseteam.simulation.CrisisManager;
import crisisresponseteam.simulation.GoreManager;
import crisisresponseteam.simulation.Map;
import crisisresponseteam.simulation.Pedestrian;
import crisisresponseteam.simulation.View;

public strictfp final class GroundTeamGame extends BasicGame {
	
	private final ComponentManager<Component> componentManager;
	
	private final CollisionManager collisionManager = new CollisionManager(64, 64);
	
	private final View view;
	
	public GroundTeamGame() {
		
		super("CRISIS RESPONSE TEAM");
		
		this.componentManager = new ComponentManager<Component>();
		
		// Gore Manager
		final GoreManager goreManager = new GoreManager(this.componentManager.takeId());
		
		this.componentManager.addComponent(goreManager);
		
		// Map
		final Map map = new Map(this.componentManager.takeId(), this.componentManager, goreManager, "assets/maps/City.tmx");
		
		this.componentManager.addComponent(map);
		
		// Ambulance
		final Ambulance ambulance = new Ambulance(this.componentManager.takeId(), new Vector2f(64f, 64f), map);
		
		this.componentManager.addComponent(ambulance);
		
		// View
		this.view = new View(this.componentManager.takeId(), map, ambulance);
		
		this.componentManager.addComponent(this.view);
		
		// Crisis Manager
		this.componentManager.addComponent(new CrisisManager(this.componentManager.takeId(), this.componentManager));
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
		
		this.collisionManager.updateCollisionPairs(this.componentManager.getComponents(Collider.class));
		
		for (final Collision i : this.collisionManager.getCollisions()) {
			
			if (i.getA() instanceof Ambulance) {
				
				if (i.getB() instanceof Pedestrian) {
					
					((Pedestrian) i.getB()).runOver();
				}
			}
			else if (i.getB() instanceof Ambulance) {
				
				if (i.getA() instanceof Pedestrian) {
					
					((Pedestrian) i.getA()).runOver();
				}
			}
		}
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		graphics.setAntiAlias(false);
		
		graphics.translate(-this.view.getX(), -this.view.getY());
		
		this.componentManager.render(gameContainer, graphics);
		
		graphics.translate(0f, 0f);
	}
}
