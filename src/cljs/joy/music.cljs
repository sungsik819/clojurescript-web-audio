(ns joy.music
  (:require-macros [joy.macro-tunes :as mtunes]))

;; 화면 처리 함수들은 여기에
(defn soft-attack
  [ctx {:keys [volume delay duration]}]
  (let [node (.createGain ctx)]
    (doto (.-gain node)
      (.linearRampToValueAtTime 0 delay)
      (.linearRampToValueAtTime volume (+ delay 0.05))
      (.linearRampToValueAtTime 0 (+ delay duration)))
    node))

(defn sine-tone
  [ctx {:keys [cent delay duration]}]
  (let [node (.createOscillator ctx)]
    (set! (-> node .-frequency .-value) 440)
    (set! (-> node .-detune .-value) (- cent 900))
    (.start node delay)
    (.stop node (+ delay duration))
    node))

(defn connect-to
  [node1 node2]
  (.connect node1 node2)
  node2)

(defn woo
  [ctx note]
  (let [linger 1.5
        note (update-in note [:duration] * linger)]
    (-> (sine-tone ctx note)
        (connect-to (soft-attack ctx note)))))

(def make-once (memoize (fn [ctor] (new ctor))))

(defn play!
  [note-fn notes]
  (if-let [ctor (or (.-AudioContext js/window)
                    (.-webkitAudioContext js/window))]
    (let [ctx (make-once ctor)
          compressor (.createDynamicsCompressor ctx)]
      (let [now (.-currentTime ctx)]
        (doseq [note notes]
          (->
           (note-fn ctx (update-in note [:delay] + now))
           (connect-to compressor))))
      (connect-to compressor (.-destination ctx)))
    (js/alert "Sorry, this browser doesn't seem to support AudioContext")))

(defn ^:export go []
  ;; (play! woo [{:cent 1100 :duration 1 :delay 0 :volume 0.4}]))
  (comment
    (play! woo [{:cent 1100 :duration 1 :delay 0.0 :volume 0.4}
                {:cent 1400 :duration 1 :delay 0.0 :volume 0.4}
                {:cent 1800 :duration 1 :delay 0.0 :volume 0.4}]))
  (play! woo (mtunes/magical-theme-macro)))