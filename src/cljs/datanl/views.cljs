(ns datanl.views
  (:require  [re-frame.core :as re-frame]
             [datanl.subs :as subs]
             [ajax.core :refer [GET POST]]
             [stylefy.core :refer [use-style]]
             [reagent.core :as reagent]
             [goog.crypt.base64 :as b64]
             [ajax.protocols :refer [-body]]
             ["faker" :as faker]))

(defn label-input []
  (let [label-field-value (re-frame/subscribe [::subs/field-value])] ;; @(re-frame/subscribe [::forms/field-value form-id label-field-path])]
    [:div.ui.form
     [:div.required.field
      [:label "Label"]
      [:input
       {:type      "text"
        :value     @label-field-value
        :on-change #(re-frame/dispatch [::set-field-value  (-> % .-target .-value)])}]]]))

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
  [:div.card.col-3
   [:img.card-img-top {:alt "Card image cap", :src (str "https://picsum.photos/400?random=" img-n)}]
   [:div.card-body
    [:h5.card-title (.companyName (.-company faker))]
    [:p.card-text (.catchPhrase (.-company faker))]
    [:a.card-link {:href "#"} (.jobTitle (.-name faker))]]])

(defn get-random-images []
  [:div {:class "row align-items-center"}
    (map card2 (range 9))])

(defn navbar []
  [:nav.navbar.navbar-expand-lg.navbar-light.bg-light
   [:a.navbar-brand
    {:href "#"}
    [:img.d-inline-block.align-top
     {:loading "lazy"
      :alt ""
      
      :width "200"
      :src "images/logo.png"}]]
   [:button.navbar-toggler
    {:aria-label "Toggle navigation"
     :aria-expanded "false"
     :aria-controls "navbarText"
     :data-target "#navbarText"
     :data-toggle "collapse"
     :type "button"}
    [:span.navbar-toggler-icon]]
   [:div#navbarText.collapse.navbar-collapse
    [:ul.navbar-nav.mr-auto
     [:li.nav-item.active
      [:a.nav-link {:href "#"} "Home " [:span.sr-only "(current)"]]]
     [:li.nav-item [:a.nav-link {:href "#"} "Features"]]
     [:li.nav-item [:a.nav-link {:href "#"} "Pricing"]]]
    [:form.form-inline
     [:input.form-control.mr-sm-2
      {:aria-label "Search", :placeholder "Search", :type "search"}]
     [:button.btn.btn-outline-success.my-2.my-sm-0
      {:type "submit"}
      "Search"]]]])

(defn login-input [val]
  [:input.form-control {:value @val
                        :on-change #(reset! val (-> % .-target .-value))}])
  
(defn login []
  (let [credentials (reagent/atom nil)] ;{:username nil :password nil})]
   (fn []
    [:form
     [login-input credentials]
     [:p @credentials]
     [:div.form-group
      [:label {:for "exampleInputEmail1"} "Email address"]
      [:input#exampleInputEmail1.form-control
       {:aria-describedby "emailHelp", :type "email"}]
      [:small#emailHelp.form-text.text-muted
       "We'll never share your email with anyone else."]]
     [:div.form-group
      [:label {:for "exampleInputPassword1"} "Password"]
      [:input#exampleInputPassword1.form-control {:type "password"}]]
     [:div.form-group.form-check
      [:input#exampleCheck1.form-check-input {:type "checkbox"}]
      [:label.form-check-label {:for "exampleCheck1"} "Check me out"]]
     [:button.btn.btn-primary {:type "submit"} "Submit"]])))


(defn jumbo []
  [:div.jumbotron.jumbotron-fluid
    [:div.container
     [:h1.display-4 (.catchPhrase (.-company faker))]
     [:p.lead
      "This sector has innovative wheelhouse. Skate to where the puck is going to be."]]])

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
    
    [:div {:class "container"}
     [login]
     (comment [photo-grid])
     [navbar]
     [jumbo]
     ;[counter-button]
     [:div {:class "flex" :style {:display "flex" :flex-direction "row" :justify-content "space-between"}}]
      

     [:div
      [get-random-images]]]))
        
 
 
