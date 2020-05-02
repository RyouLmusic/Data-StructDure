package balancedBinarySearchTree;



public class Main {

    public static void main(String[] args) {
        AVL<Integer> avl = new AVL<>(2);
        avl.insert(4);
        avl.insert(22);
        avl.insert(84);
        avl.insert(19);
        avl.insert(75);
        avl.insert(28);
        avl.insert(7);
        avl.insert(69);

        avl.remove(4);
        avl.remove(2);
        avl.remove(22);

        avl.remove(84);
        avl.remove(75);

        /** 如果只有一个节点的话，删除不会成功：程序里面会有空指针*/
//        avl.remove(7);

        avl.travIn_I1(avl.root());
    }

}
