(ns datanl.views
  (:require
   [re-frame.core :as re-frame]
   [datanl.subs :as subs]
   [datanl.state :as state]
   [ajax.core :refer [POST GET]]))


(defn button []
  [:div
   @state/button-state
   [:input
    {:type :button
     :value "+"
     :on-click (fn []
                 (swap! state/button-state inc))}]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [button]]))

(defn handler [response]
  (.log js/console (str response)))


(defn error-handler [error]
  (.log js/console (str "something bad happened: " error)))


(POST "http://localhost:8280/"
      {:params {:message "hello"}
       :format-response :json
       :handler handler
       :error-handler error-handler})


(GET "http://localhost:8280/"
        {:handler handler
         :error-handler error-handler})

