package nl.saxion.cds.data_structures.list;

import nl.saxion.cds.collection.EmptyCollectionException;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.collection.ValueNotFoundException;
import nl.saxion.cds.data_structures.solution.MyArrayList;

import java.util.Iterator;

public class MyDoublyLinkedList<T> implements SaxList<T>, Iterable<T> {
    private Node<T> head,tail;
    private int size;

    public MyDoublyLinkedList() {
        head = tail = null;
        size = 0;
    }

    @Override
    public boolean contains(T value) {
        Node<T> current = head;
        while (current != null) {
            if (current.getValue() == null && value == null) {
                return true; // Both are null
            }
            if (current.getValue() != null && current.getValue().equals(value)) {
                return true; // Match found
            }
            current = current.getNext();
        }
        return false; // No match found
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
        } else {
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
        } else {
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
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
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

    public void removeAll(T value) throws ValueNotFoundException {
        Node<T> current = head;
        boolean found = false;

        while (current != null) {
            if (current.getValue().equals(value)) {
                found = true;
                if (current == head) {
                    removeFirst();
                    current = head;
                } else if (current == tail) {
                    removeLast();
                    current = null;
                } else {
                    Node<T> previous = current.getPrevious();
                    Node<T> next = current.getNext();
                    previous.setNext(next);
                    next.setPrevious(previous);
                    current = next;
                    --size;
                }
            } else {
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ValueNotFoundException("Value not found in the list");
        }
    }
    public void removeLastOccurrence(T value) throws ValueNotFoundException {
        Node<T> current = tail;

        while (current != null) {
            if (current.getValue().equals(value)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    Node<T> previous = current.getPrevious();
                    Node<T> next = current.getNext();
                    previous.setNext(next);
                    next.setPrevious(previous);
                    --size;
                }
                return;
            }
            current = current.getPrevious();
        }
        throw new ValueNotFoundException("Value not found in the list");
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
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
        StringBuilder result = new StringBuilder();
        result.append("digraph ").append(name).append(" {\n");

        Node<T> current = head;
        int index = 0;

        while (current != null) {
            result.append("    node").append(index).append(" [label=\"").append(current.getValue()).append("\"];\n");
            current = current.getNext();
            index++;
        }

        current = head;
        index = 0;

        while (current != null) {
            if (current.getNext() != null) {
                result.append("    node").append(index).append(" -> node").append(index + 1).append(" [label=\"next\"];\n");
                result.append("    node").append(index + 1).append(" -> node").append(index).append(" [label=\"previous\"];\n");
            }
            current = current.getNext();
            index++;
        }

        result.append("}\n");
        return result.toString();
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

    /**
     * Returns the last element in the list without removing it.
     *
     * @return the last element or null if the list is empty
     */
    public T peekLast() {
        if (tail == null) {
            return null;
        }
        return tail.getValue();
    }

    public MyArrayList<T> toArrayList() {
        MyArrayList<T> list = new MyArrayList<>();
        Node<T> current = head;
        while (current != null) {
            list.addLast(current.getValue());
            current = current.getNext();
        }
        return list;
    }
}


