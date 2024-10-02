(ns joy.macro-tunes)

;; 계산 처리 하는 함수들은 여기에
(defn pair-to-note
  [[tone duration]]
  {:cent (* 100 tone)
   :duration duration
   :volume 0.4})

;; 수정 전
(comment
  (defn consecutive-notes
    [notes]
    (reductions (fn [{:keys [delay duration]} note]
                  (assoc note
                         :delay (+ delay duration)))
                notes)))

;; 수정 후
(defn consecutive-notes [notes]
  (reductions (fn [{:keys [delay duration]} note]
                (assoc note
                       :delay (+ delay duration)))
              {:delay 0 :duration 0}
              notes))

;; 수정전
(comment
  (defn notes [tone-pairs]
    (let [bpm 360
          bps (/ bpm 60)]
      (->> tone-pairs
           (map pair-to-note)
           consecutive-notes
           (map #(update-in % [:delay] / bps))
           (map #(update-in % [:duration] / bps))))))

;; 수정 후
(defn notes [tone-pairs]
  (let [bpm 360
        bps (/ bpm 60)]
    (->> tone-pairs
         (map pair-to-note)
         consecutive-notes
         (map #(update-in % [:delay] (comp double /) bps))
         (map #(update-in % [:duration] (comp double /) bps)))))


(defn magical-theme []
  (notes
   (concat
    [[11 2] [16 3] [19 1] [18 2] [16 4] [23 2]]
    [[21 6] [18 6] [16 3] [19 1] [18 2] [14 4] [17 2] [11 10]]
    [[11 2] [16 3] [19 1] [18 2] [16 4] [23 2]]
    [[26 4] [25 2] [24 4] [20 2] [24 3] [23 1] [22 2] [10 4] [19 2] [16 10]])))

(defmacro magical-theme-macro [] (vec (magical-theme)))