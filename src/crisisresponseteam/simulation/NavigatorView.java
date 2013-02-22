package crisisresponseteam.simulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponent;

public strictfp final class NavigatorView extends BasicComponent {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private static final float ACCL = 0.5f;
	private static final float TOP_SPEED = 8f;
	
	private final NavigatorMap map;
	
	private final Vector2f position;
	private final Vector2f velocity;
	
	public float getX() {
		
		return this.position.getX();
	}
	
	public float getY() {
		
		return this.position.getY();
	}
	
	public NavigatorView(final long id, final NavigatorMap map) {
		
		super(id);
		
		this.map = map;
		
		this.position = new Vector2f();
		this.velocity = new Vector2f(); 
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.clamp(); 
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_UP)) {
			
			this.velocity.y -= ACCL * delta;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_DOWN)) {
			
			this.velocity.y += ACCL * delta;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT)) {
			
			this.velocity.x -= ACCL * delta;
		}
		
		if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT)) {
			
			this.velocity.x += ACCL * delta;
		}
		
		if (this.velocity.lengthSquared() > TOP_SPEED * TOP_SPEED) {
			
			this.velocity.scale(TOP_SPEED / this.velocity.length());
		}
		
		this.position.add(this.velocity);
		
		this.velocity.scale(0.001f);
		
		this.clamp();
	}
	
	private void clamp() {
		
		if (this.position.x < 0f) {
			
			this.position.x = 0f;
		}
		
		if (this.position.y < 0f) {
			
			this.position.y = 0f;
		}
		
		if (this.position.x + WIDTH > this.map.getWidth()) {
			
			this.position.x = this.map.getWidth() - WIDTH;
		}
		
		if (this.position.y + HEIGHT > this.map.getHeight()) {
			
			this.position.y = this.map.getHeight() - HEIGHT;
		}
	}
}
