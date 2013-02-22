package crisisresponseteam.simulation;

import nlib.components.BasicComponentRenderable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public strictfp final class GoreManager extends BasicComponentRenderable {
	
	private final ParticleSystem particleSystem;
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_GORE;
	}
	
	public GoreManager(long id) {
		
		super(id);
		
		this.particleSystem = new ParticleSystem("assets/gfx/Spatter1.png");
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		this.particleSystem.update(delta);
	}
	
	@Override
	public void render(final GameContainer gameContainer, final Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		this.particleSystem.render(0f, 0f);
	}
	
	public void emit(final int x, final int y) {
		
		final ConfigurableEmitter emitter = new ConfigurableEmitter("");
		
		emitter.setPosition(x, y);
		emitter.setEnabled(true);
		
		
		this.particleSystem.addEmitter(emitter);
		
		this.particleSystem.removeEmitter(emitter);
	}
}
