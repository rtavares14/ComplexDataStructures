# CDS Assignment

## Introduction

The Dutch railroad network is 3.2 thousand kilometers long. Of that network, 67 
percent is two- or multi-track and over 70 percent is electrified. Especially 
in the north and east of our country the railroads are not electrified. The 
network has about 400 train stations, which is on average one station for 
every 8.1  kilometers of track, see 
[CBS: How many railroads are there in the Netherlands?](https://www.cbs.nl/nl-nl/visualisaties/verkeer-en-vervoer/vervoermiddelen-en-infrastructuur/spoorwegen).

The cost of a trip by train depends on the number of units, see "the Fare 
Unit Map of the Netherlands" on the 
[NS Terms and Conditions site](https://www.ns.nl/voorwaarden.html)

## Requirements
It is up to you to build a proof of concept for a track manager application 
with the following tasks:
* Read in (at startup) all stations from [stations.csv](../resources/stations.csv) 
  and the corresponding connections from [tracks.csv](../resources/tracks.csv).
* Show information of a station based on its station code.
* Show information of a station based on (the beginning of) its name. Let 
  the user choose a station if more stations start with the searched part of 
  the name.
* Show all stations of a certain type, e.g. 'stopping train station', 
  'megastation' etc. and show an alphabetically sorted list of found station names.
* Determine the shortest route between two stations to be chosen by the user; 
  show the route and total length.
* Determine the minimum number of rail connections (and total length) needed 
  so that you can get from any station within the Netherlands to any other 
  station within the Netherlands (minimum cost spanning tree, MCST).
* Showing the rail network, the route found, and the MCST in a graphical 
  representation is grounds for extra points. Use the SaxionApp, JavaFX or 
  Swing for this purpose. An example map of the Netherlands that you may 
  use is in [resources](../resources/Nederland.png); examples for on this map 
  are:
  * HDR - Den Helder approximately at (600, 400),
  * MT - Maastricht approximately at (1024, 1840) and
  * DZ - Delfzijl approximately at (1490, 150).

Create a (console) application with a menu to invoke all functionalities.

## Implementatie
The application must include the following implementations:
* A list implementation based on the [SaxList]
  (src/nl/saxion/cds/collection/SaxList.java).
* An ArrayList implementation based on the [SaxList](../src/nl/saxion/cds/collection/SaxList.java),
  the [SaxSearchable](../src/nl/saxion/cds/collection/SaxSearchable.java) 
  and the [SaxSortable](../src/nl/saxion/cds/collection/SaxSortable.java) 
  interfaces, so with;
  * a binary search algorithm,
  * a linear search algorithm,
  * a simple sorting algorithm e.g. SelectionSort or InsertionSort and
  * a QuickSort algorithm.
* A hash map implementation based on [SaxMap](../src/nl/saxion/cds/collection/SaxMap.java).
* A Binary Search Tree (BST), preferably a AVL-tree, based on 
  [SaxBinaryTree](../src/nl/saxion/cds/collection/SaxBinaryTree.java).
* A queue based on the [SaxQueue](../src/nl/saxion/cds/collection/SaxQueue.java) 
* A stack based on the [SaxStack](../src/nl/saxion/cds/collection/SaxStack.java)
* Based on the [SaxHeap](../src/nl/saxion/cds/collection/SaxHeap.java)
  interface a min-heap implementation **based on an array**
* A (generic) Graph based on [SaxGraph](../src/nl/saxion/cds/collection/SaxGraph.java), 
  with 
  * a depth first search (DFS) or a breadth first search (BFS) Iterator 
    implementation,
  * the A*-algorithm,
  * the Dijkstra-algorithm and 
  * a MCST-algorithm (Prim or Kruskal).  
* **Java collection classes and interfaces ARE NOT ALLOWED**, so
  use nothing from java.util, *except Comparator, Iterator and possibly 
  Scanner*.

## Preconditions
* Create documentation in Markdown. use [this template](Documentation.md). 
  * For all algorithms and classes mentioned in section Implementation:
    * There is a link to the source code
    * For algorithms, include a Big-Oh qualification and a brief explanation 
      of why this is the correct classification.
      _You may look up this classification and explanation._
    * Describe in broad terms how it is implemented. Also describe which 
      tests prove that your algorithm works correctly and why.
  * There is a Technical Design chapter with
    * a class diagram with a brief description of the different classes and 
      main methods and
    * Per uitgewerkte requirement een korte beschrijving van de implementatie 
      en van de gebruikte (eigen) implementaties van de SaxCollection 
    klasse(n); _voor iedere klasse die er wordt gebruikt een onderbouwde keuze 
    waarom er juist voor die klasse is gekozen en dus moet er ook altijd 
    tenminste 1 alternatief in de onderbouwing zijn meegenomen_. 
    **Neem bij een algoritme altijd de Big-Oh classificatie hierin mee** 
    (deze mag opgezocht worden).  
* Er moeten voor tenminste 75% (>90% geeft extra punten) dekkende geslaagde 
  testcases zijn uitgewerkt met JUnit 5.
  * **Met dekkend wordt bedoeld het minimum dekkingspercentage van class, 
    method, line en branch coverage.**
  * Voor velden met een restrictie moeten de good-weather en bad-weather 
    tests voorhanden zijn.
  * Getters en setters mogen worden uitgesloten van testdekking, mits deze 
    alleen bestaan uit het retourneren van de bijbehorende variabele 
    respectievelijk het wijzigen van de bijbehorende variabele. Markeer deze 
    methodes dan al zodanig (zie de sheets), zodat ze niet meetellen in de 
    dekkingspercentages.
  * Wanneer een Exception mogelijk is, moet die situatie getest worden; 
    test minimaal het juiste exceptie type.
  * **Zorg ervoor dat de voorbeelden die in het lesmateriaal staan in ieder 
    geval worden meegenomen in de testen**.

## Inlevering
Een juiste inlevering bevat:
* De code van het ontwikkelde programma; maak een zip export vanuit GitLab, 
  test deze export (werkt het allemaal nog?) en zorg dat er geen build 
  artefacts (out map) of de .git map in zit en er geen absolute pad-namen in
  de code zitten.
* De hiervoor beschreven [documentatie als Markdown bestand](Documentation.md). 

**Een incomplete inlevering (ontbreken van code en/of documentatie en/of 
code verwijzingen) wordt NIET beoordeeld.**
