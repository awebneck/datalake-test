(ns app.main
  (:import [org.apache.hadoop.conf Configuration]
           [org.apache.hadoop.fs Path FileSystem]
           [java.net URI]))

(defn build-conf
  [^String core-site-path]
  (doto (Configuration.)
    (.addResource (Path. core-site-path))))

(defn -main
  [& args]
  (let [[datalake path core-site-path] args
        conf (build-conf core-site-path)
        fs (FileSystem/newInstance (URI/create (str "adl://" datalake ".azuredatalakestore.net/")) conf)]
    (println (str (.getPath (.next (.listFiles fs (Path. path) false)))))))
