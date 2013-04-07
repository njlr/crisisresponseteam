package crisisresponseteam;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import uk.ac.ed.eusa.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.Session;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.SessionConfig;

import crisisresponseteam.simulation.Ambulance;
import crisisresponseteam.simulation.CrisisManager;
import crisisresponseteam.simulation.GoreManager;
import crisisresponseteam.simulation.Map;
import crisisresponseteam.simulation.MapTop;
import crisisresponseteam.simulation.Pedestrian;
import crisisresponseteam.simulation.View;
import crisisresponseteam.simulation.events.CommandLaunch;

public strictfp final class GroundTeamGame extends BasicGame {
	
	private final Session<SessionConfig, PlayerInfo> session;
	private final World world;
	
	public static final float PHYSICS_SCALAR = 0.1f;
	
	private final ComponentManager<Component> componentManager;
	
	private final View view;
	
	public GroundTeamGame(final Session<SessionConfig, PlayerInfo> session) {
		
		super("CRISIS RESPONSE TEAM");
		
		this.session = session;
		
		// World
		this.world = new World(new Vec2(0f, 0f), false);
		
		// Components
		this.componentManager = new ComponentManager<Component>();
		
		
		
		// Map
		final Map map = new Map(this.componentManager.takeId(), world, this.componentManager, "assets/maps/City1.tmx");
		
		this.componentManager.addComponent(map);
		
		// Ambulance
		final Ambulance ambulance = new Ambulance(this.componentManager.takeId(), this.world, 432f, 96f, this.session);
		
		this.componentManager.addComponent(ambulance);
		
		this.componentManager.addComponent(new MapTop(this.componentManager.takeId(), map));
		
		// Crisis Manager
		final CrisisManager crisisManager = new CrisisManager(this.componentManager.takeId(), this.componentManager, map, this.session);
		
		this.componentManager.addComponent(crisisManager);
		
		// View
		this.view = new View(this.componentManager.takeId(), map, ambulance, crisisManager);
		
		this.componentManager.addComponent(this.view);
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		this.session.submit(new CommandLaunch());
		this.componentManager.init(gameContainer);
		
	}

	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
		
		this.world.step(0.1f, 10, 10);
	}
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {

		
		graphics.setAntiAlias(false);
		
		graphics.translate(-this.view.getX(), -this.view.getY());
		
		this.componentManager.render(gameContainer, graphics);
		
		graphics.translate(0f, 0f);
	}
}
