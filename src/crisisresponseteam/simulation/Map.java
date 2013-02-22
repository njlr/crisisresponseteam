package crisisresponseteam.simulation;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

import crisisresponseteam.GroundTeamGame;

import nlib.components.BasicComponentRenderable;
import nlib.components.Component;
import nlib.components.ComponentManager;

public strictfp final class Map extends BasicComponentRenderable {
	
	private final ComponentManager<Component> componentManager;
	private final GoreManager goreManager;
	
	private final World world;
	private final String ref;
	
	
	private TiledMap map;
	
	public TiledMap getMap() {
		return map;
	}
	
	@Override
	public float getDepth() {
		
		return Constants.DEPTH_MAP;
	}
	
	public float getWidth() {
		
		return this.map.getWidth() * this.map.getTileWidth();
	}
	
	public float getHeight() {
		
		return this.map.getHeight() * this.map.getTileHeight();
	}
	
	public int getTileWidth() {
		
		return this.map.getTileWidth();
	}
	
	public int getTileHeight() {
		
		return this.map.getTileHeight();
	}
	
	public Map(final long id, final World world, final ComponentManager<Component> componentManager, final GoreManager goreManager, final String ref) {
		
		super(id);
		
		this.world = world;
		this.componentManager = componentManager;
		this.goreManager = goreManager;
		
		this.ref = ref;
	}
	
	@Override
	public void init(final GameContainer gameContainer) throws SlickException {
		
		super.init(gameContainer);
		
		this.map = new TiledMap(this.ref);
		
		boolean[][] isSolid = new boolean[this.map.getHeight()][this.map.getWidth()];
		
		for (int y = 0; y < isSolid.length; y++) {
			
			for (int x = 0; x < isSolid[0].length; x++) {
				
				isSolid[y][x] = false;
			}
		}
		
		for (int l = 0; l < this.map.getLayerCount(); l++) {
			
			for (int y = 0; y < this.map.getHeight(); y++) {
				
				for (int x = 0; x < this.map.getWidth(); x++) {
					
					final int tileID = this.map.getTileId(x, y, l);
					
					if (Boolean.parseBoolean(this.map.getTileProperty(tileID, "Solid", "false"))) {
						
						isSolid[y][x] = true;
					}
				}
			}
		}
		
		final float hw = this.map.getTileWidth() / 2f * GroundTeamGame.PHYSICS_SCALAR;
		final float hh = this.map.getTileHeight() / 2f * GroundTeamGame.PHYSICS_SCALAR;
		
		for (int y = 0; y < isSolid.length; y++) {
			
			for (int x = 0; x < isSolid[0].length; x++) {
				
				if (isSolid[y][x]) {
					
					final BodyDef bodyDef = new BodyDef();
					
					bodyDef.type = BodyType.STATIC;
					
					bodyDef.position = new Vec2(
							x * GroundTeamGame.PHYSICS_SCALAR * this.map.getTileWidth() + hw, 
							y * GroundTeamGame.PHYSICS_SCALAR * this.map.getTileHeight() + hh);
					
					bodyDef.fixedRotation = true;
					
					final Body body = this.world.createBody(bodyDef);
					
					final PolygonShape box = new PolygonShape();
					
					box.setAsBox(hw, hh);
					
					final FixtureDef fixtureDef = new FixtureDef();
					
					fixtureDef.density = 1f;
					fixtureDef.shape = box;
					fixtureDef.restitution = 0.1f;
					fixtureDef.friction = 0f;
				    
				    body.createFixture(fixtureDef);
				}
			}
		}
		
		for (int g = 0; g < this.map.getObjectGroupCount(); g++) {
			
			for (int o = 0; o < this.map.getObjectCount(g); o++) {
				
				final String type = this.map.getObjectType(g, o);
				
				if (type.equals("PedestrianSpawn")) {
					
					final float x = this.map.getObjectX(g, o);
					final float y = this.map.getObjectY(g, o);
					
					final int interval = Integer.parseInt(this.map.getObjectProperty(g, o, "Interval", "10000"));
					
					final PedestrianSpawn pedestrianSpawn = new PedestrianSpawn(
							this.componentManager.takeId(), 
							this.componentManager, 
							world, 
							new Vector2f(x, y), 
							interval);
					
					this.componentManager.addComponent(pedestrianSpawn);
				}
				else if (type.equals("CrisisSite")) {
					
					final float x = this.map.getObjectX(g, o);
					final float y = this.map.getObjectY(g, o);
					
					final String name = this.map.getObjectProperty(g, o, "Name", "???");
					
					final CrisisSite crisisSite = new CrisisSite(
							new Vector2f(x, y), 
							name);
					
					this.crisisSites.add(crisisSite);
					
					System.out.println(crisisSite.getName());
				}
			}
		}
	}
	
	private final List<CrisisSite> crisisSites = new ArrayList<CrisisSite>();
	
	public List<CrisisSite> getCrisisSites() {
		return crisisSites;
	}
	
	@Override
	public void update(final GameContainer gameContainer, final int delta) throws SlickException {
		
		super.update(gameContainer, delta);
		
		
	}
	
	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		
		super.render(gameContainer, graphics);
		
		for (int l = 0; l < this.map.getLayerCount(); l++) {
			
			if (!Boolean.parseBoolean(this.map.getLayerProperty(l, "Top", "False"))) {
				
				this.map.render(0, 0, l);
			}
		}
	}
}
