# Class 01. 스프링 프레임워크 시작하기

☛ BoardWeb 프로젝트 생성

* * *

# Class 02. 프레임워크 개요

### 2.1 프레임워크 개념

- 프레임워크: 아키텍처에 해당하는 골격 코드
  - 장점
    - **빠른 구현 시간**: 골격 코드가 제공되므로 개발자는 Business Logic만 구현하면 됨
    - **쉬운 관리**: 같은 프레임워크가 적용된 application들은 아키텍처가 같음
    - **개발자들으 역량 획일화**: 관리자 입장에서 개발 인력을 더 효율적으로 구성 가능
    - **검증된 아키텍처의 재사용과 일관성 유지**: 개발 및 유지보수 과정에서 아키텍처가 왜곡되건 변형되지 않으
    - **자바 기반의 프레임워크**

#### ➣ 프레임워크 표
|처리 영역|프레임워크|
|-------|-------|
|Presentation|Struts </br> Spring(MVC)|
|Business|Spring(IoC, AOP)|
|Persistence|Hibernate or JPA </br> Ibatis or Mybatis|

### 2.2 스프링 프레임워크

- Rod Johnson이 2004년에 만든 오픈소스 프레임워크
- 기존에 사용하던 EJB의 단점
  - 스펙이 매우 복잡
  - 개발 및 유지보수 복잡
  - 컴포넌트 배치 및 실행을 위해 JEUS, Weblogic 등의 고가으 WAS 필요
  - 디자인 패턴을 반드시 사용해야 하므로 디자인 패턴에 대한 이해가 무조건 있어야 함

- 그에 비해 스프링 프레임워크는 
  - 평범한 POJO를 사용하면서 EJB에서마 가능했던 기능 지원
  - 디자인 패턴이 이미 적용돼 배포됨
  - 대표적인 not POJO: Servlet 클래스
  
- 스프링 프레임워크의 특징
  - 경량 (Lightweight)
    - 스프링은 하나 이상의 JAR 파일로 구성된 여러 개의 모듈로 구성
    - 이를 통해 개발과 실행이 가능하고, 배포 또한 빠르고 쉬움
    - POJO 형태의 객체를 관리하기 때문에 단순하고 가벼움
  - 제어의 역행 (Inversion of Control)
    - IoC를 통해 application의 낮은 결합도와 높은 응집도 유지
    - IoC가 적용되면 객체 생성을 자바 코드가 직접 처리하지 않고 컨테이너가 대신 처리
    - 객체간 의존관계 역시 컨테이너가 처리 ➣ 의존관계가 명시되지 않아 결합도가 낮아지고 유지보수 용이
  - 관점지향 프로그래밍 (Aspect Oriented Programming, AOP)
    - Business Method를 개발할 때, 핵심 비즈니스 로식과 각 비즈니스 메서드마다 반복해서 등장하는 공통 로직 분리 ➣ 높은 응집도
    - 개발 및 유지보수 용이
  - 컨테이너 (Container)
    - 특정 객체의 라이프 사이클 관리
    - 객체 운용에 필요한 다양한 기능 제공
    - application 운용에 필요한 객체를 생성하고 객체 간의 의존관계를 관리한다는 점에서 스프링도 일종의 컨테이너

### 2.3 IoC 컨테이너

- IoC 컨테이너가 객체 생성과 의존관계 담당
- 코드에서 객체 생성과 의존관계 담당 부분이 사라져 낮은 결합도의 컴포넌트 구현 가능
  
#### 다형성 이용
- 두개 이상의 객체의 공통점을 추출하여 interface를 구현하고, 객체를은 이 interface를 구현하도록 함
  
#### 디자인 패턴 이용
- interface를 이용했지만, 이 역시 TV 변경 시 TV 클래스 객체 생성 코드를 수정해야 함
- Factory 패턴 적용하여 해결
  - **Factory 패턴이란?** client에서 사용할 객체 생성을 캡슐화하여 객체와 객체를 느슨한 결합 상태로 만들어줌
  - 실습 시 [Run As] → [Run Configurations...] → [Arguments] → [Program arguments]에 "lg"나 "samsung"을 입력 
  
* * *

# Class 03. 스프링 컨테이너 및 설정 파일

### 3.1 스프링 IoC 시작하기

- applicationContext.xml 만들기
  - 프로젝트의 "src/main/resources " → [New] → [Other] → "Spring Bean Configuration File in Spring 디렉터리" 선택
  - applicationContext.xml에 <bean id="tv" class="polymorphism.SamsungTV"/> 추가
```java
BeanFactory factory = new BeanFactory();  // 팩토리 클래스를 통해 팩토리 객체 생성 
AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");  // 스프링 컨테이너를 구동하여 팩토리 객체 생성
```

#### 스프링 컨테이너 동작 순서
1️⃣ TVUser 클라이언트가 스프링 설정 파일을 로딩하여 컨테이너 구동
2️⃣ 스프링 설정 파일에 <bean> 등록된 SamsungTV 객체 생성
3️⃣ getBean() 메서드로 이름이 'tv'인 객체 요청(lookup)
4️⃣ SamsungTV 객체 반환
☛ 티비의 종류를 바꿀 때 applicationContext.xml 파일만 수정하면 됨
  
#### 스프링 컨테이너 종류
☛ 스프링에서는 BeanFactory와 이를 상속한 ApplicationContext 두 가지 유형의 컨테이너 제공

- BeanFactory
  - 스프링 설정 파일에 등록된 <bean> 객체를 생성 및 관리하는, 가장 기본적인 기능만 제공
  - 지연 로딩 방식 사용 (객체 생성 시점이 컨테이너 구동 시점이 아닌 client 요청 시점)
  - 일반적인 스프링 프로젝트에서 사용 안함
  
- ApplicationContext
  - <bean> 객체 관리 기능 외 트랜잭션 관리, 메시지 기반의 다국어 처리 등 다양한 기능 제공
  - 즉시 로딩 방식 사용(컨테이너 구동 시점에 <bean>에 등록된 클래스 생성)
  - Web Application 개발 지원
|구현 클래스|기능|
|--------|---|
|GenericXmlApplicationContext| 파일 시스템이나 클래스 경로에 있는 XML 설정 파일을 로딩하여 구동하는 컨테이너|
|XmlWebApplicationContext|웹 기반의 스프링 애플리케이션을 개발할 때 사용하는 컨테이너|

### 3.2 스프링 XML 설정
  

  
