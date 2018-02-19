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
