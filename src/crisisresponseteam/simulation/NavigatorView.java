package crisisresponseteam.simulation;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import nlib.components.BasicComponentRenderable;

public strictfp final class NavigatorView extends BasicComponentRenderable {
	
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	
	private static final float ACCL = 0.1f;
	private static final float TOP_SPEED = 12f;
	
	private final NavigatorMap map;
	
	private final Vector2f position;
	private final Vector2f velocity;
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_VIEW;
	}
	
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
		
		this.velocity.scale(0.9f);
		
		this.clamp();
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		graphics.setColor(Color.blue);
		
		float ax = this.map.getAmbulancePosition().getX();
		float ay = this.map.getAmbulancePosition().getY();
		
		graphics.fillOval(
				ax - 4f, 
				ay - 4f, 
				16f, 
				16f);
		
		if (ax < this.getX() || ay < this.getY() || ax > this.getX() + WIDTH || ay > this.getY() + HEIGHT)  {
			
			if (ax < this.getX()) {
				
				ax = this.getX();
			}
			
			if (ay < this.getY()) {
				
				ay = this.getY();
			}
			
			if (ax > this.getX() + WIDTH) {
				
				ax = this.getX() + WIDTH;
			}
			
			if (ay > this.getY() + HEIGHT) {
				
				ay = this.getY() + HEIGHT;
			}
			
			graphics.fillRect(ax - 8f, ay - 8f, 16f, 16f);
		}
		
		if (this.map.getCurrentCrisisSite() != null) {
			
			graphics.setColor(Color.red);
			
			ax = this.map.getCurrentCrisisSite().getPosition().getX();
			ay = this.map.getCurrentCrisisSite().getPosition().getY();
			
			graphics.fillOval(
					ax - 4f, 
					ay - 4f, 
					16f, 
					16f);
			
			if (ax < this.getX() || ay < this.getY() || ax > this.getX() + WIDTH || ay > this.getY() + HEIGHT)  {
				
				if (ax < this.getX()) {
					
					ax = this.getX();
				}
				
				if (ay < this.getY()) {
					
					ay = this.getY();
				}
				
				if (ax > this.getX() + WIDTH) {
					
					ax = this.getX() + WIDTH;
				}
				
				if (ay > this.getY() + HEIGHT) {
					
					ay = this.getY() + HEIGHT;
				}
				
				graphics.fillRect(ax - 8f, ay - 8f, 16f, 16f);
			}
		}
		
		
		graphics.drawString("TIME LEFT: " + this.map.getTimeLeft(), this.getX() + 4, this.getY() + 4);
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
