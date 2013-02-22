package crisisresponseteam;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import crisisresponseteam.simulation.MapDisplay;

public strictfp final class NavigatorGame extends BasicGame {
	
	private final ComponentManager<Component> componentManager;
	
	public NavigatorGame() {
		
		super("CRISIS RESPONSE TEAM");
		
		this.componentManager = new ComponentManager<Component>();
		
		this.componentManager.addComponent(new MapDisplay(this.componentManager.takeId(), "assets/maps/City1.tmx"));
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void update(final GameContainer gameContainer, int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		this.componentManager.render(gameContainer, graphics);
	}
}
