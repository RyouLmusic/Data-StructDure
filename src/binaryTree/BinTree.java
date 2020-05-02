package binaryTree;

import sun.misc.Queue;

import java.util.Stack;

public class BinTree<T extends Comparable> {

    protected int _size; //树的规模
    protected BinNode<T> _root; //根节点

    public BinTree(){
    }

    public BinTree(T data){
        this._root = new BinNode<>(data);
    }

    protected int stature(BinNode<T> p){ //只有一个root的时候，其height为0；规定p为空树的时候height为-1
        if(p == null)
            return -1;
        else
            return p.height;
    }

    protected int updateHeight(BinNode<T> x){ //更新节点x的高度
        return x.height = 1 + Math.max(stature(x.LChild), stature(x.RChild)); //重新计算height的大小
    }

    protected void updateHeightAbove(BinNode<T> x){ //更新x及历代祖先的高度
        while (x != null){
            updateHeight(x);
            x = x.parent;
        }
    }


    public int size(){ //返回树的规模
        return _size;
    }

    public BinNode<T> root(){ //返回树的根节点
        return _root;
    }

    public boolean isEmpty(){ //判断树是否为空
        return _root == null;
    }
    /**节点插入：作为根节点*/
    public BinNode<T> insertAsRoot (T  e){ //插入根节点
        if (_root == null)
            _root = new BinNode<>(e);
        else {
            BinNode<T> root = new BinNode<>(e, _root);  //如果此树本来有root根节点，那么将之前的节点变成新根节点的LChild
            _root = root;
        }
        return _root;
    }
    /**节点插入：作为左孩子*/
    public BinNode<T> insertAsLC(BinNode<T> x, T e){//x.LChild = null;不然的话，原来的x左子树都会消失，被替换成新定节点

        _size++; //规模增加
        x.insertAsLC(e);
        updateHeightAbove(x); //更新x及其历代祖先的高度

        return x.LChild; //返回新建的节点
    }
    /**节点插入：作为右孩子*/
    public BinNode<T> insertAsRC(BinNode<T> x, T e){//x.Child = null;不然的话，原来的x右子树都会消失，被替换成新定节点

        _size++; //规模增加
        x.insertAsRC(e);
        updateHeightAbove(x); //更新x及其历代祖先的高度

        return x.RChild; //返回新建的节点
    }

    /**子树接入*/
    public BinNode<T> attachAsRC(BinTree<T> binTree, BinNode<T> binNode){ //将binTree当成binNode的右子树接入

        binNode.RChild = binTree._root;
        binTree._root.parent = binNode; //连接
        _size += binTree._size;//更新规模

        updateHeightAbove(binNode);

        binTree._root = null; //置空
        binTree._size = 0;

        binTree = null;

        return binNode;//返回接入位置
    }

    /**子树删除*/
    public int remove(BinNode<T> binNode){ //删除二叉树中位置binNode处的节点及其后代，返回被删除节点的数值
        //切断binNode来自父节点的连接
        BinNode<T> parent = binNode.parent;
        if(parent.LChild == binNode) //binNode是其父节点的左孩子
            parent.LChild = null;
        else  //binNode是其父节点的右孩子
            parent.RChild = null;

        updateHeightAbove(parent);

        int n = removeAt ( binNode );
        _size -= n;

        //释放内存

        return 0;
    }

    private int removeAt(BinNode<T> binNode) {//删除二叉树中位置x处的节点及其后代，返回被删除节点的数值

        if(binNode == null) return 0; //递归基

        int n = 1 + removeAt(binNode.LChild) + removeAt(binNode.RChild); //本身加左孩子树加右孩子树

        return n;
    }

    /**遍历算法：递归实现先序遍历*/
    public void travPre_R(BinNode<T> node){

        if(node == null) return; //递归基
        print(node.data);

        travPre_R(node.LChild);
        travPre_R(node.RChild);
    }
    /**遍历算法：利用栈机制实现先序遍历*/
    public void travPre_I1(BinNode<T> binNode){ //迭代版

        Stack<BinNode<T>> nodeStack = new Stack<>(); //辅助栈
        if (binNode != null)
            nodeStack.push(binNode); //将首节点进栈
        while (!nodeStack.empty()){ //只要栈是非空的就继续循环

            BinNode<T> node = nodeStack.pop();
            print(node.data); //进行访问

            if (node.RChild != null)
                nodeStack.push(node.RChild); //左孩子进栈
            if (node.LChild != null)
                nodeStack.push(node.LChild); //右孩子后进栈，但是先出栈
        }
    }/**算法B:从当前节点出发，沿左分支不断深入，直至没有左分支的节点；沿途节点遇到后立即访问，右孩子入栈暂存*/

    /**遍历算法：递归实现中序遍历*/
    public void travIn_R(BinNode<T> node){

        if (node == null) return; //递归基

        travIn_R(node.LChild); //左孩子
        print(node.data); //访问数据
        travIn_R(node.RChild);//右孩子

    }
    /**遍历算法：利用栈机制实现中序遍历*/
    public void travIn_I1(BinNode<T> binNode){

        Stack<BinNode<T>> binNodes = new Stack<>();

        goToBottom_In(binNodes, binNode);
        while (!binNodes.empty()){ //循环访问

            binNode = binNodes.pop(); //取出栈底的数据
            print(binNode.data); //进行访问
            binNode = binNode.RChild; //转向其右孩子，但是无需判断是否为空，goToBottom_In里面有进行判断
            goToBottom_In(binNodes, binNode);
        }

    }
    //把左孩子一直装到栈里直到没有了左孩子
    private void goToBottom_In(Stack<BinNode<T>> binNodes, BinNode<T> binNode) {

        while (binNode != null){
            binNodes.push(binNode);
            binNode = binNode.LChild;
        }
    }

    /**遍历算法：递归实现后序遍历*/
    public void travPost_R(BinNode<T> binNode){

        if (binNode == null) return; //递归基
        travPost_R(binNode.LChild);
        travPost_R(binNode.RChild);

        print(binNode.data);
    }

    /**遍历算法：利用栈机制实现后序遍历*/
    public void travPost_I(BinNode<T> binNode){

        Stack<BinNode<T>> binNodeStack = new Stack<>();
        binNodeStack.push(binNode); //顶节点入栈

        while (!binNodeStack.empty()){

            if(binNode.parent != binNodeStack.peek()) //如果接下来栈顶的节点是binNode的父节点，那么他已经进行放入栈的活动了，不需要进行loading_Post
                loading_Post(binNodeStack);

            binNode = binNodeStack.pop();//取出栈顶的数据进行访问

            print(binNode.data); //访问

        }

    }
    //进栈操作
    private void loading_Post(Stack<BinNode<T>> binNodeStack) {

        BinNode<T> binNode = binNodeStack.peek();
        while (binNode != null){


            binNode = binNodeStack.peek();

            if (binNode.LChild != null) { //右孩子存在的话，先装入右孩子，转而装入左孩子。

                if(binNode.RChild != null)
                    binNodeStack.push(binNode.RChild);

                binNodeStack.push(binNode.LChild); //装入左孩子

            }else { //否则，只装入右孩子
                if(binNode.RChild != null)
                    binNodeStack.push(binNode.RChild);
            }

            binNode = binNode.RChild; //指向右孩子，如果右孩子为空，就退出此次循环
        }
    }

    /**遍历算法：利用队列实现层次遍历*/
    public void travLevel(BinNode<T> binNode){

        Queue<BinNode<T>> nodeQueue = new Queue<>(); //辅助队列
        nodeQueue.enqueue(binNode); //首节点进队列

        while (!nodeQueue.isEmpty()){
            try {
                binNode = nodeQueue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(binNode.data); //访问

            if (binNode.LChild != null)
                nodeQueue.enqueue(binNode.LChild); //左孩子进队列

            if (binNode.RChild != null)
                nodeQueue.enqueue(binNode.RChild); //右孩子进队列
        }

    }

    /**打印*/
    public void print(T e){
        System.out.print(e + " ");
    }
}
