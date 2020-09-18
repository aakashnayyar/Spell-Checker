package cs146F19.Nayyar.project4;


public class RedBlackTree<Key extends Comparable<Key>> {	
		private static final int RED = 0; 
		private static final int BLACK = 1;
		private RedBlackTree.Node<String> root;

		/**
		 * This was provided
		 * @author K.Potika
		 */
		public static class Node<Key extends Comparable<Key>> { //changed to static 
			
			  Key key;  		  
			  Node<String> parent;
			  Node<String> leftChild;
			  Node<String> rightChild;
			  boolean isRed;
			  int color;
			  //int RED = 1;
			  
			  //provided
			  public Node(Key data){
				  this.key = data;
				  leftChild = null;
				  rightChild = null;
				  this.color = BLACK;
			  }		
			  
			  //provided
			  public int compareTo(Node<Key> n){ 	//this < that  <0
			 		return key.compareTo(n.key);  	//this > that  >0
			  }
			  
			  //provided
			  public boolean isLeaf(){
				  RedBlackTree rbt = new RedBlackTree<>();
				  if (this.equals(rbt.root) && this.leftChild == null && this.rightChild == null) return true;
				  if (this.equals(rbt.root)) return false;
				  if (this.leftChild == null && this.rightChild == null){
					  return true;
				  }
				  return false;
			  }
		}
		/**
		 * This was provided
		 * @param RBTnode
		 * @return boolean based on if the node is a leaf or not
		 */
		 public boolean isLeaf(RedBlackTree.Node<String> n){
			  if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
			  if (n.equals(root)) return false;
			  if (n.leftChild == null && n.rightChild == null){
				  return true;
			  }
			  return false;
		  }
		
		 //provided
		public interface Visitor<Key extends Comparable<Key>> {
			/**
			This method is called at each node.
			@param n the visited node
			*/
			void visit(Node<Key> n);  
		}
		
		//provided
		public void visit(Node<Key> n){
			System.out.println(n.key);
		}
		
		//provided
		public void printTree(){  //preorder: visit, go left, go right
			RedBlackTree.Node<String> currentNode = root;	
			printTree(currentNode);
		}
		
		//provided
		public void printTree(RedBlackTree.Node<String> node){
			System.out.print(node.key);
			if (node.isLeaf()){
				return;
			}
			if(node.leftChild!=null) {
				printTree(node.leftChild);
			}
			if(node.rightChild!=null) {
				printTree(node.rightChild);
			}
		}
		
		/**
		 * Adds node into a RBT and then fixes the tree
		 * @param data add node with key as data
		 */
		public void addNode(String data){  	//this < that  <0.  this > that  >0
			Node<String> z = new Node<String>(data); //new node
			Node<String> y = null;
			Node<String> current = root;
			while(current!=null) { //while current is not null
				y = current;
				if(z.compareTo(current) < 0) { //if key is less than current's key move left
					current = current.leftChild;
				}
				else {
					current=current.rightChild; //else move right
				}
			}
			z.parent = y;
			if(y == null) {
				root = z;
			}
			else if(z.compareTo(y)<0) {
				y.leftChild = z;
			}
			else {
				y.rightChild = z;
			}
			z.leftChild = null; //left child is null
			z.rightChild = null; //right child is null
			z.color = RED; //set color to red
			fixTree(z); //fix tree
			
		}	
		
		//provided
		public void insert(String data){
			addNode(data);	
		}
		
		/**
		 * Looks ups a string and returns a node with the same data as the node
		 * @param k a string
		 * @return node with the data
		 */
		public RedBlackTree.Node<String> lookup(String k){ 
			Node<String> current = root; // begin at root
			while(current !=null) {
				if(k.compareTo(current.key) > 0) {
					current = current.rightChild; //move right
				}
				else if(k.compareTo(current.key)<0) {
					current=current.leftChild; // move left
				}
				else {
					return current; // return current if the node.key matches the string
				}
			}
			return null; // if not found return null
			
		}
	 	
		/**
		 * Gets the sibling of the given node
		 * @param n node who's sibling you would like to find
		 * @return node, the sibling of given node
		 */
		public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n){  
			//
			if(n==root) { // if tree is empty
				return null;
			}
			
			if(n.compareTo(n.parent) >=0) {
				 return n.parent.leftChild; // if current is right child, return left child
			}
			else {
				return n.parent.rightChild; // if current is left child return right child
			}
		}
		
		/**
		 * Helps find the current nodes aunt
		 * @param n node, the node which you want the aunt for
		 * @return node, the aunt
		 */
		public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n){
			//
			if(n==root||n.parent==root) {
				return null;
			}
			else{
				return getSibling(n.parent); // return parents sibling
			}
		}
		
		/**
		 * Gets grandparent of current node
		 * @param n node, who's grandparent you want
		 * @return node, the grandparent
		 */
		public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n){
			return n.parent.parent; // return parents parent
		}
		
		/**
		 * rotates the the tree left around a given node
		 * @param n the given node
		 */
		public void rotateLeft(RedBlackTree.Node<String> n){ // follow algorithm in book
			//
			RedBlackTree.Node<String> y = n.rightChild;
			n.rightChild = y.leftChild;
			if(y.leftChild != null) {
				y.leftChild.parent = n;
			}
			y.parent = n.parent;
			if(n.parent== null) {
				root = y;
			}
			else if(n == n.parent.leftChild) {
				n.parent.leftChild = y;
			}
			else {
				n.parent.rightChild = y;
			}
			y.leftChild = n;
			n.parent = y;
		}
		
		/**
		 * rotates the the tree right around a given node
		 * @param n the given node
		 */
		public void rotateRight(RedBlackTree.Node<String> n){ // follow algorithm in book
			//
			RedBlackTree.Node<String> x = n.leftChild;
			n.leftChild = x.rightChild;
			if(x.rightChild!=null) {
				x.rightChild.parent = n;
			}
			x.parent = n.parent;
			if(n.parent == null) {
				root = x;
			} 
			else if (n == n.parent.rightChild) {
				n.parent.rightChild = x;	
			}
			else {
				n.parent.leftChild = x;
			}
			x.rightChild = n;
			n.parent = x;
			
			
		}
		
		/**
		 * Fixes the tree to make it follow the conditions of RBT
		 * @param current the node which you just added
		 */
		public void fixTree(RedBlackTree.Node<String> current) {
			//
			if(current.parent == null || current.parent.parent == null) { // if parent or grandparent don't exist
				return;
			}
			if(getAunt(current)==null) { // if aunt does not exist
				if(isLeftChild(current.parent.parent,current.parent)&&!isLeftChild(current.parent,current)) { //case 1
					rotateLeft(current.parent);
					fixTree(current.parent);
				}
				else if(!isLeftChild(current.parent.parent,current.parent)&&isLeftChild(current.parent,current)) { //case 2
					rotateRight(current.parent);
					fixTree(current.parent);
				}
				else if(isLeftChild(current.parent.parent,current.parent)&&isLeftChild(current.parent,current)) {//case 3
					current.parent.color = BLACK;
					current.parent.parent.color = RED;
					rotateRight(current.parent.parent);
				}
				else {//case 4
					current.parent.color = BLACK;
					current.parent.parent.color = RED;
					rotateLeft(current.parent.parent);
				}
			}
			else { // follow algorithm in textbook
				while(root!= current && current.parent.color == RED) {
					if(isLeftChild(current.parent.parent, current.parent)) {
						RedBlackTree.Node<String> y = current.parent.parent.rightChild;
						if(y.color == RED) {
							current.parent.color = BLACK;
							y.color = BLACK;
							current.parent.parent.color = RED;
							current = current.parent.parent;
						}
						else {
							if(!isLeftChild(current.parent,current)) {
								current = current.parent;
								rotateLeft(current);
							}
							current.parent.color = BLACK;
							current.parent.parent.color = RED;
							rotateRight(current.parent.parent);	
						}
					}
					else { 
						RedBlackTree.Node<String> y = current.parent.parent.leftChild;
						if(y.color == RED) {
							current.parent.color = BLACK;
							y.color = BLACK;
							current.parent.parent.color = RED;
							current = current.parent.parent;
						}
						else {
							if(isLeftChild(current.parent,current)) {
								current = current.parent;
								rotateRight(current);
							}
							current.parent.color = BLACK;
							current.parent.parent.color = RED;
							rotateLeft(current.parent.parent);	
						}
					}
				}
				root.color = BLACK;
			}
						
		}

		//provided
		public boolean isEmpty(RedBlackTree.Node<String> n){
			if (n.key == null){
				return true;
			}
			return false;
		}
		
		//provided
		public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
		{
			if (child.compareTo(parent) < 0 ) {//child is less than parent
				return true;
			}
			return false;
		}
		
		//provided
		public void preOrderVisit(Visitor<String> v) {
		   	preOrderVisit(root, v);
		}
		 
		//provided
		private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		  	if (n == null) {
		  		return;
		  	}
		  	v.visit(n);
		  	preOrderVisit(n.leftChild, v);
		  	preOrderVisit(n.rightChild, v);
		}	
		
	    
}

	



