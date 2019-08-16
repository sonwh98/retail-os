(ns stigmergy.retail-os.main
  (:require [reagent.core :as r]))

(def state (r/atom {:txt "hello"}))

(defn grid [state]
  (let [grid {:display :grid
              :grid-template-columns "auto minmax(200px,auto)"
              :background-color "#2196F3"
              :padding 10}
        grid-item {:background-color "rgba(255, 255, 255, 0.8)"
                   :border "1px solid rgba(0, 0, 0, 0.8)"
                   :padding 5
                   :font-size 30
                   :text-align :center}
        flexbox {:display :flex
                 :background-color :green
                 :flex-direction :row
                 :flex-wrap :wrap
                 :justify-content :flex-start}]
    [:div {:style grid}
     [:div {:style flexbox}
      (for [i (range 126)]
        ^{:key i} [:button {:style {:flex-grow 1
                                    :width 150}} i])]
     [:div {:style grid-item}
      [:button 2]]]))

(defn init []
  (r/render-component [grid state] (js/document.getElementById "app"))
  )


