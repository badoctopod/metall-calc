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
                              hex-volume]]
   [clojure.test :refer-macros [deftest is testing]]))

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
