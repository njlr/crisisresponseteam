package crisisresponseteam.simulation;

import nlib.components.Component;
import nlib.components.ComponentManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import uk.ac.ed.eusa.gamedevsoc.net.sessions.Command;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.PlayerRemovedReasonReason;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.Session;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.SessionClosedReason;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.SessionConfig;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.SessionHandler;
import uk.ac.ed.eusa.gamedevsoc.net.sessions.net.clientserver.SessionClient;
import crisisresponseteam.simulation.events.AmbulancePositionUpdatedEvent;
import crisisresponseteam.simulation.events.CrisisSiteSetEvent;
import crisisresponseteam.simulation.events.TimeLeftEvent;

public strictfp final class GameStateNavigate extends BasicGameState {
	
	public static final int ID = 66;
	
	private final SessionClient<SessionConfig, PlayerInfo> sessionClient;
	
	private final ComponentManager<Component> componentManager;
	
	private final NavigatorView navigatorView;
	
	public GameStateNavigate(final SessionClient<SessionConfig, PlayerInfo> sessionClient) {
		
		super();
		
		this.sessionClient = sessionClient;
		
		this.componentManager = new ComponentManager<Component>();
		
		this.sessionClient.add(
				new SessionHandler<SessionConfig, PlayerInfo>() {
					
					@Override
					public void commandReceived(Session<SessionConfig, PlayerInfo> session, Command command) {
						
						if (command instanceof AmbulancePositionUpdatedEvent) {
							
							componentManager.getEventBus().post((AmbulancePositionUpdatedEvent) command);
						}
						else if (command instanceof CrisisSiteSetEvent) {
							
							componentManager.getEventBus().post((CrisisSiteSetEvent) command);
						}
						else if (command instanceof TimeLeftEvent) {
							
							componentManager.getEventBus().post((TimeLeftEvent) command);
						}
					}
					
					@Override
					public void playerRemoved(Session<SessionConfig, PlayerInfo> session,
							PlayerInfo playerInfo, PlayerRemovedReasonReason reason) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void playerAdded(Session<SessionConfig, PlayerInfo> session,
							PlayerInfo playerInfo) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void sessionOpen(Session<SessionConfig, PlayerInfo> session) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void sessionClosed(Session<SessionConfig, PlayerInfo> session,
							SessionClosedReason reason) {
						// TODO Auto-generated method stub
						
					}
				});
		
		final NavigatorMap navigatorMap = new NavigatorMap(this.componentManager.takeId(), "assets/maps/City1.tmx");
		
		this.componentManager.addComponent(navigatorMap);
		this.componentManager.getEventBus().register(navigatorMap);
		
		this.navigatorView = new NavigatorView(this.componentManager.takeId(), navigatorMap);
		
		this.componentManager.addComponent(this.navigatorView);
	}

	@Override
	public int getID() {
		
		return ID;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame arg1) throws SlickException {
		
		this.componentManager.init(gameContainer);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		
		graphics.translate(-this.navigatorView.getX(), -this.navigatorView.getY());
		
		this.componentManager.render(gameContainer, graphics);
		
		graphics.translate(0f, 0f);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame arg1, int delta) throws SlickException {
		
		this.componentManager.update(gameContainer, delta);
	}
}
