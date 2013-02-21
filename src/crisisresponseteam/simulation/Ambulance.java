package crisisresponseteam.simulation;

import nlib.components.ComponentRenderable;
import nlib.physics.steering.BasicVehicleTwoAxes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public strictfp final class Ambulance extends BasicVehicleTwoAxes implements ComponentRenderable {
	
	private final static float MASS = 1f;
	private final static float DRAG_COEFFICIENT = 0.01f;
	private final static float MAX_SPEED = 4f;
	private final static float MAX_FORCE = 1f;
	private final static float TURN_SPEED = 2f;
	
	private Image image;
	
	@Override
	public float getDepth() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Ambulance(final long id, final Vector2f initialPosition) {
		
		super(id, initialPosition, MASS, DRAG_COEFFICIENT, MAX_SPEED, MAX_FORCE, TURN_SPEED, 0.01f);
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.image = new Image("assets/Ambulance.png");
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
			
			this.turn(-TURN_SPEED);
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
			
			this.turn(TURN_SPEED);
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_UP)) {
			
			this.addSteering(new Vector2f(this.getRotation()).scale(10f));
		}
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		graphics.drawImage(this.image, this.getPosition().getX(), this.getPosition().getY());
	}
}