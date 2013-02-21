package crisisresponseteam;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;

public strictfp final class GroundTeamLauncher {
	
	private GroundTeamLauncher() {
		
		super();
	}
	
	public static void main(final String[] args) throws SlickException {
		
		Utils.linkLwjgl();
		
		final GroundTeamGame groundTeamGame = new GroundTeamGame();
		
		final ScalableGame scalableGame = new ScalableGame(groundTeamGame, 640, 480, true);
		
		final AppGameContainer container = new AppGameContainer(scalableGame);
		
		//container.setDisplayMode(960, 720, false);
		container.setVSync(true);
		
		container.start();
	}
}
