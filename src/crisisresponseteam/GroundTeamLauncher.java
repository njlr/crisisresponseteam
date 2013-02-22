package crisisresponseteam;

import java.io.IOException;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfigText;
import uk.ac.ed.gamedevsoc.net.sessions.SysoutSessionHandler;
import uk.ac.ed.gamedevsoc.net.sessions.net.clientserver.LoopSessionHandler;
import uk.ac.ed.gamedevsoc.net.sessions.net.clientserver.SessionServer;

public strictfp final class GroundTeamLauncher {
	
	private GroundTeamLauncher() {
		
		super();
	}
	
	public static void main(final String[] args) throws SlickException, IOException {
		
		Utils.linkLwjgl();
		
		SessionServer<SessionConfig, PlayerInfo> server = new SessionServer<SessionConfig, PlayerInfo>(
				new SessionConfigText(""), 1234);
		
		server.add(new SysoutSessionHandler<SessionConfig, PlayerInfo>("SERVER"));
		server.add(new LoopSessionHandler<SessionConfig, PlayerInfo>());
		
		server.startAndWait();
		
		System.out.println("Press any key to launch... ");
		System.in.read();
		
		final Game game = new GroundTeamGame(server);
		
		final ScalableGame scalableGame = new ScalableGame(game, 640, 480, true);
		
		final AppGameContainer container = new AppGameContainer(scalableGame);
		
		container.setDisplayMode(1280, 1024, true);
		container.setVSync(true);
		container.setTargetFrameRate(30);
		container.setShowFPS(false);
		
		container.start();
	}
}
