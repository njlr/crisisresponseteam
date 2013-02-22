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
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.Session;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;

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
		
		// Gore Manager
		final GoreManager goreManager = new GoreManager(this.componentManager.takeId());
		
		this.componentManager.addComponent(goreManager);
		
		this.world.setContactListener(
				new ContactListener() {
					
					@Override
					public void preSolve(Contact contact, Manifold oldManifold) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void postSolve(Contact contact, ContactImpulse impulse) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void endContact(Contact contact) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void beginContact(Contact contact) {
						
						boolean ap = contact.getFixtureA().getUserData() instanceof Pedestrian;
						boolean bp = contact.getFixtureB().getUserData() instanceof Pedestrian;
						
						boolean aa = contact.getFixtureA().getUserData() instanceof Ambulance;
						boolean ba = contact.getFixtureB().getUserData() instanceof Ambulance;
						
						if ((ap && ba) || (bp && aa)) {
							
							int x = (int) (contact.getFixtureA().getAABB().getCenter().x / PHYSICS_SCALAR);
							int y = (int) (contact.getFixtureA().getAABB().getCenter().y / PHYSICS_SCALAR);
							
							goreManager.emit(x, y);
						}
					}
				});
		
		// Map
		final Map map = new Map(this.componentManager.takeId(), world, this.componentManager, goreManager, "assets/maps/City1.tmx");
		
		this.componentManager.addComponent(map);
		
		// Ambulance
		final Ambulance ambulance = new Ambulance(this.componentManager.takeId(), this.world, 128f, 128f, this.session);
		
		this.componentManager.addComponent(ambulance);
		
		this.componentManager.addComponent(new MapTop(this.componentManager.takeId(), map));
		
		// Crisis Manager
		final CrisisManager crisisManager = new CrisisManager(this.componentManager.takeId(), this.componentManager, this.session);
		
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
