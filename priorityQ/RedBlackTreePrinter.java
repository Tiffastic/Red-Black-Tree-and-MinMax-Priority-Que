package priorityQ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Thuy Nguyen This project prints out a red black tree of any size
 * However, the console might not output it nicely sometimes. So just run
 * it again to see a correctly aligned red black tree. July 25, 2014
 */
public class RedBlackTreePrinter
{
	public static void main(String[] args)
	{
		int[] numbers = {10, 85, 15, 70, 20, 60, 30, 50, 65, 80, 90, 40, 5, 55};
		printTree("Red black tree by Thuy for Margaret", numbers);

	}

	public static void printTree(String treeName, int[] numbers)
	{
		System.out.printf("%s:\n\n", treeName);
		printTree(numbers);
	}

	public static void printTree(int[] numbers)
	{
		System.out.println(Arrays.toString(numbers));
		System.out.println();

		// insert numbers into a red black tree
		MinMaxPQ<Integer> tree = new MinMaxPQ<>();
		for (int i = 0; i < numbers.length; i++)
		{
			tree.add(numbers[i]);
		}

		printRedBlackTree(tree);
		System.out.printf("%n%n%n%n");

	}

	public static void printTree(String title, MinMaxPQ<Integer> tree)
	{
		System.out.println(title + "\n");
		printTree(tree);
	}

	public static void printTree(MinMaxPQ<Integer> tree)
	{
		printRedBlackTree(tree);
	}

	public static void fillTree(MinMaxPQ<Integer> tree, int[] numbers)
	{
		for (int i = 0; i < numbers.length; i++)
		{
			tree.add(numbers[i]);
		}
	}

	public static void printRedBlackTree(MinMaxPQ<Integer> tree)
	{
		if (tree.isEmpty())
		{
			return;
		}

		// put elements into array by level order
		int maxHeight = (int) (2 * Math.log(tree.size() + 1));
		int maxSlots = (int) Math.pow(2, maxHeight + 1);
		Integer[] levelOrder = new Integer[maxSlots + 1];
		Boolean[] redOrBlack = new Boolean[maxSlots + 1];
		tree.getLevelOrder(levelOrder, redOrBlack);
		// store each node's data and its two children nodes's data
		// beginning at index 1
		// left child = 2*parent's index
		// right child = (2*parent's index) + 1

		// calculate height of tree
		int maxIndex = lastIndexOfTree(levelOrder);
		final int height = calculateHeight(maxIndex);

		// group elements into their respective levels
		List<Integer[]> levelGroup = new ArrayList<>();
		List<Boolean[]> levelGroupRedBlack = new ArrayList<>();
		groupElementsByLevel(height, levelOrder, levelGroup, redOrBlack, levelGroupRedBlack);

		// print each group level in binary tree format
		printNumbersByLevel(height, levelGroup, levelGroupRedBlack);

	}

	private static int lastIndexOfTree(Integer[] levelOrder)
	{
		// find where the last number is in the levelOrder array
		// this is how we can find the height of the tree later on
		int maxIndex = 0;
		for (int i = levelOrder.length-1; i >= 0; i--)  // start looking at the end of the array
		{
			if (levelOrder[i] != null)   // the first array slot that is not null is where the last number is
			{
				maxIndex = i;
				break;
			}
		}
		return maxIndex;

	}

	private static int calculateHeight(int maxIndex)
	{
		int level = maxIndex;  // maxIndex is where the last number is in the levelOrder array
		int height = 0;

		while (level > 1)
		{
			level = level >> 1;   // take half each time to get to the upper level of the tree
			height++;                // each level increases the height of the tree
		}

		return height;

	}

	private static void groupElementsByLevel(int height,
			Integer[] levelOrder, List<Integer[]> levelGroup, Boolean[] redOrBlack, List<Boolean[]> redBlackGroup)
	{
		// each number is grouped with other numbers in its same level
		for (int level = 0; level <= height; level++)
		{
			int startingAt = (int) Math.pow(2, level);
			int nextLevel = (int) Math.pow(2, level+1);
			Integer[] groupNumber = Arrays.copyOfRange(levelOrder, startingAt, nextLevel);
			Boolean[] groupBoolean = Arrays.copyOfRange(redOrBlack, startingAt, nextLevel);

			levelGroup.add(groupNumber);
			redBlackGroup.add(groupBoolean);
		}

	}

	private static void printNumbersByLevel(final int height,
			List<Integer[]> levelGroup, List<Boolean[]> redBlackGroup)
	{
		for (int level = height, group = 0; level >= 0; level--, group++)  // print each group level
		{
			int spaces = (int) Math.pow(2, level)*2;  // space the tree out to the right
			for (int i = 0; i < levelGroup.get(group).length; i++)  // print each number in group.
			{
				String number = "";
				if (levelGroup.get(group)[i] != null) // only print out the number, if null, leave blank
				{
					number = String.format("%02d", levelGroup.get(group)[i]);  // format number so each number has 2 digits (8 --> 08)
				}

				System.out.print(String.format("%" + spaces + "s", number));  // print the number
				spaces = (int) Math.pow(2, level)*2*2;   // space out the next level order number
			}

			System.out.println();

			if (group != height)
			{
				// print edges according to level
				printEdges(levelGroup, redBlackGroup, level, group);
			}

		}

	}

	private static void printEdges(List<Integer[]> levelGroup, List<Boolean[]> redBlackGroup, int level, int group)
	{
		int edgeHeight = (int) Math.pow(2, level);  // the spaces needed for all the edges
		int rightChildAt = 3;  // right child edge at 3 spaces after left child edge initially
		int spacesBetwnTrees = (int) Math.pow(2, level)*2-1;  // spaces between level order subtrees

		for (int i = 2; i <= edgeHeight; i += 2)  // iterate through for all edge height
		{
			int leftChildAt = (int) Math.pow(2, level)*2 - i;  // start edge one space before character on upper line

			for (int currentNumberAt = 0, childAt = 0; currentNumberAt < levelGroup.get(group).length; currentNumberAt++, childAt += 2) //  for each number in the group, print out its edges if it has children
			{
				String leftEdge = String.format("%" + leftChildAt + "s", levelGroup.get(group + 1)[childAt] == null ? "" : "/");
				// group + 1 = next group where child/null is

				if (redBlackGroup.get(group + 1)[childAt] != null)
				{
					// there is a child and it's red
					boolean leftRedLink = redBlackGroup.get(group + 1)[childAt];
					if (leftRedLink)
					{
						System.err.print(leftEdge);
					}
					else
					{
						// black child
						System.out.print(leftEdge);
					}

				}
				else
				{
					System.out.print(leftEdge);
				}

				String rightEdge = String.format("%" + rightChildAt + "s", levelGroup.get(group+1)[childAt+1] == null? "" :	"\\" );

				if (redBlackGroup.get(group + 1)[childAt + 1] != null)
				{
					// if there is a right child, print right edge
					boolean rightRedLink = redBlackGroup.get(group + 1)[childAt + 1];
					if (rightRedLink)
					{
						// red child
						System.err.print(rightEdge);
					}
					else
					{
						// black child
						System.out.print(rightEdge);
					}
				}
				else
				{
					System.out.print(rightEdge);
				}


				System.out.print(String.format("%" + spacesBetwnTrees + "s", ""));  // print spaces for next level order sub tree; to the right of current tree
			}

			System.out.println();
			rightChildAt += 4;  // increase spacing of stems between children
			spacesBetwnTrees -= 2;  // decrease spacing of stems between level order sub trees
		}

	}

}
