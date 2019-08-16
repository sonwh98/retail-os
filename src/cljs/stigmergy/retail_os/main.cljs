(ns stigmergy.retail-os.main
  (:require [reagent.core :as r]))

(def state (r/atom {:txt "hello"}))

(defn grid [state]
  (let [grid-container {:display :grid
                        :grid-template-columns "auto minmax(200px,auto)"
                        :background-color "#2196F3"
                        :padding 10}
        grid-item {:background-color "rgba(255, 255, 255, 0.8)"
                   :border "1px solid rgba(0, 0, 0, 0.8)"
                   :padding 5
                   :font-size 30
                   :text-align :center}]
    [:div {:style grid-container}
     [:div {:style grid-item}
      [:div 
       (for [i (range 1000)]
         ^{:key i} [:button i])]
      ]
     [:div {:style grid-item}
      [:button.button 2]]]))

(defn init []
  (r/render-component [grid state] (js/document.getElementById "app"))
  )


