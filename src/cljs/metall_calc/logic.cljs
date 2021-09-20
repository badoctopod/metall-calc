(ns metall-calc.logic
  (:require
   [metall-calc.config :refer [global-state]]))

(defn calculate-total
  [key]
  (reduce + (for [{:keys [weight volume qty]} (vals @global-state)]
              (condp = key
                :weight weight
                :volume volume
                :qty    qty))))

(defn generate-product!
  []
  (swap! global-state conj {(keyword (str (rand 1))) {:weight 0 :volume 0 :qty 0}}))

(defn delete-products!
  []
  (reset! global-state {}))

(defn update-total!
  [key weight volume qty]
  (swap! global-state update-in [key] assoc :weight weight :volume volume :qty qty))

(defn ->cm
  [dimensions]
  (zipmap (keys dimensions) (map #(/ % 1000) (vals dimensions))))

(defn weight
  [volume density]
  (* volume density))

(defn angle-volume
  [d qty]
  (-> (+ (:width d)
         (:wall-thickness d))
      (+ (:height d))
      (* (:wall-thickness d)
         (:length d)
         qty)))

(defn sheet-volume
  [d qty]
  (* (:width d)
     (:length d)
     (:sheet-thickness d)
     qty))

(defn profile-volume
  [d qty]
  (-> (+ (:width d)
         (:height d))
      (* (:length d)
         (:wall-thickness d)
         qty
         2)))

(defn pipe-volume
  [d qty]
  (-> (- (:diameter d)
         (:wall-thickness d))
      (* (:wall-thickness d)
         (:length d)
         qty
         3.14)))

(defn circle-volume
  [d qty]
  (-> (* (Math/pow (:diameter d) 2)
         3.14)
      (/ 4)
      (* (:length d)
         qty)))

(defn square-volume
  [d qty]
  (* (Math/pow (:width d) 2)
     (:length d)
     qty))

(defn channel-volume
  [d qty]
  (-> (+ (* 2 (:height d))
         (- (:width d)
            (* 2 (:wall-thickness d))))
      (* (:wall-thickness d)
         (:length d)
         qty)))

(defn ibeam-volume
  [d qty]
  (-> (- (:height d)
         (* 2 (:shelf-thickness d)))
      (* (:bulkhead-thickness d)
         (:length d))
      (+ (* (:width d)
            (:shelf-thickness d)
            (:length d)
            qty
            2))))

(defn tape-volume
  [d qty]
  (* (:width d)
     (:length d)
     (:tape-thickness d)
     qty))

(defn hex-volume
  [d qty]
  (-> (/ (:diameter d) 2)
      (Math/pow 2)
      (* (Math/sqrt 3)
         (:length d)
         qty
         2)))

(defmulti calculate :product-code)

(defmethod calculate
  :angle
  [{:keys [dimensions qty density key]}]
  (let [volume (angle-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :sheet
  [{:keys [dimensions qty density key]}]
  (let [volume (sheet-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :profile
  [{:keys [dimensions qty density key]}]
  (let [volume (profile-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :pipe
  [{:keys [dimensions qty density key]}]
  (let [volume (pipe-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :circle
  [{:keys [dimensions qty density key]}]
  (let [volume (circle-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :square
  [{:keys [dimensions qty density key]}]
  (let [volume (square-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :channel
  [{:keys [dimensions qty density key]}]
  (let [volume (channel-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :ibeam
  [{:keys [dimensions qty density key]}]
  (let [volume (ibeam-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :tape
  [{:keys [dimensions qty density key]}]
  (let [volume (tape-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))

(defmethod calculate
  :hex
  [{:keys [dimensions qty density key]}]
  (let [volume (hex-volume dimensions qty)
        weight (weight volume density)]
    (update-total! key weight volume qty)))
