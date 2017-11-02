/**
 *
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}


	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}

		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			//System.out.println(i+ ": " + longerList.get(i));
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}

		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}

	}


	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		/*
		System.out.println();
		for(int i=0; i<=LONG_LIST_LENGTH; i++) {
			System.out.println(i + ": " + longerList.get(i));
		}
		*/

		//test list1
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());

		// TODO: Add more tests here
		assertEquals("Remove: check previous node ", list1.head, list1.head.next.prev);

		//check index less than 0
		try {
			list1.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}

		//check index >= size
		try {
			list1.remove(list1.size+1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}

		//test emptyList
		try {
			emptyList.remove(0);
			fail("Check out of bounds");
		}catch(IndexOutOfBoundsException e) {

		}


		//Test longerList
		for(int i=0; i<=longerList.size(); i++) {
			a = longerList.remove(0);
			assertEquals("Check " + i  + " element" , i, a);
			assertEquals("Check element 0", (Integer)(i+1), longerList.get(0));
			assertEquals("Check size ", (10-i-1), longerList.size);

		}

		//check index less than 0
		try {
			longerList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}

		//check index >= size
		try {
			list1.remove(longerList.size);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}


		//test shortList
		String s = shortList.remove(0);
		assertEquals("Remove: check s is correct", "A", s);
		assertEquals("Remvoe: check element 0" , "B", shortList.get(0));
		assertEquals("Remove: check sizer", 1, shortList.size);

		try {
			shortList.remove(10);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e) {

		}


	}

	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
		int prevSize = shortList.size;
		shortList.add("C");
		assertEquals("AddEnd: size increase ", prevSize+1, shortList.size);
		assertEquals("AddEnd: tails prev elem is C ", "C", shortList.tail.prev.data);

		try{
			shortList.add(null);
			fail("Null value added");
		}
		catch(NullPointerException e) {

		}


		prevSize = emptyList.size;
		emptyList.add(5);
		assertEquals("AddEnd: size increase ", prevSize+1, emptyList.size);
		assertEquals("AddEnd: tails prev elem is 5 ", (Integer)5, emptyList.tail.prev.data);

		prevSize = longerList.size;
		longerList.add(10);
		assertEquals("AddEnd: size increase ", prevSize+1, longerList.size);
		assertEquals("AddEnd: tails prev element is 10", (Integer)10, longerList.tail.prev.data);

		prevSize = list1.size;
		list1.add(98);
		assertEquals("AddEnd: size increase ", prevSize+1, list1.size);
		assertEquals("AddEnd: tails prev element", (Integer)98, list1.tail.prev.data);

	}


	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("Check Size: emptyList", 0, emptyList.size());
		assertEquals("Check Size: shortList", 2, shortList.size);
		assertEquals("Check Size: longerList", 10, longerList.size());
		assertEquals("Check Size: list1", 3, list1.size);
	}



	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test

		//list1 test
		list1.add(1, 55);
		assertEquals("Check size", (Integer)4, (Integer)list1.size());
		assertEquals("Check element at index 1", (Integer)55,list1.get(1));
		assertEquals("Check next element", (Integer)21, list1.get(2));

		try {
			list1.add(7, 88);
			fail("Check out of bounds");
		}
		catch(IndexOutOfBoundsException e) {

		}

		try {
			list1.add(1, null);
			fail("Check adding null");
		}
		catch(NullPointerException e) {

		}

		try {
			list1.add(-1, 46);
			fail("Check lower bound add");
		}
		catch(IndexOutOfBoundsException e) {

		}


		//emptyList test
		emptyList.add(0, 1);
		assertEquals("Check size", 1, emptyList.size());
		assertEquals("Element at index 0", (Integer)1, emptyList.get(0));
		try {
			emptyList.add(7, 7);
			fail("Check out of boudns");
		}
		catch(IndexOutOfBoundsException e) {

		}


		//check shortList
		shortList.add(0, "Z");
		assertEquals("check size", 3, shortList.size);
		assertEquals("Check element at index 0", "Z", shortList.get(0));
		assertEquals("Check next element", "A", shortList.get(1));


	}

	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try {
			shortList.set(1, null);
			fail("Check null assignment");
		} catch(NullPointerException exception) {

		}


		shortList.set(1, "Z");
		assertEquals("Set: check element1", "Z", shortList.get(1));

		try {
			shortList.set(-1, "Y");
			fail("Check lower bounds");
		}
		catch(IndexOutOfBoundsException e) {

		}

		try {
			shortList.set(10, "X");
			fail("Check upper bounds");
		}
		catch( IndexOutOfBoundsException e ) {

		}
	}


	// TODO: Optionally add more test methods.

}
