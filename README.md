<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <img alt="npm" src="https://img.shields.io/badge/npm-6.14.15-blue">
  <img alt="node" src="https://img.shields.io/badge/node-14.18.2-blue">
  <a href="https://edu.nextstep.camp/c/R89PYi5H" alt="nextstep atdd">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
</p>

<br>

# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Install
#### npm 설치
```
cd frontend
npm install
```
> `frontend` 디렉토리에서 수행해야 합니다.

### Usage
#### webpack server 구동
```
npm run dev
```
#### application 구동
```
./gradlew bootRun
```

### Step1 피드백
엔티티를 update할 때, 인자를 직접 받아서 해당 상태를 변경하도록 한다.

### Step2 피드백
ConstraintViolationException은 다른 조건에서도 발생하기 때문에 범용적으로 BadRequest여도 괜찮을 것 같다.
step메서드에 params은 어떤 식이 좋을까? - 인수테스트 작성의 편리함을 위해 map도 괜찮다고 생각한다.
assert하는 부분의 검증 방법은? - 생성하고 응답된 값으로 검증도 좋지만, 하드코딩 값으로 해도 좋을 것 같다.

### Step3 피드백
일급컬렉션 활용 굿
Steps메서드로 분리 굿
불변리스트로 반환 굿
메서드명을 동사로 나타내어 역할 나타내보기
매직넘버 상수로 치환 
given과 when코드의 구분에 대해 생각해보기
jsonPath 중복제거?
조회 인수테스트의 경우 목록도 검증해보기


### Step3 생각해보기
* 구간 등록 기능
- 요구사항 설명이 주어져있음.(아 이런이런 것들이 있어야해요. 지하철 노선에 구간을 등록해야하고요. 새로운 구간을 등록할 때, 노선 상행 하행이 잘 맞아야해요. 상하상 안되고 1 2 3 1 안되고요. 네 이렇게 등록할 수 있으면 될 것 같아요)
- 위의 말을 통과하는 인수조건을 만들고, 그걸 바탕으로 인수테스트 작성 후에, 기능을 구현해야한다. 이게 ATDD 사이클임.
- 인수조건이 뭐지? 상대방한테 인수되기 위한 조건인데, 이는 당연하게 요구사항임. 인수조건 = 요구사항.
- 그러면 요구사항을 바탕으로 인수테스트를 작성해야하는데, 요구사항이 테스트를 작성하기에 적합하게 정리되어있지 않음. 그래서 한 단계 중간과정을 낌.
- 그 중간과정이 인수조건을 시나리오 형식으로 표현하는 것임.
- 그러면 제일 중요한 것. 요구사항을 시나리오 형식으로 나타내는 것. 그러니깐 이걸 먼저 해야함.

### Step3 시나리오 작성해보기
* 구간 등록 기능 (정상적인 시나리오)
- 지하철노선 신분당선(상행:강남역, 하행:양재역)이 등록되어있다.
- 신분당선에 구간(상행:양재역, 하행:판교역) 등록 요청한다.
- 구간 등록이 성공한다.

구간 등록 기능 (비정상적인 시나리오1 - 새로운 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 한다.)
- 지하철노선 신분당선(상행:강남역, 하행:양재역)이 등록되어있다.
- 신분당선에 구간(상행:역삼역, 하행:판교역) 등록 요청한다.
- 구간 등록이 실패한다.

구간 등록 기능 (비정상적인 시나리오2 - 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.)
- 지하철노선 신분당선(상행:강남역, 하행:양재역)이 등록되어있다.
- 신분당선에 구간(상행:양재역, 하행:강남역) 등록 요청한다.
- 구간 등록이 실패한다.

* 구간 제거 기능 (정상적인 시나리오)
- 지하철노선 신분당선(상행:강남역, 하행:양재역)이 등록되어있다.
- 신분당선에 구간(상행:양재역, 하행:판교역)이 등록되어있다.
- 구간(판교역) 제거 요청한다.
- 구간(양재_판교_구간) 제거가 성공한다.

구간 제거 기능(비정상적인 시나리오1 - 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다. 즉, 마지막 구간만 제거할 수 있다.)
- 지하철노선 신분당선(상행:강남역, 하행:양재역)이 등록되어있다.
- 신분당선에 구간(상행:양재역, 하행:판교역)이 등록되어한다.
- 구간(양재역) 제거 요청한다.
- 구간 제거가 실패한다.

구간 제거 기능(비정상적인 시나리오2 - 지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.)
- 지하철노선 신분당선(상행:강남역, 하행:양재역)이 등록되어있다.
- 구간(양재역) 제거 요청한다.
- 구간 제거가 실패한다.