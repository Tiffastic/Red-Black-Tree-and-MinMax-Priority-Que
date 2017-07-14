package priorityQ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class MinMaxPQTest
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

	@Test
	public void testAdd()
	{
		pqString.add("Computer Science");
		int expected = 5;
		assertEquals(expected, pqString.size());

		String expectedResults = "[ Computer Science Loves Math Thuy Zebras ]";

		assertEquals(expectedResults, pqString.toString());

	}

	@Test
	public void testMin()
	{
		String expected = "Loves";
		assertEquals(expected, pqString.min());
	}

	@Test
	public void testMin2()
	{
		Integer expected = 1;
		assertEquals(expected, oneNode.min());
	}

	@Test
	public void testRemoveMin()
	{
		pqString.add("Yak");
		assertEquals("Loves", pqString.removeMin());

		String newMin = "Math";
		assertEquals(newMin, pqString.removeMin());

		newMin = "Thuy";
		assertEquals(newMin, pqString.removeMin());

		newMin = "Yak";
		assertEquals(newMin, pqString.removeMin());

		newMin = "Zebras";
		assertEquals(newMin, pqString.removeMin());

		assertTrue(pqString.isEmpty());
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveMinDuplicates()
	{
		Integer expected = 1;
		assertEquals(expected, oneNode.removeMin());
		oneNode.removeMin();

		oneNode.add(1);
		assertEquals(expected, oneNode.removeMin());

		oneNode.add(10);
		oneNode.add(10);
		oneNode.add(30);
		oneNode.add(30);
		oneNode.add(4);
		oneNode.add(5);

		expected = 4;
		assertEquals(expected, oneNode.removeMin());

		oneNode.add(-11);
		oneNode.add(-11);
		expected = -11;
		assertEquals(expected, oneNode.removeMin());
		expected = -11;
		assertEquals(expected, oneNode.removeMin());

		expected = 5;
		assertEquals(expected, oneNode.removeMin());

		expected = 10;
		assertEquals(expected, oneNode.removeMin());
		expected = 10;
		assertEquals(expected, oneNode.removeMin());

		expected = 30;
		assertEquals(expected, oneNode.removeMin());
		expected = 30;
		assertEquals(expected, oneNode.removeMin());

		assertEquals(0, oneNode.size());
		assertTrue(oneNode.isEmpty());

		oneNode.removeMin();

	}

	@Test
	public void testMax()
	{
		String expected = "Zebras";
		assertEquals(expected, pqString.max());
	}

	@Test
	public void testMax2()
	{
		Integer expected = 1;
		assertEquals(expected, oneNode.max());
	}

	@Test
	public void testRemoveMax()
	{
		assertEquals("Zebras", pqString.removeMax());

		String newMax = "Thuy";
		assertEquals(newMax, pqString.removeMax());

		newMax = "Math";
		assertEquals(newMax, pqString.removeMax());

		newMax = "Loves";
		assertEquals(newMax, pqString.removeMax());
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveMaxDuplicates()
	{
		assertEquals(new Integer(1), oneNode.removeMax());
		oneNode.removeMax();

		oneNode.add(53);
		oneNode.add(40);
		oneNode.add(35);
		oneNode.add(25);
		oneNode.add(40);
		oneNode.add(40);
		oneNode.add(53);
		oneNode.add(25);

		Integer expectedMax = 53;
		assertEquals(expectedMax, oneNode.removeMax());

		expectedMax = 53;
		assertEquals(expectedMax, oneNode.removeMax());

		expectedMax = 40;
		assertEquals(expectedMax, oneNode.removeMax());
		expectedMax = 40;
		assertEquals(expectedMax, oneNode.removeMax());
		expectedMax = 40;
		assertEquals(expectedMax, oneNode.removeMax());

		expectedMax = 35;
		assertEquals(expectedMax, oneNode.removeMax());

		expectedMax = 25;
		assertEquals(expectedMax, oneNode.removeMax());
		expectedMax = 25;
		assertEquals(expectedMax, oneNode.removeMax());

		assertTrue(oneNode.isEmpty());

	}

	@Test
	public void testRemoveMinMax()
	{
		String min = "Loves";
		String max = "Zebras";
		assertEquals(min, pqString.removeMin());
		assertEquals(max, pqString.removeMax());

		min = "Math";
		max = "Thuy";
		assertEquals(min, pqString.min());
		assertEquals(max, pqString.max());

		pqString.add("Yak");
		max = "Yak";
		assertEquals(max, pqString.removeMax());

		pqString.add("Apples");
		min = "Apples";
		assertEquals(min, pqString.removeMin());

		max = "Thuy";
		assertEquals(max, pqString.removeMax());

		pqString.add(max);
		max = "Thuy";
		assertEquals(max, pqString.removeMax());

		max = "Math";
		min = "Math";
		assertEquals(max, pqString.max());
		assertEquals(min, pqString.min());

		assertFalse(pqString.isEmpty());
		assertEquals(1, pqString.size());

		assertEquals(max, pqString.removeMax());
		assertTrue(pqString.isEmpty());
		assertEquals(0, pqString.size());

	}

	@Test
	public void testIsEmptyFalse()
	{
		assertFalse(pqString.isEmpty());
	}

	@Test
	public void testIsEmptyTrue()
	{
		pqString.removeMax();
		pqString.removeMin();
		pqString.removeMax();
		pqString.removeMin();

		assertTrue(pqString.isEmpty());
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyPQMaxException()
	{
		emptypq.max();
	}

	@Test(expected = NoSuchElementException.class)
	public void testEmptyPQMinException()
	{
		emptypq.min();
	}

	@Test(expected = NoSuchElementException.class)
	public void testExceptionMin()
	{
		oneNode.removeMin();
		oneNode.min();
	}

	@Test(expected = NoSuchElementException.class)
	public void testExceptionMax()
	{
		oneNode.removeMax();
		oneNode.max();
	}


}
