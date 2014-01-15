(ns bhiravi.core
  (:use [clojure.java.io]
        [clojure.string]
        [overtone.live]
        [overtone.inst.sampled-piano]))


(def metro (metronome 120))

(def subject [[:c4 1] [:c#4 1] [:f4 2] [:e4 1] [:c#4 1] [:c4 1] [:c#4 1] [:e4 1] [:c#4 1] [:c4 4]
              [:c#4 1] [:f4 1] [:g4 1] [:g#4 1] [:f4 1] [:g4 2]
              [:g#4 1] [:g4 2] [:f4 1] [:e4 1] [:c#4 1] [:c4 4]
              [:c4 1] [:c#4 1] [:f4 2] [:e4 1] [:c#4 1] [:c4 1] [:c#4 1] [:e4 1] [:c#4 1] [:c4 4]]
 )


(defn play-one

  [metronome beat instrument [pitch dur]]
  (let [end (+ beat dur)]
    (if pitch
      (let [id (at (metronome beat) (instrument (note pitch)))]
        (at (metronome end) (ctl id :gate 0))))
    end))

(defn play
  ([metronome inst score]
   (play metronome (metronome) inst score))
  ([metronome beat instrument score]
   (let [cur-note (first score)]
     (when cur-note
       (let [next-beat (play-one metronome beat instrument cur-note)]
         (apply-at (metronome next-beat) play metronome next-beat instrument
                   (next score) []))))))


(play metro sampled-piano subject)
