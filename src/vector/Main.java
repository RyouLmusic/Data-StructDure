package vector;

import advancedSearchTree.bTree.Student;

public class Main {
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>();
        v.insert(0, 1);
        v.insert(2);
        v.insert(3);
        v.insert(5);
        v.insert(6);
        v.insert(7);
        v.insert(8);

        v.remove(8);

//        System.out.println(v.search(-11));
//
//        v.print();

        Vector<Student> s = new Vector<>();

        s.insert(new Student());
        s.insert(new Student());
        s.insert(new Student());

        s.print();
    }
}
