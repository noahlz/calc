(ns calc.calc)

(def instructions
  (str "Please enter an arithmetic expression separated by spaces.\n"
       "i.e. 1 + 2 / 3 * 4"))

(defn- error
  ([]    (error instructions))
  ([msg] (str "ERROR: " (if (nil? msg) 
                         instructions 
                         msg))))

(def ^{:private true} operators {'* 1
                                 '/ 1
                                 '+ 0
                                 '- 0})
(def ^{:private true} operator? (set (keys operators)))

(defn- higher-precedence? [leftop rightop]
  (< (operators leftop) (operators rightop)))

(declare parse-expr)

(defn- peel
  "Remove all outer lists until you reach
   a list that contains more than one value." 
  [expr]
  (if (and (list? expr) (= 1 (count expr)))
    (recur (first expr))
    expr))

(defn- read-value [e]
  (if (list? e)
    (parse-expr (peel e))
    (if (number? e) e)))

(defn- valid-expr? [op right]
  (and (operator? op) 
       (or (number? right) (list? right))))

(defn- higher-precedence-concat  [left op right]
  (let [right-value (read-value right)
        last-left-value (last left)
        other-left-values (drop-last left)]
    (concat other-left-values `((~op ~last-left-value ~right-value)))))

(defn- parse-expr [s]
  (let [left             (read-value (first s))
        exprs            (partition 2 (rest s))
        [[op right] & _] exprs]
    (if (and left (valid-expr? op left))
      (let [right (read-value right)]
        (reduce (fn [left [op right]]
                  (if (valid-expr? op right)
                    (if (higher-precedence? (first left) op)
                      (higher-precedence-concat left op right) 
                      (list op left (read-value right)))
                    (reduced nil)))
          (list op left right) (rest exprs))))))

(defn calc [input]
  (try 
    (let [expr (-> (str "(" input ")") 
                   read-string ;; TODO: use tools.reader?
                   peel)]
      (if (list? expr)  
        (if-let [result (eval (parse-expr expr))]
          result
          (error))
        (error)))
  (catch java.lang.RuntimeException ex
    (error (.getMessage ex)))))
    