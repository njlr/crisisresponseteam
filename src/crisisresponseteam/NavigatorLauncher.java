package crisisresponseteam;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfo;
import uk.ac.ed.gamedevsoc.net.sessions.PlayerInfoUuid;
import uk.ac.ed.gamedevsoc.net.sessions.SessionConfig;
import uk.ac.ed.gamedevsoc.net.sessions.SysoutSessionHandler;
import uk.ac.ed.gamedevsoc.net.sessions.net.clientserver.SessionClient;

public strictfp final class NavigatorLauncher {
	
	private NavigatorLauncher() {
		
		super();
	}
	
	public static void main(final String[] args) throws SlickException, IOException {
		
		Utils.linkLwjgl();
		
		final InetSocketAddress serverAddress = new InetSocketAddress(
				InetAddress.getByName("morales"), 1234);
		
		SessionClient<SessionConfig, PlayerInfo> sessionClient = new SessionClient<SessionConfig, PlayerInfo>(
				new PlayerInfoUuid(), serverAddress);
		
		sessionClient.add(new SysoutSessionHandler<SessionConfig, PlayerInfo>("CLIENT"));
		
		sessionClient.startAndWait();
		
		final Game game = new NavigatorGame(sessionClient);
		
		final ScalableGame scalableGame = new ScalableGame(game, 640, 480, true);
		
		final AppGameContainer container = new AppGameContainer(scalableGame);
		
		//container.setDisplayMode(960, 720, false);
		container.setVSync(true);
		container.setTargetFrameRate(30);
		container.setShowFPS(false);
		container.setAlwaysRender(true);
		container.setUpdateOnlyWhenVisible(false);
		
		container.start();
	}
}
