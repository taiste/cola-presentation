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
      ;Intro
      [:section {:data-state "logo"}
        [:div {:class "slide logo"}
          [:img {:src "img/logo.jpg"}]]]
      [:section {:data-state "intro"}
        [:div {:class "slide text-container"}
          [:p.intro-text "We tried some Coke."]]]
      [:section {:data-state "paths"}
        [:div.taistePaths]]

      ;Drink scoring
      [:section {:data-state "score-intro"}
        [:div {:class "slide text-container"}
          [:p.score-text "Coke scores"]]]
      [:section {:data-state "score"}
        [:div.colaScore]]

      ;Drinker scoring
      [:section {:data-state "histogram-intro"}
        [:div {:class "slide text-container"}
          [:p.histogram-text "Cokée scores"]]]
      [:section {:data-state "histogram"}
        [:div.colaHistogram]]
      [:section {:data-state "congratz"}
        [:div {:class "slide text-container"}
          [:p.congratz-text "Congratz Fossa!"]]]

      ;What to Drink?
      [:section {:data-state "scatter-intro"}
        [:div {:class "slide text-container"}
          [:p.scatter-text "What you should buy."]]]
      [:section {:data-state "scatter"}
        [:div.colaScatter]]

      ;Outro
      [:section {:data-state "outro"}
        [:div {:class "slide text-container"}
          [:p.outro-text "Thanks!"]]]]])

(defn main-page []
  [reveal-slides])

(defn text-listeners []
  (.addEventListener js/Reveal "intro" #(.add (.-classList (aget (.getElementsByClassName js/document "intro-text") 0)) "visible"))
  (.addEventListener js/Reveal "score-intro" #(.add (.-classList (aget (.getElementsByClassName js/document "score-text") 0)) "visible"))
  (.addEventListener js/Reveal "histogram-intro" #(.add (.-classList (aget (.getElementsByClassName js/document "histogram-text") 0)) "visible"))
  (.addEventListener js/Reveal "scatter-intro" #(.add (.-classList (aget (.getElementsByClassName js/document "scatter-text") 0)) "visible"))
  (.addEventListener js/Reveal "congratz" #(.add (.-classList (aget (.getElementsByClassName js/document "congratz-text") 0)) "visible"))
  (.addEventListener js/Reveal "outro" #(.add (.-classList (aget (.getElementsByClassName js/document "outro-text") 0)) "visible")))

(defn graph-listeners []
  (.addEventListener js/Reveal "paths" #(set! (.-innerHTML (aget (.getElementsByClassName js/document "taistePaths") 0)) "<object class='slide' type='text/html' data='taistePaths/index.html' ></object>"))
  (.addEventListener js/Reveal "score" #(set! (.-innerHTML (aget (.getElementsByClassName js/document "colaScore") 0)) "<object class='slide' type='text/html' data='colaMaistelu/index.html' ></object>"))
  (.addEventListener js/Reveal "histogram" #(set! (.-innerHTML (aget (.getElementsByClassName js/document "colaHistogram") 0)) "<object class='slide' type='text/html' data='colaMaisteluHistogram/index.html' ></object>"))
  (.addEventListener js/Reveal "scatter" #(set! (.-innerHTML (aget (.getElementsByClassName js/document "colaScatter") 0)) "<object class='slide' type='text/html' data='colaMaisteluScatter/index.html' ></object>")))

(defn event-listeners []
  (text-listeners)
  (graph-listeners))

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
                                   :viewDistance 2}))
  (event-listeners))

(main!)
