(ns sandbox.calc-test
  (:use [clojure.test]
        [sandbox.calc]))

(deftest tests 
  ;; TODO: could probably use test.generative!
  (testing "simple cases"
    (are [_1 _2] (= _2 (calc _1))
      "1 + 1"     2
      "1 - 2"    -1
      "1 + -2"   -1
      "-1 - 1"   -2
      "2 * 3"     6
      "4 / 2"     2
      "1 / 2"   1/2
      "-2 * 2"   -4
      "1 + 2 + 3"      6
      "2 * -3 / 12" -1/2))

  (testing "nested parens"
    (are [_1 _2] (= _2 (calc _1))
      "(1 + 2)"            3
      "(1 + 2) - (3 * 4)" -9
      "4 - (3 + 1)"        0
      "((2 + 3) * (3 - 1))"         10
      "(1 + 2) * (10 - 4) / 9 * 6)" 12
      "((1 + 2) * ((10 - 4)) / (9 * 6))" 1/3 
      "1 + 2 + 3 + 4 - (5 * 6)" -20))

  (testing "operator precedence (answers checked via google.com)"
    (are [_1 _2] (= _2 (calc _1))
      "1 + 2 * 3"         7
      "(1 + 2) * 3"       9
      "2 * 3 + 4"         10
      "1 - 2 / 3"        1/3
      "2 * 3 + 10 / 5"               8
      "4 * 5 + 5 - 2 * 5 / 2"       20
      "4 * 5 + (5 - 2) * 5 / 2"     55/2
      "10 + 2 * 100 / 40 - 37 * 100 * (2 - 4 + 8 * 16)" -466185
      "10 + 2 * 100 / ((40 - 37) * 100 * (2 - 4 + 8 * 16))" 1891/189))

  (testing "arithetic error cases"
    (are [_1] (string? (calc _1))
      "1 / 0"
      "1 / (1 - 1)"
      "(1 / (0 - 0))"))     

  (testing "misc error cases"
    (is (not= "12" (calc "1 str 2")))
    (is (not= 10   (calc "[2 + 8]")))
    (is (not= 11   (calc "1 + [2 + 8]")))
    (is (not= 10   (calc "([2 + 8])")))
    (is (not= 10   (calc "(list 4 + 6)")))
    (is (not= true (calc "7 = 7")))
    (is (not= true (calc "7 = 3 + 4")))
    (is (not= false (calc "6 = 3 + 4")))
    (is (not= true (calc "1 + 2 = 3"))))

  (testing "input error cases"
    (are [_1] (string? (calc _1))
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
      "(1 + 2")))

