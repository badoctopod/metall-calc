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
  [{:keys [width wall-thickness height length]} qty]
  (-> (+ width wall-thickness)
      (+ height)
      (* wall-thickness length qty)))

(defn sheet-volume
  [{:keys [width length sheet-thickness]} qty]
  (* width length sheet-thickness qty))

(defn profile-volume
  [{:keys [width height length wall-thickness]} qty]
  (-> (+ width height)
      (* length wall-thickness qty 2)))

(defn pipe-volume
  [{:keys [diameter wall-thickness length]} qty]
  (-> (- diameter wall-thickness)
      (* wall-thickness length qty 3.14)))

(defn circle-volume
  [{:keys [diameter length]} qty]
  (-> (* (Math/pow diameter 2) 3.14)
      (/ 4)
      (* length qty)))

(defn square-volume
  [{:keys [width length]} qty]
  (* (Math/pow width 2) length qty))

(defn channel-volume
  [{:keys [height width wall-thickness length]} qty]
  (-> (+ (* 2 height) (- width (* 2 wall-thickness)))
      (* wall-thickness length qty)))

(defn ibeam-volume
  [{:keys [height shelf-thickness bulkhead-thickness length width]} qty]
  (-> (- height (* 2 shelf-thickness))
      (* bulkhead-thickness length)
      (+ (* width shelf-thickness length qty 2))))

(defn tape-volume
  [{:keys [width length tape-thickness]} qty]
  (* width length tape-thickness qty))

(defn hex-volume
  [{:keys [diameter length]} qty]
  (-> (/ diameter 2)
      (Math/pow 2)
      (* (Math/sqrt 3) length qty 2)))

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
