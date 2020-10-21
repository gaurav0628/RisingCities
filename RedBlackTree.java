/*
*	Contains the data structure redblack tree.
* 	Redblack tree is formed using RedBlackTreeAttrib nodes.
*	This class contains methods for creation of redblack tree and also methods
*	for the maintenance of tree.
*/

import java.util.List;

public class RedBlackTree {
	
private static RedBlackTreeAttrib rootNode;
	
	//Codes for Red and Black Colour of the nodes
	private static final int COLOR_RED   = 0;
    private static final int COLOR_BLACK = 1;
    
	//Getter function for rootnode of the tree
    public RedBlackTreeAttrib getRootNode() {
		return rootNode;
	}

	//Setter function for rootnode of the tree
	public void setRootNode(RedBlackTreeAttrib rootNode) {
		RedBlackTree.rootNode = rootNode;
	}
    
	//Method for right rotation along a node .
	//Performs a simple ClockWise rotation around the given node passed as input
	//Input is of type RedBlackTreeAttrib and is the node along which rotation is to be performed.
    public void rotateRight(RedBlackTreeAttrib node) {
    	
    	RedBlackTreeAttrib tmpry = node.getlChild();
    	node.setlChild(tmpry.getrChild());
    	if(tmpry.getrChild() != null) {
    		tmpry.getrChild().setParent(node);
    	}
    	tmpry.setParent(node.getParent());
    	if(node.getParent() == null) {
    		RedBlackTree.rootNode = tmpry;
    	}
    	else {
    		if(node.getParent().getrChild() == node) {
    			node.getParent().setrChild(tmpry);
    		}
    		else {
    			node.getParent().setlChild(tmpry);
    		}
    	}
    	tmpry.setrChild(node);
    	node.setParent(tmpry);
    }
    
	//Method for left rotation along a node .
	//Performs a simple Anti ClockWise rotation around the given node passed as input
	//Input is of type RedBlackTreeAttrib and is the node along which rotation is to be performed.
    public void rotateLeft(RedBlackTreeAttrib node) {
    	
    	RedBlackTreeAttrib tmpry = node.getrChild();
    	node.setrChild(tmpry.getlChild());
    	if(tmpry.getlChild() != null) {
    		tmpry.getlChild().setParent(node);
    	}
    	tmpry.setParent(node.getParent());
    	if(node.getParent() == null) {
    		RedBlackTree.rootNode = tmpry;
    	}else {
    		if(node.getParent().getlChild() == node) {
    			node.getParent().setlChild(tmpry);
    		}
    		else {
    			node.getParent().setrChild(tmpry);
    		}
    	}
    	tmpry.setlChild(node);
    	node.setParent(tmpry);
    	
    }
    
	//Method for inserting a new node in redblack tree. 
	//Inserts a new node in similar fashion to BST to the redblack tree with no regards for maintenance of redblack property
	//Contains a call to method balanceRBL which is responsible for maintaining the sanctity of redblack property across all nodes 
	//affected by the addition of new node.
	//Input is of type RedBlackTreeAttrib and is the node which is to be inserted.
    public void insert(RedBlackTreeAttrib node) {
    	
    	RedBlackTreeAttrib rootCopy = RedBlackTree.rootNode;
    	RedBlackTreeAttrib temp = null;
    	node.setColor(RedBlackTree.COLOR_RED);
    	
    	while(rootCopy != null) {
    		temp = rootCopy;
    		if(node.getbNum() < rootCopy.getbNum()) {
        		rootCopy = rootCopy.getlChild();
        	}
        	else {
        		rootCopy = rootCopy.getrChild();
        	}
    	}
    	node.setParent(temp);
    	if(temp != null) {
    		if(node.getbNum() < temp.getbNum()) {
    			temp.setlChild(node);
    		}
    		else {
    			temp.setrChild(node);
    		}
    	}
    	else {
    		RedBlackTree.rootNode = node;
    	}
    	balanceRBLInsert(node);
    }
    
	//Method responsible for maintaining the sanctity of red black property across all the nodes affected by the newly imnserted nodes.
	//Performs roatations based on the color of parent, grand parent and uncle of the affected node.
	//Input is of type RedBlackTreeAttrib and is the node which was inserted in the insert method.
    public void balanceRBLInsert(RedBlackTreeAttrib node) {
    	
    	RedBlackTreeAttrib par, gpar;
    	par = node.getParent();
    	while(par != null && par.getColor() == RedBlackTree.COLOR_RED)
    	{
    		gpar = par.getParent();
    		if(par == gpar.getlChild()) {
    			RedBlackTreeAttrib uncle = gpar.getrChild();
    			if(null != uncle && uncle.getColor() == RedBlackTree.COLOR_RED) {
    				uncle.setColor(RedBlackTree.COLOR_BLACK);
    				par.setColor(RedBlackTree.COLOR_BLACK);
    				gpar.setColor(RedBlackTree.COLOR_RED);
    				node = gpar;
    				continue;
    			}
    			if(par.getrChild() == node) {
    				RedBlackTreeAttrib tmpry;
    				rotateLeft(par);
    				tmpry = par;
    				par = node;
    				node = tmpry;
    			}
    			par.setColor(RedBlackTree.COLOR_BLACK);
    			gpar.setColor(RedBlackTree.COLOR_RED);
    			rotateRight(gpar);
    		}
    		else {
    			RedBlackTreeAttrib uncle = gpar.getlChild();
    			if(null != uncle && uncle.getColor() == RedBlackTree.COLOR_RED) {
    				uncle.setColor(RedBlackTree.COLOR_BLACK);
    				par.setColor(RedBlackTree.COLOR_BLACK);
    				gpar.setColor(RedBlackTree.COLOR_RED);
    				node = gpar;
    				continue;
    			}
    			if(par.getlChild() == node) {
    				RedBlackTreeAttrib tmpry;
    				rotateRight(par);
    				tmpry = par;
    				par = node;
    				node = tmpry;
    			}
    			par.setColor(RedBlackTree.COLOR_BLACK);
    			gpar.setColor(RedBlackTree.COLOR_RED);
    			rotateLeft(gpar);
    		}
    	}
    	RedBlackTree.rootNode.setColor(RedBlackTree.COLOR_BLACK);
    }
    
	//Method is responsible for deletion of the input node.
	//Similar to insert method, deletion performed is with no regards of maintaining the redblack tree property.
	//Contains call to method rebalanceRBLDelete which is responsible for maintaining the redblack property after deletion of a node.
	//Input is of type RedBlackTreeAttrib and is the node which is to be deleted.
    public void delete(RedBlackTreeAttrib node) {
    	RedBlackTreeAttrib chi, par;
    	int tempCol;
    	
    	if(node.getlChild() != null && node.getrChild() != null) {
    		RedBlackTreeAttrib tmpry = node;
    		tmpry = tmpry.getrChild();
    		
    		while(tmpry.getlChild() != null) {
    			tmpry = tmpry.getlChild();
    		}
    		
    		if(node.getParent() != null) {
    			if(node.getParent().getlChild() == node){
    				node.getParent().setlChild(tmpry);
    			}
	    		else {
	    			node.getParent().setrChild(tmpry);
	    		}
    		}
    		else {
    			RedBlackTree.rootNode = tmpry;
    		}
    		
    		chi = tmpry.getrChild();
    		par = tmpry.getParent();
    		tempCol = tmpry.getColor();
    		
    		if(par == node) {
    			par = tmpry;
    		}else {
    			if(chi != null) {
    				chi.setParent(par);
    			}
    			par.setlChild(chi);
    			tmpry.setrChild(node.getrChild());
    			node.getrChild().setParent(tmpry);
    		}
    		
    		tmpry.setParent(node.getParent());
    		tmpry.setColor(node.getColor());
    		tmpry.setlChild(node.getlChild());
    		node.getlChild().setParent(tmpry);
    		
    		if(tempCol == COLOR_BLACK) {
    			rebalanceRBLDelete(chi, par);
    		}
    		
    		node = null;
    		return; 
    	}
    	
    	
    	if(node.getlChild() != null) {
    		chi = node.getlChild();
    	}
    	else {
    		chi = node.getrChild();
    	}
    	
    	par = node.getParent();
    	tempCol = node.getColor();
    	
    	if(chi != null) {
    		chi.setParent(par);
    	}
    	
    	if(par != null) {
			if(par.getlChild() != null && par.getlChild() == node){
				par.setlChild(chi);
			}
    		else {
    			par.setrChild(chi);
    		}
		}
		else {
			RedBlackTree.rootNode = chi;
		}
    	
    	if(tempCol == COLOR_BLACK) {
			rebalanceRBLDelete(chi, par);
		}
		node = null;
    }
    
	//Method responsible for maintaining the sanctity of red black property across all the nodes affected by the deleted nodes.
	//Performs roatations based on the color of parent, grand parent and uncle of the affected node.
	//Input is of type RedBlackTreeAttrib and is the node which was deleted in the delete method.
    private void rebalanceRBLDelete(RedBlackTreeAttrib chi, RedBlackTreeAttrib par) {
    	RedBlackTreeAttrib tmpry;
    	while(((chi == null || (chi.getColor() == COLOR_BLACK))) 
    			&& chi != RedBlackTree.rootNode) {
    		
    		if(par.getlChild() == chi) {
    			tmpry = par.getrChild();
    			
    			if(tmpry.getColor() == COLOR_RED) {
    				tmpry.setColor(COLOR_BLACK);
    				par.setColor(COLOR_RED);
    				rotateLeft(par);
    				tmpry = par.getrChild();
    			}
    			
    			if((tmpry.getlChild() == null || (tmpry.getlChild().getColor() == COLOR_BLACK))
    					&& tmpry.getrChild() == null || (tmpry.getrChild().getColor() == COLOR_BLACK)) {
    				tmpry.setColor(COLOR_RED);
    				chi = par;
    				par = chi.getParent();
    			}else {
    				
    				if(tmpry.getrChild() == null || (tmpry.getrChild().getColor() == COLOR_BLACK)) {
    					tmpry.getlChild().setColor(COLOR_BLACK);
    					tmpry.setColor(COLOR_RED);
    					rotateRight(tmpry);
    					tmpry = par.getrChild();
    				}
    				
    				tmpry.setColor(par.getColor());
    				par.setColor(COLOR_BLACK);
    				tmpry.getrChild().setColor(COLOR_BLACK);
    				rotateLeft(par);
    				chi = RedBlackTree.rootNode;
    				break;
    			}
    		}else {
    			
    			tmpry = par.getlChild();
    			if(tmpry.getColor() == COLOR_RED) {
    				tmpry.setColor(COLOR_BLACK);
    				par.setColor(COLOR_RED);
    				rotateRight(par);
    				tmpry = par.getlChild();
    			}
    			
    			if((tmpry.getlChild() == null || (tmpry.getlChild().getColor() == COLOR_BLACK))
    					&& tmpry.getrChild() == null || (tmpry.getrChild().getColor() == COLOR_BLACK)) {
    				tmpry.setColor(COLOR_RED);
    				chi = par;
    				par = chi.getParent();
    			}else {
    				
    				if((tmpry.getlChild() == null || (tmpry.getlChild().getColor() == COLOR_BLACK))) {
    					tmpry.getrChild().setColor(COLOR_BLACK);
    					tmpry.setColor(COLOR_RED);
    					rotateLeft(tmpry);
    					tmpry = par.getlChild();
    				}
    				
    				tmpry.setColor(par.getColor());
    				par.setColor(COLOR_BLACK);
    				tmpry.getlChild().setColor(COLOR_BLACK);
    				rotateRight(par);
    				chi = RedBlackTree.rootNode;
    				break;
    			}	
    		}
    	}
    	if(chi != null) {
    		chi.setColor(COLOR_BLACK);
    	}
    }
    
	//Method is responsible for returning a node of given building number. 
	//Takes input root node of the redblack tree and building number of the building to be searched
	//Returns Building node.
    public RedBlackTreeAttrib printBuilding(RedBlackTreeAttrib rNode, int buildingNum) {
    	
    	if(rNode == null || (rNode.getbNum() == buildingNum)) {
    		return rNode;
    	}
    	if(buildingNum < rNode.getbNum()) {
    		return printBuilding(rNode.getlChild(), buildingNum);
    	}
    	return printBuilding(rNode.getrChild(), buildingNum);
    	
    }
    
	//Method is responsible for returning nodes that lie in range between 2 given building numbers 
	//Takes input root node of the redblack tree and building numbers of the buildings which will serve as range 
	//to be searched
	//Returns list of Building nodes.
    public List<RedBlackTreeAttrib> printBuilding(RedBlackTreeAttrib rtNode, int buildingNum1, int buildingNum2, List<RedBlackTreeAttrib> triplets) {
    	
    	if(rtNode == null) {
    		return null;
    	}
    	
    	if(buildingNum1 < rtNode.getbNum()) {
    		printBuilding(rtNode.getlChild(), buildingNum1, buildingNum2, triplets);
    	}
    	
    	if(buildingNum1 <= rtNode.getbNum() && rtNode.getbNum() <= buildingNum2) {
    		triplets.add(rtNode);
    	}
    	
    	if(buildingNum2 > rtNode.getbNum()) {
    		printBuilding(rtNode.getrChild(), buildingNum1, buildingNum2, triplets);
    	}
    	return triplets;
    	
    }
    
	//Method which updates the executed time of a specific node in the redblack tree
	//Takes as input root node and building number of the node which is to be updated.
	//Return type is mentioned as RedBlackTreeAttrib, but returns null for maintaining recursive calls.
    public RedBlackTreeAttrib updateBuilding(RedBlackTreeAttrib rNode, int buildingNum) {
    	
    	if(rNode != null ) {

        	if((rNode.getbNum() == buildingNum)) {
        		rNode.setExecTime(rNode.getExecTime() + 1);
        	}
        	if(buildingNum < rNode.getbNum()) {
        		return updateBuilding(rNode.getlChild(), buildingNum);
        	}
        	return updateBuilding(rNode.getrChild(), buildingNum);
    	}
    	return null;
    	
    }
    
}
