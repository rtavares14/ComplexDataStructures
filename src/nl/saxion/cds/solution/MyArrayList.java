package nl.saxion.cds.solution;

import nl.saxion.cds.collection.*;

import java.util.Comparator;
import java.util.Iterator;

public class MyArrayList<V> implements SaxList<V>, SaxSearchable<V>, SaxSortable<V> {
    // Minimal size of the internal array
    private static final int MINIMUM_SIZE = 32;
    // Extending means doubling in size, until the size is bigger than this maximum extension size
    private static final int MAXIMUM_EXTENSION = 256;

    // Java prohibits creating an array with a generic type, so we use Object
    private Object[] elements;
    // Number of elements in use
    private int size;

    public MyArrayList() {
        this(MINIMUM_SIZE);
    }

    public MyArrayList(int capacity) {
        this.size = 0;
        elements = new Object[capacity];
    }

    @Override
    // Do no type checking; a Java hack, because we store objects of a generic type V in an Object array
    @SuppressWarnings("unchecked")
    public boolean contains(V value) {
        for (int i = 0; i < size; ++i) {
            V element = (V) elements[i];
            if (element.equals(value)) return true;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (V) elements[index];
    }

    @Override
    public void addLast(V value) {
        checkAndExtendSize(size);  // Ignore invalid index => IndexOutOfBoundsException
        elements[size - 1] = value;
    }

    @Override
    public void addFirst(V value) {
        checkAndExtendSize(0); // Ignore invalid index => IndexOutOfBoundsException
        elements[0] = value;
    }

    @Override
    public void addAt(int index, V value) throws IndexOutOfBoundsException {
        checkAndExtendSize(index); // Ignore invalid index => IndexOutOfBoundsException
        elements[index] = value;
    }


    @Override
    public void set(int index, V value) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        elements[index] = value;
    }

    @Override
    // Do no type checking; a Java hack, because we store objects of a generic type V in an Object array
    @SuppressWarnings("unchecked")
    public V removeLast() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        V value = (V) elements[--size];
        elements[size] = null; // this element no longer contains valid info
        return value;
    }

    @Override
    // Do no type checking; a Java hack, because we store objects of a generic type V in an Object array
    @SuppressWarnings("unchecked")
    public V removeFirst() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException();
        V value = (V) elements[0];
        removeAt(0);
        return value;
    }

    @Override
    // Do no type checking; a Java hack, because we store objects of a generic type V in an Object array
    @SuppressWarnings("unchecked")
    public V removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException(Integer.toString(index));
        V value = (V) elements[index];
        if (index < --size) {
            // shift all element one to the left (removing the element to delete)
            System.arraycopy(elements, index + 1, elements, index, size - index);
        }
        elements[size] = null; // this element no longer contains valid info
        return value;
    }

    @Override
    public void remove(V value) throws ValueNotFoundException {
        int index = 0;
        for (var anElement : this) {
            if (anElement == null) {
                if (value == null) break; // found null value
            } else {
                if (anElement.equals(value)) break; // found value
            }
            ++index;
        }
        if (index == size)
            throw new ValueNotFoundException(value == null ? "null" : value.toString());
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
    // Do no type checking; a Java hack, because we store objects of a generic type V in an Object array
    @SuppressWarnings("unchecked")
    public String graphViz(String name) {
        var builder = new StringBuilder();
        builder.append("digraph ");
        builder.append(name);
        builder.append(" {\n");
        for (int i = 0; i < size - 1; ++i) {
            V from = (V) elements[i];
            V to = (V) elements[i + 1];
            builder.append(String.format("\"%s\" -> \"%s\"\n", (from == null ? "NULL_" + i : from.toString()), (to == null ? "NULL_" + (i + 1) : to.toString())));
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * Check if the array of elements can hold another element and if not extend the array.
     * Make room on position index and adjust size.
     *
     * @param index position where to make room for a new element, valid 0..size (size == add at end)
     * @throws IndexOutOfBoundsException index < 0 or > size
     */
    private void checkAndExtendSize(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException(Integer.toString(index));
        if (elements.length < size + 1) {
            // extend array by doubling in size if size is smaller ten maximum extension
            int capacity = elements.length < MAXIMUM_EXTENSION ? elements.length * 2 : elements.length + MAXIMUM_EXTENSION;
            var newElements = new Object[capacity];
            // copy existing elements
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
        if (index < size) {
            // Make room for the new element
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

            // Handle null values based on how you want to treat them
            if (currentElement == null && nextElement != null) {
                return false; // If current is null and next is not, they are out of order
            } else if (currentElement != null && nextElement == null) {
                continue; // If current is not null and next is null, nulls are treated as larger
            } else if (currentElement != null && nextElement != null) {
                // Use comparator for non-null values
                if (comparator.compare(currentElement, nextElement) > 0) {
                    return false; // Found an out-of-order pair
                }
            }
        }
        return true; // All elements are in order
    }

    /**
     * Do a selection sort (in place) on the elements in ascending order.
     */
    @Override
    public void simpleSort(Comparator<V> comparator) {
        for (int index = 0; index < size; ++index) {
            // search for smallest element in the sequence between smallest and seqLength
            int smallest = index;
            for (int index2 = index; index2 < size; ++index2) {
                if (comparator.compare(get(smallest), get(index2)) > 0) {
                    smallest = index2;
                }
            }
            // swap smallest element with element at smallest
            swap(index, smallest);
        }
    }

    /**
     * Swap the elements on the given position.
     *
     * @param index1 first element index
     * @param index2 second element index
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
     * Recursively do a quick sort (in place) on the elements in ascending order.
     */
    @Override
    public void quickSort(Comparator<V> comparator) {
        quickSort(comparator, 0, size - 1);
    }

    /**
     * Recursively do a quick sort (in place) on the elements from begin until (including) end in ascending order.
     *
     * @param comparator method to compare two V objects
     * @param begin      start of range
     * @param end        end of range
     */

    private void quickSort(Comparator<V> comparator, int begin, int end) {
        if (end - begin >= 1) {
            int pivot = splitInPlace(comparator, begin, end);
            quickSort(comparator, begin, pivot - 1);
            quickSort(comparator, pivot + 1, end);
        }
    }

    /**
     * Split the range of elements into 2 parts; on the left the elements which are smaller
     * than the pivot, on the right te elements which are bigger then the pivot.
     * Start with the first element is the pivot value and return the index of the pivot afterward.
     *
     * @param comparator method to compare two objects
     * @param begin      left index
     * @param end        right index
     * @return the current index of the pivot
     */
    private int splitInPlace(Comparator<V> comparator, int begin, int end) {
        V pivotValue = get(end);  // Using the last element as the pivot
        int i = begin - 1; // Index of the smaller element

        for (int j = begin; j < end; j++) {
            if (comparator.compare(get(j), pivotValue) <= 0) {
                i++;
                swap(i, j);  // Move smaller element to the left of the pivot
            }
        }

        swap(i + 1, end);  // Place the pivot element in its correct position
        return i + 1;       // Return the pivot index
    }

    @Override
    public int linearSearch(Object element) {
        for (int i = 0; i < size; ++i) {
            if (element == null) {
                if (elements[i] == null) {
                    return i; // Found the null element
                }
            } else {
                if (element.equals(elements[i])) {
                    return i; // Found the matching element
                }
            }
        }
        return SaxSearchable.NOT_FOUND; // Element not found
    }

    @Override
    public int binarySearch(Comparator<V> comparator, V element) {
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int middlePosition = low + (high - low) / 2;  // To avoid potential overflow
            int middleNumber = comparator.compare((V) elements[middlePosition], element);

            if (middleNumber == 0) {
                return middlePosition;  // Element found
            } else if (middleNumber < 0) {
                low = middlePosition + 1;  // Search the right half
            } else {
                high = middlePosition - 1;  // Search the left half
            }
        }
        return -1;  // Element not found
    }

    public void insertionSort(Comparator<V> comparator) {
        // Start at the second element since the first is trivially sorted
        for (int i = 1; i < size; i++) {
            V currentValue = get(i);
            int j = i - 1;

            // Shift elements of the sorted part of the array to the right to make room
            // for the currentValue
            while (j >= 0 && comparator.compare(get(j), currentValue) > 0) {
                set(j + 1, get(j));  // Shift the element to the right
                j--;
            }

            // Insert currentValue in its correct position
            set(j + 1, currentValue);
        }
    }


    @Override
    public Iterator<V> iterator() {
        return new Iterator<>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            // Do no type checking; a Java hack, because we store objects of a generic type V in an Object array
            public V next() {
                return (V) elements[currentIndex++];
            }

            // Remove is not necessary; just for demonstration purposes / as a challenge
            @Override
            public void remove() {
                MyArrayList.this.removeAt(currentIndex - 1);
            }
        };
    }
}
