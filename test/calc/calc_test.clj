(ns calc.calc-test
  (:use [clojure.test]
        [midje.sweet]
        [calc.calc]))

;; Couldn't get this to work - got "exception while translating
;; this form" from Midje
;; (defmacro calc-facts [docstring fct]
;;   `(facts docstring
;;      (tabular
;;        ~fct)))

(defmacro calc-facts-about [docstring & inputs]
  `(facts ~docstring
     (tabular
       (fact (calc ?input) => ?result)
         ?input ?result 
         ~@inputs)))

(defmacro calc-errors-for [docstring & inputs]
  `(facts ~docstring
     (tabular
       (fact (calc ?input) => string?)
         ?input 
         ~@inputs)))

(defmacro calc-shouldnot [docstring & inputs]
  `(facts ~docstring
     (tabular
       (fact (calc ?input) =not=> ?shouldntbe)
         ?input ?shouldntbe
         ~@inputs)))

;; TODO: could probably use test.generative!

(calc-facts-about "simple arithmetic" 
  "1 + 1"          2
  "1 - 2"         -1
  "1 + -2"        -1
  "-1 - 1"        -2
  "2 * 3"          6
  "4 / 2"          2
  "1 / 2"        1/2
  "-2 * 2"        -4
  "1 + 2 + 3"      6
  "2 * -3 / 12" -1/2)

(calc-facts-about "nested parens"
  "(1 + 2)"                            3
  "(1 + 2) - (3 * 4)"                 -9
  "4 - (3 + 1)"                        0
  "((2 + 3) * (3 - 1))"               10
  "(1 + 2) * (10 - 4) / 9 * 6)"       12
  "((1 + 2) * ((10 - 4)) / (9 * 6))" 1/3 
  "1 + 2 + 3 + 4 - (5 * 6)"          -20)

(calc-facts-about "operator precedence (answers checked via google.com)"
  "1 + 2 * 3"                     7
  "(1 + 2) * 3"                   9
  "2 * 3 + 4"                    10
  "1 - 2 / 3"                   1/3
  "2 * 3 + 10 / 5"                8
  "4 * 5 + 5 - 2 * 5 / 2"        20
  "4 * 5 + (5 - 2) * 5 / 2"      55/2

  "10 + 2 * 100 / 40 - 37 * 100 * (2 - 4 + 8 * 16)"      -466185
  "10 + 2 * 100 / ((40 - 37) * 100 * (2 - 4 + 8 * 16))" 1891/189)

(calc-errors-for "arithetic errors"
  "1 / 0"
  "1 / (1 - 1)"
  "(1 / (0 - 0))")     

(calc-shouldnot "support the following expressions values"
  "1 str 2"      "12" 
  "[2 + 8]"       10   
  "1 + [2 + 8]"   11   
  "([2 + 8])"     10   
  "(list 4 + 6)"  10   
  "7 = 7"         true 
  "7 = 3 + 4"     true 
   "6 = 3 + 4"    false
  "1 + 2 = 3"     true)

(calc-errors-for "user input"
  "1"
  "+" 
  "+ 1"
  "1 +"
  "1 1"
  "+ +" 
  "+ 1 1"
  "+ 1 +"
  "1 1 +"
  "+ + 1"
  "1 + +"
  "1 1 1"
  "+ 1 + 1"
  "1 + + 1" 
  "1 + 2 + +"
  "()"
  ;; "1 + 2)" ;; read-string ignores the trailing parens!
              ;; TODO: implement mini-REPL with read and StringInputStream 
  ") 1 + 2 ("
  "(1 + 2")

