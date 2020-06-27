(ns datanl.views
  (:require
   [re-frame.core :as re-frame]
   [datanl.subs :as subs]))
   

(defn label-input []
  (let [label-field-value (re-frame/subscribe [::subs/field-value])] ;; @(re-frame/subscribe [::forms/field-value form-id label-field-path])]
    [:div.ui.form
     [:div.required.field
      [:label "Label"]
      [:input
       {:type      "text"
        :value     @label-field-value}]]]))
:on-change #(re-frame/dispatch [::set-field-value  (-> % .-target .-value)])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/field-value])]
    [:div
     [:div {:class "flex" :style {:display "flex" :flex-direction "row" :justify-content "space-between"}}
       [:img {:src "images/logo.png" :class "header-itema" :style {:max-width "25%"}}]
       [:div {:class "header-item" :style {:text-color "green"}} "Start page"]
       [:div {:class "header-item"} "Settings"]
       [:div {:class "header-item"} "My Cart"]]
     [:div
      [:p @name] 
      [label-input]]]))
         
         
