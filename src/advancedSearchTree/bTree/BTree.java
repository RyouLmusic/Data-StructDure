package advancedSearchTree.bTree;


public class BTree<T extends Comparable> {

    private final int PATH_NUMBER; //阶次，路的总数最大值，最小值为 PATH_NUMBER/2 取上整
    private int _size; //树的规模
    private BTNode<T> _root; //树根

    protected BTNode<T> _hot; //动态操作的节点。search()最后访问的非空节点


    public BTree(int pathNum, T e){
        PATH_NUMBER = pathNum;
        _root = new BTNode<>(e);
        _size = 1;
    }

    public BTree(int pathNum){
        PATH_NUMBER = pathNum;
        _size = 0;
    }
    protected void solveOverFlow(BTNode<T> v){//解决上溢

        if (PATH_NUMBER >= v.child.size()) return; //递归基， 没有溢出

        int rank = PATH_NUMBER / 2; //被溢出分裂成上面的节点位置(此时的PATH_NUMBER = key.size() = v.child.size()-1)


        BTNode<T> newNode = new BTNode<>(); //创建新的节点，是e的右孩子

        for (int j = 0; j < PATH_NUMBER-rank-1; j++){ //将v节点的后半部分复制到新的节点中
            newNode.child.insert(j, v.child.remove(rank+1)); // 每次rank+1这个位置的元素都会变化
            newNode.key.insert(j, v.key.remove(rank+1));//
        }

        newNode.child.insert(PATH_NUMBER-rank-1, v.child.remove(rank+1)); //孩子比关键码多一个，所以把最后一个加上

        if (newNode.child.get(0) != null) { //如果newNode的孩子非空，那么把他们的父节点指向newNode
            for (int i = 0; i < newNode.child.size(); i++) //newNode.child.size() = PATH_NUMBER-rank
                newNode.child.get(i).parent = newNode;
        }

        BTNode<T> p = v.parent; //v的父节点
        if (p == null){ //p为空，所以v为根节点
            p = new BTNode<>();
            _root = p;

            p.child.insert(0, v);
            v.parent = p;
        }

        T e = v.key.remove(rank); //取出要上升的关键码
        int r = p.key.search(e); //查找关键码插入的位置
        p.key.insert(r+1, e); // 插入关键码
        p.child.insert(r+2, newNode); //插入孩子
        newNode.parent = p; //把孩子的父节点指向p

        solveOverFlow(p); //递归解决
    }
    protected void solveUnderFlow(BTNode<T> v){ //解决下溢

        if ((PATH_NUMBER+1) / 2 <= v.key.size()) return; //递归基，v并没有下溢
        BTNode<T> p = v.parent;
        //只有合并会减少根节点的key数目
        if (p == null) { //v是根节点,递归基：已到根节点，没有孩子的下限

            if (v.key.isEmpty() && v.child.get(0) != null){ //但倘若作为树根的v已不含关键码，却有（唯一的）非空孩子，则

                _root = v.child.get(0);
                _root.parent = null; //树高降低
            }
            return;
        }

        int rank = 0;
        while (p.child.get(rank) != v) rank++; //确认v是p的第几个孩子

        //情况一
        if (rank > 0) { //v的左兄弟存在
            BTNode<T> lb = p.child.get(rank-1);

            if ((PATH_NUMBER + 1) / 2 < lb.child.size()){ //至少多出一个
                T e = p.key.get(rank-1); //获取p的那个关键码
                v.key.insert(0, e); //将获取的关键码插入v的第一个关键码位置

                e = lb.key.remove(lb.key.size()-1); //获取左兄弟的最后一个关键码，并删除，并且把这个关键码给p
                p.key.set(rank-1, e); //将e替换刚刚被v拿去的关键码的位置

                BTNode<T> u = lb.child.remove(lb.child.size()-1);
                v.child.insert(0, u); //将左兄弟v的最后一个关键码的孩子转移到v新插入的节点的第一个孩子的位置
                if (u != null) u.parent = v;

                return;
            }
        }

        //情况二
        if (p.child.size()-1 > rank) { //v的右兄弟存在

            BTNode<T> rb = p.child.get(rank+1); //右兄弟必存在
            if ((PATH_NUMBER+1) / 2 < rb.child.size()) { //rb的右孩子足够可以被v借走一个关键码

                T e = p.key.get(rank); //获取介于中间的关键码
                v.key.insert(v.key.size(), e); //插入v的最后一个位置

                e = rb.key.remove(0); //获取rb的第一个关键码，并直接删除
                p.key.set(rank, e);//将p被v拿去的关键码替换成从rb获取的关键码

                BTNode<T> u = rb.child.remove(0);
                v.child.insert(v.child.size(), u);
                if (u != null) u.parent = v;

                return;
            }
        }

        //情况三: 左、右兄弟要么为空（但不可能同时），要么都太“瘦”——合并
        if (rank > 0){ //与左兄弟合并

            BTNode<T> lb = p.child.get(rank-1);//左兄弟
            lb.key.insert(lb.key.size(), p.key.remove(rank-1)); //将p的关键码转移到lb的最后的位置上来
            p.child.remove(rank); //将p的孩子v的连接断开

            BTNode<T> u = v.child.remove(0);
            lb.child.insert(lb.child.size(), u);
            if (u != null) u.parent = lb; //v的最左侧孩子过继给ls做最右侧孩子

            while (!v.key.isEmpty()){ //将v的关键码和孩子都移到lb来

                lb.key.insert(lb.key.size(), v.key.remove(0)); //关键码的转移

                u = v.child.remove(0);
                lb.child.insert(lb.child.size(), u);
                if (u != null) u.parent = lb;
            }

            //此时v已经得到释放，再也没有指针指向v
        }else { //与右兄弟合并

            BTNode<T> rb = p.child.get(rank+1);//得到右兄弟，一定有

            rb.key.insert(0, p.key.remove(rank)); //将p.key.get(rank)的关键码转移到rb的key的0位置
            p.child.remove(rank); //将p的孩子v删除

            BTNode<T> u = v.child.remove(v.child.size()-1);
            rb.child.insert(0, u);
            if (u != null) u.parent = rb; //将v最右关键码的孩子作为rb起始位置的孩子

            while (!v.key.isEmpty()){ //将v的关键码和孩子都移到rb来
                rb.key.insert(0, v.key.remove(v.key.size()-1)); //关键码的转移

                u = v.child.remove(v.child.size()-1);
                rb.child.insert(0, u);
                if (u != null) u.parent = rb; //孩子的转移
            }

            //v得到释放
        }

        solveUnderFlow(p); //递归处理下溢
    }

    public BTNode<T> search(T e){ //查找

        BTNode<T> v = _root;
        _hot = null;

        while (v != null){

            int rank = v.key.search(e); //返回e在key里面的位置
            if (rank >= 0 && v.key.get(rank).compareTo(e) == 0) return v; //利用短路原理进行判断，如果找对了就直接返回v

            _hot = v;
            v = v.child.get(rank+1); //引用到下层
        }

        return null; //失败，直接返回null，当然v也是null
    }

    public boolean insert(T e){  //插入

        if (_root == null){ //如果空树的话，直接处理
            _root = new BTNode<>(e);
            _size++;
        }
        BTNode<T> v = search(e);
        if (v != null) return false; //确认e不存在，可以进行插入

        int rank = _hot.key.search(e); //找到插入的位置

        _hot.key.insert(rank+1, e); //插入e，在rank+1的位置
        _hot.child.insert(rank+2, null); //插入新的孩子，

        _size++;//规模加一

        solveOverFlow(_hot); //解决上溢问题
        return false;
    }

    public boolean remove(T e){ //删除

        BTNode<T> v = search(e); //查找是否存在
        if (v == null) return false; //e不存在，所以直接返回false
        int rank = v.key.search(e); //确定目标关键码在节点v中的秩（由上，肯定合法）

        if (v.child.get(0) != null) { //如果v不是叶子

            //找出e的直接后继
            BTNode<T> u = v.child.get(rank+1);  //先往右，接着一直向左，直到叶节点
            while (u.child.get(0) != null)
                u = u.child.get(0);

            v.key.set(rank, u.key.get(0));//把v的key换成u的第一个值
            v = u;
            rank = 0;
        }
        v.key.remove(rank);//删除
        v.child.remove(rank+1); //删除v的孩子
        _size--;//规模减一

        solveUnderFlow(v); //处理下溢
        return true;
    }


    /**中序遍历：递归实现*/
    public void travIn_R(BTNode<T> btNode){
        if (btNode == null) return; //递归基

        if (btNode.child.get(0) == null){ //到达页节点的时候，直接进行遍历访问
            btNode.key.print();
            return;
        }

        for (int i = 0; i < btNode.child.size(); i++){ //循环进行访问btNode.key
            travIn_R(btNode.child.get(i)); //向下递归

            if (i < btNode.key.size()) //关键码的vector大小比孩子的vector小
                print(btNode.key.get(i)); //进行访问
        }

    }

    /**中序遍历：迭代实现 TODO 以后实现*/
    public void travIn_I(BTNode<T> btNode){
    }

    public void print(T e){
        System.out.print(e + " ");
    }

    public BTNode<T> getRoot(){
        return _root;
    }

    public int getSize(){
        return _size;
    }
}
