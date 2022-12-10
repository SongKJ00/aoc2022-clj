(ns day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-input-puzzle
  [filename]
  (-> (io/resource filename)
      slurp
      str/split-lines))

(defn input->assignments
  [input]
  (let [[first-start-section first-end-section second-start-section second-end-section]
        (->> (re-find #"(\d+)-(\d+),(\d+)-(\d+)" input)
             rest
             (map parse-long))]
    {:first-start-section  first-start-section
     :first-end-section    first-end-section
     :second-start-section second-start-section
     :second-end-section   second-end-section}))

(defn fully-contains-other?
  [{:keys [first-start-section first-end-section second-start-section second-end-section]}]
  (-> (or
       (and (<= first-start-section second-start-section)
            (>= first-end-section second-end-section))
       (and (<= second-start-section first-start-section)
            (>= second-end-section first-end-section)))
      boolean))

(defn overlap-other?
  [{:keys [first-start-section first-end-section second-start-section second-end-section] :as assginments}]
  (-> (and (>= first-end-section second-start-section)
           (<= first-start-section second-end-section))
      boolean))

(comment
  ;; part1
  (->> (get-input-puzzle "day4.txt")
       (map input->assignments)
       (filter fully-contains-other?)
       count)
  
  ;; part2
  (->> (get-input-puzzle "day4.txt")
       (map input->assignments)
       (filter overlap-other?)
       count))
