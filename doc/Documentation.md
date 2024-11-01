# Algorithms and classes
>In this file I will approach my algorithms and data structures that ware developed for this  project. 
In total, I developed 8 algorithms being A* my favorite and nine data structures being the AVL tree and the Hashmap my favorites.

## My ArrayList
**Classification**: `O(N)` because it has to iterate through the list to find the target value.

### Overview
Since [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) implementation was the simplest among the data structures, as it is based on a straightforward list structure. While much of the core functionality was given, several methods required additional implementation, such as Linear Search, Binary Search, and QuickSort.

The [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) is **sortable** and **searchable** being both **sortable** and **searchable**, [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) works well for general data operations. Plus, it’s a **generic** structure, so it’s flexible for storing any type of data.

- **Tests**: I tested [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) in [My ArrayList Test](../test/data_structures/lists/MyArrayListTest.java) to make sure everything worked as expected.

<br>

#### My binary search algorithm
**Classification**: `O(log N)` because it divides the search range in half repeatedly.

**Implementation**:
The binary search algorithm was inspired by the teacher’s pseudocode sheets but implemented in my own way to fit the structure of my list. This method divides the search range in half repeatedly, narrowing down to find the target value efficiently in sorted data.

<br>

#### My linear search algorithm
**Classification**: `O(N)` because it has to iterate through the list to find the target value.

**Implementation**:
Inspired by the teacher’s examples, I developed my linear search to work effectively within my data structures. This method checks each element one by one, making it straightforward and useful for unsorted lists.

<br>

#### My QuickSort algorithm
**Classification**: `O(N log N)` because it divides the list into smaller sections and sorts them recursively.

**Implementation**:
For QuickSort, I took inspiration from the provided materials but adjusted it to work with my specific doubly linked list structure. This algorithm uses a pivot to split the list and then recursively sorts each section. It’s optimized for my setup and works well within the structure.

---

## My DoublyLinkedList
**Classification**: `O(N)` because it has to iterate through the list to find the target value.

### Overview
[My DoublyLinkedList](../src/nl/saxion/cds/data_structures/list/MyDoublyLinkedList.java) was another simple structure, so I built this one as well on my own. It’s a generic doubly linked list with nodes defined in my [DoublyLinkedList Node](../src/nl/saxion/cds/data_structures/list/MyDLLNode.java) class.

### Implementation Details
I added some extra methods to make it more versatile:
- **Remove All**: Deletes every node that has a specific value.
- **Remove Last Occurrence**: Deletes only the last node with a given value, which is useful when there are duplicates, but only the most recent one needs to be removed.

These additional methods make [My DoublyLinkedList](../src/nl/saxion/cds/data_structures/list/MyDoublyLinkedList.java) better for cases where I need more control over which nodes are deleted.

- **Tests**: I tested [My DoublyLinkedList](../src/nl/saxion/cds/data_structures/list/MyDoublyLinkedList.java) in [My DoublyLinkedList Test](../test/data_structures/lists/MyDoublyLinkedListTest.java) to make sure everything worked as expected.

---

## My HashMap
**Classification**: `O(1)` because it uses a hash function to find the bucket index quickly.

### Overview
Building [My HashMap](../src/nl/saxion/cds/data_structures/map/MyHashMap.java) was one of my favorite parts of this project! I watched a few YouTube videos to better understand hashmaps and how they work, also I say the class sheets. Two good videos that were especially helpful were:

- [Video 1](https://www.youtube.com/watch?v=H62Jfv1DJlU)
- [Video 2](https://youtu.be/1Ovg3IC-p5A?si=Ik4QdllbVCM6GH-p)

### Why I Liked It
What I love about hashmaps is how fast they can be for looking up values. The key-value pair system makes it possible to access data quickly by using a unique key.

### Implementation Details
In my [My HashMap](../src/nl/saxion/cds/data_structures/map/MyHashMap.java), each bucket uses a **doubly linked list** to handle collisions. Here are a few features:
- **Efficient Lookup**: The hash function calculates the bucket index, so we can look up values quickly.
- **Dynamic Resizing**: If we reach a certain load factor, it resizes to keep operations fast.
- **Exception Handling**: It throws custom exceptions for duplicate keys and missing keys.

Overall, this hashmap combines speed with an easy-to-understand key-value concept, making it a useful and enjoyable structure to implement.

- **Tests**: I tested [My HashMap](../src/nl/saxion/cds/data_structures/map/MyHashMap.java) in [My HashMap Test](../test/data_structures/hashmap/MyHashMapTest.java) to make sure everything worked as expected.

---

## My BS Tree
**Classification**: `O(log N)` because it divides the data at each level to find the target value.

### Overview
[My BS Tree](../src/nl/saxion/cds/data_structures/trees/MyBinarySearchTree.java) is a tree structure that keeps data sorted and allows quick searching. I followed videos and class notes for this, and it mostly worked as expected. The structure helps in quickly finding, adding, or removing items because the tree divides the data at each level.

- [Video 1](https://www.youtube.com/watch?v=Gt2yBZAhsGM)
- [Video 2](https://www.youtube.com/watch?v=mtvbVLK5xDQ)

#### Implementation Details
- **Remove Method**: This includes a remove method to delete items, which I had to make sure didn’t break the tree’s organization I had to remake it a few times to make it work properly and had to debug it with help of Chat GPT.
- **In-Order Traversal**: I added an in-order traversal method to display the tree’s contents in order, which was a fun challenge to implement.

Overall, [My BS Tree](../src/nl/saxion/cds/data_structures/trees/MyBinarySearchTree.java) was a fun challenge to implement, and it’s a useful structure for keeping data sorted and quickly accessible.

- **Tests**: I tested [My BS Tree](../src/nl/saxion/cds/data_structures/trees/MyBinarySearchTree.java) in [My BS Tree Test](../test/data_structures/trees/MyBinarySearchTreeTest.java) to make sure everything worked as expected.

---

## My AVL Tree

**Classification**: `O(log N)` because it balances itself to keep operations fast.

### Overview
[My AVL Tree](../src/nl/saxion/cds/data_structures/trees/MyAVLTree.java) is a balanced binary search tree, meaning it automatically keeps itself organized to ensure fast operations. I found balancing the tree challenging, especially getting the rotations right, which took me about 8 or 9  tries. I used online videos, class sheets, and tips from the teacher to figure it out, but eventually, it all worked.

- [Video 1](https://www.youtube.com/watch?v=jDM6_TnYIqE&t=1960s)
- [Video 2](https://www.youtube.com/watch?v=zP2xbKerIds)
- [Video 3](https://www.youtube.com/watch?v=vRwi_UcZGjU)

#### Implementation Details
- **Balancing**: I added balancing methods to keep the tree in check, which was a fun challenge to get right. 

Overall, [My AVL Tree](../src/nl/saxion/cds/data_structures/trees/MyAVLTree.java) was a challenging, and it’s a useful tool for keeping data organized and quickly accessible.

- **Tests**: I tested [My AVL Tree](../src/nl/saxion/cds/data_structures/trees/MyAVLTree.java) in [My AVL Tree Test](../test/data_structures/trees/MyAVLTreeTest.java) to make sure everything worked as expected.

---

## My MinHeap
**Classification**: `O(log N)` because it keeps the smallest item on top, which requires some shifting.

### Overview
[My MinHeap](../src/nl/saxion/cds/data_structures/heap/MyMinHeap.java) is a data structure that lets me store elements in a way where I can quickly access the smallest (or largest) item, it was made my me with the help od videos and the class sheets. This makes it great for prioritizing tasks or managing data that needs to stay sorted. I made it flexible so that it can act as either a min-heap (smallest item on top) or a max-heap (largest item on top), based on a setting I choose when creating it.

- [Video 1](https://www.youtube.com/watch?v=HqPJF2L5h9U&t)
- [Video 2](https://www.youtube.com/watch?v=WCm3TqScBM8)

#### Implementation Details
- **Keeping Things in Order**: I added helper methods called bubbleUp and bubbleDown. After adding an item, bubbleUp moves it to the right spot if it's out of order. Similarly, when removing the top item, bubbleDown helps shift things around to fill the empty space and keep everything in the right order.
- **Flexible Min or Max Heap:**: To allow both min-heap and max-heap setups, I added a compare method that checks if we’re looking for a smaller or larger value, based on the isMinHeap setting. This way, the code can decide whether to make a min-heap or max-heap without needing separate methods.

Overall, [My MinHeap](../src/nl/saxion/cds/data_structures/heap/MyMinHeap.java) was a fun project that helped me practice sorting data with a heap structure.

- **Tests**: I tested [My MinHeap](../src/nl/saxion/cds/data_structures/heap/MyMinHeap.java) in [My MinHeap Test](../test/data_structures/heap/MyMinHeapTest.java) to make sure everything worked as expected.

---

## My Stack
**Classification**: `O(1)` because it only changes the last element in the list.

### Overview
[My Stack](../src/nl/saxion/cds/data_structures/stack/MyStack.java) is a simple data structure that follows the Last-In-First-Out (LIFO) principle, meaning the most recently added item is the first one removed. This structure is especially useful where you need to track a sequence of operations in reverse order. It was made with the help of the class sheets.

#### Implementation Details
- **Push and Pop**: The push method adds an item to the end of the stack, while pop removes the last item added. Both are quick since they only change the last element in the list.
- **Peek**: The peek method lets us look at the top item without taking it off the stack. This is handy for checking the next item to be removed without changing anything.

Overall, [My Stack](../src/nl/saxion/cds/data_structures/stack/MyStack.java) is a useful LIFO structure that leverages the efficiency of a doubly linked list for stack operations.

- **Tests**: I tested [My Stack](../src/nl/saxion/cds/data_structures/stack/MyStack.java) in [My Stack Test](../test/data_structures/stack/MyStackTest.java) to make sure everything worked as expected.

---

## My Queue
**Classification**: `O(1)` because it only changes the first and last elements in the list.

### Overview
[My Queue](../src/nl/saxion/cds/data_structures/queue/MyQueue.java) is a First-In-First-Out (FIFO) data structure that’s useful for handling items in the order they were added. I implemented it using a doubly linked list for fast access to both ends. I made it with the help of the class sheets.

#### Implementation Details
- **Enqueue and Dequeue**: The enqueue method adds items to the front of the queue, while dequeue removes items from the back. This setup maintains the FIFO order and is quick because it only modifies the ends of the list.

Overall, [My Queue](../src/nl/saxion/cds/data_structures/queue/MyQueue.java) is a useful FIFO structure that leverages the efficiency of a doubly linked list for queue operations.

- **Tests**: I tested [My Queue](../src/nl/saxion/cds/data_structures/queue/MyQueue.java) in [My Queue Test](../test/data_structures/queue/MyQueueTest.java) to make sure everything worked as expected.

---

## My Graph
**Classification**: `O(N)` because it has to explore all nodes and edges to find the target value.

### Overview

[My Graph](../src/nl/saxion/cds/data_structures/graph/MyGraph.java)  represents nodes and edges, where relationships between elements can be explored and visualized. I really enjoyed creating this class and used it for my visual representation project. I used the class sheets and some online sites to help me understand how to implement it.

- [Site 1](https://www.geeksforgeeks.org/implementing-generic-graph-in-java/)
- [Site 2](https://www.baeldung.com/java-graphs)

#### Implementation Details
- **Add Node and Edge**: You can add nodes and edges easily, including directed and bidirectional edges with weights.
- **Breadth-First Search**: This method explores all neighboring nodes step by step, which is helpful for finding the shortest path in simple graphs or navigating networks.
- **Dijkstra’s Algorithm**: This is a way to find the shortest path in a weighted graph, ensuring the path with the lowest cost is found.
- **A*‎ Algorithm**: This is another pathfinding method that uses estimates to help find routes faster, which is great for more complicated graphs.
- **Minimum Spanning Tree**: This connects all nodes in a graph with the least total weight, useful for optimizing connections.

Overall [My Graph](../src/nl/saxion/cds/data_structures/graph/MyGraph.java) combines these features to handle different graph tasks effectively.

- **Tests**: I tested [My Graph](../src/nl/saxion/cds/data_structures/graph/MyGraph.java) in [My Graph Test](../test/data_structures/graph/MyGraphTest.java) to make sure everything worked as expected.

---

### My iterative depth first search algorithms
Classification: O(N)

Implementation:

### My iterative breadth first search algorithm
Classification: O(N)

Implementation:

### My Dijkstra algorithm
Classification: O(N log N)

Implementation:

### My A* algorithm
Classification: O(N log N)

Implementation:

### My MCST algorithm
Classification: O(N log N)

Implementation:

# Technical design My Application
I chose to do a simple menu in my console that will provide the user 6 options.
My user has the opportunity to look by a station code using MyHashMap class by looking for the station code as the key.
The user can also 

## Class diagram and reading the data
In here after a lof of thinking I decided to approach a method to read my data in this way. Since I used my Stations in several data structures (MyDoubllyLinkList, MyHashMao and MyBinnarrySerachtTree) I decided to create one single method that will read the file only once and give all my stations in the 3 different data structures.
For my tracks data I simply used a method that will read the csv and give me all my station in MyArrayList structure since I only needed that.

# Station search by station code
This option allows users to find a station using its code. I used [My HashMap](../src/nl/saxion/cds/data_structures/map/MyHashMap.java) for quick retrieval.

- **Implementation**: In the `showStationInfoByName` method, the user inputs a station code, and the method retrieves the corresponding [Station](../src/nl/saxion/cds/model/Station.java) object from `stationMap`.
- **Time Complexity**: **O(1)** on average, due to the efficient lookup of the hash map.

- I chose to use a hashmap because it is the most efficient way to search for a station by its code. Since the station code is unique, the hashmap provides a fast way to retrieve the station object.
- I also could have used a binary search tree, but it would have been less efficient for this specific task. The hashmap is the best choice for this scenario.

# Station search based on the beginning of the name
This option lets users search for stations by the beginning of their names. I implemented a linear search using [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java).

- **Implementation**: The `showStationInfoByPartialName` method prompts for a name prefix and iterates through the `stationsList` to find matches, which are collected in `matchingStations`.
- **Time Complexity**: **O(N)**, where **N** is the number of stations, as it checks each station one by one.

- I chose a linear search because the list is unsorted, and a linear search is the simplest way to find matches. It’s not the most efficient, but it works well for this task.
- I could have used a binary search if the list was sorted, but since it’s not, a linear search is the best choice.

- I shose 
- I could have also used a hashmap, but it would have required a more complex implementation. The linear search is straightforward and effective for this scenario.

## Implementation shortest route


## Implementation minimum cost spanning tree

## Implementation graphic representation(s)