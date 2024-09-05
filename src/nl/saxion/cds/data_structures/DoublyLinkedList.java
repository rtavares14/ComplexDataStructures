package nl.saxion.cds.data_structures;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.collection.ValueNotFoundException;

import java.util.Iterator;

public class DoublyLinkedList<T> implements SaxList<T>, Iterable<T> {
    private Node<T> head,tail;
    private int size;

    public DoublyLinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue().equals(value)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getValue();
    }

    @Override
    public void addLast(T value) {
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            head = newNode;
            tail = head;
        }
        else if (1 == size ){
            tail = newNode;
            head.setNext(tail);
            tail.setPrevious(head);
        }
        else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        ++size;
    }

    @Override
    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            head = newNode;
            tail = head;
        }
        else if (size == 1) {
            head.setPrevious(newNode);
            newNode.setNext(head);
            tail = head;
            head = newNode;
        }
        else {
            head.setPrevious(newNode);
            newNode.setNext(head);
            head = newNode;
        }
        ++size;
    }

    @Override
    public void addAt(int index, T value) throws IndexOutOfBoundsException {
        if (index > size || index < 0){
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        if(isEmpty() || index == 0){
            addFirst(value);
        }
        else if (index == size) {
            addLast(value);
        }
        else{
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }

            Node<T> newNode = new Node<>(value);
            Node<T> currentPrev = current.getPrevious();

            currentPrev.setNext(newNode);
            newNode.setPrevious(currentPrev);
            newNode.setNext(current);
            current.setPrevious(newNode);

            ++size;
        }
    }

    @Override
    public void set(int index, T value) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        current.setValue(value);
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();

        T value = tail.getValue();

        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }

        --size;

        return value;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();

        T value = head.getValue();

        if (size == 1) {
            head = tail = null;
        } else {
            head = head.getNext();
            head.setPrevious(null);
        }

        --size;

        return value;
    }

    @Override
    public T removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid Index");
        }

        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }

            Node<T> previous = current.getPrevious();
            Node<T> next = current.getNext();
            previous.setNext(next);
            next.setPrevious(previous);
            --size;

            return current.getValue();
        }
    }

    @Override
    public void remove(T value) throws ValueNotFoundException {
        Node<T> current = head;

        while (current != null) {
            if (current.getValue().equals(value)) {
                if (current == head) {
                    removeFirst(); // The size will be decremented in removeFirst
                } else if (current == tail) {
                    removeLast(); // The size will be decremented in removeLast
                } else {
                    Node<T> previous = current.getPrevious();
                    Node<T> next = current.getNext();
                    previous.setNext(next);
                    next.setPrevious(previous);
                    --size; // Decrement size
                }
                return;
            }
            current = current.getNext();
        }
        throw new ValueNotFoundException("Value not found in the list");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.getValue();
                current = current.getNext();
                return value;
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String graphViz(String name) {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");

        Node<T> current = head;
        while (current != null) {
            result.append(current.getValue());
            if (current.getNext() != null) {
                result.append(", ");
            }
            current = current.getNext();
        }

        result.append("]");
        return result.toString();
    }

}


