package crisisresponseteam.simulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import nlib.components.BasicComponentRenderable;

public class MapTop extends BasicComponentRenderable {
	
	private Map map;
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_TOP;
	}
	
	public MapTop(final long id, Map map) {
		
		super(id);
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		for (int l = 0; l < this.map.getMap().getLayerCount(); l++) {
			
			if (Boolean.parseBoolean(this.map.getMap().getLayerProperty(l, "Top", "False"))) {
				
				this.map.getMap().render(0, 0, l);
			}
		}
	}
}
