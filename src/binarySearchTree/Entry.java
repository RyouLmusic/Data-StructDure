package binarySearchTree;

public class Entry<K, V> {

    K key; //关键码
    V value; //数据

    public Entry(){ //默认构造函数
    }

    public Entry(K key, V value){ //带参数的构造函数
        this.key = key;
        this.value = value;
    }

    public Entry(Entry<K, V> entry){  //克隆其他entry
        this.key = entry.key;
        this.value = entry.value;
    }

}
