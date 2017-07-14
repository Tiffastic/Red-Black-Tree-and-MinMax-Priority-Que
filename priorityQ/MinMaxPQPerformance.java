package priorityQ;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class MinMaxPQPerformance
{
	public static void main(String[] args)
	{
		/** testing may take a while **/

		performanceAdd();
		// performanceGetMin();
		// performanceGetMax();
		// performanceRemoveMin();
		// performanceRemoveMax();
		// performanceIsEmpty();

	}

	private enum Test
	{
		ADD, MIN, MAX, REMOVE_MIN, REMOVE_MAX, IS_EMPTY;
	}

	private static void performanceTest(String fileName, String testName, Test typeOf)
	{
		try
		{
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);

			Label testLabel = new Label(0, 0, testName);
			Label label1 = new Label(0, 2, "n elements");
			Label label2 = new Label(2, 2, "nanoseconds");

			sheet.addCell(testLabel);
			sheet.addCell(label1);
			sheet.addCell(label2);

			for (int i = 100, j = 3; i < 3000000; i += 100, j++)
			{
				MinMaxPQ<Integer> pq = new MinMaxPQ<>();
				double start = System.nanoTime();

				if (typeOf == Test.ADD)
				{
					addNumbersToMinMaxPQ(i, pq);
				}
				else
				{
					addNumbersToMinMaxPQ(i, pq);
					start = System.nanoTime();

					if (typeOf == Test.MIN)
					{
						pq.min();
					}
					else if (typeOf == Test.REMOVE_MIN)
					{
						pq.removeMin();

					}
					else if (typeOf == Test.MAX)
					{
						pq.max();
					}
					else if (typeOf == Test.REMOVE_MAX)
					{
						pq.removeMax();
					}
					else if (typeOf == Test.IS_EMPTY)
					{
						pq.isEmpty();
					}
				}


				double end = System.nanoTime();
				double result = end - start;

				Number nElements = new Number(0, j, i);
				Number performance = new Number(2, j, result);
				sheet.addCell(nElements);
				sheet.addCell(performance);
			}

			workbook.write();
			workbook.close();
		}
		catch (WriteException | IOException e)
		{
			System.err.println(e.getMessage());
		}

	}

	private static void performanceAdd()
	{
		String testAddFile = "C:/Users/Thuy/Documents/2420/MinMaxPerformanceTest/testAddOptimized2.xls";
		String testAddName = "Test .add() method";
		performanceTest(testAddFile, testAddName, Test.ADD);
	}

	private static void performanceGetMin()
	{
		String testMinFile = "C:/Users/Thuy/Documents/2420/MinMaxPerformanceTest/testMin.xls";
		String testMinName = "Test .min() method";
		performanceTest(testMinFile, testMinName, Test.MIN);
	}

	private static void performanceGetMax()
	{
		String testMaxFile = "C:/Users/Thuy/Documents/2420/MinMaxPerformanceTest/testMax.xls";
		String testMaxName = "Test .max() method";
		performanceTest(testMaxFile, testMaxName, Test.MAX);
	}

	private static void performanceRemoveMin()
	{
		String testRemoveMinFile = "C:/Users/Thuy/Documents/2420/MinMaxPerformanceTest/testRemoveMin.xls";
		String testRemoveMinName = "Test .removeMin() method";
		performanceTest(testRemoveMinFile, testRemoveMinName, Test.REMOVE_MIN);
	}

	private static void performanceRemoveMax()
	{

		String testRemoveMaxFile = "C:/Users/Thuy/Documents/2420/MinMaxPerformanceTest/testRemoveMax.xls";
		String testRemoveMaxName = "Test .removeMax() method";
		performanceTest(testRemoveMaxFile, testRemoveMaxName, Test.REMOVE_MAX);
	}

	private static void performanceIsEmpty()
	{
		String testIsEmptyFile = "C:/Users/Thuy/Documents/2420/MinMaxPerformanceTest/testIsEmpty.xls";
		String testIsEmptyName = "Test .isEmpty() method";
		performanceTest(testIsEmptyFile, testIsEmptyName, Test.IS_EMPTY);

	}

	private static void addNumbersToMinMaxPQ(int n, MinMaxPQ<Integer> pq)
	{
		for (int i = 1; i <= n; i++)
		{
			pq.add(i);
		}
	}

}
