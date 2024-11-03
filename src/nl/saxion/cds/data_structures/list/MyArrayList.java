package nl.saxion.cds.data_structures.list;

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

    /**
     * Constructor that initializes the list with a given capacity.
     *
     * @param capacity the initial capacity of the list
     */
    public MyArrayList(int capacity) {
        this.size = 0;
        elements = new Object[capacity];
    }

    /**
     * Checks if the list is sorted according to the natural order.
     *
     * @return true if the list is sorted, false otherwise
     */
    private void checkNull(V value) {
        if (value == null) {
            throw new IllegalArgumentException("Null values are not allowed in this list.");
        }
    }

    /**
     * Returns the index of the first occurrence of the specified element in the list.
     *
     * @param value the value to search for
     * @return the index of the element, or -1 if the element is not found
     */
    @Override
    public boolean contains(V value) {
        checkNull(value);
        for (int i = 0; i < size; ++i) {
            V element = (V) elements[i];
            if (element.equals(value)) return true;
        }
        return false;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public V get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (V) elements[index];
    }

    /**
     * Adds the value to the end of the list.
     *
     * @param value the value to add
     */
    @Override
    public void addLast(V value) {
        checkNull(value);
        checkAndExtendSize(size);
        elements[size - 1] = value;
    }

    /**
     * Adds the value to the front of the list.
     *
     * @param value the value to add
     */
    @Override
    public void addFirst(V value) {
        checkNull(value);
        checkAndExtendSize(0);
        elements[0] = value;
    }

    /**
     * Adds the value at the specified index in the list.
     *
     * @param index the index to add the value at
     * @param value the value to add
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public void addAt(int index, V value) throws IndexOutOfBoundsException {
        checkNull(value);
        checkAndExtendSize(index);
        elements[index] = value;
    }

    /**
     * Sets the element at the specified index to the given value.
     *
     * @param index the index of the element to set
     * @param value the value to set
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @Override
    public void set(int index, V value) throws IndexOutOfBoundsException {
        checkNull(value);
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        elements[index] = value;
    }

    /**
     * Removes the last element from the list.
     *
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public V removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        V value = (V) elements[--size];
        elements[size] = null;
        return value;
    }

    /**
     * Removes the first element from the list.
     *
     * @return the removed element
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public V removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        V value = (V) elements[0];
        removeAt(0);
        return value;
    }

    /**
     * Removes the element at the specified index from the list.
     *
     * @param index the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
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

    /**
     * Removes the first occurrence of the specified element from the list.
     *
     * @param value the value to remove
     * @throws ValueNotFoundException if the value is not found in the list
     */
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

    /**
     * Returns the first element in the list.
     *
     * @return the first element in the list
     * @throws EmptyCollectionException if the list is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a string representation of the list in GraphViz format.
     *
     * @param name the name of the graph
     * @return a string representation of the list in GraphViz format
     */
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

    /**
     * Checks if the list is sorted according to the natural order.
     *
     * @return true if the list is sorted, false otherwise
     */
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

    /**
     * Returns a string representation of the list.
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.size(); i++) {
            sb.append(this.get(i));
            if (i < this.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Checks if the list is sorted according to the given comparator.
     *
     * @param comparator the comparator to use for sorting
     * @return true if the list is sorted, false otherwise
     */
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

    /**
     * Sorts the list using the bubble sort algorithm.
     *
     * @param comparator the comparator to use for sorting
     */
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

    /**
     * Swaps two elements in the list.
     *
     * @param index1 the index of the first element
     * @param index2 the index of the second element
     */
    protected void swap(int index1, int index2) {
        assert index1 >= 0 && index1 < size;
        assert index2 >= 0 && index2 < size;

        if (index1 != index2) {
            var temp = elements[index1];
            elements[index1] = elements[index2];
            elements[index2] = temp;
        }
    }

    /**
     * Sorts the list using the bubble sort algorithm.
     *
     * @param comparator the comparator to use for sorting
     */
    @Override
    public void quickSort(Comparator<V> comparator) {
        quickSort(comparator, 0, size - 1);
    }

    /**
     * Sorts the list using the quick sort algorithm.
     *
     * @param comparator the comparator to use for sorting
     * @param begin      the beginning index of the list
     * @param end        the ending index of the list
     */
    private void quickSort(Comparator<V> comparator, int begin, int end) {
        if (end - begin >= 1) {
            int pivot = splitInPlace(comparator, begin, end);
            quickSort(comparator, begin, pivot - 1);
            quickSort(comparator, pivot + 1, end);
        }
    }

    /**
     * Splits the list in place using the last element as the pivot.
     *
     * @param comparator the comparator to use for sorting
     * @param begin      the beginning index of the list
     * @param end        the ending index of the list
     * @return the index of the pivot element
     */
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

    /**
     * Searches for an element in the list using a linear search algorithm.
     *
     * @param element the element to search for
     * @return the index of the element, or -1 if the element is not found
     */
    @Override
    public int linearSearch(V element) {
        checkNull( element);
        for (int i = 0; i < size; ++i) {
            V currentElement = (V) elements[i];
            if (currentElement.equals(element)) {
                return i;
            }
        }
        return SaxSearchable.NOT_FOUND;
    }

    /**
     * Searches for an element in the list using a binary search algorithm.
     *
     * @param comparator the comparator to use for sorting
     * @param element    the element to search for
     * @return the index of the element, or -1 if the element is not found
     */
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

    /**
     * Sorts the list using the insertion sort algorithm.
     *
     * @param comparator the comparator to use for sorting
     */
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

    /**
     * Returns an iterator over the elements in this list.
     *
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int index = 0;

            /**
             * Returns true if the iteration has more elements.
             *
             * @return true if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return index < size;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             */
            @Override
            public V next() {
                return (V) elements[index++];
            }
        };
    }
}
