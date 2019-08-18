(defproject retail-os "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.kaicode/wocket "0.1.5-SNAPSHOT"]
                 [org.clojure/clojurescript "1.10.520"]
                 [compojure "1.6.1"]
                 [reagent "0.8.1"]
                 [stigmergy/tily "0.1.8-SNAPSHOT"]]

  :plugins [[lein-figwheel "0.5.18"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    :target-path]

  :figwheel {:server-port 3449}

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src/cljs" "src/cljc" "env/dev/cljs"]
                :figwheel {:on-jsload "stigmergy.cdr.main/jsload"
                           :websocket-host :js-client-host}
                :compiler {:main stigmergy.retail-os.init
                           :asset-path "js/compiled/dev"
                           :output-to "resources/public/js/compiled/retail-os.js"
                           :output-dir "resources/public/js/compiled/dev"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}
               ]}

  :target-path "target/%s"

  :profiles {:project/dev {:dependencies [[figwheel-sidecar "0.5.18"]
                                          [cider/piggieback "0.4.0"]
                                          [binaryage/devtools "0.9.10"]]
                           :source-paths ["src/clj" "src/cljc" "env/dev/clj"]}
             :project/prod {:prep-tasks ["compile" ["cljsbuild" "once" "prod"]]
                            :source-paths ["src/clj" "src/cljc"]
                            :main stigmergy.retail-os.server
                            :aot :all}

             :dev [:project/dev]
             :uberjar [:project/prod]}

  
  )
