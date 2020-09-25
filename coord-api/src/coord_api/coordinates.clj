(ns coord-api.coordinates
  (:require
   [clojure.string :as str]
   [coord-api.util :refer [trace write-object read-file]]))

(def coordinates (read-file "resources/coordinates.txt"))

(defn- extract-coords [line]
  (if line
    (->> (re-seq #"\d+" (get line 0))
         (map #(Integer. %))
         (split-at 2)
         (map vec)
         (vec))))

(defn- extract-action-type [type coordinates]
  ;; Extracts the coordinates that match a given
  ;; key. Expected keys are [activate, deactivate, toggle]
  (let [pattern (re-pattern (str "(" type ".*$)"))]
    (->> coordinates
         (map #(re-find pattern %))
         (filter #(not (nil? %)))
         (map extract-coords))))

(defn- transform-to-type [coordinates]
  ;; Transforms a given arbitrary coordinate file
  ;; content into an more structure internal data structure
  (hash-map :toggle (extract-action-type "toggle" coordinates)
            :activate (extract-action-type "^activate" coordinates)
            :deactivate (extract-action-type "deactivate" coordinates)))

(defn get-coords []
  (transform-to-type coordinates))

(defn get-filtered-coords [type-filter]
  ((keyword type-filter) (get-coords)))

(defn generate-sub-grid-coords [sub-grid]
  (map (fn [avec]
         (let [x1 (first (first avec))
               y1 (first (second avec))
               x2 (second (first avec))
               y2 (second (second avec))
               coords (for [x (range x1 (+ x2 1)) y (range y1 (+ y2 1))]
                        (list x y))]
           {avec coords}))
       sub-grid))

(defn generate-grid-coords []
  (let [coordinates (get-coords)]
    (map #(generate-sub-grid-coords %) coordinates)))


