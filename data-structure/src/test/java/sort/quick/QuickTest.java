package sort.quick;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuickTest {

	private static int loop = 0;

	@Before
	public void before() {
		loop = 0;
	}

	@After
	public void after() {
		System.out.println(String.format("total count : %d", loop));
	}

	public static void sortStack(int[] list, int size) {
		int low = 0;
		int high = size - 1;
		int pivotLocation;

		Stack<IntPair> s = new Stack<IntPair>();
		s.push(new IntPair(0, -1)); // a marker to identify the bottom of the stack
		while (!s.isEmpty()) {
			while (low <= high) {
				pivotLocation = partition(list, low, high);
				s.push(new IntPair(pivotLocation + 1, high)); // record info for second recursive call
				high = pivotLocation - 1; // execute first recursive call
			}
			IntPair pop = s.pop(); // fetch next recursive call to execute
			low = pop.getLeft();
			high = pop.getRight();
		}
	}

	public static void sort(int dataSet[], int left, int right) {
		loop++;
		if (left < right) {
			int index = partition(dataSet, left, right);
			sort(dataSet, left, index - 1);
			sort(dataSet, index + 1, right);
		}
	}

	private static int partition(int dataSet[], int left, int right) {

		int first = left;
		int pivot = dataSet[first];

		++left;

		while (left <= right) {
			while (dataSet[left] <= pivot && left < right) {
				++left;
			}
			while (dataSet[right] > pivot && left <= right) {
				--right;
			}
			if (left < right) {
				swap(dataSet, left, right);
			} else {
				break;
			}
		}
		swap(dataSet, first, right);
		return right;

	}

	private static void swap(int[] dataSet, int left, int right) {
		int temp = dataSet[right];
		dataSet[right] = dataSet[left];
		dataSet[left] = temp;
	}

	@Test
	public void testRecursive() {
		int[] dataSet = { 8, 4, 7, 3, 1, 5 };

		sort(dataSet, 0, dataSet.length - 1);
		System.out.println(Arrays.toString(dataSet));
	}
	
	@Test
	public void testStack() throws Exception {
		int[] dataSet = { 8, 4, 7, 3, 1, 5 };

		sortStack(dataSet, dataSet.length);
		System.out.println(Arrays.toString(dataSet));
		
	}

}

class IntPair {

	private final int left;
	private final int right;

	public IntPair(int left, int right) {
		this.left = left;
		this.right = right;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

}
