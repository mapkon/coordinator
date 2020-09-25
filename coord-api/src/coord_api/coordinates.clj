(ns coord-api.coordinates
  (:require
   [clojure.string :as str]
   [clojure.java.io :refer [reader]]))

(def coordinates
  ;; Loads the coordinates from the file
  (with-open [rdr (reader "resources/coordinates.txt")]
    (doall (line-seq rdr))))

(defn- extract-coords [line]
  (if line
    (->> (re-seq #"\d+" (get line 0))
         (map #(Integer. %))
         (partition 2)
         (into []))))

(defn- extract-action-type [type coordinates]
  ;; Extracts the coordinates that match a given
  ;; key. Expected keys are [activate, deactivate, toggle]
  (let [pattern (re-pattern (str "(" type ".*$)"))]
    (->> coordinates
         (map #(re-find pattern %))
         (filter #(not (nil? %)))
         (map extract-coords))))

(defn- transform-to-map [coordinates]
  ;; Transforms a given arbitrary coordinate file
  ;; content into an more structure internal data structure
  (hash-map :toggle (extract-action-type "toggle" coordinates)
            :activate (extract-action-type "^activate" coordinates)
            :deactivate (extract-action-type "deactivate" coordinates)))

(defn get-coords []
  (transform-to-map coordinates))

(defn get-filtered-coords [type-filter]
  ((keyword type-filter) (get-coords)))

