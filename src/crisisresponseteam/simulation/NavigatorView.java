package crisisresponseteam.simulation;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
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
	
	private Image ambulanceImage;
	private Image crisisImage;
	private Image scan;
	
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
		
		this.ambulanceImage = new Image("assets/gfx/AmbulanceArrow.png");
		this.crisisImage = new Image("assets/gfx/CrisisArrow.png");
		
		this.ambulanceImage.setCenterOfRotation(this.ambulanceImage.getWidth() / 2, this.ambulanceImage.getHeight() / 2);
		this.crisisImage.setCenterOfRotation(this.crisisImage.getWidth() / 2, this.crisisImage.getHeight() / 2);
		
		this.scan = new Image("assets/gfx/Scanlines.png");
		
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
	
	private final Random random = new Random();
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		for (int x = 0; x < WIDTH; x += this.scan.getWidth()) {
			
			for (int y = -this.random.nextInt(32); y < HEIGHT; y+= this.scan.getHeight()) {
				
				this.scan.draw(x + this.getX(), y + this.getY());
			}
		}
		
		graphics.setColor(Color.blue);
		
		float ax = this.map.getAmbulancePosition().getX();
		float ay = this.map.getAmbulancePosition().getY();
		
		graphics.fillOval(
				ax - 4f, 
				ay - 4f, 
				16f, 
				16f);
		
		if (ax < this.getX() || ay < this.getY() || ax > this.getX() + WIDTH || ay > this.getY() + HEIGHT)  {
			
			if (ax < this.getX() + 32) {
				
				ax = this.getX() + 32;
			}
			
			if (ay < this.getY() + 32) {
				
				ay = this.getY() + 32;
			}
			
			if (ax > this.getX() + WIDTH - 32) {
				
				ax = this.getX() + WIDTH - 32;
			}
			
			if (ay > this.getY() + HEIGHT  - 32) {
				
				ay = this.getY() + HEIGHT - 32;
			}
			
			this.ambulanceImage.setRotation(
					(float) new Vector2f(ax, ay).sub(
							new Vector2f(this.getX() + WIDTH / 2, this.getY() + HEIGHT / 2)).getTheta());
			
			this.ambulanceImage.draw(
					ax - this.ambulanceImage.getCenterOfRotationX(), 
					ay - this.ambulanceImage.getCenterOfRotationY());
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
				
				if (ax < this.getX() + 32) {
					
					ax = this.getX() + 32;
				}
				
				if (ay < this.getY() + 32) {
					
					ay = this.getY() + 32;
				}
				
				if (ax > this.getX() + WIDTH - 32) {
					
					ax = this.getX() + WIDTH - 32;
				}
				
				if (ay > this.getY() + HEIGHT  - 32) {
					
					ay = this.getY() + HEIGHT - 32;
				}
				
				this.crisisImage.setRotation(
						(float) new Vector2f(ax, ay).sub(
								new Vector2f(this.getX() + WIDTH / 2, this.getY() + HEIGHT / 2)).getTheta());
				
				this.crisisImage.draw(
						ax - this.crisisImage.getCenterOfRotationX(), 
						ay - this.crisisImage.getCenterOfRotationY());
			}
		}
		
		graphics.setColor(Color.white);
		
		if (this.map.getTimeLeft() > 0) {
		graphics.drawString("TIME LEFT: " + this.map.getTimeLeft(), this.getX() + 4, this.getY() + 4);
		}
		else {
			graphics.drawString("GAME OVER", this.getX() + 4, this.getY() + 4);	
		}
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
