package crisisresponseteam;

import nlib.utils.Utils;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public strictfp final class GroundTeamLauncher {
	
	private GroundTeamLauncher() {
		
		super();
	}
	
	public static void main(final String[] args) throws SlickException {
		
		Utils.linkLwjgl();
		
		final GroundTeamGame groundTeamGame = new GroundTeamGame();
		
		final AppGameContainer container = new AppGameContainer(groundTeamGame);
		
		container.setDisplayMode(640, 480, false);
		container.setVSync(true);
		
		container.start();
	}
}
