package crisisresponseteam.simulation;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import crisisresponseteam.simulation.events.CrisisSiteSetEvent;
import crisisresponseteam.simulation.events.TimeLeftEvent;

import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.Session;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;

import nlib.components.BasicComponentRenderable;
import nlib.components.Component;
import nlib.components.ComponentManager;

public strictfp final class CrisisManager extends BasicComponentRenderable {
	
	public static final int TIME_START = 90 * 1000;
	private static final int TIME_ADDED_PER_CRISIS = 15 * 1000;
	
	private static final float CRISIS_SITE_RADIUS = 32f;
	
	private final ComponentManager<Component> componentManager;
	private final Map map;
	private final Session<SessionConfig, PlayerInfo> session;
	
	private final Random random;
	
	private int timeLeft;
	
	private CrisisSite currentCrisisSite;
	
	private Image image;
	
	public CrisisSite getCurrentCrisisSite() {
		
		return this.currentCrisisSite;
	}
	
	public int getTimeLeft() {
		
		return timeLeft / 1000;
	}
	
	public CrisisManager(final long id, final ComponentManager<Component> componentManager, final Map map, final Session<SessionConfig, PlayerInfo> session) {
		
		super(id);
		
		this.componentManager = componentManager;
		this.map = map;
		this.session = session;
		
		this.random = new Random();
		
		this.currentCrisisSite = null;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.timeLeft = TIME_START;
		
		this.image = new Image("assets/gfx/CrisisZone.png");
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (this.timeLeft > 0) {
			
			if (this.currentCrisisSite == null) {
				
				this.currentCrisisSite = getNextCrisisSite();
				
				this.session.submit(new CrisisSiteSetEvent(this.currentCrisisSite));
				this.session.submit(new TimeLeftEvent(this.timeLeft));
			}
			
			this.timeLeft -= delta;
			
			if (this.timeLeft <= 0) {
				
				// TODO: Game Over!
			}
			else {
				
				if (this.currentCrisisSite != null) {
					
					final List<Ambulance> ambulances = this.componentManager.getComponents(Ambulance.class);
					
					for (final Ambulance i : ambulances) {
						
						if (this.currentCrisisSite.getPosition().distanceSquared(new Vector2f(i.getX(), i.getY())) < CRISIS_SITE_RADIUS * CRISIS_SITE_RADIUS) {
							
							this.currentCrisisSite = this.getNextCrisisSite();
							
							this.timeLeft += TIME_ADDED_PER_CRISIS;
							
							this.session.submit(new CrisisSiteSetEvent(this.currentCrisisSite));
							this.session.submit(new TimeLeftEvent(this.timeLeft));
						}
					}
				}
			}
		}
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		if (this.currentCrisisSite != null) {
			
			graphics.drawImage(
					this.image, 
					this.currentCrisisSite.getPosition().getX() - this.image.getWidth() / 2f, 
					this.currentCrisisSite.getPosition().getY() - this.image.getHeight() / 2f);
		}
	}
	
	public void crisisResolved(final CrisisSite crisisSite) {
		
		if (this.currentCrisisSite == crisisSite) {
			
			this.currentCrisisSite = getNextCrisisSite();
			
			this.timeLeft += TIME_ADDED_PER_CRISIS;
		}
	}
	
	private CrisisSite getNextCrisisSite() {
		
		final List<CrisisSite> list = this.map.getCrisisSites();
		
		if (list.isEmpty()) {
			
			return null;
		}
		else {
			
			System.out.println(list.get(this.random.nextInt(list.size())));
			
			return list.get(this.random.nextInt(list.size()));
		}
	}
}
