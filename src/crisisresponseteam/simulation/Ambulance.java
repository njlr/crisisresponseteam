package crisisresponseteam.simulation;

import nlib.components.BasicComponentRenderable;

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

public class Ambulance extends BasicComponentRenderable {
	
	//private static final float DEGTORAD = 0.0174532925199432957f;
	private static final float RADTODEG = 57.295779513082320876f;
	
	private static final float FORCE = 10000000f;
	private static final float TURN_RATE = 100000f; 
	
	private final Body body;
	
	private Image image;
	
	public float getX() {
		
		return this.body.getPosition().x;
	}
	
	public float getY() {
		
		return this.body.getPosition().y; 
	}
	
	public Ambulance(final long id, final World world, final float x, final float y) {
		
		super(id);
		
		final BodyDef bodyDef = new BodyDef();
		
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position = new Vec2(x, y);
		bodyDef.linearDamping = 0.1f;
		bodyDef.angularDamping = 1f;
		
		this.body = world.createBody(bodyDef);
		
		final PolygonShape box = new PolygonShape();
		
		box.setAsBox(64, 32f);
		
		final FixtureDef fixtureDef = new FixtureDef();
		
		fixtureDef.density = 1f;
		fixtureDef.shape = box;
	    fixtureDef.friction = 0.3f;
	    
	    this.body.createFixture(fixtureDef);
	}
	
	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.image = new Image("assets/gfx/Ambulance.png");
		
		this.image.setCenterOfRotation(this.image.getWidth() / 2f, this.image.getHeight() / 2f);
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
			
			this.body.applyForce(
					this.body.getWorldVector(new Vec2(0, 1)).mulLocal(FORCE), 
					this.body.getWorldPoint(new Vec2(0, 8)));
		}
		
		float speed = Math.max(0f, this.body.getLinearVelocity().length() / 5f);
		
		if (left) {
			
			this.body.applyAngularImpulse(-speed * TURN_RATE);
		}
		
		if (right) {
			
			this.body.applyAngularImpulse(speed * TURN_RATE);
		}
		
		this.image.setRotation(this.body.getAngle() * RADTODEG);
		
		System.out.println(this.body.getAngle());
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.drawImage(
				this.image, 
				this.body.getWorldCenter().x - this.image.getWidth() / 2f, 
				this.body.getWorldCenter().y - this.image.getHeight() / 2f);
	}
}
