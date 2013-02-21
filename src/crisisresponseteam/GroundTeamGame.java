package crisisresponseteam;

import java.util.Random;

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
import crisisresponseteam.simulation.Pedestrian;

public strictfp final class GroundTeamGame extends BasicGame {
	
	private final ComponentManager<Component> componentManager;
	
	private final CollisionManager collisionManager = new CollisionManager(64, 64);
	
	public GroundTeamGame() {
		
		super("CRISIS RESPONSE TEAM");
		
		this.componentManager = new ComponentManager<Component>();
		
		this.componentManager.addComponent(new Ambulance(this.componentManager.takeId(), new Vector2f(64f, 64f)));
		
		final Random random = new Random();
		
		for (int i = 0; i < 8; i++) {
			
			this.componentManager.addComponent(new Pedestrian(this.componentManager.takeId(), new Vector2f(random.nextInt(640), random.nextInt(480))));
		}
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
			
			for 
		}
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		this.componentManager.render(gameContainer, graphics);
	}
}
