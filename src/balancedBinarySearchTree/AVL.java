package balancedBinarySearchTree;

import binarySearchTree.BSTImpl;
import binaryTree.BinNode;

public class AVL<T extends Comparable> extends BSTImpl<T> {

    public AVL() {
    }
    public AVL(T data) {
        super(data);
    }

    /**
     * 返回插入的新节点
     * @param data
     * @return
     */
    @Override
    public BinNode<T> insert(T data) {

        BinNode<T> binNode = search(data);  //查找之后_hot为找到节点的父节点
        if (binNode != null) return binNode;   // 数据为data的节点已经存在，那么直接退出insert函数

        BinNode<T> newNode = new BinNode<>(_hot, data); //创建新的节点，并且直接连接_hot为父节点
        FromParentTo(newNode, _hot); //来自父节点的连接

        _size++; //规模加一
/**
        BinNode<T> v = newNode;
        BinNode<T> p = null; //v的父节点   */
        BinNode<T> g; //p的父节点


        //由于新的节点的插入可能会使_hot的祖先失去平衡
        for (g = _hot; g != null; g = g.parent) { //循环进行检测是否失衡

            if(!isBalanced(g)){ //出现不平衡 ，在第一个出现不平衡之后，进行旋转平衡操作之后，其他父节点都会平衡
//                upToParent(p, g); //将p回溯到p的父节点为g
//                upToParent(v, g); //  这样效率可能比较慢一点

                /**
                rotateAt(v, p, g); //进行适度平衡操作  */

                rotateAt(tallerChild(tallerChild(g)));
                break; // 进行一次旋转平衡操作之后，其他父节点都会平衡，所以直接退出循环
            }else {
                updateHeight ( g ); //如果树没有失衡，那么就不用进行适度平衡操作，只要进行
            }
            /**
            p = g;
            if (p != _hot) //跳过第一次，保证v的父节点为p
                v = v.parent;
            */
        }

        return newNode;
    }

    /**
     * 将p回溯到p的父节点为g
     * @param p
     * @param g
     */
    private void upToParent(BinNode<T> p, BinNode<T> g) {

        while (true) {
            if (p.parent == g) //到了p的父节点为g，直接返回
                break;

            p = p.parent;
        }
    }

    /**
     * 找出g更高的儿子。不需要这样弄
     * @param x
     * @return
     */
    private BinNode<T> tallerChild(BinNode<T> x) {
        if (stature(x.LChild) > stature(x.RChild))
            return x.LChild;
        else if (stature(x.RChild) > stature(x.LChild))
            return x.RChild;
        else {/*等高：与父亲x同侧者（zIg-zIg或zAg-zAg）优先*/

            if (x.isRChild(x.parent))
                return x.RChild;
            else
                return x.LChild;
        }

    }

    /**
     * 判断是否处于平衡状态，可以使用一个数据 int balFac(平衡因子) 用于记录
     * @param g
     * @return
     */
    private boolean isBalanced(BinNode<T> g) {
        int balFac = stature(g.RChild) - stature(g.LChild); //这个是插入新的数据之后height还没有更新的，所以下面的balFac为> 2 || <-2
        if (balFac > 1 || balFac < -1) //没有平衡
            return false;
        else
            return true;
    }


    @Override
    public boolean remove(T data) {

        BinNode<T> binNode = search(data); // 先查询

        if (binNode == null) return false; //节点并没有存在，所以删除失败

        _hot = removeAt(binNode); //进行删除

//        updateHeight(_hot); //进行检测平衡与否之前先进行更新高度

        for (BinNode<T> g = _hot; g != null; g = g.parent){ //_hot是被删除节点所在位置的父节点 TODO 没有描述清晰

            if (!isBalanced(g)){ //不平衡

                rotateAt(tallerChild(tallerChild(g)));
            }
            updateHeight(g); //更新高度
        }

        return true;
    }

}
