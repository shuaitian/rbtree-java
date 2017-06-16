package cn.shuaitian.rbtree;

public class Main {
	public static void main(String[] args) {
		RBTree<String,Integer> tree = new RBTree<>();
		for(int i = 0; i < 32000; i++){
			tree.put("rbtree"+i, i);
		}
		
		System.out.println(tree.get("rbtree12") + " ,compareCount:" + tree.getLastCompareCount());
		System.out.println("root:" + tree.getRoot().getKey());
	}
}
