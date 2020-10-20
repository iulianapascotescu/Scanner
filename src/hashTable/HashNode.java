package hashTable;

public class HashNode<K> {
    K key;
    HashNode<K> next;

    public HashNode(K key) {
        this.key = key;
        this.next = null;
    }
}
