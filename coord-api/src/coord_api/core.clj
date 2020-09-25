(ns coord-api.core
  (:require
   [ring.adapter.jetty :as ring]
   [coord-api.routes :refer [app]]))

(defn start [port]
  (ring/run-jetty app {:port port
                       :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
