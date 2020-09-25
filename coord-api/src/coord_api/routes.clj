(ns coord-api.routes
  (:require
   [compojure.route :as route]
   [compojure.core :refer [context]]
   [ring.middleware.json :as middleware]
   [compojure.core :refer [defroutes GET]]
   [ring.middleware.cors :refer [wrap-cors]]
   [coord-api.coordinates :refer [get-coords get-filtered-coords]]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn- json-resp
  [body & {:keys [status] :or {status 200}}]
  {:status status
   :headers {"Content-Type" "application/json"}
   :body body})

(defroutes routes
  (route/resources "/")
  (GET "/" []
    (json-resp {:message "Hello Pilloxa"}))
  (context "/coordinates" []
    (GET "/" []
      (json-resp {:coordinates (get-coords)}))
    (GET "/:coord-type" [coord-type :as req]
      (json-resp {:coordinates (get-filtered-coords coord-type)})))
  (route/not-found
   (json-resp {:message "Something went wrong, we have dispatched some monkeys to fix it." :status 404})))

(def app
  (-> routes
      (wrap-cors :access-control-allow-origin [#".*"]
                 :access-control-allow-methods [:get])
      (middleware/wrap-json-body {:keywords? true})
      (middleware/wrap-json-response)
      (wrap-defaults site-defaults)))
