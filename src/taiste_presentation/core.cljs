(ns taiste-presentation.core
    (:require [reagent.core :as reagent]))

(enable-console-print!)

(println "This text is printed from src/taiste-presentation/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(defn reveal-slides []
  [:div.reveal
    [:div.slides
      [:section {:data-state "logo"}
        [:div {:class "slide logo"}
          [:img {:src "img/logo.jpg"}]]]
      [:section {:data-state "intro"}
        [:div {:class "slide text-container"}
          [:p.intro-text "Maisteltiin Colaa."]]]
      [:section {:data-state "paths"}
        [:div.taistePaths]]
      [:section {:data-state "score"}
        [:div.colaScore]]
      [:section {:data-state "outro"}
        [:div {:class "slide text-container"}
          [:p.outro-text "Yllätys?"]]]]])

(defn main-page []
  [reveal-slides])

(defn main! []
  (reagent/render [main-page]
    (.getElementById js/document "app"))
  (.initialize js/Reveal (clj->js {:progress false
                                   :width "100%"
                                   :height "100%"
                                   :center false
                                   :margin 0
                                   :padding 0
                                   :minScale 1
                                   :maxScale 1
                                   :viewDistance 3}))
  (.addEventListener js/Reveal "intro" #(.add (.-classList (aget (.getElementsByClassName js/document "intro-text") 0)) "visible"))
  (.addEventListener js/Reveal "outro" #(.add (.-classList (aget (.getElementsByClassName js/document "outro-text") 0)) "visible"))
  (.addEventListener js/Reveal "paths" #(set! (.-innerHTML (aget (.getElementsByClassName js/document "taistePaths") 0)) "<object class='slide' type='text/html' data='taistePaths/index.html' ></object>"))
  (.addEventListener js/Reveal "score" #(set! (.-innerHTML (aget (.getElementsByClassName js/document "colaScore") 0)) "<object class='slide' type='text/html' data='colaMaistelu/index.html' ></object>")))

(main!)