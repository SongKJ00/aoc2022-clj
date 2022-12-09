(ns day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn get-input-puzzle
  [filename]
  (-> (io/resource filename)
      slurp
      str/split-lines))

(def encrypted-ch->shape
  {:X :rock
   :Y :paper
   :Z :scissors
   :A :rock
   :B :paper
   :C :scissors})

(def shape-relations
  {:rock     {:win  :paper
              :draw :rock
              :lose :scissors}
   :scissors {:win  :rock
              :draw :scissors
              :lose :paper}
   :paper    {:win  :scissors
              :draw :paper
              :lose :rock}})

(def encrypted-ch->outcomes
  {:X :lose
   :Y :draw
   :Z :win})

(def shape-scores
  {:rock     1
   :paper    2
   :scissors 3})

(def outcome-scores
  {:lose 0
   :draw 3
   :win 6})

(defn input->round
  "input to round
   input: \"A Y\"
   outpput: {:oppo-choice :A :my-choice :Y}"
  [input]
  (let [[oppo-choice my-choice] (->> (str/split input #" ")
                                     (map keyword))]
    {:oppo-choice oppo-choice
     :my-choice   my-choice}))

(defn calc-score-when-shape-is-encrypted
  [{:keys [oppo-choice my-choice] :as round}]
  (let [oppo-shape              (encrypted-ch->shape oppo-choice)
        my-shape                (encrypted-ch->shape my-choice)
        shape-score             (my-shape shape-scores)
        {:keys [win draw lose]} (my-shape shape-relations)
        outcome-score           (condp = oppo-shape
                                  win (:lose outcome-scores)
                                  draw (:draw outcome-scores)
                                  lose (:win outcome-scores))]
    (+ shape-score outcome-score)))

(defn calc-score-when-outcome-is-encrypted
  [{:keys [oppo-choice my-choice] :as round}]
  (let [outcome-to-make         (encrypted-ch->outcomes my-choice)
        oppo-shape              (encrypted-ch->shape oppo-choice)
        {:keys [win draw lose]} (oppo-shape shape-relations)
        my-shape                (condp = outcome-to-make
                                  :win win
                                  :draw draw
                                  :lose lose)
        shape-score             (my-shape shape-scores)
        outcome-score           (outcome-to-make outcome-scores)]
    (+ shape-score outcome-score)))

(comment
  ;; part1
  (->> (get-input-puzzle "day2.txt")
       (map input->round)
       (map calc-score-when-shape-is-encrypted)
       (apply +))
  
  ;;part2
  (->> (get-input-puzzle "day2.txt")
       (map input->round)
       (map calc-score-when-outcome-is-encrypted)
       (apply +)))
