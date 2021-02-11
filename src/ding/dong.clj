(ns ding.dong
  (:require
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :as defaults]
   [ring.util.response :as response]))

(defn- allow-origin?
  [_request]
  true)

(defn- assoc-cors-headers
  [request response]
  (let [origin       (response/get-header request "Origin")
        cors-headers {"Access-Control-Allow-Origin"      origin
                      "Access-Control-Allow-Headers"     "GET, POST, OPTIONS"
                      "Access-Control-Allow-Credentials" "true"}]
    (update response :headers merge cors-headers)))

(defn wrap-cors
  "Naive CORS middleware - this is not production quality. Does not support
  preflight requests. Does not validate origins. Only good for demo purposes.

  See Pedestal for anything more than a toy web app. Refer to MDN for docs.

  - https://github.com/pedestal/pedestal
  - https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS"
  [handler]
  (fn [request]
    (let [response (handler request)]
      (cond->> response
        (allow-origin? request)
        (assoc-cors-headers request)))))

(defn handler
  [_request]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    "OK!"})

(def site
  (-> handler
      (defaults/wrap-defaults defaults/site-defaults)
      wrap-cors))

(defn -main
  [& _args]
  (jetty/run-jetty site {:host "0.0.0.0" :join? true :port 3000}))
