/*
*	Contains the node structure for a single entry in minheap.
* 	Each node in minheap is a building with three attribuites : Building number, Executed Time and Total Time
*	Getters and Setters are present for each attribute.
*/

public class MinHeapAttrib {
		int bNum;
		int execTime;
		int totTime;
		
		public MinHeapAttrib(int bNum, int execTime, int totTime) {
			this.bNum = bNum;
			this.execTime = execTime;
			this.totTime = totTime;
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

}
