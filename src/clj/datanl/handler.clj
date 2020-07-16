(ns datanl.handler
  (:require
    [compojure.core :refer [GET defroutes]]
    [compojure.route :refer [resources]]
    [compojure.handler :as handler]
    [ring.util.response :refer [resource-response]]
    [ring.middleware.reload :refer [wrap-reload]]
    [shadow.http.push-state :as push-state]))


(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (resources "/")

  (GET "/about" [] "hello my friend"))

(def dev-handler (-> #'routes wrap-reload push-state/handle))

(def sandler (handler/site routes))