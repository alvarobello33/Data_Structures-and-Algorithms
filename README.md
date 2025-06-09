# 🛡️ Java Data Structures & Algotithms Project — Els Cavallers de la Taula de Hash 🛡️

**Group 9**  
**Nil Bagaria Nofre ✨  
Guillem Alba Payà ✨  
Isabel Rodríguez ✨  
Álvaro Bello ✨  
Development Date**: 28/05/2023 📅


## Project Overview 🚀
This project was developed as part of the **Advanced Programming and Data Structures** course at **La Salle - Universitat Ramon Llull** during the 2022–2023 academic year.

It explores the implementation of various non-linear data structures in Java, including **graphs, binary search trees (BSTs), R-trees, and hash tables**, along with associated algorithms for traversal, search, insertion, deletion, and optimization.


## Key Features 🌟

- Understand and implement custom non-linear data structures in Java.
- Apply classical algorithms (e.g., DFS, Prim, Dijkstra, AVL balancing).
- Evaluate performance and complexity with different datasets.
- Develop good coding and debugging practices through testing and analysis.



## Project Structure 📂

```
els-cavallers-taula-hash/
├── src/ 
│  ├── datasets/         # Test datasets (XXS to XXL)
│  │
│  ├── graphs/      # Graph classes and algorithms
│  ├── btree/       # Binary search tree (BTree, Persona)
│  ├── rtree/       # R-Tree and geometric figures
│  ├── taules/      # Hash table with Acusat records
│  ├── Controller.java
│  ├── FileReader.java
│  ├── Main.java
│  └── Menu.java
│
├── paed-2223-s2-g9.iml           # Star UML diagram of clases of the project
├── memoria_paed_s2_G9_cat.pdf    # Detailed documentation in Catalan
├── memory_paed_s2_G9_eng.pdf     # Detailed documentation in English
└── README.md
```

## Project Phases & Data Structures 📌

### 🔹 Phase 1: “Sobre orenetes i cocos” (Graphs)
- **Structures:** Adjacency list–based directed graph
- **Key classes:** `LlocInteres`, `Trajecte`, `Graph`, `Fase1`, `Dijkstra`
- **Algorithms:**
  - Depth-First Search (DFS)
  - Prim’s Algorithm (MST)
  - Dijkstra (with constraints like max distance for African/European birds)

### 🔹 Phase 2: “Caça de bruixes” (Binary Search Trees)
- **Structures:** AVL-like Binary Search Tree
- **Key classes:** `Persona`, `BTree`
- **Algorithms:**
  - Insert with auto-balancing
  - Delete with rotations
  - In-order traversal
  - Search (by exact and approximate weight)

### 🔹 Phase 3: “Tanques de bardissa” (R-Trees)
- **Structures:** 2D R-Tree for spatial data
- **Key classes:** `RTree`, `Rectangle`, `Bardissa`, `Figure`, `Punt`
- **Algorithms:**
  - Insert (recursive with nearest-node selection)
  - Delete (by coordinates)
  - Search by area
  - Aesthetic optimization (k-nearest search)

### 🔹 Phase 4: “D’heretges i blasfems” (Hash Tables)
- **Structures:** Custom chained hash table
- **Key classes:** `Acusat`, `HashTable`
- **Algorithms:**
  - Custom hash function
  - Insert, get, delete operations
  - Mark as heretic
  - Judgment filtering
  - Histogram display


## Testing & Evaluation 🧪

- Multiple datasets used (`XXS` to `XXL`) for benchmarking performance.
- Execution time measured with Java’s `System.nanoTime()`.
- Identified performance bottlenecks (e.g., `StackOverflowError` in large trees).
- Custom datasets were also created for debugging and validation.


## Known Issues 🚧

- Stack overflow with unbalanced or deep trees on large datasets.
- Visual tree rendering was complex and required custom formatting.
- Minor bugs in Bardissa deletion and area search on edge cases.
- Limited comments in parts of the code affect readability.


## Development Environment 🛠️
- **Language:** Java
- **IDE:** IntelliJ IDEA
- **Version Control:** Bitbucket (Git)
