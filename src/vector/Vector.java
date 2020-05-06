package vector;

public class Vector<T extends Comparable> {


    private final int DEFAULT_CAPACITY = 3; //默认大小

    private int _size; //规模

    private int _capacity; //容量

    T[] _elem; //数据区

    public Vector(int c){
        _elem = (T[]) new Comparable[_capacity = c];
        _size = 0;
    }

    public Vector(){
        _elem = (T[]) new Comparable[_capacity = DEFAULT_CAPACITY];
        _size = 0;
    }

    private void expand(){ //扩容
        if (_size < _capacity) return; //尚未满员，不必扩容
        _capacity = Math.max(_capacity, DEFAULT_CAPACITY); //不得小于最小容量

        T[] oldElem = _elem;
        _elem = (T[]) new Comparable[_capacity <<= 1]; //加倍

        for (int i = 0;i < _size;i++){
            _elem[i] = oldElem[i];
        }

    }

    private void shrink(){

        if ((_size<<2) > _capacity || _capacity <= 6) return; //不需要缩容

        T[] oldElem = _elem;
        _elem = (T[]) new Comparable[_capacity >>= 1];

        for (int i = 0;i < _size;i++){
            _elem[i] = oldElem[i];
        }
    }


    public T get(int i){
        return _elem[i];
    }
    public void set(int i, T e){
        _elem[i] = e;
    }

    public int insert(int rank, T e){ //rank是插入的位置，e是插入的元素
        expand();
        for (int i = _size; i > rank; i--) //依次往后移
            _elem[i] = _elem[i-1];

        _elem[rank] = e; //插入
        _size++; //规模加一
        return rank; //返回插入的位置 --秩
    }

    public int insert(T e){ //直接插到最后
        return insert(_size, e);
    }

    public int remove(int lo, int hi){ //区间删除：删除[lo,hi) 所以 0<= lo <= hi <= _size

        if (lo == hi) return 0;

        while (hi < _size) _elem[lo++] = _elem[hi++];

        _size = lo;
        shrink();

        return (hi-lo);
    }

    public T remove(int rank){ //元素删除，删除在rank位置上的元素
        if (rank >= _size) return null;

        T e = _elem[rank];
        remove(rank, rank+1);

        return e;
    }

    public int search(T e){ //返回不大于e的最后一个元素的位置（）


        return binSearch(e, 0, _size);
    }


    private int search(T e, int lo, int hi){
        int rank = 0;
        for (int i = lo; i < hi; i++){
            if (_elem[i].compareTo(e) == 0)
                return (i-1);
            else if (e.compareTo(_elem[i]) < 0){
                rank = i-1;
                break;
            }
        }
        return rank;
    }

    private int binSearch(T e, int lo, int hi) {
        int mid; //避免(lo+hi)溢出

        while (lo < hi) {
            mid = lo + (hi - lo) / 2;

            if (e.compareTo(_elem[mid]) < 0) {
               hi = mid;
            }else {
                lo = mid + 1;
            }
        }  //出来时必有   lo == hi;_elem[lo-1] <= e
        return lo - 1;
    }

    public int size(){
        return _size;
    }

    public void print(){
        for (int i = 0; i < _size; i++)
            System.out.print(_elem[i] + " ");
    }

    public boolean isEmpty(){
        return _size == 0;
    }
}
