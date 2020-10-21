/**
 * Driver class for rising city project.
 * Takes input in form of a file that is given as a argument to the program.
 * Output is written to output_file.txt and is in form of building tuples.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class risingCity {

	private static int GLOBAL_CLOCK = 0;

	public static void main(String args[]) throws IOException {

		//Initialize minheap and redblack tree
		MinHeap minHeap = new MinHeap() ;
		RedBlackTree rblTree = new RedBlackTree();
		List <MinHeapAttrib> mhList= new ArrayList<MinHeapAttrib>();
		List <RedBlackTreeAttrib> rblList= new ArrayList<RedBlackTreeAttrib>();

		String inputPath = null;

		if(args.length != 0) {
			inputPath = args[0];
		}

		//Take input from file. File name is taken input as a command line arg.
		File inputFile = new File(inputPath);
		Scanner scanner = new Scanner(inputFile);
		BufferedWriter out = new BufferedWriter(new FileWriter("output_file.txt"));
		String line = null;
		MinHeapAttrib minVal = null;
		MinHeapAttrib prevMinVal = null;
		RedBlackTreeAttrib rbtVal =null;
		boolean lastBuildingFlag = true;
		int days = 0;
		

		//This is the main loop which controls the functionality of golbal. Each loop of this denotes a day in the systemn.
		do {
			int bnum1, totlTime;
			Boolean flag = true;
			int counter = 0;
			int lineNumber = 0;
			String Command = null;
			int bNum = 0, globalTime = 0;
			String [] words = null;
			
			if(scanner.hasNext() && line == null) {
				line = scanner.nextLine();
			}
			
			//This is the logic to extract print or insert imstructions from input.
			if(line != null) {

				words = line.split("[,?.@:()]"); 
				globalTime = Integer.parseInt(words[0].trim());
				Command = words[1].trim(); 
				bNum = Integer.parseInt(words[2].trim()); 

			}

			if(globalTime == GLOBAL_CLOCK) {
				
				//Logic for inserting building into minheap and redblack tree
				if("Insert".equals(Command)) {
					totlTime = Integer.parseInt(words[3].trim());
					MinHeapAttrib minHeapBuilding = new MinHeapAttrib(bNum, 0, totlTime);
					mhList.add(minHeapBuilding);
					RedBlackTreeAttrib rbtBuilding = new RedBlackTreeAttrib(bNum, 0, totlTime);
					rblList.add(rbtBuilding);
					minHeap.insertBuilding(minHeapBuilding);
					rblTree.insert(rbtBuilding);
				}

				//Logic for range search on redblack tree. Returns multiple number of nodes which are saved to output.
				//Range is decided by the input building numbers.
				if("PrintBuilding".equals(Command) && words.length == 4) {
					bnum1 = Integer.parseInt(words[3].trim());
					List <RedBlackTreeAttrib> searchRes = new ArrayList<RedBlackTreeAttrib>();
					searchRes  = rblTree.printBuilding(rblTree.getRootNode(), bNum, bnum1, searchRes);
					int count = 0;
					String op = "";
					if(searchRes != null){
					for(RedBlackTreeAttrib res : searchRes) {
							op = op + ("("+res.getbNum()+","+res.getExecTime()+","+res.getTotTime()+"),");
						}
					}
					op = op.substring(0, op.length()-1);
					out.write(op);
					out.newLine();
					op = null;
				}

				//Searches and prints a single node of redblacktree
				if("PrintBuilding".equals(Command) && words.length == 3) {
					RedBlackTreeAttrib res =rblTree.printBuilding(rblTree.getRootNode(), bNum);
					String op = "("+res.getbNum()+","+res.getExecTime()+","+res.getTotTime()+")";
					out.write(op);
					out.newLine();
				}

				//Logic for extraction of new buildings
				if(days == 5 && minVal != null) {
					prevMinVal = minVal;
					prevMinVal.setExecTime(prevMinVal.getExecTime() + 5);
					if(prevMinVal.getExecTime() < prevMinVal.getTotTime()) {
						minHeap.insertBuilding(prevMinVal);
						rblTree.updateBuilding(rblTree.getRootNode(), 
								rblTree.printBuilding(rblTree.getRootNode(), prevMinVal.getbNum()).getbNum());
					}
					else {
						RedBlackTreeAttrib rbtT = rblTree.printBuilding(rblTree.getRootNode(), prevMinVal.getbNum());
						rblTree.delete(rblTree.printBuilding(rblTree.getRootNode(), prevMinVal.getbNum()));
						String op = "("+rbtT.getbNum()+","+GLOBAL_CLOCK+")";
						out.write(op);
						out.newLine();
					}
					minVal = null;
					days = 0;
				}
				line = null;
			}
			
			//Extract a new building to work from minheap
			if(minVal == null && !minHeap.isEmpty()) {
				minVal = minHeap.extractBuildingToWork();
			}
			GLOBAL_CLOCK++;
			//Logic for extraction of new buildings
			if (minVal != null){
				days++;
				if(days == 5 && minVal != null) {
					prevMinVal = minVal;
					prevMinVal.setExecTime(prevMinVal.getExecTime() + 5);
					if(prevMinVal.getExecTime() < prevMinVal.getTotTime()) {
						MinHeap.insertBuilding(prevMinVal);
						rblTree.updateBuilding(rblTree.getRootNode(), 
								minVal.getbNum());
					}
					else {
						RedBlackTreeAttrib rbtT = rblTree.printBuilding(rblTree.getRootNode(), prevMinVal.getbNum());
						rblTree.delete(rblTree.printBuilding(rblTree.getRootNode(), prevMinVal.getbNum()));
						String op = "("+rbtT.getbNum()+","+GLOBAL_CLOCK+")";
						out.write(op);
						out.newLine();
					}
					minVal = null;
					days = 0;
				}
				else {
					rblTree.updateBuilding(rblTree.getRootNode(), 
							minVal.getbNum());
				}
			}
			
		}while(line != null || !minHeap.isEmpty() || minVal != null);
		out.close();
	}
}
