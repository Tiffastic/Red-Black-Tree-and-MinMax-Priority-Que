package priorityQ;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing extra methods
 * 
 * @author Thuy
 */
public class MinMaxPQTestExtra
{

	private MinMaxPQ<String> pqString;
	private MinMaxPQ<Integer> oneNode;
	private MinMaxPQ<String> emptypq = new MinMaxPQ<>();

	@Before
	public void setUp() throws Exception
	{
		pqString = new MinMaxPQ<>();

		pqString.add("Thuy");
		pqString.add("Loves");
		pqString.add("Math");
		pqString.add("Zebras");

		oneNode = new MinMaxPQ<>();
		oneNode.add(1);
	}

	/**
	 * tests for extra methods
	 */

	@Test
	public void testGetStringInOrder()
	{
		String expected = "[ Loves Math Thuy Zebras ]";
		assertEquals(expected, pqString.toString());

		pqString.add("Yak");
		pqString.add("Apples");
		expected = "[ Apples Loves Math Thuy Yak Zebras ]";
		assertEquals(expected, pqString.toString());

	}

	@Test
	public void testToStringNumbers()
	{
		String expected = "[ 1 ]";
		assertEquals(expected, oneNode.toString());

		oneNode.add(new Integer(0));
		oneNode.add(new Integer(10));
		oneNode.add(new Integer(5));
		oneNode.add(new Integer(-3));

		expected = "[ -3 0 1 5 10 ]";
		assertEquals(expected, oneNode.toString());
	}

	@Test
	public void testToStringEmpty()
	{
		String expected = "[ ]";
		assertEquals(expected, emptypq.toString());
	}

	@Test
	public void testRemove()
	{
		pqString.delete("Math");
		String expected = "[ Loves Thuy Zebras ]";
		assertEquals(expected, pqString.toString());

		pqString.delete("Thuy");
		String expected2 = "[ Loves Zebras ]";
		assertEquals(expected2, pqString.toString());

		pqString.delete("Loves");
		String expected3 = "[ Zebras ]";
		assertEquals(expected3, pqString.toString());

		pqString.delete("Zebras");
		String expected4 = "[ ]";
		assertEquals(expected4, pqString.toString());
	}

	@Test
	public void testRemove2()
	{
		pqString.delete("Zebras");
		String expected = "[ Loves Math Thuy ]";
		assertEquals(expected, pqString.toString());
		assertEquals("Thuy", pqString.max());
	}

}
