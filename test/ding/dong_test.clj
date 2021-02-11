(ns ding.dong-test
  (:require
   [clojure.test :refer [deftest is]]
   [ding.dong :as sut]
   [matcher-combinators.test]
   [ring.mock.request :as mock]))

(deftest cors
  (let [origin  "https://www.example.com"
        request (-> (mock/request :get "/")
                    (mock/header "Origin" origin))]
    (is (match? {:status  200
                 :headers {"Access-Control-Allow-Origin" origin
                           "Access-Control-Allow-Headers" "GET, POST, OPTIONS"
                           "Access-Control-Allow-Credentials" "true"}}
                (sut/site request)))))
