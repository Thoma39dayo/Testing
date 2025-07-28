# PERFORMANCE_RESULTS.md

## Table of Contents
- [Introduction](#introduction)
- [Method Complexity Analysis](#method-complexity-analysis)
  - [addVertex](#addvertex)
  - [addEdge](#addedge)
  - [hasVertex](#hasvertex)
  - [hasEdge (by edge)](#hasedge-by-edge)
  - [hasEdge (by vertices)](#hasedge-by-vertices)
  - [getEdge](#getedge)
  - [getEdgeLength](#getedgelength)
  - [remove (vertex)](#remove-vertex)
  - [removeEdge](#removeedge)
  - [getTotalEdgeLength](#gettotaledgelength)
  - [getVertices](#getvertices)
  - [getEdges](#getedges)
  - [getEdges (vertex)](#getedges-vertex)
  - [getNeighbors](#getneighbors)
- [Empirical Results](#empirical-results)
- [Performance Plots](#performance-plots)
- [Discussion](#discussion)

## Introduction
This document analyzes the runtime complexity and empirical performance of all Task 1 and Task 2 methods for both ALGraph (Adjacency List) and AMGraph (Adjacency Matrix) implementations.

## Method Complexity Analysis

### addVertex
- **ALGraph:** O(1) — HashSet and HashMap insertions are O(1) on average.
- **AMGraph:** O(1) amortized — ArrayList/HashMap insertions are O(1), but resizing the matrix is O(V^2) (happens rarely).

### addEdge
- **ALGraph:** O(1) — HashSet insertions and adjacency list updates are O(1).
- **AMGraph:** O(1) — Direct matrix assignment and HashSet insertion are O(1).

### hasVertex
- **ALGraph:** O(1) — HashSet lookup.
- **AMGraph:** O(1) — HashMap lookup.

### hasEdge (by edge)
- **ALGraph:** O(1) — HashSet lookup.
- **AMGraph:** O(1) — HashSet lookup.

### hasEdge (by vertices)
- **ALGraph:** O(deg(v1)) — Must scan adjacency list for v1 (deg(v1) is degree of v1).
- **AMGraph:** O(1) — Matrix lookup.

### getEdge
- **ALGraph:** O(deg(v1)) — Must scan adjacency list for v1.
- **AMGraph:** O(E) — Must scan all edges to find the matching one (since only length is stored in matrix).

### getEdgeLength
- **ALGraph:** O(deg(v1)) — Uses getEdge.
- **AMGraph:** O(1) — Matrix lookup.

### remove (vertex)
- **ALGraph:** O(deg(v) + V) — Remove all incident edges (deg(v)), then remove from all structures.
- **AMGraph:** O(E + V) — Remove all incident edges (scan all edges), update matrix, reindex vertices.

### removeEdge
- **ALGraph:** O(1) — HashSet removals and adjacency list updates are O(1).
- **AMGraph:** O(1) — Matrix and HashSet removals are O(1).

### getTotalEdgeLength
- **ALGraph:** O(E) — Must sum all edge lengths.
- **AMGraph:** O(E) — Must sum all edge lengths.

### getVertices
- **ALGraph:** O(V) — Copy HashSet.
- **AMGraph:** O(V) — Copy ArrayList.

### getEdges
- **ALGraph:** O(E) — Copy HashSet.
- **AMGraph:** O(E) — Copy HashSet.

### getEdges (vertex)
- **ALGraph:** O(deg(v)) — Copy adjacency list for v.
- **AMGraph:** O(E) — Scan all edges for those incident to v.

### getNeighbors
- **ALGraph:** O(deg(v)) — Scan adjacency list for v.
- **AMGraph:** O(V * deg(v)) — Scan matrix row and then scan edges for each neighbor.

## Empirical Results

*Empirical timing results for each method will be added here after benchmarking.*

## Performance Plots

*Performance plots (e.g., time vs. graph size) will be included here.*

## Discussion

*Discussion of how empirical results compare to theoretical expectations will be included here.* 