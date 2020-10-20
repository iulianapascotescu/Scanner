package hashTable;

import utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class HashTable<K> {
    private List<HashNode<K>> list;
    private int size;
    private int capacity;

    public HashTable() {
        this.list = new ArrayList<>();
        this.size = 0;
        this.capacity = 17;

        for (int i = 0; i < this.capacity; i++)
            this.list.add(null);
    }

    public HashTable(int capacity) {
        this.list = new ArrayList<>();
        this.size = 0;
        this.capacity = capacity;

        for (int i = 0; i < capacity; i++)
            this.list.add(null);
    }

    public int getSize() {
        return this.size;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isEmpty() {
        return this.list.size() == 0;
    }

    private int getElementIndex(K key) {
        int hash = key.hashCode();
        if(hash < 0)
            hash = (-1) * hash;
        return hash % this.capacity;
    }

    public int getElementPosition(K key) {
        int position = 0;
        int index = getElementIndex(key);
        HashNode<K> head = list.get(index);
        while (head != null) {
            if (head.key.equals(key))
                return position;
            head = head.next;
            position++;
        }
        return -1;
    }

    public Pair<Integer, Integer> get(K key) {
        return new Pair<>(this.getElementIndex(key), this.getElementPosition(key));
    }

    public void add(K key) {
        int bucketIndex = getElementIndex(key);
        HashNode<K> head = list.get(bucketIndex);
        while (head != null) {
            if (head.key.equals(key))
                return;
            head = head.next;
        }
        this.size++;
        head = this.list.get(bucketIndex);
        HashNode<K> newNode = new HashNode<K>(key);
        newNode.next = head;
        this.list.set(bucketIndex, newNode);
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < this.capacity; i++) {
            HashNode<K> head = list.get(i);
            while (head != null) {
                string += head.key + " " + this.get(head.key);
                string += "\n";
                head = head.next;
            }
        }
        return string;
    }

}
