package cn.shuaitian.rbtree;

public class RBTree<Key extends Comparable<Key>,Value> {
	private Node<Key,Value> root;
	private int compareCount = 0;
	
	/**
	 * 该函数会将一棵树进行左旋操作，左旋即相当于将2-3树中3节点较小者作为根节点转为较大者作为根节点，也就是将右红连接转成
	 * 左红连接。该函数只是一个左旋操作的通用版本，函数内并没有对传入节点的右节点做颜色判断。
	 * @author shuaitian
	 * @param  需要进行左旋操作的树的根节点
	 * @return 左旋操作完成后的树根节点
	 */
	private Node<Key,Value> rotateLeft(Node<Key,Value> node){
		Node<Key,Value> x = node.getRight();
		if(x != null)
			x.setParent(node.getParent());
		if(node.getParent() != null && node.getParent().getLeft() == node){
			node.getParent().setLeft(x);
		}
		else if(node.getParent() != null && node.getParent().getRight() == node){
			node.getParent().setRight(x);
		}
		node.setRight(x.getLeft());
		if(x.getLeft() != null)
			x.getLeft().setParent(node);
		x.setLeft(node);
		if(node != null)
			node.setParent(x);
		boolean color = x.getColor();
		x.setColor(node.getColor());
		node.setColor(color);
		return x;
	}
	
	/**
	 * 同rotateLeft函数，右旋操作
	 * @author shuaitian
	 * @param  需要进行右旋操作的根节点 
	 * @return 右旋操作完成后的树节点
	 */
	private Node<Key,Value> rotateRight(Node<Key,Value> node){
		Node<Key,Value> x = node.getLeft();
		if(x != null)
			x.setParent(node.getParent());
		if(node.getParent()!=null && node.getParent().getLeft() == node){
			node.getParent().setLeft(x);
		}
		else if(node.getParent()!=null && node.getParent().getRight() == node){
			node.getParent().setRight(x);
		}
		node.setLeft(x.getRight());
		if(x.getRight() != null)
			x.getRight().setParent(node);
		x.setRight(node);
		if(node != null)
			node.setParent(x);
		boolean color = x.getColor();
		x.setColor(node.getColor());
		node.setColor(color);
		return x;
	}
	
	
	/**
	 * 红黑树的递归插入函数，此函数参考自《Algorithms》Forth Edition chapter 3.3.2,实现很简洁。
	 * @author shuaitian
	 * @param node
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unused")
	private Node<Key,Value> put1(Node<Key,Value> node,Key key,Value value){
		if(node == null){
			return new Node<Key,Value>(key,value,Node.RED);
		}
		if(key.compareTo(node.getKey()) < 0){
			//插入节点比当前节点小，左递归调用
			node.setLeft(put1(node.getLeft(),key,value));
		}
		else if(key.compareTo(node.getKey()) > 0){
			//插入节点比当前节点大，右递归调用
			node.setRight(put1(node.getRight(),key,value));
		}
		else{
			//插入节点等于当前节点，不进行插入，更新值域
			node.setValue(value);
		}
		Node<Key,Value> left = node.getLeft();
		Node<Key,Value> right = node.getRight();
		Node<Key,Value> leftleft = null;
		if(left != null)
			leftleft = left.getLeft();

		if((left==null || left.isBlack()) && (right!=null && right.isRed())){
			node = rotateLeft(node);
		}
		
		if((left != null && left.isRed()) && (leftleft!=null && leftleft.isRed())){
			node = rotateRight(node);
		}
		if((left!=null && left.isRed()) && (right != null && right.isRed())){
			node.setRed();
			node.getLeft().setBlack();
			node.getRight().setBlack();
		}
		return node;
	}
	
	/**
	 * 红黑树递归插入函数，根据定义实现的一个版本
	 * @author shuaitian
	 * @param node
	 * @param key
	 * @param value
	 * @return
	 */
	private void put2(Node<Key,Value> node,Key key,Value value){
		
		Key k = node.getKey();
		if(key.compareTo(k) < 0){
			if(node.getLeft() == null){
				//在当前节点的左边添加一个新节点
				node.setLeft(new Node<Key,Value>(key,value,Node.RED,node));
				//添加新节点后，可以能会破坏红黑树的性质，所以需要进行调整。
				adjust(node);
			}
			else{
				put2(node.getLeft(),key,value);
		
			}
		}
		else if(key.compareTo(k) > 0){
			if(node.getRight() == null){
				//在当前节点的右边添加一个新节点
				node.setRight(new Node<Key,Value>(key,value,Node.RED,node));
				//添加新节点后，可以能会破坏红黑树的性质，所以需要进行调整。
				adjust(node);
			}
			else{
				put2(node.getRight(),key,value);
			}
		}
	
	}
	
	
	/**
	 * 红黑树的调整函数,实际上就是根据2-3树生成时，将节点插入到2节点和3节点时，不同的处理方式，理解了2-3树再理解下面这段代码非常容易
	 * @author shuaitian
	 * @param node
	 */
	private void adjust(Node<Key,Value> node){

		if(isBlack(node) && isBlack(node.getLeft()) && isRed(node.getRight())){
			node = rotateLeft(node);
			if(node.getParent() == null)
				root = node;
		}
		if(isRed(node) && isRed(node.getLeft())){
			Node<Key,Value> parent = node.getParent();
			parent = rotateRight(parent);
			flipColors(parent);
			Node<Key,Value> nextNode = parent.getParent();
			if(nextNode == null){
				//已经到了根节点
				this.root = parent;
			}else
				adjust(nextNode);
		}
		if(isRed(node) && isRed(node.getRight())){
			node = rotateLeft(node);
			Node<Key,Value> parent = node.getParent();
			parent = rotateRight(parent);
			flipColors(parent);
			Node<Key,Value> nextNode = parent.getParent();
			if(nextNode == null){
				//已经到了根节点
				this.root = parent;
			}else
				adjust(nextNode);
		}
		if(isBlack(node) && isRed(node.getLeft()) && isRed(node.getRight())){
			flipColors(node);
			Node<Key,Value> nextNode = node.getParent();
			if(nextNode == null){
				//已经到了根节点
				this.root = node;
			}else
				adjust(nextNode);
		}
	}
	
	/**
	 * 判断当前节点颜色是否为黑色，空节点颜色默认为黑色
	 * @author shuaitian
	 * @param node
	 * @return
	 */
	private boolean isBlack(Node<Key,Value> node){
		if(node == null || node.isBlack())
			return true;
		return false;
	}
	
	/**
	 * 判断当前节点颜色是否为红色
	 * @author shuaitian
	 * @param node
	 * @return
	 */
	private boolean isRed(Node<Key,Value> node){
		if(node != null && node.isRed())
			return true;
		return false;
	}
	
	/**
	 * 更改左右子节点颜色为黑色，并将自己的颜色设置为红色
	 * @author shuaitian
	 * @param node
	 */
	private void flipColors(Node<Key,Value> node){
		node.getLeft().setBlack();
		node.getRight().setBlack();
		node.setRed();
	}
	
	/**
	 * get函数递归实现，为public Value get(Key key) 提供调用接口
	 * @param node
	 * @param key
	 * @return
	 */
	private Value get(Node<Key,Value> node,Key key){
		compareCount++;
		if(node == null)
			return null;
		Key k = node.getKey();
		if(key.compareTo(k) < 0)
			return get(node.getLeft(),key);
		else if(key.compareTo(k) > 0)
			return get(node.getRight(),key);
		else
			return node.getValue();
	}
	
	/**
	 * 对外提供的调用接口
	 * @param key
	 * @param value
	 */
	public void put(Key key,Value value){

//		root = put1(root,key,value);
//		root.setBlack();
		if(root == null){
			root = new Node<Key,Value>(key,value,Node.BLACK);
		}
		else{
			put2(root,key,value);
			root.setBlack();
		}
	}
	
	/**
	 * 对外提供的调用接口
	 * @param key
	 * @return
	 */
	public Value get(Key key){
		compareCount = 0;
		return get(root,key);
	}
	
	
	/**
	 * for test
	 * @return
	 */
	public int getLastCompareCount(){
		return this.compareCount;
	}
	
	/**
	 * for test
	 * @return
	 */
	public Node<Key,Value> getRoot(){
		return this.root;
	}
}
