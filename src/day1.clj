(ns day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-input-puzzle
  [filename]
  (-> (io/resource filename)
      slurp
      (str/split #"\n\n")))

(defn parse
  [input]
  (->> (str/split-lines input)
       (map parse-long)))

(defn ->total-calories
  [calories]
  (apply + calories))

(comment
  ;; part1
  (->> (get-input-puzzle "day1.txt")
       (map parse)
       (map ->total-calories)
       (apply max))
  
  ;;part2
  (->> (get-input-puzzle "day1.txt")
       (map parse)
       (map ->total-calories)
       sort
       reverse
       (take 3)
       (apply +)))
