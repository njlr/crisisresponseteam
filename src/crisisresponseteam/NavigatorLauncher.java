package crisisresponseteam;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public strictfp final class NavigatorLauncher {
	
	private NavigatorLauncher() {
		
		super();
	}
	
	public static void main(final String[] args) throws SlickException {
		
		Utils.linkLwjgl();
		
		final Game game = new NavigatorGame();
		
		final ScalableGame scalableGame = new ScalableGame(game, 640, 480, true);
		
		final AppGameContainer container = new AppGameContainer(scalableGame);
		
		//container.setDisplayMode(960, 720, false);
		container.setVSync(true);
		container.setTargetFrameRate(30);
		
		container.start();
	}
}
