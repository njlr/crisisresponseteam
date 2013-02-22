package crisisresponseteam.simulation;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import crisisresponseteam.simulation.events.CommandLaunch;

import uk.ac.ed.gamedevsoc.net.sessions.Command;
import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.PlayerRemovedReasonReason;
import uk.ac.ed.gamedevsoc.net.sessions.Session;
import uk.ac.ed.gamedevsoc.net.sessions.SessionClosedReason;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;
import uk.ac.ed.gamedevsoc.net.sessions.SessionHandler;
import uk.ac.ed.gamedevsoc.net.sessions.net.clientserver.SessionClient;

public strictfp final class GameStateWaiting extends BasicGameState {
	
	public static final int ID = 77;
	
	private final SessionClient<SessionConfig, PlayerInfo> sessionClient;
	
	private boolean done;
	
	public GameStateWaiting(final SessionClient<SessionConfig, PlayerInfo> sessionClient) {
		
		super();
		
		this.sessionClient = sessionClient;
		
		done = false;
		this.sessionClient.add(
				new SessionHandler<SessionConfig, PlayerInfo>() {
					
					@Override
					public void commandReceived(Session<SessionConfig, PlayerInfo> session,
							Command command) {
						
						if (command instanceof CommandLaunch) {
							
							done = true;
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
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame stateBasedGame, int arg2) throws SlickException {
		// TODO Auto-generated method stub
		if (done) {
			
			stateBasedGame.enterState(GameStateNavigate.ID);
		}
	}
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics graphics) throws SlickException {
		
		graphics.drawString("Waiting... ", 4f, 4f);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return ID;
	}

}
