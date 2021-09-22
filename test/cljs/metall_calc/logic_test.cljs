(ns metall-calc.logic-test
  (:require
   [metall-calc.logic :refer [weight
                              angle-volume
                              sheet-volume
                              profile-volume
                              pipe-volume
                              circle-volume
                              square-volume
                              channel-volume
                              ibeam-volume
                              tape-volume
                              hex-volume
                              calculate-total
                              generate-product!
                              delete-products!
                              update-total!
                              ->cm]]
   [clojure.test :refer-macros [deftest is testing]]
   [metall-calc.config :refer [global-state]]))

(defn insert-sample-products!
  []
  (swap! global-state conj {:id1 {:product-code "angle"
                                  :qty 50
                                  :weight 100
                                  :volume 30}
                            :id2 {:product-code "sheet"
                                  :qty 20
                                  :weight 50
                                  :volume 3}
                            :id3 {:product-code "sheet"
                                  :qty 20
                                  :weight 50
                                  :volume 17}}))

(deftest calculate-total-test
  (testing "Calculate total"
    (delete-products!)
    (insert-sample-products!)
    (is (= 90 (calculate-total :qty)))
    (is (= 200 (calculate-total :weight)))
    (is (= 50 (calculate-total :volume)))))

(deftest generate-product-test
  (testing "Generate product"
    (generate-product!)
    (is (not-empty @global-state))))

(deftest delete-products-test
  (testing "Delete products"
    (delete-products!)
    (is (empty? @global-state))))

(deftest update-total-test
  (testing "Update total"
    (delete-products!)
    (generate-product!)
    (let [key (first (keys @global-state))]
      (update-total! key 100 30 5)
      (is (= {:weight 100 :volume 30 :qty 5} (key @global-state))))))

(deftest ->cm-test
  (testing "Convert coll vals to cm"
    (is (= (->cm {:wall-thickness 5000
                  :width 1250
                  :height 5500})
           {:wall-thickness 5
            :width 1.25
            :height 5.5}))))

(deftest weight-test
  (testing "WEIGHT calculated properly"
    (is (= 4 (weight 2 2)))))

(deftest volume-test
  (testing "ANGLE volume calculated properly"
    (is (= 0.075000000000000005 (angle-volume {:width 0.05
                                               :wall-thickness 0.05
                                               :height 0.05
                                               :length 10}
                                              1))))
  (testing "SHEET volume calculated properly"
    (is (= 0.025 (sheet-volume {:width 0.05
                                :sheet-thickness 0.05
                                :length 10}
                               1))))
  (testing "PROFILE volume calculated properly"
    (is (= 0.1 (profile-volume {:width 0.05
                                :height 0.05
                                :wall-thickness 0.05
                                :length 10}
                               1))))
  (testing "PIPE volume calculated properly"
    (is (= 0.01256 (pipe-volume {:diameter 0.05
                                 :wall-thickness 0.01
                                 :length 10}
                                1))))
  (testing "CIRCLE volume calculated properly"
    (is (= 0.019625000000000004 (circle-volume {:diameter 0.05
                                                :length 10}
                                               1))))
  (testing "SQUARE volume calculated properly"
    (is (= 0.025000000000000005 (square-volume {:width 0.05
                                                :length 10}
                                               1))))
  (testing "CHANNEL volume calculated properly"
    (is (= 0.025000000000000005 (channel-volume {:width 0.05
                                                 :height 0.05
                                                 :wall-thickness 0.05
                                                 :length 10}
                                                1))))
  (testing "IBEAM volume calculated properly"
    (is (= 0.025000000000000005 (ibeam-volume {:height 0.05
                                               :width 0.05
                                               :shelf-thickness 0.05
                                               :bulkhead-thickness 0.05
                                               :length 10}
                                              1))))
  (testing "TAPE volume calculated properly"
    (is (= 0.025 (tape-volume {:width 0.05
                               :tape-thickness 0.05
                               :length 10}
                              1))))
  (testing "HEX volume calculated properly"
    (is (= 0.02165063509461097 (hex-volume {:diameter 0.05
                                            :length 10}
                                           1)))))
