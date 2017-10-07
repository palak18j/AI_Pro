/**
 * CSCI 2120 Fall 2014
 * Risk Game Class CardTest
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package tests;

import classes.Card;
import junit.framework.TestCase;

public class CardTest extends TestCase
{
	private Card test1;
	private Card test2;
 
	
	protected void setUp() 
	{
		test1 = new Card( "type1", "");
		test2 = new Card( "", "territory2");
	}
	
	//Testing Card's getType() method
	public void testGetType()
	{
	
		assertEquals( test1.getType(), "type1" );
		assertEquals( test2.getType(), "" );
		
	}
	
	//Testing Card's getTerritory() method
	public void testGetTerritory()
	{
		assertEquals( test1.getTerritory(), "" );
		assertEquals( test2.getTerritory(), "territory2" );	
		assertEquals( test1.getTerritory(), test2.getType() );
	}
}
