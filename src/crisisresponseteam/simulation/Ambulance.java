package crisisresponseteam.simulation;

import nlib.components.ComponentRenderable;
import nlib.physics.steering.BasicVehicleTwoAxes;
import nlib.utils.Utils;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import uk.ac.ed.gamedevsoc.collisions.Collider;

public strictfp final class Ambulance extends BasicVehicleTwoAxes implements ComponentRenderable, Collider {
	
	private final static float MASS = 1f;
	private final static float DRAG_COEFFICIENT = 0.01f;
	private final static float MAX_SPEED = 4f;
	private final static float MAX_FORCE = 1f;
	private final static float TURN_SPEED = 2f;
	private final static float MAX_FORCE_LATERAL = 0.05f;
	
	private final static float RADIUS = 16f;
	
	private Image image;
	
	@Override
	public float getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Ambulance(final long id, final Vector2f initialPosition) {
		
		super(id, initialPosition, MASS, DRAG_COEFFICIENT, MAX_SPEED, MAX_FORCE, TURN_SPEED, MAX_FORCE_LATERAL);
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.image = new Image("assets/Ambulance.png");
		
		this.image.setCenterOfRotation(image.getWidth() / 2f, image.getHeight() / 2f);
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
			
			if (this.isMoving()) {
				
				this.turn(-TURN_SPEED);
			}
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
			
			if (this.isMoving()) {
				
				this.turn(TURN_SPEED);
			}
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_UP)) {
			
			this.addSteering(new Vector2f(this.getRotation()).scale(10f));
		}
		
		this.image.setRotation(this.getRotation());
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
}
