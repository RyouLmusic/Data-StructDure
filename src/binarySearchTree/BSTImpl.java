package binarySearchTree;

import binaryTree.BinNode;
import binaryTree.BinTree;

public class BSTImpl<T extends Comparable> extends BST<T> {

    protected BinNode<T> _hot; // 被选中的节点的父节点

    private boolean isRChild = true;

    public BSTImpl(){
        super();
    }
    public BSTImpl(T data){
        this._root = new BinNode<>(data);
    }

    @Override
    public BinNode<T> search(T data) { //返回值是找到的节点

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

        _hot = removeAt(binNode); //分类进行删除

        _size--;//规模减一

        updateHeightAbove(_hot);
        return true;
    }

    protected BinNode<T> removeAt(BinNode<T> binNode) {

        BinNode<T> w = binNode; //实际被删除的节点
        BinNode<T> hot = null;
        if (binNode.LChild == null){ //没有左孩子

            if (binNode == _root && binNode.RChild != null){
                _root = binNode.RChild;
                _root.parent = null;
            }else {
                binNode = binNode.RChild; //
                hot = w.parent; //保存被删除节点的父节点，双分支的是保存数据交换后的
                fromParent(hot, w, binNode);// 更新来自父节点的连接
            }


        }else if (binNode.RChild == null) { //没有右孩子

            if (binNode == _root && binNode.LChild != null){
                _root = binNode.LChild;
                _root.parent = null;
            }else {

                binNode = binNode.LChild;
                hot = w.parent; //保存被删除节点的父节点，双分支的是保存数据交换后的

                fromParent(hot, w, binNode);// 更新来自父节点的连接
            }

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
        return hot;
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

    /**
     * 旋转操作
     * @param v
     * @return
     */
    protected BinNode<T> rotateAt(BinNode<T> v, BinNode<T> p, BinNode<T> g){
       return null;
    }

    protected BinNode<T> rotateAt(BinNode<T> v){

        BinNode<T> p = v.parent;
        BinNode<T> g = p.parent; //视v、p和g相对位置分四种情况

        if (p.isRChild(g)){ //zag(向右倾斜)

            if (v.isRChild(p)){ // zag-zag

                p.parent = g.parent; //向上联接
                boolean connecSucc =  FromParentTo(p, p.parent);//向下连接
                if (!connecSucc) {
                    _root = p;
                }


                return connect34 ( g, p, v, g.LChild, p.LChild, v.LChild, v.RChild );
            }else {  //zag-zig
                v.parent = g.parent;

                FromParentTo(v, v.parent);//向下连接

                return connect34(g, v, p, g.LChild, v.LChild, v.RChild, p.RChild);
            }

        }else {// zig

            if (v.isRChild(p)){ //zig-zag
                v.parent = g.parent;

                FromParentTo(v, v.parent);//向下连接

                return connect34(p, v, g, p.LChild, v.LChild, v.RChild, g.RChild);
            }else { //zig-zig
                p.parent = g.parent; //向上联接

                FromParentTo(p, p.parent);//向下连接
                return connect34 ( v, p, g, v.LChild, v.LChild,p.RChild, g.RChild );
            }
        }

    }

    /**
     * 3-4重构法：直接拆开整合
     * @param a
     * @param b
     * @param c
     * @param T0
     * @param T1
     * @param T2
     * @param T3
     * @return
     */
    protected BinNode<T> connect34(BinNode<T> a, BinNode<T> b, BinNode<T> c,
                                   BinNode<T> T0, BinNode<T> T1, BinNode<T> T2, BinNode<T> T3){

        a.LChild = T0;
        if (T0 != null) T0.parent = a;
        a.RChild = T1;
        if (T1 != null) T1.parent = a;

        c.LChild = T2;
        if (T2 != null) T2.parent = c;
        c.RChild = T3;
        if (T3 != null) T3.parent = c;

        b.LChild = a;
        b.RChild = c;
        a.parent = b;
        c.parent = b;

        updateHeight(a); //更新高度，T系列的高度并不会因为重构而改变
        updateHeight(b);
        updateHeight(c);

        return b;
    }


    //来自父节点的连接
    protected boolean FromParentTo(BinNode<T> child, BinNode<T> parent) {
        if (parent != null && child.isRChild(parent)) { //利用短路原理
            parent.RChild = child;
            return true;
        }
        else if (parent != null) {
            parent.LChild = child;
            return true;
        }
        return false;
    }
}
