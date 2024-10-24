package nl.saxion.cds.data_structures.solution;

import nl.saxion.cds.collection.*;

import java.util.Comparator;
import java.util.Iterator;

public class MyArrayList<V> implements SaxList<V>, SaxSearchable<V>, SaxSortable<V> {
    private static final int MINIMUM_SIZE = 32;
    private static final int MAXIMUM_EXTENSION = 256;

    private Object[] elements;
    private int size;

    public MyArrayList() {
        this(MINIMUM_SIZE);
    }

    public MyArrayList(int capacity) {
        this.size = 0;
        elements = new Object[capacity];
    }

    private void checkNull(V value) {
        if (value == null) {
            throw new IllegalArgumentException("Null values are not allowed in this list.");
        }
    }

    @Override
    public boolean contains(V value) {
        checkNull(value);
        for (int i = 0; i < size; ++i) {
            V element = (V) elements[i];
            if (element.equals(value)) return true;
        }
        return false;
    }

    @Override
    public V get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (V) elements[index];
    }

    @Override
    public void addLast(V value) {
        checkNull(value);
        checkAndExtendSize(size);
        elements[size - 1] = value;
    }

    @Override
    public void addFirst(V value) {
        checkNull(value);
        checkAndExtendSize(0);
        elements[0] = value;
    }

    @Override
    public void addAt(int index, V value) throws IndexOutOfBoundsException {
        checkNull(value);
        checkAndExtendSize(index);
        elements[index] = value;
    }

    @Override
    public void set(int index, V value) throws IndexOutOfBoundsException {
        checkNull(value);
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        elements[index] = value;
    }

    @Override
    public V removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        V value = (V) elements[--size];
        elements[size] = null;
        return value;
    }

    @Override
    public V removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        V value = (V) elements[0];
        removeAt(0);
        return value;
    }

    @Override
    public V removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        V value = (V) elements[index];
        if (index < --size) {
            System.arraycopy(elements, index + 1, elements, index, size - index);
        }
        elements[size] = null;
        return value;
    }

    @Override
    public void remove(V value) throws ValueNotFoundException {
        checkNull(value);

        int index = -1;
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new ValueNotFoundException(value.toString());
        }
        removeAt(index);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String graphViz(String name) {
        var builder = new StringBuilder();
        builder.append("digraph ");
        builder.append(name);
        builder.append(" {\n");
        for (int i = 0; i < size - 1; ++i) {
            V from = (V) elements[i];
            V to = (V) elements[i + 1];
            builder.append(String.format("\"%s\" -> \"%s\"\n", from.toString(), to.toString()));
        }
        builder.append("}");
        return builder.toString();
    }

    private void checkAndExtendSize(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(Integer.toString(index));
        if (elements.length < size + 1) {
            int capacity = elements.length < MAXIMUM_EXTENSION ? elements.length * 2 : elements.length + MAXIMUM_EXTENSION;
            var newElements = new Object[capacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
        if (index < size) {
            System.arraycopy(elements, index, elements, index + 1, size - index);
        }
        ++size;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; i < size; ++i) {
            builder.append(' ');
            builder.append(elements[i]);
        }
        builder.append(" ]");
        return builder.toString();
    }

    @Override
    public boolean isSorted(Comparator<V> comparator) {
        for (int i = 0; i < size - 1; i++) {
            V currentElement = (V) elements[i];
            V nextElement = (V) elements[i + 1];

            if (comparator.compare(currentElement, nextElement) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void simpleSort(Comparator<V> comparator) {
        for (int index = 0; index < size; ++index) {
            int smallest = index;
            for (int index2 = index; index2 < size; ++index2) {
                if (comparator.compare(get(smallest), get(index2)) > 0) {
                    smallest = index2;
                }
            }
            swap(index, smallest);
        }
    }

    protected void swap(int index1, int index2) {
        assert index1 >= 0 && index1 < size;
        assert index2 >= 0 && index2 < size;

        if (index1 != index2) {
            var temp = elements[index1];
            elements[index1] = elements[index2];
            elements[index2] = temp;
        }
    }

    @Override
    public void quickSort(Comparator<V> comparator) {
        quickSort(comparator, 0, size - 1);
    }

    private void quickSort(Comparator<V> comparator, int begin, int end) {
        if (end - begin >= 1) {
            int pivot = splitInPlace(comparator, begin, end);
            quickSort(comparator, begin, pivot - 1);
            quickSort(comparator, pivot + 1, end);
        }
    }

    private int splitInPlace(Comparator<V> comparator, int begin, int end) {
        V pivotValue = get(end);
        int i = begin - 1;

        for (int j = begin; j < end; j++) {
            if (comparator.compare(get(j), pivotValue) <= 0) {
                i++;
                swap(i, j);
            }
        }

        swap(i + 1, end);
        return i + 1;
    }

    @Override
    public int linearSearch(V element) {
        checkNull((V) element);
        for (int i = 0; i < size; ++i) {
            V currentElement = (V) elements[i];
            if (currentElement.equals(element)) {
                return i;
            }
        }
        return SaxSearchable.NOT_FOUND;
    }

    @Override
    public int binarySearch(Comparator<V> comparator, V element) {
        checkNull(element);
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int middlePosition = low + (high - low) / 2;
            int middleNumber = comparator.compare((V) elements[middlePosition], element);

            if (middleNumber == 0) {
                return middlePosition;
            } else if (middleNumber < 0) {
                low = middlePosition + 1;
            } else {
                high = middlePosition - 1;
            }
        }
        return -1;
    }

    public void insertionSort(Comparator<V> comparator) {
        for (int i = 1; i < size; i++) {
            V currentValue = get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(get(j), currentValue) > 0) {
                set(j + 1, get(j));
                j--;
            }

            set(j + 1, currentValue);
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public V next() {
                return (V) elements[index++];
            }
        };
    }
}
