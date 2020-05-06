package advancedSearchTree.bTree;

public class Student implements Comparable<Student> {

    int id;

    @Override
    public int compareTo(Student o) {
        return this.id - o.id;
    }
}
