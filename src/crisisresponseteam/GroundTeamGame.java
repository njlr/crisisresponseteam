package crisisresponseteam;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.AABB;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import crisisresponseteam.simulation.Ambulance;
import crisisresponseteam.simulation.CrisisManager;
import crisisresponseteam.simulation.GoreManager;
import crisisresponseteam.simulation.Map;
import crisisresponseteam.simulation.View;

public strictfp final class GroundTeamGame extends BasicGame {
	
	private final World world;
	
	private final ComponentManager<Component> componentManager;
	
	private final View view;
	
	public GroundTeamGame() {
		
		super("CRISIS RESPONSE TEAM");
		
		// World
		this.world = new World(new Vec2(0f, 0f), false);
		
		// Components
		this.componentManager = new ComponentManager<Component>();
		
		// Gore Manager
		final GoreManager goreManager = new GoreManager(this.componentManager.takeId());
		
		this.componentManager.addComponent(goreManager);
		
		// Map
		final Map map = new Map(this.componentManager.takeId(), this.componentManager, goreManager, "assets/maps/City.tmx");
		
		this.componentManager.addComponent(map);
		
		// Ambulance
		final Ambulance ambulance = new Ambulance(this.componentManager.takeId(), world, 64f, 64f);
		
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
		
		this.world.step(1f / 30, 1, 1);
		
		this.componentManager.update(gameContainer, delta);
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		graphics.setAntiAlias(false);
		
		graphics.translate(-this.view.getX(), -this.view.getY());
		
		this.componentManager.render(gameContainer, graphics);
		
		graphics.translate(0f, 0f);
	}
}
