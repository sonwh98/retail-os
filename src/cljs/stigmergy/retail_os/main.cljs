(ns stigmergy.retail-os.main
  (:require [reagent.core :as r]))

(def state (r/atom {:line-items [{:item/name "Cookies"
                                  :item/price 1.75
                                  :item/quantity 10}
                                 {:item/name "Pizza"
                                  :item/price 20.50
                                  :item/quantity 3}
                                 ]}))

(defn item-grid []
  (let [flexbox {:display :flex
                 ;;:background-color :green
                 :flex-direction :row
                 :flex-wrap :wrap
                 :justify-content :flex-start}]
    [:div {:style flexbox}
     (for [i (range 50)]
       ^{:key i} [:button {:style {:flex-grow 1
                                   :padding 10
                                   :margin 5
                                   :min-width 100}} i])]))

(defn line-item-grid [line-items]
  (let [container [:div {:style {:display :grid
                                 :grid-template-columns "15% minmax(40%,auto) 25% 25%"
                                 :grid-auto-rows 15
                                 :background-color "rgba(255, 255, 255, 0.8)"
                                 :font-size 15
                                 :text-align :left}}]
        style {:style {:border-bottom "1px solid"
                       :border-left "1px solid"
                       :height 15}}
        header (let [header ["Qty" "Description" "Price" "SubTotal"]]
                 (map (fn [h]
                        [:div style h])
                      header))
        lines (map (fn [{:keys [:item/quantity :item/name :item/price]}]
                     [[:div style quantity]
                      [:div style name]
                      [:div style price]
                      [:div style (* quantity price)]])
                   @line-items)
        lines (reduce (fn [acc line]
                        (into acc line))
                      lines)
        container (into container header)
        container (into  container  lines)
        ]
    container
    ))

(defn main-grid [state]
  (let [items (r/cursor state [:items])
        line-items (r/cursor state [:line-items])]
    (fn []
      [:div {:style {:display :grid
                     :grid-template-columns "auto minmax(300px,auto)"
                     :background-color "#2196F3"
                     }}
       [item-grid items]
       (line-item-grid line-items)])))

(defn init []
  (r/render-component [main-grid state] (js/document.getElementById "app"))
  )


(comment
  [[:div {:style {:border "1px solid"}} 10]
   [:div {:style {:border "1px solid"}} "Cookies"]
   [:div {:style {:border "1px solid"}} 100]
   [:div {:style {:border "1px solid"}} 10]
   [:div {:style {:border "1px solid"}} "Pizza"]
   [:div {:style {:border "1px solid"}} 100]
   [:div {:style {:border "1px solid"}} "Qty"]
   [:div {:style {:border "1px solid"}} "Description"]
   [:div {:style {:border "1px solid"}} "Price"]
   [:div {:style {:border "1px solid"}} "SubTotal"]]

  ([:div {:style {:border "1px solid", :height 15}} 1000]
   [:div {:style {:border "1px solid", :height 15}} 100]
   [:div {:style {:border "1px solid", :height 15}} "Pizza"]
   [:div {:style {:border "1px solid", :height 15}} 10]
   [:div {:style {:border "1px solid", :height 15}} 1000]
   [:div {:style {:border "1px solid", :height 15}} 100]
   [:div {:style {:border "1px solid", :height 15}} "Cookies"]
   [:div {:style {:border "1px solid", :height 15}} 10]
   [:div {:style {:border "1px solid", :height 15}} "Qty"]
   [:div {:style {:border "1px solid", :height 15}} "Description"]
   [:div {:style {:border "1px solid", :height 15}} "Price"]
   [:div {:style {:border "1px solid", :height 15}} "SubTotal"])
  )
