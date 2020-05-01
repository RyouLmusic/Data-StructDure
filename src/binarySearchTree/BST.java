package binarySearchTree;

import binaryTree.BinNode;
import binaryTree.BinTree;

public abstract class BST<T extends Comparable> extends BinTree {

    abstract BinNode<T> search(T data);//查找
    abstract BinNode<T> insert(T data);//插入
    abstract boolean remove(T data);//删除


}
