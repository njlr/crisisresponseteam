package crisisresponseteam.simulation;

import nlib.components.BasicComponentRenderable;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.Session;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;

import crisisresponseteam.GroundTeamGame;
import crisisresponseteam.simulation.events.AmbulancePositionUpdatedEvent;

public class Ambulance extends BasicComponentRenderable {
	
	//private static final float DEGTORAD = 0.0174532925199432957f;
	private static final float RADTODEG = 57.295779513082320876f;
	
	private static final float FORCE = 100f;
	private static final float FORCE_REVERSE = 10f;
	
	private static final float TURN_RATE = 500f;
	private static final float TURN_SPEED_LIMITER = 5f;
	
	private final Body body;
	
	private float rotation;
	
	private Image image;
	
	private final Session<SessionConfig, PlayerInfo> session;
	
	public float getX() {
		
		return this.body.getPosition().x / GroundTeamGame.PHYSICS_SCALAR;
	}
	
	public float getY() {
		
		return this.body.getPosition().y / GroundTeamGame.PHYSICS_SCALAR; 
	}
	
	@Override
	public float getDepth() {
		// TODO Auto-generated method stub
		return Constants.DEPTH_AMBULANCE;
	}
	
	public Ambulance(final long id, final World world, final float x, final float y, final Session<SessionConfig, PlayerInfo> session) {
		
		super(id);
		
		this.session = session;
		
		final BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position = new Vec2(
				x * GroundTeamGame.PHYSICS_SCALAR, 
				y * GroundTeamGame.PHYSICS_SCALAR);
		
		bodyDef.linearDamping = 0.1f;
		bodyDef.angularDamping = 1f;
		
		this.body = world.createBody(bodyDef);
		
		final PolygonShape box = new PolygonShape();
		
		box.setAsBox(12f * GroundTeamGame.PHYSICS_SCALAR, 16f * GroundTeamGame.PHYSICS_SCALAR);
		
		final CircleShape circle = new CircleShape();
		
		circle.m_p.set(0f, 0f);
		circle.m_radius = 12f * GroundTeamGame.PHYSICS_SCALAR;
		
		final FixtureDef fixtureDef = new FixtureDef();
		
		fixtureDef.density = 100f;
		fixtureDef.shape = circle;
		fixtureDef.restitution = 0.1f;
		fixtureDef.friction = 0f;
		fixtureDef.userData = this;
	    
	    this.body.createFixture(fixtureDef);
	    
	    this.rotation = 0f;
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.image = new Image("assets/gfx/Ambulance.png");
		
		this.image.setCenterOfRotation(this.image.getWidth() / 2f, this.image.getHeight() / 2f);
		
		this.rotation = this.body.getAngle();
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_UP)) {
			
			up = true;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_DOWN)) {
			
			down = true;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
			
			left = true;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
			
			right = true;
		}
		
		if (up) {
			
			this.body.applyLinearImpulse(
					this.body.getWorldVector(new Vec2(0, 1)).mulLocal(FORCE), 
					this.body.getWorldCenter());
		}
		
		if (down) {
			
			this.body.applyLinearImpulse(
					this.body.getWorldVector(new Vec2(0, -1)).mulLocal(FORCE_REVERSE), 
					this.body.getWorldPoint(new Vec2(0, 48)));
		}
		
		float turnScalar = Math.max(0f, Math.min(this.body.getLinearVelocity().length() /  TURN_SPEED_LIMITER, 1f));
		
		if (left) {
			
			this.body.applyTorque(-TURN_RATE * turnScalar);
		}
		
		if (right) {
			
			this.body.applyTorque(TURN_RATE * turnScalar);
		}
		
		this.drift(0.9f);
		
		this.rotation = (this.rotation + this.body.getAngle()) / 2f;
		
		this.image.setRotation(this.rotation * RADTODEG);
		
		this.session.submit(new AmbulancePositionUpdatedEvent(this.getX(), this.getY()));
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawImage(
				this.image, 
				this.getX() - this.image.getWidth() / 2f, 
				this.getY() - this.image.getHeight() / 2f);
	}
	
	private void drift(final float amount) {
		
		final Vec2 forward = this.body.getWorldVector(new Vec2(0, 1));
		final Vec2 right = this.body.getWorldVector(new Vec2(1, 0));
		
	    final Vec2 forwardVelocity = forward.mulLocal(Vec2.dot(this.body.getLinearVelocity(), forward));
	    final Vec2 rightVelocity = right.mulLocal(Vec2.dot(this.body.getLinearVelocity(), right));
	    
	    this.body.setLinearVelocity(forwardVelocity.add(rightVelocity.mulLocal(amount)));
	}
}
