package advancedSearchTree.splay;

import binarySearchTree.BSTImpl;
import binaryTree.BinNode;

/**
 * 适合用于需要局部性原理的数据：但是不能避免单次最坏的情况出现
 * @param <T>
 */
public class SplayTree<T extends Comparable> extends BSTImpl<T> {


    public SplayTree(){}

    public SplayTree(T data){
        this._root = new BinNode(data);
    }

    protected BinNode<T> splay(BinNode<T> v) { //将v伸展至树根

        if (v == null) return null; //如果v为空则直接返回

        BinNode<T> p; // v的parent
        BinNode<T> g; // g的parent

        while ( (p = v.parent) != null && (g = p.parent) != null) { //自下而上，反复对v做双层伸展
            BinNode<T> gg = g.parent; //每轮之后v都以原曾祖父（great-grand parent）为父

            if (v.isRChild(p)){ //zag

                if (p.isRChild(g)){ //zag-zag
                    /**对改变了孩子的连接的先进行，以免影响接下来的连接*/
                    attachAsRChild(p.LChild, g); // g.RChild = p.LChild
                    attachAsRChild(v.LChild, p); // p.RChild = v.LChild
                    attachAsLChild(g, p); //p.LChild = g
                    attachAsLChild(p, v); //v.LChild = p

                }else { //zag-zig

                    attachAsRChild(v.LChild, p);
                    attachAsLChild(v.RChild, g);
                    attachAsLChild(p, v);
                    attachAsRChild(g, v);

                }
            }else { //zig

                if (p.isRChild(g)){ //zig-zag

                    attachAsRChild(v.LChild, g);
                    attachAsLChild(v.RChild, p);
                    attachAsLChild(g, v);
                    attachAsRChild(p, v);

                }else {//zig-zig

                    attachAsLChild(v.RChild, p);
                    attachAsLChild(p.RChild, g);
                    attachAsRChild(p, v);
                    attachAsRChild(g, p);
                }

            }

            //进行3-4重构之后，要把v和gg(g的父节点)进行连接

            if (gg == null){ //gg为空的话，v为根节点
                v.parent = null; //断开v之前的parent
                _root = v;
            }else {
                v.parent = gg;
                FromParentTo(v, gg);
            }
            updateHeight ( g ); updateHeight ( p ); updateHeight ( v ); //更新高度,g都是最底层的所以最先更新
        }
        //双层伸展结束时，必有g == NULL，但p可能非空，利用(p = v.parent) != null && (g = p.parent) != null的短路原理
        if ((p = v.parent) != null){  //若p果真非空，则额外再做一次单旋

            if (v.isRChild(p)) {

                attachAsRChild(v.LChild, p);
                attachAsLChild(p, v);
            }else {
                attachAsLChild(v.RChild, p);
                attachAsRChild(p, v);
            }

            updateHeight(p);
            updateHeight(v);
        }
        v.parent = null;
        _root = v;
        return _root;
    }

    //lChild和parent进行上下连接
    private void attachAsLChild(BinNode<T> lChild, BinNode<T> parent) {
        parent.LChild = lChild;
        if (lChild != null)
            lChild.parent = parent;
    }
    //rChild和parent进行上下连接
    private void attachAsRChild(BinNode<T> rChild, BinNode<T> parent) {
        parent.RChild = rChild;
        if (rChild != null)
            rChild.parent = parent;
    }

    @Override
    public BinNode<T> search(T data) {

        BinNode<T> binNode = super.search(data); //

        if (binNode == null)
            splay(_hot);
        else
            splay(binNode);

        return _root;
    }

    @Override
    public BinNode<T> insert(T data) {

        if (_root == null) { //如果_root为空
            _size++;
            return _root = new BinNode<>(data);
        }
        BinNode<T> binNode = search(data);
        if (data == binNode.data) return _root; //要确保data数据不存在之前的splay树中，否则直接返回root

        BinNode<T> newNode = new BinNode<>(data);
        if (newNode.isRChild(binNode)){ //newNode.data > binNode.data

            if (binNode.RChild != null)
                attachAsRChild(binNode.RChild, newNode);
            binNode.RChild = null;
            attachAsLChild(binNode, newNode);
            _root = newNode;
        }else {

            if (binNode.LChild != null)
                attachAsLChild(binNode.LChild, newNode);
            binNode.LChild = null;
            attachAsRChild(binNode, newNode);
            _root = newNode;
        }
        updateHeightAbove(newNode);
        return _root;
    }

    @Override
    public boolean remove(T data) {
        BinNode<T> binNode = search(data); //binNode保存的是原来的root，即是要被删除的那个节点
        if (_root == null || binNode.data != data) return false; //若树空或目标不存在，则无法删除

        if (binNode.RChild == null){ //若无右子树
            _root = binNode.LChild;
            if (_root != null)
                _root.parent = null;
        }else if (binNode.LChild == null){ //若无左子树
            _root = binNode.RChild;
            if (_root != null)
                _root.parent = null;
        }else { //左右都有
            BinNode<T> lTree = _root.LChild;

            lTree.parent = null;
            _root.LChild = null; //切断左子树

            _root = _root.RChild;
            _root.parent = null; //把原来的root节点切断，只保留右子树

            search(binNode.data); //以原树根为目标，做一次（必定失败的）查找。至此，右子树中最小节点必伸展至根，且（因无雷同节点）其左子树必空，于是

            _root.LChild = lTree;
            lTree.parent = _root; //左子树重新连接
        }

        if (_root != null) updateHeight(_root); //其他的节点的高度没有改变

        return true;
    }
}
