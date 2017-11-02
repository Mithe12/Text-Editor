package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 *
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element )
	{
		// TODO: Implement this method
		add(this.size(), element);
		/*
		LLNode<E> newNode = new LLNode(element);
		LLNode<E> current = head;
		int sizeBeforeAdd = size;

		while( current.next != tail ) {
			current = current.next;
		}

		current.next = newNode;
		newNode.prev = current;
		newNode.next = tail;
		tail.prev = newNode;
		size++;


		if(this.size == 0) {
			head.next = newNode;
			newNode.prev = head;
			newNode.next = tail;
			tail.prev = newNode;
		}
		else {
			//check if we also need to check tail.prev = current
			while( current.next != tail ) {
				current = current.next;
			}
			current.next = newNode;
			newNode.prev = current;
			newNode.next = tail;
			tail.prev = newNode;

		}
		*/
		/*
		if( size > sizeBeforeAdd ) {
			return true;
		}
		else return false;
		*/
		return true;
	}

	/** Get the element at position index
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
		// TODO: Implement this method.
		LLNode<E> current;
		if( index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		else {
			current = head;
			for( int i=0; i<=index; i++ ) {
				current = current.next;
			}
		}
		return current.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element )
	{
		// TODO: Implement this method
		if( index < 0 || index > size ) {
			throw new IndexOutOfBoundsException();
		}

		if( element == null ) {
			throw new NullPointerException();
		}

		LLNode<E> newNode = new LLNode(element);
		LLNode<E> current = head;

		for( int i=0; i<index; i++ ) {
			current = current.next;
		}

		newNode.next = current.next;
		current.next = newNode;
		newNode.prev = current;
		newNode.next.prev = newNode;
		size++;

	}


	/** Return the size of the list */
	public int size()
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 *
	 */
	public E remove(int index)
	{
		// TODO: Implement this method
		if( index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LLNode<E> nodeAtIndex = head;
		for(int i=0; i<=index; i++) {
			nodeAtIndex = nodeAtIndex.next;
		}
		E valueAtIndex = nodeAtIndex.data;
		nodeAtIndex.prev.next = nodeAtIndex.next;
		nodeAtIndex.next.prev = nodeAtIndex.prev;
		nodeAtIndex.next = null;
		nodeAtIndex.prev = null;
		this.size--;
		return valueAtIndex;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element)
	{
		// TODO: Implement this method
		if( index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		if( element == null ) {
			throw new NullPointerException();
		}

		E prevVal;
		prevVal = this.get(index);

		LLNode<E> current = head;
		for( int i=0; i<=index; i++ ) {
			current = current.next;
		}
		current.data = element;
		return prevVal;
	}

	@Override
	public String toString() {
		String result = "";
		LLNode<E> current = head;
		for(int i=0; i<size; i++) {
			head = head.next;
			result += head.toString();
		}

		return result;
	}


}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e)
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	@Override
	public String toString() {
		return "[" + data + "]";
	}



}
