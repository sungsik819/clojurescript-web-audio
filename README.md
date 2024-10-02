# Joy of Clojure ch13 웹 오디오 edn 버전
책에는 lein으로 되어 있어서 edn으로 컨버팅 및 테스트 완료 버전

## 빌드
```
clj -M:cljsbuild-dev

or 

clj -M:cljsbuild-prod
```

* index.html 파일을 실행하여 play 버튼 누르면 음악 재생됨

## cljs와 clj의 연동을 테스트 해봄
* clj로 구현된 macro를 cljs에서 사용하기 위해서는 :require-macro로 추가 해준다.

```cljs
(ns joy.music
  (:require-macros [joy.macro-tunes :as mtunes]))
```

* :require-macro에 있는 파일들이 컴파일 타임 과정을 거치게 되어 문법적인 체크가 가능 해진다.

> macro_tunes.clj의 수정 전 수정 후 코드를 참고

* 단, 오류를 발견 해도 어디에서 오류가 발견되었는지 찾는것이 쉽지 않다. -> 이 부분은 좀 더 연구 해 봐야 될듯
