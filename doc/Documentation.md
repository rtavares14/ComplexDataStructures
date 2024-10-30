# Algorithms and classes
>In this file I will approach my algorithms and data structures that ware developed for this  project. 
In total, I developed 8 algorithms being A* my favorite and nine data structures being the AVL tree and the Hashmap my favorites.

## My ArrayList
**Classification**: `O(N)`

### Overview
Since [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) implementation was the simplest among the data structures, as it is based on a straightforward list structure. While much of the core functionality was given, several methods required additional implementation, such as Linear Search, Binary Search, and QuickSort.

The [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) is **sortable** and **searchable** eing both **sortable** and **searchable**, `MyArrayList` works well for general data operations. Plus, it’s a **generic** structure, so it’s flexible for storing any type of data.

- **Tests**: I tested [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java) in [My ArrayList Test](../test/data_structures/lists/MyArrayListTest.java) to make sure everything worked as expected.

<br>

#### My binary search algorithm
**Classification**: `O(log N)`

**Implementation**:
The binary search algorithm was inspired by the teacher’s pseudocode sheets but implemented in my own way to fit the structure of my list. This method divides the search range in half repeatedly, narrowing down to find the target value efficiently in sorted data.

<br>

#### My linear search algorithm
**Classification**: `O(N)`

**Implementation**:
Inspired by the teacher’s examples, I developed my linear search to work effectively within my data structures. This method checks each element one by one, making it straightforward and useful for unsorted lists.

<br>

#### My QuickSort algorithm
**Classification**: `O(N log N)` (average case)

**Implementation**:
For QuickSort, I took inspiration from the provided materials but adjusted it to work with my specific doubly linked list structure. This algorithm uses a pivot to split the list and then recursively sorts each section. It’s optimized for my setup and works well within the structure.

---

## My DoublyLinkedList
**Classification**: `O(N)`

### Overview
[My DoublyLinkedList](../src/nl/saxion/cds/data_structures/list/MyDoublyLinkedList.java) was another simple structure, so I built this one as well on my own. It’s a generic doubly linked list with nodes defined in my [DoublyLinkedList Node](../src/nl/saxion/cds/data_structures/list/MyDLLNode.java) class.

I added some extra methods to make it more versatile:
- **Remove All**: Deletes every node that has a specific value.
- **Remove Last Occurrence**: Deletes only the last node with a given value, which is useful when there are duplicates, but only the most recent one needs to be removed.

These additional methods make [My DoublyLinkedList](../src/nl/saxion/cds/data_structures/list/MyDoublyLinkedList.java) better for cases where I need more control over which nodes are deleted.

- **Tests**: I tested [My DoublyLinkedList](../src/nl/saxion/cds/data_structures/list/MyDoublyLinkedList.java) in [My DoublyLinkedList Test](../test/data_structures/lists/MyDoublyLinkedListTest.java) to make sure everything worked as expected.

---

## My HashMap
Classification O(1)

Implementation:

## My BS Tree
Classification O(log N)

Implementation:

## My AVL Tree


## My MinHeap
Classification: O(log N)

Implementation:

## My Stack
Classification: O(1)

Implementation:

## My Queue
Classification: O(1)

Implementation:

## My Graph
Classification: O(N)

Implementation:

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

# Station search based on the beginning of the name
This option lets users search for stations by the beginning of their names. I implemented a linear search using [My ArrayList](../src/nl/saxion/cds/data_structures/list/MyArrayList.java).

- **Implementation**: The `showStationInfoByPartialName` method prompts for a name prefix and iterates through the `stationsList` to find matches, which are collected in `matchingStations`.
- **Time Complexity**: **O(N)**, where **N** is the number of stations, as it checks each station one by one.

## Implementation shortest route

## Implementation minimum cost spanning tree

## Implementation graphic representation(s)