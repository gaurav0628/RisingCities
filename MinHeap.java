/*
* Contains methods for creating and maintaing the minheap structure.
* minheap is made up of objects of type MinHeapAttrib.
* Contains methods to create maintain and extract min from heap.
*/

public class MinHeap {
	
	private static int HEAP_MAXSIZE = 2001;
	public static int currHeapSize = 0;
	private static MinHeapAttrib[] heapValues; 
	private static final int ROOT = 1;
	
	public MinHeap() {
		heapValues = new MinHeapAttrib[MinHeap.HEAP_MAXSIZE];
		MinHeapAttrib firstAttrib = new MinHeapAttrib(0, Integer.MIN_VALUE ,0);
		heapValues[0] = firstAttrib;
	}
	
	//Return location of parent
	static int getParentLoc(int loc) {
		return (loc / 2);
	}
	
	//Returns location of left child
	static int getLChildLoc(int loc) {
		return (2*loc);
	}
	
	//Returns location of right child
	static int getRChildLoc(int loc) {
		return ((2*loc) + 1);
	}
	
	//Function that swaps building in the heap
	static void swapBuildings(int b1Loc, int b2Loc) {
		MinHeapAttrib temp = heapValues[b1Loc];
		heapValues[b1Loc] = heapValues[b2Loc];
		heapValues[b2Loc] = temp;
	}
	
	//Inserts a new building in the heap
	public static void insertBuilding(MinHeapAttrib building) {
		heapValues[++currHeapSize] = building;
		int tempSize = currHeapSize;
		
		while(heapValues[tempSize].getExecTime() < heapValues[getParentLoc(tempSize)].getExecTime()
				|| ((heapValues[tempSize].getExecTime() == heapValues[getParentLoc(tempSize)].getExecTime()) 
						&& (heapValues[tempSize].getbNum() < heapValues[getParentLoc(tempSize)].getbNum()))) {
			swapBuildings(tempSize, getParentLoc(tempSize));
			tempSize = getParentLoc(tempSize);
		}
	}
	
	//Funation that maintains the minheap property after insertion and extract min
	private static void rebalanceBuildingHeap(int loc) {
		if(!(loc > currHeapSize/2)) {
			if(heapValues[loc].getExecTime() > heapValues[getLChildLoc(loc)].getExecTime()
					|| heapValues[loc].getExecTime() > heapValues[getRChildLoc(loc)].getExecTime()) {
				if(heapValues[getLChildLoc(loc)].getExecTime() < heapValues[getRChildLoc(loc)].getExecTime()) {
					swapBuildings(loc, getLChildLoc(loc));
					rebalanceBuildingHeap(getLChildLoc(loc));
				}
				else {
					swapBuildings(loc, getRChildLoc(loc));
					rebalanceBuildingHeap(getRChildLoc(loc));
				}		
			}
			
			if(heapValues[loc].getExecTime() == heapValues[getLChildLoc(loc)].getExecTime()
					|| heapValues[loc].getExecTime() == heapValues[getRChildLoc(loc)].getExecTime()) {
				if(heapValues[getLChildLoc(loc)].getbNum() < heapValues[getRChildLoc(loc)].getbNum()) {
					swapBuildings(loc, getLChildLoc(loc));
					rebalanceBuildingHeap(getLChildLoc(loc));
				}
				else {
					swapBuildings(loc, getRChildLoc(loc));
					rebalanceBuildingHeap(getRChildLoc(loc));
				}
			}
		}
	}
	
	//Extracts min building on which work is started
	public MinHeapAttrib extractBuildingToWork() {
		MinHeapAttrib minBuilding = heapValues[ROOT];
		heapValues[ROOT] = heapValues[currHeapSize];
		currHeapSize--;
		rebalanceBuildingHeap(ROOT);
		return minBuilding;
	}
	
	//Checks if heap is empty
	public static boolean isEmpty() {
		return (currHeapSize == 0);
	}
	
}
