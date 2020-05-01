package binarySearchTree;

import binaryTree.BinNode;
import binaryTree.BinTree;

public class BSTImpl<T extends Comparable> extends BST<T> {

    private BinNode<T> _hot; // 被选中的节点的父节点

    private boolean isRChild = true;

    public BSTImpl(){
        super();
    }
    public BSTImpl(T data){
        this._root = new BinNode<>(data);
    }

    @Override
    public BinNode<T> search(T data) {

        _hot = null;
        return searchIn(_root, data, _hot);
    }

    private BinNode<T> searchIn(BinNode<T> root, T data, BinNode<T> binNode) {

        if (root == null || root.data == data) return root; //如果要找的目标就是root或者root为空，那么直接返回
        _hot = root.parent; //指定_hot
        BinNode<T> temp = root; //临时节点，以免改变原来树的节点

        while (true){

            if (data.compareTo(temp.data) > 0) {  //如果data大于root的data，转向右孩子
                _hot = temp;
                isRChild = true;
                temp = temp.RChild;
            }else { //如果data小于root的data，转向左孩子
                _hot = temp;
                isRChild = false;
                temp = temp.LChild;
            }

            if (temp == null || temp.data.compareTo(data) ==0) { //如果相等或者不存在，就直接返回
                return temp;
            }

        }

    }

    @Override
    public BinNode<T> insert(T data) { //没有重复

        BinNode<T> binNode = search(data); //找到data,也更新了_hot的数据

        if (binNode == null) { //没有重复的，可以插入
            binNode = new BinNode<>(_hot, data); //_hot作为binNode的父节点new
            if (isRChild) //判断是左孩子还是右孩子
                _hot.RChild = binNode;
            else
                _hot.LChild = binNode;

            _size++;

            updateHeightAbove(binNode);
        }
        return null;
    }

    /**
     * TODO 如果root节点只有一个孩子的话，程序不够完善，可以通过增加root节点的判断解决
     * @param data
     * @return
     */
    @Override
    public boolean remove(T data) {

        BinNode<T> binNode = search(data);//找到data,也更新了_hot的数据

        if (binNode == null) return false; //确定binNode存在，而且_hot为binNode的父节点

        removeAt(binNode, _hot); //分类进行删除

        _size--;//规模减一

        updateHeightAbove(_hot);
        return true;
    }

    private void removeAt(BinNode<T> binNode, BinNode<T> hot) {

        BinNode<T> w = binNode; //实际被删除的节点

        if (binNode.LChild == null){ //没有左孩子
            binNode = binNode.RChild; //

            hot = w.parent; //保存被删除节点的父节点，双分支的是保存数据交换后的
            fromParent(hot, w, binNode);// 更新来自父节点的连接

        }else if (binNode.RChild == null) { //没有右孩子
            binNode = binNode.LChild;
            hot = w.parent; //保存被删除节点的父节点，双分支的是保存数据交换后的
            fromParent(hot, w, binNode);// 更新来自父节点的连接

        }else { //双分支
            binNode = getSuccessor(binNode);
            T data = w.data;
            w.data = binNode.data;
            binNode.data = data;  //交换w和binNode的数据

            hot = binNode.parent;
            binNode = binNode.RChild; //binNode的左还是一定是null

            if (isRChild) //是右孩子
                hot.RChild = binNode;
            else  //是左孩子
                hot.LChild = binNode;

            if (binNode != null)
                binNode.parent = hot;

        }

    }
    //来自父节点的连接
    private void fromParent(BinNode<T> hot, BinNode<T> w, BinNode binNode) {
        if (hot.data.compareTo(w.data) > 0) {//来自w的父亲节点的连接
            hot.LChild = binNode;
        }else {
            hot.RChild = binNode;
        }
        if (binNode != null)
            binNode.parent = hot;
    }

    //找到w节点在中序遍历下的直接后继
    private BinNode<T> getSuccessor(BinNode<T> w) {

        w = w.RChild; //在w的左孩子之后直接循环得到其右孩子直到为空
        isRChild = true;
        while (true){

            if (w.LChild == null)
                return w;
            w = w.LChild;
            isRChild = false;

        }
    }


    private BinNode<T> rotateAt(){
        return null;
    }

    private BinNode<T> connect34(){
        return null;
    }


    public BinNode<T> get_hot(){
        return _hot;
    }
}
