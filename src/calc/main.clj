(ns calc.main
  (:require [clojure.string :as s :only [trim]]
            [calc.calc :as calc])
  (:gen-class))

(def ^{:private true} exit-tokens #{"exit" "quit" "bye"})
(def ^{:private true} running (atom true))

(defn- should-exit-on [input]
  (if (or (nil? input) ;; handle Ctrl-D
          (exit-tokens (s/trim input))) 
    (do (println "Bye!") 
        (swap! running (fn [& args] false)))
    input))

(defn- display-instructions []
  (println "Calculator 1.0")
  (println calc/instructions)
  (println "Type" (apply str (interpose ", "  exit-tokens))
           "or Ctrl-D to exit."))

(defn -main [& args]
  (display-instructions)
  (while @running 
    (println "Enter an expression:")
    (if-let [input (should-exit-on (read-line))]
      (println "=> " (calc/calc input)))))

