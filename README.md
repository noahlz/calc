calc
====

A toy CLI calculator in Clojure.

Requirements
------------

- Clojure 1.5.x
- [Leiningen 2](http://leiningen.org/)

Example Usage
-------------

    lein run

    Calculator 1.0
    Please enter an arithmetic expression separated by spaces.
    i.e. 1 + 2 / 3 * 4
    Type bye, quit, exit or Ctrl-D to exit.
    Enter an expression:
    1 + 2
    =>  3
    Enter an expression:
    2 * 3 + 4
    =>  10
    Enter an expression:
    10 + 2 * 100 / ((40 - 37) * 100 * (2 - 4 + 8 * 16))
    =>  1891/189
    Enter an expression:
    Bye!

You can also run from the command line as follows:

    $ lein uberjar
    $ java -cp target/calc-0.1-SNAPSHOT-standalone.jar calc.main


Motivation and Goals
====================

The core functionality of the calc app itself is not that interesting. My primary goal in this project is to server as a platform for learning more about the Clojure projects - development cycle, test frameworks, trying new tools, etc. - without getting too bogged down in the details of the actual application. 

Acknowledgements
================

I used Eric Robert's _Programming Abstractions in C_ (Addison Wesley, 1997) as a reference in coding my implementation. Chapter 14 "Expression Trees" describes an almost identical problem.

License
=======

Creative Commons CC0 1.0 Universal 

See: http://creativecommons.org/publicdomain/zero/1.0/legalcode

Noah Zucker (nzucker@gmail.com / [@noahlz](http://twitter.com/noahlz))

