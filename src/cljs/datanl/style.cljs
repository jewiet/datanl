(ns datanl.style
  (:require  [stylefy.core :as stylefy]
             [garden.color :as color]
             [garden.core :refer [css]]))
  
(def colors {:panel-color "#388a43"})
  
(stylefy/tag "body" {:background-color (color :app-bg-color)
                     :font-family "IBM Plex Sans, Sans-Serif"
                     :color (color :body-text-color)
                     :font-size "16px"
                     :line-height "1.5"
                     :stylefy/manual [[:a {:color (color :link-color)}
                                       [:h1 :h2 :h3 :h4 :h5 :h6 {:margin "0.2em 0"
                                                                 :color (color :header-text-color)}]
                                       [:h1 {:font-size "50px"
                                             :font-weight 600
                                             :line-height "65px"
                                             :letter-spacing "-0.03em"}]
                                       [:h2 {:font-size "38px"
                                             :font-weight 500
                                             :line-height "49px"
                                             :letter-spacing "-0.03em"}]
                                       [:h3 {:font-size "28px"
                                             :font-weight 500
                                             :line-height "36px"
                                             :letter-spacing "-0.02em"}]
                                       [:h4 {:font-size "21px"
                                             :line-height "27px"}]
                                       [:h5 {:font-size "12px"
                                             :font-weight 500
                                             :line-height "16px"
                                             :letter-spacing "0.08em"
                                             :text-transform "uppercase"}]
                                       [:.MuiSvgIcon-root {:font-size "24px"}]
                                       [:input {:font-family "inherit"}]
                                       [:img {:max-width "100%"
                                              :height "auto"}]]]})
