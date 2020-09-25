(ns coord-api.util
  (:require
   [clojure.java.io :refer [reader writer]]))

(defn trace [x]
  (println x)
  x)

(defn read-file [path]
  (with-open [rdr (reader path)]
    (doall (line-seq rdr))))

(defn write-object
  "Serializes an object to disk so it can be opened again later.
   Careful: It will overwrite an existing file at file-path."
  [obj]
  (with-open [wr (writer "coords.txt")]
    (.write wr (pr-str obj))))
