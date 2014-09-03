package pt.ua.it.atnog.utils.structures;

public class KDNode <T extends Point>{
    public T data;
    public KDNode<T> left, right;

    public KDNode(T data) {
        this.data = data;
        left = null;
        right = null;
    }

    public String toString() {
        return data.toString();
    }
}