(ns day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as s]))

(defn get-input-puzzle
  [filename]
  (-> (io/resource filename)
      slurp
      str/split-lines))

(defn split-items-at-middle
  "input: abcdef
   output: [(a b c) (d e f)]"
  [xs]
  (let [middle-idx (/ (count xs) 2)]
    (->> (split-at middle-idx xs))))

(defn item->priority
  [item]
  (cond
    (Character/isLowerCase item) (-> item int (- 96))
    (Character/isUpperCase item) (-> item int (- 38))))

(defn ->item-sets
  [xs]
  (map set xs))

(defn find-duplicated-item-in-sets
  [sets]
  (-> (apply s/intersection sets)
      first))

(s/intersection #{1} #{1 2} #{1 2 3})

(comment
  ;; part1
  (->> (get-input-puzzle "day3.txt")
       (map split-items-at-middle)
       (map ->item-sets)
       (map find-duplicated-item-in-sets)
       (map item->priority)
       (apply +))
  
  ;; part2
  (->> (get-input-puzzle "day3.txt")
       (partition 3)
       (map ->item-sets)
       (map find-duplicated-item-in-sets)
       (map item->priority)
       (apply +)))
