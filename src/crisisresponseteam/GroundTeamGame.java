package crisisresponseteam;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import crisisresponseteam.simulation.Ambulance;

public strictfp final class GroundTeamGame extends BasicGame {
	
	private final ComponentManager<Component> componentManager;
	
	public GroundTeamGame() {
		
		super("CRISIS RESPONSE TEAM");
		
		this.componentManager = new ComponentManager<Component>();
		
		this.componentManager.addComponent(new Ambulance(this.componentManager.takeId(), new Vector2f(64f, 64f)));
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		this.componentManager.render(gameContainer, graphics);
	}
}
