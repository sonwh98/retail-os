(ns stigmergy.retail-os.main
  (:require [reagent.core :as r]))

(def state (r/atom {:line-items [{:item/name "Cookies"
                                  :item/price 100.0
                                  :item/quantity 10}]}))

(defn item-grid []
  (let [flexbox {:display :flex
                 :background-color :green
                 :flex-direction :row
                 :flex-wrap :wrap
                 :justify-content :flex-start}]
    [:div {:style flexbox}
     (for [i (range 126)]
       ^{:key i} [:button {:style {:flex-grow 1
                                   :width 150}} i])]))

(defn line-item-header []
  (let [header ["Qty" "Description" "Price" "SubTotal"]]
    (mapv (fn [h]
            [:div {:style {:border "1px solid"}} h])
          header)))

(defn line-item-grid [line-items]
  (let [grid-container [:div {:style {:display :grid
                                      :grid-template-columns "15% minmax(40%,auto) 25% 25%"
                                      :background-color "rgba(255, 255, 255, 0.8)"
                                      :border "1px solid rgba(0, 0, 0, 0.8)"
                                      :font-size 15
                                      :text-align :left}}]]
    (into grid-container (line-item-header))
    
    )

  )

(defn main-grid [state]
  [:div {:style {:display :grid
                 :grid-template-columns "auto minmax(300px,auto)"
                 :background-color "#2196F3"
                 :padding 10}}
   [item-grid]
   [line-item-grid]])

(defn init []
  (r/render-component [main-grid state] (js/document.getElementById "app"))
  )


