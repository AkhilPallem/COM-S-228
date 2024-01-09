package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * @author Akhil Pallem
 * 
 */

/**
 * Implementation of the list interface based on linked nodes that store
 * multiple items per node. Rules for adding and removing elements ensure that
 * each node (except possibly the last one) is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
	/**
	 * Default number of elements that may be stored in each node.
	 */
	private static final int DEFAULT_NODESIZE = 4;

	/**
	 * Number of elements that can be stored in each node.
	 */
	private final int nodeSize;

	/**
	 * Dummy node for head. It should be private but set to public here only for
	 * grading purpose. In practice, you should always make the head of a linked
	 * list a private instance variable.
	 */
	public Node head;

	/**
	 * Dummy node for tail.
	 */
	private Node tail;

	/**
	 * Number of elements in the list.
	 */
	private int size;

	/**
	 * Constructs an empty list with the default node size.
	 */
	public StoutList() {
		this(DEFAULT_NODESIZE);
	}

	/**
	 * Constructs an empty list with the given node size.
	 * 
	 * @param nodeSize number of elements that may be stored in each node, must be
	 * an even number
	 */
	public StoutList(int nodeSize) {
		if (nodeSize <= 0 || nodeSize % 2 != 0)
			throw new IllegalArgumentException();

		// dummy nodes
		head = new Node();
		tail = new Node();
		head.next = tail;
		tail.previous = head;
		this.nodeSize = nodeSize;
	}

	/**
	 * Constructor for grading only. Fully implemented.
	 * 
	 * @param head
	 * @param tail
	 * @param nodeSize
	 * @param size
	 */
	public StoutList(Node head, Node tail, int nodeSize, int size) {
		this.head = head;
		this.tail = tail;
		this.nodeSize = nodeSize;
		this.size = size;
	}

	/**
	 * @return the number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the item to the end of the list
	 * 
	 * @param item the item to add to the list
	 * @return true, if item was added successfully
	 * false, if item already exists in the list
	 */
	@Override
	public boolean add(E item) {
		// If the item is null throw NullPointerException
		if (item == null) {
			throw new NullPointerException("Item is null");
		}
		if(contains(item)) {
			return false;
		}
		//If this is an empty list
		if (size == 0) {
			Node n = new Node();
			n.addItem(item);
			head.next = n;
			n.previous = head;
			n.next = tail;
			tail.previous = n;
		} else {
			//If the last node is not full, add the node to it 
			if (tail.previous.count < nodeSize) {
				tail.previous.addItem(item);
			}
			else {
				Node n = new Node();
				n.addItem(item);
				Node temp = tail.previous;
				temp.next = n;
				n.previous = temp;
				n.next = tail;
				tail.previous = n;
			}
		}
		// Increases the size of the list
		size++;
		return true;
	}

	/**
	 * A simple contains method to search through list to check for a duplicate before adding
	 *
	 * @param item the item to search for
	 * @return whether the list contains the item or not
	 */
	public boolean contains(E item) {
		if(size < 1) {
			return false;
		}
		Node temp = head.next;
		while(temp != tail) {
			for(int i=0;i<temp.count;i++) {
				if(temp.data[i].equals(item))
					return true;
				temp = temp.next;
			}
		}
		return false;
	}

	/**
	 * Adds item to a specific index
	 * Basically followed the guideline from the project description
	 *
	 * @param pos the position where the item should go to
	 * @param item the item to add to the list
	 */
	@Override
	public void add(int pos, E item) {
		
		// If pos is out of bounds, throw IndexOutOfBoundsException
		if (pos < 0 || pos > size)
			throw new IndexOutOfBoundsException("Index is out of bounds");
		
		// If the list is empty it creates a new node and put X at offset 0
		if (head.next == tail)
			add(item);
		NodeInfo nodeInfo = find(pos);
		Node temp = nodeInfo.node;
		int offset = nodeInfo.offset;
		if (offset == 0) {
			if (temp.previous.count < nodeSize && temp.previous != head) {
				temp.previous.addItem(item);
				size++;
				return;
			}
			else if (temp == tail) {
				add(item);
				size++;
				return;
			}
		}
		if (temp.count < nodeSize) {
			temp.addItem(offset, item);
		}
		else {
			Node newS = new Node();
			int half = nodeSize / 2;
			int count = 0;
			while (count < half) {
				newS.addItem(temp.data[half]);
				temp.removeItem(half);
				count++;
			}
			Node oldS = temp.next;
			temp.next = newS;
			newS.previous = temp;
			newS.next = oldS;
			oldS.previous = newS;
			if (offset <= nodeSize / 2) {
				temp.addItem(offset, item);
			}
			if (offset > nodeSize / 2) {
				newS.addItem((offset - nodeSize / 2), item);
			}
		}
		//Increases the size of the list
		size++;
	}

	/**
	 * Removes an item that is in a specific index
	 * Basically followed the guideline from the project description
	 * 
	 * @param pos the position where the item should be removed
	 * @return item that was removed
	 */
	@Override
	public E remove(int pos) {
		if (pos < 0 || pos > size)
			throw new IndexOutOfBoundsException("Index is out of bounds");
		NodeInfo nodeInfo = find(pos);
		Node tempo = nodeInfo.node;
		int offset = nodeInfo.offset;
		E nodeVal = tempo.data[offset];
		if (tempo.next == tail && tempo.count == 1) {
			Node predecessor = tempo.previous;
			predecessor.next = tempo.next;
			tempo.next.previous = predecessor;
		}
		else if (tempo.next == tail || tempo.count > nodeSize / 2) {
			tempo.removeItem(offset);
		}
		else {
			tempo.removeItem(offset);
			Node succesor = tempo.next;
			if (succesor.count > nodeSize / 2) {
				tempo.addItem(succesor.data[0]);
				succesor.removeItem(0);
			}
			else if (succesor.count <= nodeSize / 2) {
				for (int i = 0; i < succesor.count; i++) {
					tempo.addItem(succesor.data[i]);
				}
				tempo.next = succesor.next;
				succesor.next.previous = tempo;
				succesor = null;
			}
		}
		//Decreases the size of the list 
		size--;
		return nodeVal;
	}

	/**
	 * Sort all elements in the stout list in the NON-DECREASING order. You may do
	 * the following. Traverse the list and copy its elements into an array,
	 * deleting every visited node along the way. Then, sort the array by calling
	 * the insertionSort() method. (Note that sorting efficiency is not a concern
	 * for this project.) Finally, copy all elements from the array back to the
	 * stout list, creating new nodes for storage. After sorting, all nodes but
	 * (possibly) the last one must be full of elements.
	 * 
	 * Comparator<E> must have been implemented for calling insertionSort().
	 */
	public void sort() {
		E[] sortDataL = (E[]) new Comparable[size];
		int tempI = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				sortDataL[tempI] = temp.data[i];
				tempI++;
			}
			temp = temp.next;
		}
		head.next = tail;
		tail.previous = head;
		insertionSort(sortDataL, new ElementComparator());
		size = 0;
		for (int i = 0; i < sortDataL.length; i++){
			add(sortDataL[i]);
		}

	}

	/**
	 * Sort all elements in the stout list in the NON-INCREASING order. Call the
	 * bubbleSort() method. After sorting, all but (possibly) the last nodes must be
	 * filled with elements.
	 * 
	 * Comparable<? super E> must be implemented for calling bubbleSort().
	 */
	public void sortReverse() {
		E[] rsortDataL = (E[]) new Comparable[size];
		int tempI = 0;
		Node temp = head.next;
		while (temp != tail) {
			for (int i = 0; i < temp.count; i++) {
				rsortDataL[tempI] = temp.data[i];
				tempI++;
			}
			temp = temp.next;
		}
		head.next = tail;
		tail.previous = head;
		bubbleSort(rsortDataL);
		size = 0;
		for (int i = 0; i < rsortDataL.length; i++) {
			add(rsortDataL[i]);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new StoutListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new StoutListIterator(index);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes.
	 */
	public String toStringInternal() {
		return toStringInternal(null);
	}

	/**
	 * Returns a string representation of this list showing the internal structure
	 * of the nodes and the position of the iterator.
	 *
	 * @param iter an iterator for this list
	 */
	public String toStringInternal(ListIterator<E> iter) {
		int count = 0;
		int position = -1;
		if (iter != null) {
			position = iter.nextIndex();
		}

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		Node current = head.next;
		while (current != tail) {
			sb.append('(');
			E data = current.data[0];
			if (data == null) {
				sb.append("-");
			} else {
				if (position == count) {
					sb.append("| ");
					position = -1;
				}
				sb.append(data.toString());
				++count;
			}

			for (int i = 1; i < nodeSize; ++i) {
				sb.append(", ");
				data = current.data[i];
				if (data == null) {
					sb.append("-");
				} else {
					if (position == count) {
						sb.append("| ");
						position = -1;
					}
					sb.append(data.toString());
					++count;

					// iterator at end
					if (position == size && count == size) {
						sb.append(" |");
						position = -1;
					}
				}
			}
			sb.append(')');
			current = current.next;
			if (current != tail)
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Node type for this list. Each node holds a maximum of nodeSize elements in an
	 * array. Empty slots are null.
	 */
	private class Node {
		/**
		 * Array of actual data elements.
		 */
		public E[] data = (E[]) new Comparable[nodeSize];

		/**
		 * Link to next node.
		 */
		public Node next;

		/**
		 * Link to previous node;
		 */
		public Node previous;

		/**
		 * Index of the next available offset in this node, also equal to the number of
		 * elements in this node.
		 */
		public int count;

		/**
		 * Adds an item to this node at the first available offset. Precondition: count
		 * < nodeSize
		 * 
		 * @param item element to be added
		 */
		void addItem(E item) {
			if (count >= nodeSize) {
				return;
			}
			data[count++] = item;
			// useful for debugging
			// System.out.println("Added " + item.toString() + " at index " + count + " to
			// node " + Arrays.toString(data));
		}

		/**
		 * Adds an item to this node at the indicated offset, shifting elements to the
		 * right as necessary.
		 * 
		 * Precondition: count < nodeSize
		 * 
		 * @param offset array index at which to put the new element
		 * @param item   element to be added
		 */
		void addItem(int offset, E item) {
			if (count >= nodeSize) {
				return;
			}
			for (int i = count - 1; i >= offset; --i) {
				data[i + 1] = data[i];
			}
			count++;
			data[offset] = item;
			// useful for debugging
			//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
		}

		/**
		 * Deletes an element from this node at the indicated offset, shifting elements
		 * left as necessary. Precondition: 0 <= offset < count
		 * 
		 * @param offset
		 */
		void removeItem(int offset) {
			E item = data[offset];
			for (int i = offset + 1; i < nodeSize; ++i) {
				data[i - 1] = data[i];
			}
			data[count - 1] = null;
			--count;
		}
	}

	/**
	 * The helper class called NodeInfo
	 */
	private class NodeInfo {
		public Node node;
		public int offset;

		public NodeInfo(Node node, int offset) {
			this.node = node;
			this.offset = offset;
		}
	}

	/**
	 * The helper method to locate an specific item called find
	 * 
	 * @param int pos 
	 */
	private NodeInfo find(int pos) {
		Node temp = head.next;
		int currPos = 0;
		while (temp != tail) {
			if (currPos + temp.count <= pos) {
				currPos += temp.count;
				temp = temp.next;
				continue;
			}
			NodeInfo nodeInfo = new NodeInfo(temp, pos - currPos);
			return nodeInfo;

		}
		return null;
	}

	/**
	 * Custom Iterator for StoutList
	 */
	private class StoutListIterator implements ListIterator<E> {

		final int lastPrev = 0;
		final int lastNext = 1;

		/**
		 * pointer of iterator
		 */
		int current;
		
		/**
		 * data structure of iterator in array form
		 */
		public E[] dataL;
		
		/**
		 * tracks the lastAction taken by the program
		 * it is mainly used for remove() and set() method to determine
		 * which item to remove or set
		 */
		int last_Action;

		/**
		 * Default constructor
		 * Sets the pointer of iterator to the beginning of the list
		 */
		public StoutListIterator() {
			current = 0;
			last_Action = -1;
			setup();
		}

		/**
		 * Constructor finds node at a given position.
		 * Sets the pointer of iterator to the specific index of the list
		 * 
		 * @param pos
		 */
		public StoutListIterator(int pos) {
			current = pos;
			last_Action = -1;
			setup();
		}

		/**
		 * Takes the StoutList and put its data into dataList in an array form
		 */
		private void setup() {
			dataL = (E[]) new Comparable[size];
			int tempI = 0;
			Node temp = head.next;
			while (temp != tail) {
				for (int i = 0; i < temp.count; i++) {
					dataL[tempI] = temp.data[i];
					tempI++;
				}
				temp = temp.next;
			}
		}

		/**
		 * @return whether iterator has next available value or not
		 */
		@Override
		public boolean hasNext() {
			return (current < size);
		}

		/**
		 * Returns the next ready value and shifts the pointer by 1
		 * 
		 * @return the next ready value of the iterator
		 */
		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			last_Action = lastNext;
			return dataL[current++];
		}

		/**
		 * Removes from the list the last element returned by next() or previous().
		 * Can only be called once per call of next() or previous()
		 * Also removes the element from the StoutList
		 */
		@Override
		public void remove() {
			if (last_Action == lastNext) {
				StoutList.this.remove(current - 1);
				setup();
				last_Action = -1;
				current--;
				if (current < 0) {
					current = 0;
				}
			} else if (last_Action == lastPrev) {
				StoutList.this.remove(current);
				setup();
				last_Action = -1;
			} else {
				throw new IllegalStateException();
			}
		}

		/**
		 * @return whether iterator has previous available value or not
		 */
		@Override
		public boolean hasPrevious() {
			if (current <= 0)
				return false;
			else {
				return true;		
			}
		}

		/**
		 * @return index of next available element
		 */
		@Override
		public int nextIndex() {
			return current;
		}
		
		/**
		 * Returns previous available element and shifts pointer by -1
		 * 
		 * @return previous available element
		 */
		@Override
		public E previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			last_Action = lastPrev;
			current--;
			return dataL[current];
		}

		/**
		 * @return index of previous element
		 */
		@Override
		public int previousIndex() {
			return (current - 1);
		}

		/**
		 * Replaces the element at the current pointer
		 * 
		 * @param arg0 replacing element
		 */
		@Override
		public void set(E e) {
			if (last_Action == lastNext) {
				NodeInfo node = find(current - 1);
				node.node.data[node.offset] = e;
				dataL[current - 1] = e;
			} else if (last_Action == lastPrev) {
				NodeInfo node = find(current);
				node.node.data[node.offset] = e;
				dataL[current] = e;
			} else {
				throw new IllegalStateException();
			}

		}

		/**
		 * Adds an element to the end of the list
		 * 
		 * @param arg0 adding element
		 */
		@Override
		public void add(E e) {
			StoutList.this.add(current, e);
			current++;
			setup();
			last_Action = -1;

		}

		// Other methods you may want to add or override that could possibly facilitate
		// other operations, for instance, addition, access to the previous element,
		// etc.
		//
		// ...
		//
	}

	/**
	 * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING
	 * order.
	 * 
	 * @param arr  array storing elements from the list
	 * @param comp comparator used in sorting
	 */
	private void insertionSort(E[] arr, Comparator<? super E> comp) {
		for (int i = 1; i < arr.length; i++) {
			E key = arr[i];
			int j = i - 1;
			while (j >= 0 && comp.compare(arr[j], key) > 0) {
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = key;
		}
	}

	/**
	 * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a
	 * description of bubble sort please refer to Section 6.1 in the project
	 * description. You must use the compareTo() method from an implementation of
	 * the Comparable interface by the class E or ? super E.
	 * 
	 * @param arr array holding elements from the list
	 */
	private void bubbleSort(E[] arr) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n - i - 1; j++) {
				if (arr[j].compareTo(arr[j + 1]) < 0) {
					E temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
			}
		}

	}

	/**
	 * Custom Comparator to be used by insertion sort.
	 */
	class ElementComparator<E extends Comparable<E>> implements Comparator<E> {
		@Override
		public int compare(E arg0, E arg1) {
			return arg0.compareTo(arg1);
		}

	}

}

