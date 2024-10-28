# Algorithms and classes
>In this file I will approach my algorithms and data structures that ware developed for this  project. 
In total, I developed 8 algorithms being A* my favorite and nine data structures being the AVL tree and the Hashmap my favorites.

## My ArrayList
Classification: O(N)

Implementation: the implementation from the MyArrayList was the easiest of all. It was the simplest data Structure, and it was already given to us expected some method that we needed to implement but that I will talk in the  added algorithms.<br>

In here MyArrayList is sortable and searchable making it perfect for...<br>

Also since is generic can be usefull for :

I tested it in my test folder and the class is MyArrayListTest

## My DoublyLinkedList
Classification: O(N)

Implementation: this was the data structure from the project it is based on a generic DoublyLinkedList, but with some touches from me. <br>

In here the DLL is composed of nodes from my DLLNode class.<br>

I personally tried to improve my list by adding some mew methods like (Remove All and Remove Last Occurrence). <br>

Both of them I found it usefully when I wanted to delete all the nodes with the same value,
Or remove the last occurrence I found it usefully when for example that are several nodes with same value it will delete the last one being added.

### My binary search algorithm
Classification: O(log N)

Implementation:

### My linear search algorithm
Classification: O(N)

Implementation:

### My QuickSort algorithm
Classification: O(N log N)

Implementation:

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
This was the first option that I did and the one who took me less time i decided to do it my looking in MyHashMap any entry with the key being the station code.
In here it took me a time complexity of O...

# Station search based on the beginning of the name
In here I used a simple custom linear search approach. What I do is I get my station in MyArrayList and then I go...
In here it took me a time complexity of O...

## Implementation shortest route

## Implementation minimum cost spanning tree

## Implementation graphic representation(s)