# 1장. 오브젝트와 의존관계
</br>

> ⭐︎
> ## Intro

### 스프링의 핵심 철학
- 객체지향 프로그래밍의 장점을 활용

### 스프링과 오브젝트
- 오브젝트의 라이프 사이클
  - 오브젝트의 생성, 사용 및 소멸
  - 타 오브젝트와 관계 맺기
- 오브젝트의 설계
  - OOD(Object Oriented Design): 객체 지향 설계
  - 디자인 패턴: 다양한 목적에 부합되게 재활용 가능한 설계
  - 리팩토링: 지속적인 구조 개선
  - 단위 테스트: 오브젝트의 동작이 기대한 대로 되는지 효과적으로 검증

### 스프링이 제공하는 것
- 오브젝트 설계, 구현, 사용, 개선에 대한 기준 제시
- 객체지향 기술과 설계, 구현에 관한 실용적 전략
- 검증된 베스트 프렉티스

* * *
</br>

> ⭐︎
> ## I. 초난감 DAO  
</br>

✎ **DAO**(Data Access Object): DB를 사용해 데이터를 조회하거나 조작하는 기능을 전담하도록 만든 Object  
✎ **JavaBean**: 원래는 비주얼 툴에서 조작 가능한 컴포넌트이지만, 자바의 주력 개발 플랫폼이 웹 기반의 엔터프라이즈 방식으로 바뀌면서 현재는 디폴트 생성자와 프로퍼티를 포함하여 만들어진 오브젝트를 지칭


### User

- 사용자 정보를 저장할 때는 JavaBean 규약을 따르는 오브젝트를 이용하며 편리
  - 정보를 저장할 User 클래스 생성
  - 실제 User 객체에 담긴 정보를 저장한 DB의 table 생성

### UserDao

- 사용자 정보를 DB에 넣고 관리할 수 있는 DAO 클래스
- JDBC를 이용하는 작업 순서
  - DB 연결을 위한 커넥션 가져오기
  - SQL을 담은 PreparedStatement 생성
    - 쉽고 가독성 좋은 코드 작성을 위해 (Statement보단) PreparedStatement를 주로 사용
  - 만들어진 PreparedStatement 실행
  - 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 정보를 저장할 Object에 전달
  - 작업 중에 생성된 Resource는 작업 종료 후 반드시 close()
  - JDBC API가 만들어 내는 예외는 잡아서(catch) 직접 처리하거나 메서드에 throws 선언

### main()을 이용한 DAO 테스트 코드

- static method main()을 이용하여 테스트
- 오류 발생 시 확인할 사항
  - DB 설정 정보
  - Connection 설정 정보
  - DB 드라이버(jar 파일) 유무

* * *
</br>  

> ⭐︎
> ## II. DAO의 분리 
</br>

✎ **리팩토링**: 기존의 코드를 외부의 동작방식에는 변화 없이 내부 구조를 변경해서 재구성하는 작업 또는 기술  
✎ **디자인 패턴**: 소프트웨어 설계 시 특정 상황에서 자주 만나는 문제를 해결하기 위해 사용할 수 있는 재사용 가능한 솔루션  
✎ **템플릿 메서드 패턴**(Template Method Pattern): 슈퍼클래스에 기본적인 로직의 흐름을 만들고, 그 기능의 일부를 추상 메서드나 오버라이딩이 가능한 protected 메서드 등으로 만든 뒤 서브클래스에서 이런 메서드를 필용 맞게 구현해서 사용하도록 하는 디자인 패턴  
✎ **팩토리 메서드 패턴**(Factory Method Pattern): 서브클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것  

### 관심사의 분리

- 객체 지향에서의 오브젝트에 대한 설계와 이를 구현한 코드는 끊임없이 변함
  - 사용자의 비즈니스 프로세스와 그에 따른 요구사항의 변화
  - 애플리케이션이 더 이상 사용되지 않으면 변화 중지
- 따라서 미래의 변화를 대비하여 객체를 설계해야 함
  - 변화에 어떻게 대비할 것인가
  - 분리와 확장을 고려한 설계

### getConnection 추출

- UserDao의 관심사항
  - DB와 연결을 위한 Connection을 어떻게 가져올까
  - DB에 보낼 SQL 문장을 담은 Statement를 만들고 실행
  - DB 관련 오브젝트를 닫아 공유 리소스를 시스템에 돌려주는 것

- 중복 코드의 메서드 추출
  - 아래와 같은 작업을 리팩토링이라고 함
```java
private Connection getConnection() throws ClassNotFoundException, SQLException {
  Class.forName("com.mysql.jdbc.Driver");
  Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "id", "passwd");
  return c;
}
```
### DB 커넥션 만들기의 독립
- UserDao class 바이너리 파일을 타 회사들에게 제공하기 위한 방법
  - 상속을 통한 확장: getConnection()을 추상 메서드로 제공


- 템플릿 메서드 패턴 (스프링에서 애용하는 디자인 패턴임) 
<img width="458" alt="상속을 통한 UserDao 확장 방법" src="https://user-images.githubusercontent.com/56003992/150070278-4835a885-fca2-4f36-83e6-720692e9e964.png">

- 팩토리 메서드 패턴
<img width="508" alt="UserDao에 적용된 메서드 패턴" src="https://user-images.githubusercontent.com/56003992/150070857-497eb070-5464-4032-b135-4a836e12ba94.png">

* * *
</br>  

> ⭐︎
> ## III. DAO의 확장 
</br>

✎ **d**:   

### 클래스의 분리

- ㅇ
</br>  
