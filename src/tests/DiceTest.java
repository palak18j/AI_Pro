/**
 * CSCI 2120 Fall 2014
 * Risk Game Class DiceTest
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package tests;

import classes.Dice;
import junit.framework.TestCase;

public class DiceTest extends TestCase
{
	private Dice test1; 
 
	
	protected void setUp() 
	{
		test1 = new Dice(); 
	}
	
	//testing Dice's int[] roll( int, int ) method
	public void testRoll()
	{
		int[] testResult;
		
		/*testing all possible combinations of rolls to return the appropriate number of results*/
		testResult = test1.roll(1, 1);
		assertEquals( testResult.length, 3 );
		
		/* all results should be between 1...6 */
		for( int i : testResult )
			assertTrue( i <= 6 && i >= 0);
		
		testResult = test1.roll(1, 2);
		assertTrue( testResult.length == 4 );
		
		for( int i : testResult )
			assertTrue( i <= 6 && i >= 0);
		
		testResult = test1.roll(2, 1);
		assertTrue( testResult.length == 4);
		
		for( int i : testResult )
			assertTrue( i <= 6 && i >= 0);
		
		testResult = test1.roll(2, 2);
		assertTrue( testResult.length == 5 );
		
		for( int i : testResult )
			assertTrue( i <= 6 && i >= 0);
		
		testResult = test1.roll(3, 2);
		assertTrue( testResult.length == 6 );
		
		for( int i : testResult )
			assertTrue( i <= 6 && i >= 0);
		
		/* test ordering */
		testResult = test1.roll( 6, 10 );
		for( int i = 0; i < 5; i++ )
		{
			assertTrue( testResult[i] <= testResult[i+1] );
		}
		for( int i = 6; i < 14; i++ )
		{
			assertTrue( testResult[i] <= testResult[i+1] );
		}
	}
}
