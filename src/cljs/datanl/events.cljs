(ns datanl.events
  (:require
   [re-frame.core :as re-frame]
   [datanl.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))
   
(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-field-value
 (fn [db [_ v]]
   (assoc db :new-form v)))