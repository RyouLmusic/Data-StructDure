package binarySearchTree;

public class Main {
    public static void main(String[] args) {

        BST<Integer> binTree = new BSTImpl<>(11);
        binTree.insert(15);
        binTree.insert(25);
        binTree.insert(7);
        binTree.insert(78);
        binTree.insert(14);
        binTree.insert(71);
        binTree.insert(85);
        binTree.insert(782);
        binTree.insert(254);
        binTree.insert(358);

        binTree.remove(4);

        System.out.println("H: "+binTree.root().height);

        System.out.println(binTree.remove(11));
        System.out.println(((BSTImpl<Integer>) binTree).root().data);
//        System.out.println(binTree);
        binTree.travIn_I1(binTree.root());
    }
}
