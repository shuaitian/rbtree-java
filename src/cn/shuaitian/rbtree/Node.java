package cn.shuaitian.rbtree;
/**
 * RBTree Node
 * @author shuaitian
 * @param <Key,Value> Key为排序键值，Value为存储值
 */
public class Node<Key extends Comparable<Key>,Value> {
	public static final boolean RED = true;
	public static final boolean BLACK = false;
	private Node<Key,Value> left;
	private Node<Key,Value> right;
	private Node<Key,Value> parent;
	private boolean color;	//true for red and false for black
	private Key key;
	private Value value;
	public Node(Key key,Value value,boolean color){
		this(key,value,color,null);
	}
	
	public Node(Key key,Value value,boolean color,Node<Key,Value> parent){
		this.key = key;
		this.value = value;
		this.color = color;
		this.parent = parent;
	}
	
	public void setKey(Key key){
		this.key = key;
	}
	public Key getKey(){
		return this.key;
	}
	public Node<Key,Value> getLeft() {
		return left;
	}
	public void setLeft(Node<Key,Value> left) {
		this.left = left;
	}
	public Node<Key,Value> getRight() {
		return right;
	}
	public void setRight(Node<Key,Value> right) {
		this.right = right;
	}
	public Node<Key,Value> getParent() {
		return parent;
	}
	public void setParent(Node<Key,Value> parent) {
		this.parent = parent;
	}
	public boolean isColor() {
		return color;
	}
	public void setColor(boolean color) {
		this.color = color;
	}
	public Value getValue() {
		return value;
	}
	public void setValue(Value value) {
		this.value = value;
	}
	public boolean isRed(){
		return color;
	}
	public boolean isBlack(){
		return !color;
	}
	public void setRed(){
		this.color = true;
	}
	public void setBlack(){
		this.color = false;
	}
	public boolean getColor() {
		return this.color;
	}
	
}
