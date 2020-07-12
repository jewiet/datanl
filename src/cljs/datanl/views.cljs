(ns datanl.views
  (:require  [re-frame.core :as re-frame]
             [datanl.subs :as subs]
             [ajax.core :refer [GET POST]]
             [stylefy.core :refer [use-style]]
             [reagent.core :as reagent]
             [goog.crypt.base64 :as b64]
             [ajax.protocols :refer [-body]]))

(defn label-input []
  (let [label-field-value (re-frame/subscribe [::subs/field-value])] ;; @(re-frame/subscribe [::forms/field-value form-id label-field-path])]
    [:div.ui.form
     [:div.required.field
      [:label "Label"]
      [:input
       {:type      "text"
        :value     @label-field-value}]
      :on-change #(re-frame/dispatch [::set-field-value  (-> % .-target .-value)])]]))

(defonce result (atom ""))

(defn handler [response]
  (reset! result response))  ;(js->clj response :keywordize-keys true)))


(defn error-handler [error]
  (.log js/console (str "something bad happened: " error)))

(defn get-pic-urls []
  (GET "https://picsum.photos/v2/list/200"
    {:handler handler
     :response-format  :json
     :keywords? true
     :error-handler error-handler}))

(defn photo-grid []
  (get-pic-urls)
  (let [urls (take 15 (map :download_url @result))]
    [:div
      (for [url urls]
         [:img {:src url :style {:max-width "10%"}}])]))


(defn card2 [img-n]
  [:div.card.col-4
   [:img.card-img-top {:alt "Card image cap", :src (str "https://picsum.photos/400?random=" img-n)}]
   [:div.card-body
    [:h5.card-title "Card title"]
   ; [:h6.card-subtitle.mb-2.text-muted "Card subtitle"]
    [:p.card-text
     "Some quick example text to build on the card title and make up the bulk of the card's content."]
    [:a.card-link {:href "#"} "Card link"]
    [:a.card-link {:href "#"} "Another link"]]])

(defn get-random-images []
  [:div {:class "row align-items-center"}
    (map card2 (range 9))])



(def button-style {:padding "25px"
                   :background-color "#fcb603"
                   :border "1px solid black"})

(defonce counter (reagent/atom 0))

(defn counter-button []
  [:div (use-style button-style)
   @counter
   [:input
    {:type :button
     :value "+"
     :on-click #(swap! counter inc)}]])


(defn main-panel []
  (let [name (re-frame/subscribe [::subs/field-value])]
    [:div {:class "jumbotron container"}
     (comment [photo-grid])

     [counter-button]
     [:div {:class "flex" :style {:display "flex" :flex-direction "row" :justify-content "space-between"}}
      [:img {:src "images/logo.png" :class "header-itema" :style {:max-width "25%"}}]
      [:div {:class "header-item" :style {:text-color "green"}} "Start page"]
      [:div {:class "header-item"} "Settings"]
      [:div {:class "myButton"} "My Cart"]]
     [:div
      [get-random-images]]]))
