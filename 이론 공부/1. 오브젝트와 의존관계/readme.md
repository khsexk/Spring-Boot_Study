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

✎ **추상화**: 어떤 것들의 공통적인 성격을 뽑아내어 이를 따로 분리해내는 작업    

### 변화의 성격

- JDBC API를 사용할 것인지, DB 전용 API를 사용할 것인지
- 어떤 테이블 이름과 필드 이름을 사용해 어떤 SQL을 만들 것인지
- etc
- 하지만 상속은 사실 여러가지 단점을 가지고 있어 변화를 따라가기 힘듦

### 클래스의 분리

- 관심사가 다르고 변화의 성격 또한 다를 때
  - SimpleConnectionMaker 객체를 생성
  - 객체의 메서드로 connection 연결
<img width="454" alt="두 개의 독립된 클래스로 분리한 결과" src="https://user-images.githubusercontent.com/56003992/150083559-dd1b59b4-a6f2-45ad-a7e1-1115c2bcb81b.png">

- 이식성이 높아짐
- But. UserDao가 SimpleConnectionMaker라는 특정 클래스에 종속돼 버림
  - 해결해야 할 문제
    - SimpleConnectionMaker의 메서드의 다르게 하면 결국 UserDao 코드를 변경해야 함
    - DB 커넥션을 제공하는 클래스가 어떤 것인지 UserDao가 구체적으로 알고 있어야 함 (이후 변경되면 자유로운 확장이 힘들어짐)

### 인터페이스 도입

```java
public class DConnectionMaker implements ConnectionMaker {
  ...
  public Connection makeConnection() throws ClassNotFoundException, SQLException {
    // D 사의 독자적인 방법으로 Connection 생성 코드
  }
}
```
- 인터페이스(interface)를 통해 클래스 분리
- But. 여전히 UserDao의 생성자에서는 분리가 안됨

### 관계 설정 책임의 분리

- UserDao와 UserDao가 사용할 ConnectionMaker의 특저 구혀 클래스 사이의 관계르 설정해주는 것이 관심
- Object를 꼭 UserDao 코드 내에서 만들 필요 없음
  - 해당 interface 타입의 object라면 파라미터로 전달 가능
  - 파라미터로 제공받는 오브젝트는 인터페이스에 정의된 메서드만 이용한다면 어떤 클래스로부터 만들어졌는지 상관없음
<img width="456" alt="클래스 사이에 불필요한 의존관계를 갖고 있는 구조" src="https://user-images.githubusercontent.com/56003992/150160860-876f486e-ee0c-4748-b4a3-490cddb118fd.png">
  
- UserDao의 모든 코드는 ConnectionMake 인터페이스 외 어떤 클래스와도 관계를 가져서는 안됨
- But. UserDao 오브젝트가 동작하려면 특정 클래스의 Object와 관계를 맺어야 함
- 따라서 오브젝트 사이에 다이나믹한 관계를 만들자
  - 클래스끼리의 관계와 다륵 코드에서 알 수 없음
  - 해당 클래스가 구현한 interface를 사용했다면 그 클래스의 Object를 interface 타입으로 받아 사용 가능
  - OOP의 특징인 다형성 덕분에 가능

<img width="369" alt="UserDao 오브젝트와 DConnectionMaker 오브젝트 사이의 관계가 만들어진 상태" src="https://user-images.githubusercontent.com/56003992/150162561-ae023b6c-47b3-40d5-bfd4-190859217acf.png">
- 위의 사진은 모델링 시점의 클래스 다이어그램이 아닌 **런타임 시점의 Object 다이어그램**
- UserDao 오브젝트가 DConnectionManager 오브젝트를 사용하려면 두 클래스의 Object 사이에 관계를 맺어줘야 함
  - 이 관계는 런타임 사용관계 또는 링크, 또는 의존관계라 불림
  
* 수정한 UserDao 생성자
``` java
public UserDao(ConnectionMaker connectionMaker) {
  this.connectionMaker = connectionMaker;
}
```
  
* main() 메서드
``` java
public class UserDaoTest {
  public static void main(String[] args) throws ClassNotFoundException, SQLException) {
    ConnectionMaker connectionMaker = new DConnectionMaker();
    UserDao dao = new UserDao(connectionMaker);
  }
}
```

* 최종 구조
<img width="471" alt="관계 설정 책임을 담당한 클라이언트 UserDaoTest가 추가된 구조" src="https://user-images.githubusercontent.com/56003992/150164592-ab8bc82c-4971-40b0-9f44-9c807e409ac8.png">

### 원칙과 패턴

- **개방 페쇄 원칙** (Open-Closed Principle)
  - 클래스나 모듈은 확장에는 열려 있어야 하고, 변경에는 닫혀 있어야 한다
  - 객체지향 설계 원칙(SOLID)에는 단일 책임 원칙(SRP), 개방 폐쇄 원칙(OCP), 리스코프 치환 원칙(LSP), 인터페이스 분리 원칙(ISP), 의존관계 역전 원칙(DIP)가 있음
  
- **높은 응집도와 낮은 결합도**
  - SW 개발의 고전적인 원리
  - 응집도가 높다 ➢ 변화가 일어날 때 해당 모듈에서 변하는 부분이 크다 & 하나의 모듈 및 클래스가 하나의 책임에만 집중한다
  - 결합도가 낮다 ➢ 연결은 유지하는 데 필요한 최소한의 방법만 간접적인 형태로 제공한다 (서로 독립적이다)
  - 유지보수 용이

- **전략 패턴**(Strategy Pattern)
  - 자신의 기능 맥락(context)에서 필요에 따라 변경이 필요한 알고리즘을 interface를 통해 통째로 외부로 분리시키고, 이를 구현한 구체적인 알고리즘 클래스를 필요에 따라 바꿔서 사용할 수 있게 하는 디자인 패턴

☛ **Spring이란 객체지향적 설계 원칙과 디자인 패턴에 나타난 장점을 자연스럽게 개발자들이 활용할 수 있게 해주는 Framework**

* * *
</br>  

> ⭐︎
> ## IV. 제어의 역전 (IoC)
</br>

✎ **IoC**: Inversion of Control   

* * *
</br>  

> ⭐︎
> ## V. 
</br>

✎ **IoC**: Inversion of Control   

* * *
</br>  

> ⭐︎
> ## VI. 
</br>

✎ **IoC**: Inversion of Control   
