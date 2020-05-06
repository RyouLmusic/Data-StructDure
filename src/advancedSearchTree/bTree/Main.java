package advancedSearchTree.bTree;

public class Main {
    public static void main(String[] args) {
//        BTree<Student> bTree = new BTree<>(3, new Student());
        BTree<Integer> b = new BTree<>(3, 5);

        b.insert(6);
        b.insert(2);
        b.insert(7);
        b.insert(3);
        b.insert(4);
        b.insert(8);
        b.insert(15);
        b.insert(86);
        b.insert(269);
        b.insert(25);
        b.insert(24);
        b.insert(75);
        b.insert(753);
        b.insert(126);
        b.insert(782);
        b.insert(125);
        b.insert(358);
        b.insert(694);

        b.remove(358);
        b.remove(126);
        b.remove(75);
        b.remove(269);
        b.remove(753);
        b.remove(694);
        b.remove(5);
        b.remove(3);
        b.remove(2);
        b.remove(125);
        b.remove(15);
        b.remove(782);
        b.remove(4);
        b.remove(6);
        b.remove(24);
        b.remove(25);
        b.remove(8);
        b.remove(86);
        b.remove(7);


        System.out.println(b.getSize());

//        System.out.println(b.search(4));
//        b.remove(6);
//        b.remove(2);
//        b.remove(7);
//        b.remove(3);
//        b.remove(4);
//        b.remove(8);

        b.travIn_R(b.getRoot());

    }
}
