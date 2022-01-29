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
<img src="https://user-images.githubusercontent.com/56003992/150781032-4e21c0e3-cd76-49e0-b203-f6da3c80dd6a.jpeg" width="512" height="256">

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

#### &lt;beans&gt; 루트 엘리먼트
☛ 스프링 컨테이너가 <bean> 저장소에 해당하는 XML 설정 파일을 참조 → <bean>의 생명주기 관리 및 여러 서비스 제공  
☛ 스프링 프로젝트 전체에서 가장 중요한 역할 담당 (정확하게 작성 및 관리해야 함)  
→ 자식 엘리먼트: <bean>, <descroption>, <alias>, <import>, etc
  
#### &lt;import&gt; 엘리먼트
☛ &lt;import&gt; 태그를 이용하여 여러 스프링 설정 파일을 포함  
☛ 설정 파일의 가독성 up, 관리 수월  
```xml
<import resource="파일 이름.xml" />
```
  
#### &lt;bean&gt; 엘리먼트
☛ 스프링 설정 파일에 클래스를 등록하기 위해 사용
☛ id 속성: bean 객체를 위한 이름을 지정할 때 사용하는 속성 (필요하지 않을 때는 생략 가능)  
☛ name 속성: 특수기호가 포함된 id를 사용할 때 id 속성 대신 사용  
☛ class 속성은 필수 (정확한 패키지 경로와 클래스 이름을 지정해야 함 → 자동 완성 기능 활용 권장)  

#### &lt;bean&gt; 엘리먼트 속성
* init-method : 객체 생성 후 멤버변수 초기화 작업을 위해 지원되는 속성
* destroy-method : Spring Container가 객체를 삭제하기 직전에 호출될 임의의 메서드를 지정하는 속성 
* lazy-init : 해당 <bean>이 사용되는 시점에 객체를 생성하도록 지정하는 속성 (default: false)
* scope → 하나의 객체만 생성하도록 제어하는 속성
  * 스프링에서는 이 속성을 통해 객체를 싱글톤(singleton) 객체로 생성해주는 기능을 컨테이너가 제공
  * 대부분 싱글톤으로 운영되고. scope 속성의 default 값도 "singleton"
  * singleton or prototype

* * *
  
# Class 04. 의존성 주입

### 4.1 의존성 관리
☛ 객체의 생성과 의존관계를 컨테이넉 자동으로 관리 → Spring IoC의 핵심 원리
<img src="https://user-images.githubusercontent.com/56003992/150780780-4e4af7a0-ce2b-4943-8415-f8c596632deb.jpeg" width="512" height="256">
- **Dependency Lookup**: 컨테이너가 application 운용에 필요한 객체를 생성하고 client는 컨테이너가 생성한 객체를 검색(Lookup)하여 사용하는 방식
- **Dependency Injection**: 객체 사이의 의존관계를 스프링 설정 파일에 등록된 정보를 바탕으로 컨테이너가 자동으로 처리해주는 방식 (setter 기반 또는 생성자 기반)

**의존성 관계**: 객체와 객체의 결합 관계
→ 하나의 객체에서 다른 객체의 변수나 메서드를 이용해야 한다면 이용하려는 객체에 대한 객체 생성과 생성된 객체의 레퍼런스 정보 필요

### 4.2 생성자 인젝션 이용하기
☛ 스프링 컨테이너는 XML 설정 파일에 등록된 클래스를 찾아서 객체 생성 시 기본적으로 디폴트 생성자 호출
☛ 생성자 인젝션은 매개변수를 가지는 다른 생성자를 호출하도록 설정하는 작업 처리

- 생성자 인제션을 위해서는
  - &lt;bean&gt; 등록 설정에서 시작태그와 종료태그 사이에 &lt;constructor-arg&gt; 엘리먼트를 추가
  - &lt;constructor-arg&gt; 엘리먼트에 ref 속성으로 생성자 인자로 전달할 객체의 아이디 참조
```xml
<bean id="tv" class="polymorphism.SamsungTV">
  <constructor-arg ref="sony"></constructor-arg>
  </bean>
  
<bean id="sony" class="polymorphism.SonySpeaker"></bean>
```
- 스프링 컨테이너는 기본적으로 bean 등록 순서대로 객체의 기본 생성자를 호출하여 객체 생성
- 생성자 인젝션으로 의존성 주입될 SonySpeaker 객체가 먼저 생성돼, 이후 생성될 SamsungTV 객체는 SonySpeaker 객체 참조
  
#### 다중 변수 매핑
☛ 스프링 설정 파일에 &lt;constructor-arg&gt; 엘리먼트를 매개변수 개수만큼 추가해야 함
☛ 객체일 때는 ref 속성, 기본형 데이터나 고정된 문자열일 때는 value 속성 사용
☛ index 속성을 통해 어떤 값이 몇 번째 매개변수로 매핑되는지 지정 가능

### 4.3 Setter 인젝션 이용하기
☛ Setter 메서드를 호출하여 의존성 주입을 처리하는 방법
☛ 대부분은 Setter 인젝션을 사용, setter 메서드가 제공되지 않는 클래스에 대해서만 생성자 인젝션 사용

- 스프링 컨테이너가 자동으로 호출되며, &lt;bean&gt; 객체 생성 직후 호출됨
- Setter 인젝션이 동작하려면 Setter 메서드는 물론, 기본 생성자도 반드시 필요
- &lt;constructor-arg&gt; 엘리먼트 대신 &lt;property&gt; 엘리먼트 사용
- 기본 자료형은 동일하게 value 속성 사용
- name 속성값이 "speaker"라면 호출되는 메서드는 "setSpeaker()"

#### p 네임스페이스 사용하기
☛ p 네임스페이스를 이용하면 Setter 인젝션을 설정할 때 좀 더 효율적으로 의존성 주입 처리 가능
  
- p 네임스페이스 선언
```xml
xmlns:p="http://www.springframework.org/schema/p"
```

- 참조형 변수에 참조할 객체 할당
```xml
p:변수명-ref="참조할 객체의 이름이나 아이디"
```
    
- 기본형이나 문자형 변숭 직접 값을 설정
```xml
p:변수명="설정할 값"
```
  
### 4.4 컬렉션 객체 설정

|컬렉션 유형|엘리먼트|속성|
|--------|------|---|
|java.util.List, 배열|&lt;list&gt;|&lt;value&gt;|
|java.util.Set|&lt;set&gt;|&lt;value&gt;|
|java.util.Map|&lt;map&gt;|&lt;key&gt;&lt;value&gt;, &lt;value&gt;|
|java.util.Properties|&lt;props&gt;|&lt;prop key="key"&gt;value&lt;/prop&gt;|
  
* * *
  
# Class 05. 어노테이션 기반 설정

### 5.1 어노테이션 설정 기초

☛ 어노테이션 설정을 추가하려면 스프링 설정 파일의 &lt;beans&gt;에 Context 관련 네임스페이스와 스키마 문서의 위치를 등록해야 함  
☛ [Namespaces] 탭으로 추가하거나 &lt;beans&gt; 안에 밑의 코드 작성  
```
xmls:context="http://www.springframework.org/schema/context"
xsi:schemaLocation에 "http://www.springframework.org/schema/context"와 "http://www.springframework.org/schema/context/spring-context-4.2.xsd" 추가
```
  
#### component-scan 설정
: application에서 사용할 객체들을 &lt;bean&gt; 등록하지 않고 자동으로 생성하기 위해 정의하는 엘리먼트  
```
<context:component-scan base-package="패키지_이름"></context:component-scan>
```
  
#### @Component
☛ 클래스 선언부 위에 어노테이션 설정  
☛ XML에서 &lt;bean&gt;으로 설정하든, 어노테이션을 사용하든 그 클래스에 기본 생성자가 있어야 함  
☛ client가 스프링 컨테이너가 생성한 객체를 요청하려면 id를 꼭 설정해줘야 함  
```java
//XML
<bean id="tv" class="polymorphism.LgTV"></bean>

// Annotation
@Component("tv")
```

### 5.2 의존성 주입 설정 

#### 의존성 주입 어노테이션
|Annotation|Explanation|
|----------|-----------|
|@Autowired|주로 변수 위에 설정하여 해당 타입의 객체를 찾아 자동으로 할당 </br> org.springframework.beans.factory.annotation.Autowired|
|@Qualifier|특정 객체의 이름을 이용하여 의존성 주입할 때 사용 </br> org.springframework.beans.factory.annotation.Qualifier|
|@Inject|@Autowired와 동일한 기능 제공 </br> javax.annotation.Resource|
|@Resource|@Autowired와 @Qualifier의 기능을 결합한 어노테이션 </br> javax.inject.Inject|

#### @Autowired
☛ 생성자나 메서드, 멤버변수 위에 모두 사용 가능 (대부분 멤버변수 위에 선언)  
☛ 스프링 컨테이너는 해당 멤버 변수의 타입을 체크하고, 그 타입의 객체가 메모리에 존재하는지 확인 후 그 객체를 변수에 주입  
☛ 만약 어노테이션이 붙지 않았다면 **NoSuchBeanDefinitionException** 발생  

#### @Qualifier
❗️ 의존성 주입 대상이 되는 같은 타입의 객체가 두 개 이상일 때 문제 발생  

<img src="https://user-images.githubusercontent.com/56003992/151109853-aecfb66e-8baa-49f7-af7e-b13a7dcf92f0.jpeg" width=400 height=210>
  
☛ 이런 문제를 해결하기 위해 Qualifier 사용 (객체의 이름을 이용하여 의존성 주입)  
```java
// 두 스피커 중 AppleSpeaker 지정
@Autowired
@Qualifier("apple")
private Speaker speaker
```

#### @Resource
☛ name 속성을 사용하여 스프링 컨테이너가 해당 이름으로 생성된 객체 검색 및 의존성 주입 처리  
☛ **@Inject** 어노테이션 또한 이름을 기반으로 의존성 주입 처리
```java
@Resource(name="apple")
private Speaker speaker
```
  
### 5.3 추가 어노테이션
|Annotation|Which|Mean|
|----------|-----|----|
|@Service|XXXServiceImpl|비즈니스 로직을 처리하는 Service 클래스|
|@Repository|XXXDAO|DB 연동을 처리하는 DAO 클래스|
|@Controller|XXXController|User Request를 제어하는 Controller 클래스|
  
* * *
  
# Class 06. 비즈니스 컴포넌트 실습 1
  
☛ 프로젝트에서 사용하는 구조로 Business Component를 구현한 후, 스프링의 Dpendency Lookup과 Dependency Injection을 점검하는 챕터
  
### 6.1 BoardService 컴포넌트 구조
<img src="https://user-images.githubusercontent.com/56003992/151112968-84e6e6f5-a8b0-449e-b79a-1a498913387f.jpeg" width=512 height=220>
    
➣ [실습 예제](https://github.com/khsexk/Spring-Boot_Study/tree/main/SPING%20QUICK%20START/Day_1/BoardService)  
: H2 DB가 아닌 MySQL을 사용하여 구현 (DAO 파트가 책의 예제와 다름)
  
* * *
  
# Class 07. 비즈니스 컴포넌트 실습 2
  
❖ 실습 과정: 어노테이션이 아닌 Setter 인젝션으로 의존성 주입 처리 후 어노테이션을 변경
  
**UserService 컴포넌트 구조**  
<img src="https://user-images.githubusercontent.com/56003992/151377246-b132cf65-35b2-4f07-90d3-49f119df2724.jpeg" width=400 height=170>. 
  
  
  


* * *
