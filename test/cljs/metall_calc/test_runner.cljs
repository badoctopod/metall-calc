(ns metall-calc.test-runner
  (:require
   [metall-calc.logic-test]
   [figwheel.main.testing :refer-macros [run-tests-async]]))

(defn -main
  [& args]
  (run-tests-async 5000))
