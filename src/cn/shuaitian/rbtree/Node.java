package cn.shuaitian.rbtree;
/**
 * RBTree Node
 * @author tishuai
 *
 * @param <T>
 */
public class Node<T> {
	private Node<T> left;
	private Node<T> right;
	private Node<T> parent;
	private boolean color;	//true for red and false for black
	private T value;
	public Node<T> getLeft() {
		return left;
	}
	public void setLeft(Node<T> left) {
		this.left = left;
	}
	public Node<T> getRight() {
		return right;
	}
	public void setRight(Node<T> right) {
		this.right = right;
	}
	public Node<T> getParent() {
		return parent;
	}
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	public boolean isColor() {
		return color;
	}
	public void setColor(boolean color) {
		this.color = color;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
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
	
}
