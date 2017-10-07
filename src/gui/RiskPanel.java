/**
 * CSCI 2120 Fall 2014
 * Risk class RiskPanel
 * @author Shane McCulley
 * @date Dec 5, 2014
 **/
package gui;

import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import engine.RiskUtils;

@SuppressWarnings( "serial" )
public class RiskPanel extends JPanel
{
	private BufferedImage image;
	
	public RiskPanel( LayoutManager manager )
	{
		super( manager);
	}
	
	public RiskPanel()
	{
		super();
		image = RiskUtils.getImage( "background.jpg" );
	}
	
	@Override
	public void paintComponent( Graphics g )
	{
		// TODO Auto-generated method stub
		super.paintComponent( g );
		g.drawImage( image, 0, 0,getWidth(),getHeight(), null ); ///m1
	}
}
