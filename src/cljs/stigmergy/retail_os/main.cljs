(ns stigmergy.retail-os.main
  (:require [reagent.core :as r]
            [stigmergy.tily :as util]))

(def state (r/atom {:items [{:item/name "Cheese Burger"
                             :item/description "Sandwich with beef and cheese"
                             :item/price 5.00
                             :item/image "https://cdn4.iconfinder.com/data/icons/food-and-drinks-filled-color/300/14215737Untitled-3-512.png"}

                            {:item/name "Club Sandwich"
                             :item/description "Sandwich"
                             :item/price 5.00
                             :item/image "https://cdn4.iconfinder.com/data/icons/food-and-drinks-filled-color/300/14215737Untitled-3-512.png"}
                            
                            ]
                    :line-items [{:item/name "Cookies"
                                  :item/price 1.75
                                  :item/quantity 10}
                                 {:item/name "Pizza"
                                  :item/price 20.50
                                  :item/quantity 3}
                                 ]}))

(defn add-line-item [i]
  (swap! state update-in [:line-items] 
         (fn [line-items]
           (let [index-line-items (util/with-index line-items)
                 [[index line-item]] (filter (fn [[index line-item]]
                                               (= (:item/name line-item)
                                                  i))
                                             index-line-items)]
             (if index
               (let [line-item (update-in line-item [:item/quantity] inc)]
                 (-> line-items
                     (util/drop-nth index)
                     (util/insert-at index line-item)))
               (conj line-items {:item/name i
                                 :item/price 5.00
                                 :item/quantity 1}))))))

(defn item-grid [items]
  (let [flexbox {:style {:display :flex
                         :flex-direction :row
                         :flex-wrap :wrap
                         :justify-content :flex-start}}
        style {:flex-grow 1
               :padding 10
               :margin 5
               :background-color :red
               :min-width 50
               :max-width 50}]
    [:div flexbox
     (for [i (range 50)]
       ^{:key i} [:button {:style style
                           :on-click #(add-line-item i)}
                  i])]))

(defn line-item-grid [line-items]
  (let [container [:div {:style {:display :grid
                                 :grid-template-columns "15% minmax(40%,auto) 25% 25%"
                                 :grid-auto-rows 15
                                 :background-color "rgba(255, 255, 255, 0.8)"
                                 :font-size 15
                                 :text-align :left
                                 :overflow-y :auto
                                 :overflow-x :hidden}}]
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
                     :height "100%"
                     }}
       [item-grid items]
       (line-item-grid line-items)])))

(defn init []
  (r/render-component [main-grid state] (js/document.getElementById "app"))
  )



