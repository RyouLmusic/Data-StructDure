package advancedSearchTree.splay;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {

        SplayTree<Integer> splayTree = new SplayTree<>(12);

        splayTree.insert(14);
        splayTree.insert(28);
        splayTree.insert(47);
        splayTree.insert(56);

//        System.out.println(splayTree.search(39).data);

        splayTree.insert(39);
        splayTree.insert(13);
        splayTree.insert(8);
        splayTree.insert(3);
        splayTree.insert(92);

        splayTree.remove(92);
        splayTree.remove(3);
        splayTree.remove(12);
        splayTree.remove(47);
        splayTree.remove(8);
        splayTree.remove(13);
        splayTree.remove(14);
        splayTree.remove(28);
        splayTree.remove(39);
        splayTree.remove(56);

        splayTree.insert(92);
//
//
        System.out.println(splayTree.search(15));
        System.out.println(splayTree.root());
        splayTree.travIn_I1(splayTree.root());


        Vector<Integer> vector = new Vector<>();

        List<Integer> list = new ArrayList<>();

    }
}
