package binaryTree;

import java.util.TreeSet;

public class Main {

    public static void main(String[] args) {

        BinTree<Integer> binTree = new BinTree<>();
        binTree.insertAsRoot(9);
        BinNode<Integer> root = binTree.root();

        binTree.insertAsRC(root, 18);
        binTree.insertAsLC(root, 10);

        binTree.insertAsLC(root.LChild, 6);


        BinTree<Integer> binTree1 = new BinTree<>();
        binTree1.insertAsRoot(100);
        BinNode<Integer> root1 = binTree1.root();

        binTree1.insertAsRC(root1, 300);
        binTree1.insertAsLC(root1, 200);

        binTree.attachAsRC(binTree1, root.LChild);

//        binTree.remove(root.LChild.RChild);

        binTree.travLevel(binTree.root());

    }
}
