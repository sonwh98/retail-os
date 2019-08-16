(ns user
  (:require [figwheel-sidecar.repl-api :as f]
            [taoensso.timbre :as log :include-macros true]))

(defn start []
  (f/start-figwheel!))

(defn stop []
  (f/stop-figwheel!))

(defn cljs []
  (f/cljs-repl))

(log/set-level! :info)

