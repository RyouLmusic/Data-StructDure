package binaryTree;

public class BinNode<T extends Comparable> {

    public BinNode<T> parent;//父节点
    public BinNode<T> LChild;//左孩子
    public BinNode<T> RChild;//右孩子

    public T data; //数据
    public int height;//树的高度
    private int _size; //TODO 树的规模

    public BinNode() {
    }
    public BinNode(T e) {
        this.data = e;
    }
    public BinNode(T data, BinNode<T> LChild) {  //给insertAsRoot方法准备的构造函数
        this.LChild = LChild;
        this.data = data;
    }

    public BinNode(BinNode<T> parent, T data) {  //给insertAsLC,insertAsRC方法准备的构造函数
        this.parent = parent;
        this.data = data;
    }


    protected BinNode<T> insertAsLC(T e){ //插入为左孩子
        return LChild = new BinNode<>(this, e);
    }

    protected BinNode<T> insertAsRC(T e){ //插入为右孩子
        return RChild = new BinNode<>(this, e);
    }
    //TODO 这里可以通过 _size 来改变性能
    public int size(){ //返回树的规模

        int s = 1; // 计入本身
        if (LChild != null) s += LChild.size(); //计入左孩子的规模
        if (RChild != null) s += RChild.size(); //计入右孩子的规模

        return s;
    }

    /**
     * 只在平衡搜索二叉树和AVL中有用
     * @param parent
     * @return
     */
    public boolean isRChild(BinNode<T> parent){ //判断this的节点是其父节点的右孩子

        if (this.data.compareTo(parent.data) > 0)//在右边
            return true;
        else
            return false;
    }

}
