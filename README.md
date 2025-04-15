# TinyPython to JVM Bytecode Compiler

## 📌 과제 개요

이 프로젝트는 TinyPython 코드를 Jasmin 문법의 Java Bytecode로 변환하는 컴파일러를, 과제 요구사항에 맞춰 구현한 프로젝트입니다.
<br/>

## 🎯 과제 목표

- TinyPython으로 작성된 프로그램(`.tpy`)을 Java Bytecode (`.j` 파일)로 변환하는 컴파일러 제작
- 생성된 `.j` 파일은 Jasmin을 사용해 `.class` 파일로 변환
- 변환된 프로그램은 `java` 명령어로 실행 가능해야 함
<br/>

## 📂 구현 파일 설명

### 📁 src 디렉토리 구성

| 파일명 | 설명 |
|--------|------|
| `Main.java` | 프로젝트의 진입점으로, ANTLR 파서를 초기화하고 TinyPython 코드를 Java Bytecode로 변환하는 전반적인 실행 흐름을 담당합니다. |
| `tinyPythonBaseListener.java` | ANTLR이 자동 생성한 베이스 리스너 클래스입니다. 커스텀 리스너에서 필요한 메서드만 오버라이드하여 사용할 수 있도록 도와줍니다. |
| `tinyPythonLexer.java` | TinyPython 문법에 기반한 렉서(Lexer)로, 소스 코드를 토큰 단위로 분리합니다. ANTLR에 의해 자동 생성됩니다. |
| `tinyPythonListener.java` | 구문 트리를 탐색하며 동작하는 리스너 인터페이스입니다. 사용자가 직접 구현한 리스너 클래스에서 이 인터페이스를 구현하여 로직을 작성합니다. |
| `tinyPythonParser.java` | TinyPython 문법을 바탕으로 생성된 파서(Parser) 클래스입니다. ANTLR이 자동 생성하며 문법 분석 기능을 제공합니다. |
| `tinyPythonToJasmin.java` | 핵심 변환 로직을 담당하는 클래스입니다. 리스너를 구현하여 TinyPython 구문을 Jasmin(Java Bytecode)으로 변환합니다. |

> 위의 클래스들 중 `Lexer`, `Parser`, `BaseListener`, `Listener`는 ANTLR을 통해 `.g4` 파일로부터 자동 생성된 파일입니다.

---
<br/>


## 📐 문법 및 제약 조건

### 변경된 문법 사항
- 함수 정의 이후 main 문이 시작되도록 구조 변경
- `defs` 라는 non-terminal 키워드 추가
- 비교 연산 표현식 단순화: `expr (comp_op expr)* → expr comp_op expr`

### 컴파일 시 제약 조건
1. 클래스는 기본적으로 존재한다고 가정
2. 모든 함수는 `static` 메서드로 작성
3. 함수 정의는 파일 상단에 위치하고, 이후 `main` 함수가 위치
4. 함수 안에 함수가 존재하지 않음 (nesting 금지)
5. 반드시 `main` 함수 존재
6. 모든 인자 및 리턴 타입은 `int`로 제한
7. 연산자(사칙/비교)도 `int`만 처리 가능
8. `Print`는 문자열, 숫자 출력만 가능하고 내부 연산은 불가능

## 🚫 고려하지 않아도 되는 기능

- 배열
- 예외 처리
- 라이브러리 사용 (Python/Java)
- 비정상 입력
- Python 패키지/Java 패키지 사용
