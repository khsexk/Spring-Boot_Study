# Class 01. 스프링 AOP
  
✎ 비즈니스 컴포턴트 개발에서 가장 중요한 두 가지 원칙: 낮은 결합도와 높은 응집도 유지  
✎ IoC → 낮은 결합도 유지  
✎ AOP → 높은 응집도 유지  

### 1.1 AOP 이해하기
  
❖ AOP(Aspect Oriented Programming): 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 것 (관점 지향 프로그래밍)  
  
- AOP의 핵심 개념: **관심 분리**(Separation of Concerns)
  - 횡단 관심: 메서드마다 공통으로 등장하는 로깅이나 예외, 트랜잭션 처리 등의 코드
  - 핵심 관심: User Request에 따라 실제로 수행되는 핵심 비즈니스 로직
<img src="https://user-images.githubusercontent.com/56003992/152282250-42ab9393-6e8b-4a30-aa23-a346a17d74f0.png" width=512 height=200>  

❖ OOP에서 횡단 관심에 해당하는 공통 코드를 완벽하게 독립적인 모듈로 분리하기 힘든 이유
: 클래스로 분리시키면 해당 클래스들의 결합도가 높아짐  

### 1.2 AOP 시작하기

☛ pom.xml에 AOP 관렬 라이브러리 추가  
```xml
<!-- AspectJ -->
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjrt</artifactId>
  <version>${aspectj.version}</version>
</dependency>
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjweaver</artifactId>
  <version>1.9.7</version>
</dependency> 
```  
☛ [applicationContext.xml] → [Namespaces] 에서 aop 네임스페이스 추가  
☛ LogAdvice 클래스를 교체하고 싶으면 applicationContext.xml만 수정  
  
* * *

# Class 02. AOP 용어 및 기본 설정

### 2.1 AOP 용어 정리
  
#### ❖ JoinPoint
: Client가 호출되는 모든 비즈니스 메서드  
→ 실습에선 BoardServiceImpl, UserServiceImpl  
  
#### ❖ Pointcut  
: 필터링된 JoinPoint
→ 트랜잭션을 처리하는 공통 기능을 만들었다고 가정했을 때, 횡단 관심 기능은 CRUD 기능의 메서드에 대해서는 당연히 동작해야 하지만, 검색 기능은 메서드에 대해서는 동작할 필요가 없음  
→ <aop:pointcut id="id" expression="필터링에 대한 설정" />  
<img src="https://user-images.githubusercontent.com/56003992/152288685-d3e316f6-6738-4b59-b0a3-097f35fc77cf.png" width=256 height=100>  
- 상단은 리턴타입과 매개변수를 무시하고 com.muticampus.biz 패키지로 시작하는 클래스 중 Impl로 끝나는 클래스으 모든 메서드를 포인트컷으로 설정
- 하단은 상단과 같지만 get으로 시작하는 메서드만 포인트컷으로 설정
  
#### ❖ Advice  
: 횡당 관심에 해당하는 공통 기능의 코드  
→ 독립된 클래스의 메서드로 작성되며, 스프링 설정파일에서 동작 시점 지정  
→ <aop:[before | after | after-returning | after-throwing | around] pointcut-ref="id" method="메서드" />  
  
#### ❖ Weaving
: Pointcut으로 지정된 핵심 관심 메서드가 호출될 때, Advice에 해당하는 횡단 관심 메서드가 삽입되는 과정  
→ Weaving을 통해 비즈니스 메서드를 수정하지 않고도 횡단 관심에 해당하는 기능을 추가하거나 변경할 수 있음  
→ 처리방식: 컴파일타임 위빙, 로딩타임 위빙, 런타임 위빙 (스프링에서는 런타임 위빙 방식만 지원)  
  
#### ⭐️ 그림을 통한 정리
<img src="https://user-images.githubusercontent.com/56003992/152290679-4d0a0c0b-972e-4a5a-9850-2e84f89183d7.png" width=512 height=170>  
  
  
### 2.2 AOP 엘리먼트
  
☛ AOP 관련 설정은 XML 방식과 annotation 방식이 있지만, XML부터 알아보자.  
  
#### ❖ <aop:config> 엘리먼트
: AOP 설정에서 루트 엘리먼트  

#### ❖ <aop:pointcut> 엘리먼트
: Pointcut 지정을 위해 사용
: <aop:config>나 <aop:aspect>의 자식 엘리먼트로 사용  
: but <aop:aspect> 하위에 설정된 Pointcut은 해당 <aop:aspect>에서만 사용 가능  

#### ❖ <aop:aspect> 엘리먼트 
: 핵심 관심에 해당하는 Pointcut 메서드와 횡단 관심에 해당하는 Advise 메서드를 결합하기 위해 사용  
: aspect를 어떻게 설정하느냐에 따라 위빙 결과가 달라짐  
```XML
<aop:aspect ref="log">
    <aop:before pointcut-ref="getPintcut" method="printLog" />
</aop:aspect>
```  

#### ❖ <aop:advisor> 엘리먼트
: aspect와 같은 기능 수행  
: but, 트랜잭션 설정과 같은 몇몇 특수한 경우는 무조건 Advisor를 사용해야 함  
→ **Advise 객체의 id를 모르거나 메서드 이름을 확인할 수 없을 때는 aspect를 사용할 수 없다❗️**  

### 2.3 포인트컷 표현식
: expression="execution(①리턴타입 ②패키지경로. .③클래스명.④메서드명(⑤매개변수))"  
  
#### ① 리턴타입 지정
|표현식|설명|
|----|---|
|&#42;|모든 리턴타입 허용|
|void|리턴타입이 void인 메서드 선택|
|!void|리턴타입이 void가 아닌 메서드 선택|
  
#### ② 패키지 지정
|표현식|설명|
|----|---|
|com.springbook.biz|정확하게 com.springbook.biz 패키지만 선택|
|com.springbook.biz..|com.springbook.biz 패키지로 시작하는 모든 패키지 선택|
|com.springbook..impl|com.springbook 패키지로 시작하면서 마지막 패키지 이름이 impl로 끝나는 패키지 선택|
  
#### ③ 클래스 지정
|표현식|설명|
|----|---|
|BoardServiceImpl|정확하게 BoardServiceImpl 클래스만 선택|
|&#42;Impl|Impl로 끝나는 클래스 선택|
|BoardService+|클래스 이름 뒤에 +가 붙으면 해당 클래스로부터 파생된 모든 자식 클래스 선택 </br> 인터페이스 뒤에 +가 붙으면 해당 인터페이스를 구현한 모든 클래스 선택|
  
#### ④ 메서드 지정
|표현식|설명|
|----|---|
|&#42;(..)|가장 기본 설정으로 모든 메서드 선택|
|get&#42;(..)|메서드 이름이 get으로 시작하는 모든 메서드 선택|
  
#### ⑤ 매개변수 지정
|표현식|설명|
|----|---|
|(..)|..은 매개변수 개수와 타입에 제약이 없음|
|(&#42;)|반드시 1개의 매개변수를 가진 메서드만 선택|
|(com.springbook.user.UserVO)|매개변수로 UserVo를 가지는 메서드만 선택|
|(!com.springbook.user.UserVO)|매개변수로 UserVo를 가지지 않는 메서드만 선택|
|(Integer, ..)|한 개 이상의 매개변수를 가지되, 첫 번째 매개변수의 타입이 Integer인 메서드만 선택|
|(Integer, &#42;)|두 개 이상의 매개변수를 가지되, 첫 번째 매개변수의 타입이 Integer인 메서드만 선택|
  
* * *

# Class 03. 어드바이스 동작 시점

### 3.1 Before 어드바이스
  

  
* * *

# Class 04. JoinPoint와 바인드 변수

### 4.1 JoinPoint 메서드
  
