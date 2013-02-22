package crisisresponseteam.simulation;

import java.util.Random;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentRenderable;
import nlib.physics.steering.Steering;

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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import crisisresponseteam.GroundTeamGame;

public strictfp final class Pedestrian extends BasicComponentRenderable implements ComponentRenderable {
	
	private static final float RADTODEG = 57.295779513082320876f;
	
	private static final float WANDER_CIRCLE_RADIUS = 256f;
	private static final float WANDER_CIRCLE_DISTANCE = 16f;
	private static final float WANDER_CIRCLE_CHANGE = 5f;
	
	private final Random random;
	
	private Image image;
	
	private float wander;
	
	private boolean isAlive;
	
	private final Body body;
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_PEDESTRIAN;
	}
	
	public float getX() {
		
		return this.body.getPosition().x / GroundTeamGame.PHYSICS_SCALAR;
	}
	
	public float getY() {
		
		return this.body.getPosition().y / GroundTeamGame.PHYSICS_SCALAR; 
	}
	
	public Pedestrian(final long id, final World world, final float x, final float y) {
		
		super(id);
		
		this.random = new Random();
		
		this.isAlive = false;
		
		final BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position = new Vec2(
				x * GroundTeamGame.PHYSICS_SCALAR, 
				y * GroundTeamGame.PHYSICS_SCALAR);
		
		bodyDef.linearDamping = 0.1f;
		bodyDef.angularDamping = 1f;
		
		this.body = world.createBody(bodyDef);
		
		final CircleShape circle = new CircleShape();
		
		circle.m_p.set(0f, 0f);
		circle.m_radius = 4f * GroundTeamGame.PHYSICS_SCALAR;
		
		final FixtureDef fixtureDef = new FixtureDef();
		
		fixtureDef.density = 10f;
		fixtureDef.shape = circle;
		fixtureDef.restitution = 0.5f;
		fixtureDef.friction = 0f;
		fixtureDef.userData = this;
	    
	    this.body.createFixture(fixtureDef);
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.image = new Image("assets/gfx/Ped1.png"); 
		
		this.image.setCenterOfRotation(this.image.getWidth() / 2f, this.image.getHeight() / 2f);
		
		this.wander = this.random.nextFloat() * 360f;
		
		this.isAlive = true;
	}
	
	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		Vector2f v = this.wander().scale(0.001f);
		
		this.body.applyForce(new Vec2(v.getX(), v.getY()), this.body.getPosition());
		
		//this.image.setRotation(this.body);TODO
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		graphics.drawImage(this.image, this.getX() - image.getWidth() / 2f, this.getY() - image.getHeight() / 2f);
	}
	
	public void runOver() {
		
		if (!this.isAlive) {
			
			return;
		}
		
		this.triggerDestroy();
		
		this.isAlive = false;
	}
	
	private Vector2f wander() {
		
		final Vector2f circlePosition = new Vector2f(
				this.body.getLinearVelocity().x, 
				this.body.getLinearVelocity().y);
		
		circlePosition.normalise();
		circlePosition.scale(WANDER_CIRCLE_DISTANCE);
		circlePosition.add(new Vector2f(this.getX(), this.getY()));
		
		this.wander += this.random.nextFloat() * WANDER_CIRCLE_CHANGE * 2f - WANDER_CIRCLE_CHANGE;
		
		final Vector2f target = circlePosition.add(new Vector2f(this.wander).scale(WANDER_CIRCLE_RADIUS));
		
		return Steering.seek(new Vector2f(this.getX(), this.getY()), new Vector2f(
				this.body.getLinearVelocity().x, 
				this.body.getLinearVelocity().y), target);
	}
}
