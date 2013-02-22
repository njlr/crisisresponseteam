package crisisresponseteam;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import crisisresponseteam.simulation.NavigatorMap;
import crisisresponseteam.simulation.NavigatorView;

public strictfp final class NavigatorGame extends BasicGame {
	
	private final ComponentManager<Component> componentManager;
	
	private final NavigatorView navigatorView;
	
	public NavigatorGame() {
		
		super("CRISIS RESPONSE TEAM");
		
		this.componentManager = new ComponentManager<Component>();
		
		final NavigatorMap navigatorMap = new NavigatorMap(this.componentManager.takeId(), "assets/maps/City1.tmx");
		
		this.componentManager.addComponent(navigatorMap);
		
		this.navigatorView = new NavigatorView(this.componentManager.takeId(), navigatorMap);
		
		this.componentManager.addComponent(this.navigatorView);
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
		
		graphics.translate(-this.navigatorView.getX(), -this.navigatorView.getY());
		
		this.componentManager.render(gameContainer, graphics);
		
		graphics.translate(0f, 0f);
	}
}
