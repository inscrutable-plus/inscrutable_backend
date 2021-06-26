# 서버 set up commands

1. jdk 설치
    ```
    sudo apt install openjdk-11-jre-headless
    ```
2. 환경 변수 설정
    ```
    sudo vi /etc/environment
    ```
    아래 내용 추가
    ```
    JAVA_HOME="/usr/lib/jvm/java-1.11.0-openjdk-amd64"
    ```
    아래 명령어를 통해 reload
    ```
    source /etc/environment
    ```
3. 서버 실행
    ```
    sudo sh mvnw spring-boot:run
    ```


# API 상세 설명

## Problem
### 백준 문제 리스트와 관련 있는 API 입니다.

| API 방식 | API 링크        | Parameters                                    | 리턴 형식                                                                                                                                                                                                                        |
| -------- | --------------- | --------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| GET      | /problem/count  | `team`: 팀 고유 번호 <br> or <br> `handle`: 백준 핸들 | `Integer` <br> ex) `103`                                                                                                                                                                                                         |
| GET      | /problem/solved | `team`: 팀 고유 번호 <br> or <br> `handle`: 백준 핸들 | `Iterable<Solve>` <br> ex) `[{"solveId":1,"problemId":1,"handle":"ingyu1008"},{"solveId":2,"problemId":2,"handle":"ingyu1008"},{"solveId":3,"problemId":2,"handle":"thak1411"},{"solveId":4,"problemId":4,"handle":"thak1411"}]` |


# Models

```
Solve {
    solveId         integer
                    example: 1234
                    문제 해결에 대한 고유 번호 입니다.
    problemId       integer
                    example: 10944
                    해결한 문제 번호입니다.
    handle          string
                    example: ingyu1008
                    문제를 해결한 유저의 핸들입니다.
}
```
