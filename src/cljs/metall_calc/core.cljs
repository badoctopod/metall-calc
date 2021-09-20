(ns ^:figwheel-hooks metall-calc.core
  (:require
   [metall-calc.config :refer [global-state brands products metals]]
   [metall-calc.logic :refer [generate-product!
                              delete-products!
                              update-total!
                              calculate
                              calculate-total
                              ->cm]]
   [reagent.core :as r]
   [reagent.dom :as rdom]))

(defn form-filled?
  [state]
  (let [product-code (:product-code @state)
        dimensions (:dimensions @state)]
    (and product-code (not-empty dimensions))))

(defn obfuscator?
  [state]
  (if (:obfuscator @state)
    true
    false))

(defn untoggle-all!
  [state]
  (swap! state assoc
         :obfuscator       false
         :metal-dropdown   false
         :brand-dropdown   false
         :product-dropdown false))

(defn toggle-dropdown!
  [dropdown state]
  (if (obfuscator? state)
    (swap! state assoc :obfuscator false dropdown false)
    (swap! state assoc :obfuscator true  dropdown true)))

(defn product
  [key]
  (let [state (r/atom {:metal-dropdown   false
                       :brand-dropdown   false
                       :product-dropdown false
                       :obfuscator       false
                       :metal-code       ""
                       :product-code     ""
                       :metal-name       "Выберите металл..."
                       :brand-name       "Выберите марку..."
                       :product-name     "Выберите изделие..."
                       :density          0
                       :weight           0
                       :dimensions       {}})]
    (fn []
      [:div {:class "product"}
       [:i {:class "fa fa-times"
            :title "Убрать из расчета"
            :on-click #(swap! global-state dissoc key)}]
       (when (obfuscator? state)
         [:div {:class "obfuscator"
                :on-click #(untoggle-all! state)}])
       [:div {:class "props"}
        [:div {:class "select-dropdown"}
         [:h4 {:class "label"} "Металл"]
         [:div {:class "select"
                :on-click #(toggle-dropdown! :metal-dropdown state)}
          [:span (:metal-name @state)]
          [:i {:class "fa fa-angle-down"}]]
         (when (:metal-dropdown @state)
           [:ul {:class "dropdown"}
            (doall
             (for [{:keys [id code name]} metals]
               ^{:key id}
               [:li {:class "item"
                     :data-metal-code code
                     :on-click #(do
                                  (toggle-dropdown! :metal-dropdown state)
                                  (swap! state assoc :brand-name "Выберите марку..."
                                         :density 0
                                         :metal-name
                                         (-> % .-target .-innerText)
                                         :metal-code
                                         (-> % .-target (.getAttribute "data-metal-code"))))}
                (str name)]))])]
        [:div {:class "select-dropdown"}
         [:h4 {:class "label"} "Марка металла"]
         [:div {:class "select"
                :on-click #(toggle-dropdown! :brand-dropdown state)}
          [:span (:brand-name @state)]
          [:i {:class "fa fa-angle-down"}]]
         (when (:brand-dropdown @state)
           [:ul {:class "dropdown"}
            (doall
             (for [{:keys [id brand density]}
                   (filter #(= (:code %) (:metal-code @state)) brands)]
               ^{:key id}
               [:li {:class "item"
                     :data-density density
                     :on-click #(do
                                  (toggle-dropdown! :brand-dropdown state)
                                  (swap! state assoc :brand-name
                                         (-> % .-target .-innerText)
                                         :density
                                         (-> % .-target (.getAttribute "data-density")))
                                  (when (form-filled? state)
                                    (calculate {:product-code (keyword (:product-code @state))
                                                :dimensions (->cm (dissoc (:dimensions @state) :qty))
                                                :qty (get-in @state [:dimensions :qty])
                                                :density (:density @state)
                                                :key key})))}
                (str brand)]))])]
        [:div {:class "select-dropdown"}
         [:h4 {:class "label"} "Изделие"]
         [:div {:class "select"
                :on-click #(toggle-dropdown! :product-dropdown state)}
          [:span (:product-name @state)]
          [:i {:class "fa fa-angle-down"}]]
         (when (:product-dropdown @state)
           [:ul {:class "dropdown"}
            (doall
             (for [{:keys [id code name]} products]
               ^{:key id}
               [:li {:class "item"
                     :data-product-code code
                     :on-click #(do
                                  (toggle-dropdown! :product-dropdown state)
                                  (update-total! key 0 0 0)
                                  (swap! state assoc :dimensions {}
                                         :product-name
                                         (-> % .-target .-innerText)
                                         :product-code
                                         (-> % .-target (.getAttribute "data-product-code"))))}
                (str name)]))])]
        (when-not (empty? (:product-code @state))
          [:div {:class "input-group"}
           [:h4 {:class "label"} "Измерения"]
           (doall
            (for [{:keys [id code name]}
                  (:dimension (into {} (filter #(= (:code %)
                                                   (:product-code @state))
                                               products)))]
              ^{:key id}
              [:div
               [:h5 {:class "label"} name]
               [:input {:lang "ru"
                        :placeholder "Введите измерение..."
                        :type "number"
                        :min "0"
                        :value ((keyword code) (:dimensions @state))
                        :on-change #(do
                                      (swap! state update-in [:dimensions]
                                             assoc (keyword code)
                                             (js/parseFloat
                                              (-> % .-target .-value)))
                                      (when (form-filled? state)
                                        (calculate {:product-code (keyword (:product-code @state))
                                                    :dimensions (->cm (dissoc (:dimensions @state) :qty))
                                                    :qty (get-in @state [:dimensions :qty])
                                                    :density (:density @state)
                                                    :key key})))}]]))])]
       [:h4 {:class "total"}
        (.toFixed (:weight (key @global-state)) "2") " кг"]])))

(defn app
    []
    [:div {:class "layout"}
     [:h1 "Калькулятор металлопроката"]
     [:h4.total
      "Всего изделий " [:span.red (calculate-total :qty)] " шт, "
      "массой " [:span.red (.toFixed (calculate-total :weight) "2")] " кг "
      "и объемом " [:span.red (.toFixed (calculate-total :volume) "2")] " куб.м"]
     [:div.button-group
      [:button {:on-click #(generate-product!)} "Добавить изделие"]
      [:button {:on-click #(delete-products!)} "Удалить все"]]
     (when-not (empty? @global-state)
       [:div {:class "products"}
        (for [[key _] @global-state]
          ^{:key key}
          [product key])])])

(defn mount-app
  []
  (rdom/render [#'app] (.getElementById js/document "app")))

(defn ^:export init
  []
  (mount-app))

(defn ^:after-load on-reload
  []
  (mount-app))
