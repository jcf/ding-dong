(ns user
  (:require
   [clojure.spec.alpha :as s]
   [clojure.spec.test.alpha :as stest]
   [clojure.tools.namespace.repl :refer [set-refresh-dirs]]))

(set-refresh-dirs "dev" "src" "test")
(s/check-asserts true)
(stest/instrument)
