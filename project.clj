(defproject metall-calc "1.0.0"
  :description "metall-calc"

  :min-lein-version "2.9.1"

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.879"]
                 [reagent "1.1.0"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]]

  :plugins [[lein-ancient "0.6.15"]
            [lein-kibit "0.1.8"]]

  :source-paths ["src/cljs"]

  :test-paths ["test/cljs"]

  :aliases {"fig"         ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build"   ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:prod"    ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "prod"]
            "fig:test"    ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" "metall-calc.test-runner"]}

  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.14"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]
                   :resource-paths ["target"]
                   :clean-targets ^{:protect false} ["target" "resources/public/js" "resources/public/cljs-out"]}})
