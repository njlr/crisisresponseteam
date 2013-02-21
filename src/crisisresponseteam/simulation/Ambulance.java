package crisisresponseteam.simulation;

import nlib.components.BasicComponentRenderable;
import nlib.components.ComponentRenderable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import uk.ac.ed.gamedevsoc.collisions.Collider;

public strictfp final class Ambulance extends BasicComponentRenderable implements ComponentRenderable, Collider {
	
	private static final float RADIUS = 16f;
	
	private static final float ACCELERATION = 0.003f;
	private static final float MAX_SPEED = 0.2f;
	private static final float TURN_SPEED = 1f;
	private static final float MAX_TURN = 10f;
	private static final float DRAG = 0.01f;
	private static final float WHEEL_BASE = 12f;
	
	private final Vector2f initialPosition;
	
	private final Vector2f position;
	
	private float rotation;
	private float speed;
	
	private float steering;
	
	private Image image;
	
	@Override
	public float getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Vector2f getPosition() {
		
		return this.position.copy();
	}
	
	public Ambulance(final long id, final Vector2f initialPosition) {
		
		super(id);
		
		this.initialPosition = initialPosition;
		
		this.position = new Vector2f();
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.image = new Image("assets/gfx/Ambulance.png");
		
		this.image.setCenterOfRotation(this.image.getWidth() / 2f, this.image.getHeight() / 2f);
		
		this.position.set(this.initialPosition);
		
		this.rotation = 0f;
		this.speed = 0f;
		this.steering = 0f;
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
			
			this.steering -= TURN_SPEED;
			
			if (this.steering < -MAX_TURN) {
				
				this.steering = -MAX_TURN;
			}
		}
		else if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
			
			this.steering += TURN_SPEED;
			
			if (this.steering > MAX_TURN) {
				
				this.steering = MAX_TURN;
			}
		}
		else {
			
			float i = -this.steering;
			
			if (i > TURN_SPEED) {
				
				i = TURN_SPEED;
			}
			else if (i < -TURN_SPEED) {
				
				i = -TURN_SPEED;
			}
			
			this.steering += i;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_UP)) {
			
			this.speed += ACCELERATION;
			
			if (this.speed > MAX_SPEED) {
				
				this.speed = MAX_SPEED;
			}
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_DOWN)) {
			
			this.speed -= ACCELERATION;
			
			if (this.speed < -MAX_SPEED) {
				
				this.speed = -MAX_SPEED;
			}
		}
		
		this.image.setRotation(this.rotation);
		
		final Vector2f h = new Vector2f(this.rotation);
		
		final Vector2f fWheel = this.position.copy().add(h.copy().scale(WHEEL_BASE / 2f));
		final Vector2f bWheel = this.position.copy().sub(h.copy().scale(WHEEL_BASE / 2f));
		
		bWheel.add(h.copy().scale(this.speed * delta));
		fWheel.add(new Vector2f(this.rotation + this.steering).scale(this.speed * delta));
		
		this.position.set(bWheel.copy().add(fWheel).scale(0.5f));
		
		this.rotation = (float) fWheel.sub(bWheel).getTheta();
		
		this.speed *= 1f - DRAG;
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		graphics.drawImage(this.image, this.getX(), this.getY());
	}

	@Override
	public float getRadius() {
		
		return RADIUS;
	}

	@Override
	public boolean canCollide(final Collider other) {
		
		return true;
	}

	@Override
	public float getX() {
		
		return this.position.getX();
	}

	@Override
	public float getY() {
		
		return this.position.getY();
	}
}
