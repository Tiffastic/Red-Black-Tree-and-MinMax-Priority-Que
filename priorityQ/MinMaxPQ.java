package priorityQ;

import java.util.NoSuchElementException;

public class MinMaxPQ<T extends Comparable<T>>
{

	private Node head;
	private Node smallestNode;
	private Node largestNode;
	private int size;

	public void add(T element)
	{
		if (element == null)
		{
			throw new NullPointerException("Null element not allowed");
		}

		if (head == null)
		{
			head = new Node(element, null, null, null);
			head.red = false;
			head.Iam = Child.ROOT;

			largestNode = head;
			smallestNode = head;
		}
		else if (element.compareTo(largestNode.data) >= 0)
		{
			insertIterative(element, largestNode);
		}
		else if (element.compareTo(smallestNode.data) <= 0)
		{
			insertIterative(element, smallestNode);
		}
		else
		{
			// insert(element, head);
			insertIterative(element, head);
		}

		size++;

	}

	public T min()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException("MinMaxPQ is empty");
		}

		return smallestNode.data;
	}

	public T max()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException("MinMaxPQ is empty");
		}

		return largestNode.data;
	}

	public T removeMin()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException("MinMaxPQ is empty");
		}

		T smallest = smallestNode.data;
		Node nextSmallest = nextSmallestNode();
		// the next smallest will be either the right child or the parent
		delete(smallestNode);
		size--;
		smallestNode = nextSmallest;
		return smallest;
	}

	public T removeMax()
	{
		if (isEmpty())
		{
			throw new NoSuchElementException("MinMaxPQ is empty");
		}

		T biggest = largestNode.data;
		Node nextLargest = nextLargestNode();
		delete(largestNode);
		// next largest Node is either the left child, if there is not one,
		// then the parent
		size--;
		largestNode = nextLargest;

		return biggest;
	}

	public boolean isEmpty()
	{
		return head == null;
	}

	/** Extra code **/
	public int size()
	{
		return size;
	}

	public T delete(T element)
	{
		Node target = findElement(element, head);
		if (target == null)
		{
			throw new NoSuchElementException(element + " is not in the this");
		}

		if (target == smallestNode)
		{
			return removeMin();
		}
		else if (target == largestNode)
		{
			return removeMax();
		}
		else
		{
			delete(target);
			size--;
			return element;
		}

	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("[ ");
		getStringInOrder(sb, head);
		sb.append("]");

		return sb.toString();
	}

	// for printing red-black tree
	public void getLevelOrder(T[] levelOrder, Boolean[] redBlack)
	{
		makeArray(levelOrder, redBlack, head, 1);
	}

	/**
	 * private methods for public code These private methods support the
	 * public code
	 */
	private void insertIterative(T element, Node x)
	{
		boolean hasAdded = false;
		while (!hasAdded)
		{
			if (element.compareTo(x.data) < 0)
			{
				if (x.left == null)
				{
					x.left = new Node(element, null, null, x);
					x.left.Iam = Child.LEFT;

					if (element.compareTo(smallestNode.data) <= 0)
					{
						smallestNode = x.left;
					}

					balanceTree(x.left);
					hasAdded = true;
				}
				else
				{
					x = x.left;
				}
			}
			else
			{
				if (x.right == null)
				{
					x.right = new Node(element, null, null, x);
					x.right.Iam = Child.RIGHT;
					if (element.compareTo(largestNode.data) >= 0)
					{
						largestNode = x.right;
					}

					balanceTree(x.right);
					hasAdded = true;
				}
				else
				{
					x = x.right;
				}
			}
		}

	}

	private void insertRecursive(T element, Node x)
	{
		if (element.compareTo(x.data) < 0)
		{
			if (x.left == null)
			{
				x.left = new Node(element, null, null, x);
				x.left.Iam = Child.LEFT;

				if (element.compareTo(smallestNode.data) < 0)
				{
					smallestNode = x.left;
				}

				balanceTree(x.left);
			}
			else
			{
				insertRecursive(element, x.left);
			}
		}
		else
		{
			if (x.right == null)
			{
				x.right = new Node(element, null, null, x);
				x.right.Iam = Child.RIGHT;
				if (element.compareTo(largestNode.data) > 0)
				{
					largestNode = x.right;
				}

				balanceTree(x.right);
			}
			else
			{
				insertRecursive(element, x.right);
			}
		}
	}

	private void balanceTree(Node x)
	{
		if (x == head)
		{
			// x.Iam = Child.ROOT;
			x.red = false;
			return;
		}
		if (x.parent.red == false)
		{
			return;
		}

		// now we know that parent is red
		Node grandparent = x.parent.parent;
		grandparent.red = true;

		// case 1: left uncle is black
		boolean leftUncleIsBlack = grandparent.left == null || grandparent.left.red == false ? true : false;

		if (leftUncleIsBlack)
		{
			if (x.Iam == Child.RIGHT)
			{
				// child is on opposite side of uncle
				grandparent.right.red = false;
			}
			else
			{
				// child is on same side of uncle
				x.red = false;
				rotateRight(x.parent);
			}

			rotateLeft(grandparent);
			return;
		}

		// case 2: right uncle is black
		boolean rightUncleIsBlack = grandparent.right == null || grandparent.right.red == false;

		if (rightUncleIsBlack)
		{
			// child is opposite of uncle
			if (x.Iam == Child.LEFT)
			{
				grandparent.left.red = false;
			}
			else
			{
				// child is same side of uncle
				x.red = false;
				rotateLeft(x.parent);
			}

			rotateRight(grandparent);
			return;
		}

		// case 3: the uncle is red, move red up and balance tree again
		boolean uncleIsRed = grandparent.left.red == true && grandparent.right.red == true ? true : false;

		if (uncleIsRed)
		{
			grandparent.left.red = false;
			grandparent.right.red = false;
			balanceTree(grandparent);
			return;
		}

	}

	private void rotateRight(Node x)
	{
		// nodes that are affected:
		Node leftChild = x.left;
		Node grandchild = leftChild.right;
		Node grandparent = x.parent;

		leftChild.parent = grandparent;
		leftChild.right = x;

		x.parent = leftChild;
		x.left = grandchild;

		leftChild.Iam = x.Iam;
		x.Iam = Child.RIGHT;

		if (grandchild != null)
		{
			grandchild.Iam = Child.LEFT;
			grandchild.parent = x;
		}

		if (x == head)
		{
			head = leftChild;
			return;
		}

		if (leftChild.Iam == Child.LEFT)
		{
			grandparent.left = leftChild;
		}
		else
		{
			grandparent.right = leftChild;
		}
	}

	private void rotateLeft(Node x)
	{
		// nodes that will be affected:
		Node rightChild = x.right;
		Node grandchild = x.right.left;
		Node grandparent = x.parent;

		rightChild.parent = grandparent;
		rightChild.left = x;

		x.parent = rightChild;
		x.right = grandchild;

		rightChild.Iam = x.Iam;
		x.Iam = Child.LEFT;

		if (grandchild != null)
		{
			grandchild.Iam = Child.RIGHT;
			grandchild.parent = x;
		}

		if (x == head)
		{
			head = rightChild;
			return;
		}

		if (rightChild.Iam == Child.LEFT)
		{
			grandparent.left = rightChild;
		}
		else
		{
			grandparent.right = rightChild;
		}

	}

	private Node findElement(T element, Node x)
	{
		if (x == null)
		{
			return null;
		}

		if (element.compareTo(x.data) == 0)
		{
			return x;
		}
		else if (element.compareTo(x.data) < 0)
		{
			return findElement(element, x.left);
		}
		else
		{
			return findElement(element, x.right);
		}

	}

	private void delete(Node x)
	{
		if (x == null)
		{
			return;
		}
		// if x is a leaf, delete it
		if (x.right == null && x.left == null)
		{
			if (x.Iam == Child.LEFT)
			{
				x.parent.left = null;
			}
			else if (x.Iam == Child.RIGHT)
			{
				x.parent.right = null;
			}
			else if (x.Iam == Child.ROOT)
			{
				head = null;
			}

			balanceTreeDeletion(x);
			return;
		}

		// x has only one child, the left
		if (x.right == null)
		{
			if (x.Iam == Child.LEFT)
			{
				x.parent.left = x.left;

			}
			else if (x.Iam == Child.RIGHT)
			{
				x.parent.right = x.left;
			}
			else if (x.Iam == Child.ROOT)
			{
				head = x.left;
				x.left.Iam = Child.ROOT;
			}

			x.left.Iam = x.Iam;
			x.left.parent = x.parent;
			balanceTreeDeletion(x.left);
			return;
		}

		// x has only one child, the right
		if (x.left == null)
		{
			if (x.Iam == Child.RIGHT)
			{
				x.parent.right = x.right;
			}
			else if (x.Iam == Child.LEFT)
			{
				x.parent.left = x.right;
			}
			else if (x.Iam == Child.ROOT)
			{
				head = x.right;
				x.right.Iam = Child.ROOT;
			}

			x.right.Iam = x.Iam;
			x.right.parent = x.parent;
			balanceTreeDeletion(x.right);
			return;
		}

		// x has two children, replace x with its in order
		// predecessor
		// find its precessor and switch x's data with the precessor,
		// recursively delete the predecessor
		boolean xHasTwoChildren = x.left != null && x.right != null;
		if (xHasTwoChildren)
		{
			Node predecessor = findPredecessor(x.left);
			T temp = x.data;
			x.data = predecessor.data;
			predecessor.data = temp;

			delete(predecessor);
		}

	}

	private Node findPredecessor(Node x)
	{
		if (x.right == null)
		{
			return x;
		}

		return findPredecessor(x.right);
	}

	private void balanceTreeDeletion(Node x)
	{
		if (x.Iam == Child.ROOT)
		{
			x.red = false;
			return;
		}

		if (x.red == true)
		{
			// absorb the black
			x.red = false;
			return;
		}

		// x is black and is not the root
		Node parent = x.parent;

		if (x.Iam == Child.RIGHT)
		{
			Node sibling = parent.left;

			if (sibling == null)
			{
				x.red = true;
				balanceTreeDeletion(parent);
				return;
			}
			// case 1: sibling is black and all sibling's children are
			// black or null or combination, but not red
			if (sibling.red == false && (sibling.left == null || sibling.left.red == false) && (sibling.right == null || sibling.right.red == false))
			{
				sibling.red = true;
				balanceTreeDeletion(parent);
				return;
			}

			// case 2: if sibling is black it has an aligning (same side)
			// red child
			if (sibling.red == false && sibling.left != null && sibling.left.red == true)
			{
				sibling.red = parent.red;
				parent.red = false;
				sibling.left.red = false;
				rotateRight(parent);
				return;
			}

			// case 3: sibling is black and it has a misaligning (opposite
			// side) red child
			if (sibling.red == false && sibling.right != null && sibling.right.red == true)
			{
				sibling.right.red = parent.red;
				rotateLeft(sibling);
				parent.red = false;
				rotateRight(parent);
				return;
			}

			// case 4: parent is black, sibling is red
			if (parent.red == false && sibling.red == true)
			{
				sibling.red = false;
				parent.red = true;
				rotateRight(parent);
				balanceTreeDeletion(x);
				return;
			}

		}

		if (x.Iam == Child.LEFT)
		{

			Node sibling = parent.right;

			// case 1: sibling and all its children are black
			if (sibling.red == false && (sibling.left == null || sibling.left.red == false) && (sibling.right == null || sibling.right.red == false))
			{
				sibling.red = true;
				balanceTreeDeletion(parent);
				return;
			}

			// case 2: when sibling is black and has aligning (same side)
			// red child
			if (sibling.red == false && sibling.right != null && sibling.right.red == true)
			{
				sibling.red = parent.red;
				parent.red = false;
				sibling.right.red = false;

				rotateLeft(parent);
				return;
			}

			// case 3: sibling is black and has a misaligning (opposite
			// side) red child
			if (sibling.red == false && sibling.left != null && sibling.left.red == true)
			{
				sibling.left.red = parent.red;
				rotateRight(sibling);
				parent.red = false;
				rotateLeft(parent);
				return;
			}

			// case 4: parent is black, sibling is red
			if (parent.red == false && sibling.red == true)
			{
				parent.red = true;
				sibling.red = false;
				rotateLeft(parent);
				balanceTreeDeletion(x);
				return;
			}
		}
	}

	private Node nextSmallestNode()
	{

		return smallestNode.right == null ? smallestNode.parent : smallestNode.right;
	}

	private Node nextLargestNode()
	{
		return largestNode.left == null ? largestNode.parent : largestNode.left;
	}

	private void getStringInOrder(StringBuilder sb, Node x)
	{
		if (x == null)
		{
			return;
		}

		getStringInOrder(sb, x.left);
		sb.append(x.data).append(" ");
		getStringInOrder(sb, x.right);

	}

	private void makeArray(T[] array, Boolean[] redBlack, Node x, int index)
	{
		if (x == null)
		{
			return;
		}

		array[index] = x.data;
		redBlack[index] = x.red == true ? true : false;
		makeArray(array, redBlack, x.left, 2 * index);
		makeArray(array, redBlack, x.right, 2 * index + 1);
	}

	private enum Child
	{
		LEFT, RIGHT, ROOT;
	}

	private class Node
	{

		Node left, right, parent;
		T data;
		boolean red = true;
		Child Iam;

		public Node(T element, Node leftChild, Node rightChild, Node parent)
		{
			data = element;
			left = leftChild;
			right = rightChild;
			this.parent = parent;
		}

		@Override
		public String toString()
		{
			return String.format("data = %s, red = %s, I am = %s, left = %s, right = %s, parent = %s", data, red, Iam, left == null ? "null" : left.data, right == null ? "null" : right.data, parent == null ? "null" : parent.data);
		}
	}
}
