public class Tree {
	private Node root;
	
	public void insert(int x){
		Node n = new Node(x);
		this.insert(n);
	}
	public void insert(Node n){
		if(root == null){
			root = n;
			return;
		}else{
			this.insert(root,n);
		}
	}
	private void insert(Node curRoot, Node n){
		if(curRoot.value == n.value){
			Node temp = curRoot;
			while(temp.below!= null){
				temp = temp.below;
			}
			temp.below = n;
			n.parent = temp;
			return;
		}
		
		if(curRoot.value > n.value){
			if(curRoot.left != null){
				insert(curRoot.left,n);
				return;
			}else{
				curRoot.left = n;
				n.parent = curRoot;
				return;
			}
		}else{ // curRoot.value < n.value
			if(curRoot.right != null){
				insert(curRoot.right,n);
				return;
			}else{
				curRoot.right = n;
				n.parent = curRoot;
				return;
			}
		}
	}
	
	public void printRoot(){
		if(root != null)
			System.out.println("Root value: "+root.value);
		else
			System.err.println("Root is null.");
	}
	public void print(){
		if(root == null){
			System.out.println("No tree!");
		}
		else{
			print(root);
		}
	}
	private void print(Node n){
		if(n.left!= null){
			print(n.left);
		}
		
		
		System.out.println(n.value);
		if(n.below != null){
			print(n.below);
		}
		
		if(n.right != null){
			print(n.right);
		}
	}
	
	public Node getMin(Node cur){
		Node temp = cur;
		while(temp.left != null){
			temp = temp.left;
		}
		return temp;
	}
	
	public Node successor(Node cur){
		if(cur == null){
			System.err.println("Node is null in successor funtion!");
			return null;
		}
		
		if(cur.below != null){
			return cur.below;
		}
		
		if(cur.right != null){
			return getMin(cur.right);
		}else{
			Node temp = cur;
			while(temp.parent != null && temp.parent.right == temp){
				temp = temp.parent;
			}
			if(temp.parent == null){
				return null;
			}else{
				return temp.parent;
			}
		}
		
	}
	
	public void delete(Node n){
		System.out.println("Deleting " + n.value);
		if(n.below != null){
			deleteIfBelowIsNotNull(n);
			return;
		}
		if(n.right == null && n.left == null){
			
			if(n.parent != null){
				if(n.parent.right == n)
					n.parent.right = null;
				else if(n.parent.left == n)
					n.parent.left = null;
				else if(n.parent.below == n)
					n.parent.below = null;
			}else{
				root = null;
			}
			n = null;
			return;
		}
		if(n.right == null || n.left == null){
			deleteWithOneChild(n);
			return;
		}
		
		if(n.right != null && n.left != null){
			deleteWithTwoChildren(n);
		}
		
	}
	private void deleteIfBelowIsNotNull(Node n){
		n.below.parent = n.parent;
		n.below.right = n.right;
		n.below.left = n.left;
		if(n.parent == null){ 
			root = n.below;
			n = null;
			return;
		}else if(n.parent.right == n){
			n.parent.right = n.below;
			n = null;
			return;
		}else if(n.parent.below == n){
			n.parent.below = n.below;
			n=null;
			return;
		}else if(n.parent.left== n){
			n.parent.left = n.below;
			n = null;
			return;
		}
		System.err.println("An error occured in delete");
		return;
	}
	

	private void deleteWithOneChild(Node n) {
		if(n.right == null){
			if(n.parent == null){ // n == root
				root = n.left;
				n.left.parent = null;
				n = null;
				return;
			}
			else if(n == n.parent.right){
				n.parent.right = n.left;
				n.left.parent = n.parent;
				n = null;
				return;
			}else if(n == n.parent.left){
				n.parent.left = n.left;
				n.left.parent = n.parent;
				n = null;
				return;
			}else if(n == n.parent.below){
				System.err.println("Wrong in deleteWithOneChild function.");
				return;
			}
		}
		
		if(n.left == null){
			if(n.parent == null){ // n == root
				root = n.right;
				n.right.parent = null;
				n = null;
				return;
			}else if(n == n.parent.right){
				n.parent.right = n.right;
				n.right.parent = n.parent;
				n = null;
				return;
			}else if(n == n.parent.left){
				n.parent.left = n.right;
				n.right.parent = n.parent;
				n = null;
				return;
			}else if(n == n.parent.below){
				System.err.println("There has to be something wring in deleteWithOneChild function.(2)");
				return;
			}
		}
		
		System.err.println("There has to be something wring in deleteWithOneChild function.(3)");
		return;
	}
	private void deleteWithTwoChildren(Node n) {
		
		Node successor = this.successor(n);
		
	
		Node temp = new Node(successor.value);
		temp.parent = successor.parent;
		boolean isTempLeftChild = false;
		if(successor.parent.right == successor){
			successor.parent.right = temp;
		}else{
			successor.parent.left = temp;
			isTempLeftChild = true;
		}
		temp.right = successor.right;
		temp.left = successor.left;
		
		
		successor.parent = n.parent;
		if(n.parent == null){
			successor.parent = null;
			root = successor;
		}else if(n.parent.left == n){
			n.parent.left = successor;
		}else{
			n.parent.right = successor;
		}
		successor.right = n.right;
		if(successor.right != null)
			successor.right.parent = successor;
		successor.left = n.left;
		if(successor.left != null)
			successor.left.parent = successor;
		
		
		
				if(temp.parent != n){
			n.parent = temp.parent;
		}else{
			n.parent = successor;
		}
		n.left = temp.left;
		n.right = temp.right;
		
			if(isTempLeftChild == true){
			if(temp.parent != n){
				temp.parent.left = n;
			}else{
				successor.left = n;
			}
			}else{
			if(temp.parent != n){
				temp.parent.right = n;
			}else{
				successor.right = n;
			}
		}
		temp = null;
		
			if(n.left == null && n.right == null){
			if(n.parent.left == n){
				n.parent.left = null;
			}else{
				n.parent.right = null;
			}
			n = null;
			return;
		}else{ 
			
			deleteWithOneChild(n);
		}
		
	}

	
	
	public static void main(String[] args) {
		Tree t = new Tree();
		Node node = t.new Node(5);
		Node node2 = t.new Node(4);
		Node node3 = t.new Node(9);
		Node node4 = t.new Node(5);
		Node node5 = t.new Node(7);
		Node node6 = t.new Node(2);
		Node node7 = t.new Node(2);
		
		
		t.print();
		t.insert(node);
		t.insert(node2);
		t.insert(node3);
		t.insert(node4);
		t.insert(node5);
		t.insert(node6);
		t.insert(node7);

		
		t.delete(node5);
		t.delete(node);
		t.delete(node3);
		
		t.print();
		t.printRoot();
		
	}
	
	
	
	public class Node {
		Node left;
		Node right;
		Node below;
		Node parent;
		int value;
		public Node(){
			this(Integer.MAX_VALUE);
			
		}
		public Node(int x){
			left = null;
			right = null;
			parent = null;
			value = x;
			
		}
	}
}
