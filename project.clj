(defproject calc/calc "0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev 
              {:dependencies [[midje "1.5.1"] ]
               :plugins [[lein-midje "3.0.1"]]
               :repl-options {:init  (do (use 'midje.repl) (autotest))}}}
  :aliases {"test" "midje"}
  :main calc.main)

