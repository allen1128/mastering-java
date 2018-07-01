# Effective Java

enum
------------

* prefer it over constant int: more readable, safer (singleton)
* can be associated with data and constant specific method implementation
* can use strategy pattern to avoid boilerplate code

generics
------------
* don't use raw types because you might run into runtime errors (lose type safety of generics).
* favor generic type (class/interface) and generic methods.
* use bounded wildcards to increase API flexbility (PECS: producer extends, consumer super).
* use of a generic type should not need to think about wildcard types.


stream & lambda
-------------
* prefer lambda to anonymous classes
* prefer method reference to lambda
* Stream API : stream (finite or infinite sequence of data elements + stream pipeline which is multistage computations on the elements)
* Stream pipeline: source stream + (0 or more) intermediate operations + 1 terminal operations
* intermdiate operations: filter, map, flatMap, limit, sorted, distinct, skip, generate, iterate, etc.
* terminal operation: forEach, count, collect, reduce, anyMatch, noneMatch, allMAtch, findAny, findFirst, etc.
* by default stream is run sequencially and with parallel method, stream can be executed in parallel (difficult to get it right though)
* prefer collections as return type of stream

