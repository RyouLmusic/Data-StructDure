package advancedSearchTree.bTree;

import vector.Vector;

public class BTNode<T extends Comparable> implements Comparable {

    protected BTNode<T> parent;

    protected Vector<T> key;//关键码，总比孩子少一个
    protected Vector<BTNode<T>> child; //孩子

    public BTNode(){
        this.parent = null;
        this.key = new Vector<>();
        this.child = new Vector<>();
    }

    public BTNode(T e, BTNode<T> lChild, BTNode<T> rChild){
        this.parent = null;

        this.key = new Vector<>();
        this.child = new Vector<>();
        this.key.insert(0, e); //将第一个元素插入第一个V

        child.insert(0, lChild);
        child.insert(1, rChild); //插入两个孩子

        if (lChild != null) lChild.parent = this;
        if (rChild != null) rChild.parent = this; //将孩子的父节点指向本节点
        //结束初始化
    }

    public BTNode(T e){ //没有孩子
        this.parent = null;

        this.key = new Vector<>();
        this.child = new Vector<>();
        this.key.insert(0, e); //将第一个元素插入第一个V

        this.child.insert(0, null);
        this.child.insert(1, null); //插入两个孩子，都是为空
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
