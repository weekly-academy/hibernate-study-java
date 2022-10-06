## 스터디 코드 관리 방식

1. 스터디 대상 프로젝트를 자신의 계정으로 fork 한다.
2. fork한 저장소를 자신의 컴퓨터로 clone 한다.
3. "본인의 깃헙 아이디/주차"로 브랜치를 생성한다.(예: heli-os/week-1)
4. 코드 작성 & commit 후, fork한 자신의 원격 저장소로 push 한다.
5. github 에서 pull request를 작성한다.

    - pull request 목적지는 스터디 대상 프로젝트의 "본인의 깃헙 아이디" branch로 한다.
    - pull request 출발지는 fork한 자신의 저장소의 3번에서 생성한 branch로 한다.
    - ex) weekly-acaemy:heli-os  <- heli-os:heli-os/week-1

6. 각 주차별 리뷰가 완료되면 5번의 pull requqest 를 merge 한다.
7. 다음 주차부터는 브랜치의 주차를 증가시키며 위와 동일한 방식으로 코드를 관리한다. 

    - ...
    - heli-os/week-2
    - heli-os/week-3
    - heli-os/week-4
    - ...
