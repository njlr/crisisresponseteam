package crisisresponseteam;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;
import uk.ac.ed.gamedevsoc.net.sessions.net.clientserver.SessionClient;
import crisisresponseteam.simulation.GameStateNavigate;
import crisisresponseteam.simulation.GameStateWaiting;

public strictfp final class NavigatorGame extends StateBasedGame {
	
	private final SessionClient<SessionConfig, PlayerInfo> sessionClient;
	
	public NavigatorGame(SessionClient<SessionConfig, PlayerInfo> sessionClient) {
		
		super("CRISIS RESPONSE TEAM");
		
		this.sessionClient = sessionClient;
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		
		this.addState(new GameStateWaiting(this.sessionClient));
		this.addState(new GameStateNavigate(this.sessionClient));
		
		this.enterState(GameStateWaiting.ID);
	}
}
