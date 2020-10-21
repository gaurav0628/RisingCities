/*
* Contains node structure for redblack tree.
* Each node represents a building and has information building number, executed time and total time
* Also has additional parameters such as parent right child and left child and color of the node
* Contains getters and setters for each of these attributes.
*/

public class RedBlackTreeAttrib {
	int bNum;
	int execTime;
	int totTime;
	int color;
	RedBlackTreeAttrib parent;
	RedBlackTreeAttrib lChild;
	RedBlackTreeAttrib rChild;
	
	public RedBlackTreeAttrib(int buildingNums, int executed_time, int total_time) {
		this.bNum = buildingNums;
		this.execTime = executed_time;
		this.totTime = total_time;
		this.color = 0;
		this.parent = null;
		this.lChild = null;
		this.rChild = null;
	}
	
	public int getbNum() {
		return bNum;
	}
	
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
	
	public int getExecTime() {
		return execTime;
	}
	
	public void setExecTime(int execTime) {
		this.execTime = execTime;
	}
	
	public int getTotTime() {
		return totTime;
	}
	
	public void setTotTime(int totTime) {
		this.totTime = totTime;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	public RedBlackTreeAttrib getParent() {
		return parent;
	}
	
	public void setParent(RedBlackTreeAttrib parent) {
		this.parent = parent;
	}
	
	public RedBlackTreeAttrib getlChild() {
		return lChild;
	}
	
	public void setlChild(RedBlackTreeAttrib lChild) {
		this.lChild = lChild;
	}
	
	public RedBlackTreeAttrib getrChild() {
		return rChild;
	}
	
	public void setrChild(RedBlackTreeAttrib rChild) {
		this.rChild = rChild;
	}
}
