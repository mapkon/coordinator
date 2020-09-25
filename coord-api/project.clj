(defproject coord-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[compojure "1.6.2"]
                 [ring-cors "0.1.13"]
                 [ring/ring-json "0.5.0"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/clojure "1.10.1"]
                 [ring/ring-jetty-adapter "1.8.1"]]

  :main coord-api.core/-main
  :repl-options {:init-ns coord-api.core})
