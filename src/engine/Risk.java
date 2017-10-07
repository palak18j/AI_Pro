package engine;

import gui.GameGUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Risk
{

	public static void main( String[] args )
	{
                System.out.println("hhh11");
            /* start program on EDT */
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
                            System.out.println("here");
                            createAndShowGUI();
			}
		} );
	}
	
	public static void createAndShowGUI()
	{
            System.out.println("invoked here");
                            System.out.println("shutup");
            RiskGameEngine gameEngine = new RiskGameEngine();
		
                GameGUI gui = new GameGUI( gameEngine );

		gameEngine.addObserver( gui );

		gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		gui.setSize( RiskUtils.GAME_SIZE );
		gui.setResizable( false );
		gui.setLocation( RiskUtils.getStartScreenPosition() );
		gui.setVisible( true );
	}
}
