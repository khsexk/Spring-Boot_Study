# 1장. 오브젝트와 의존관계 - PART 2
</br>

> ⭐︎
> ## V. 스프링의 IoC
</br>

✎ **Bean**: 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트   
✎ **스프링에 대해**
- application 개발의 다양한 영역과 기술에 관여 및 많은 기능 제공
- Bean Factory 또는 application context

### 1.5.1 오브젝트 팩토리를 이용한 스프링 IoC

***애플리케이션 컨텍스트와 설정정보***
- application context: IoC 방식을 따라 만들어진 일종의 bean factory
  - 빈 팩토리 ➣ 빈을 생성하고 관계를 설정하는 IoC의 기본 기능에 초점을 맞춘 것
  - 애플리케이션 컨텍스트 ➣ 애플리케이션 전반에 걸쳐 모든 구성요소의 제어 작업을 담당하는 IoC 엔진이라는 의미 부각
<img width="498" alt="설계도" src="https://user-images.githubusercontent.com/56003992/150659463-16f2fdab-6c07-4676-a29f-e45cc1a4d560.png">
- 위의 설계도는 애플리케이션 컨텍스트와 설정정보를 나타냄  

***DaoFactory를 사용하는 애플리케이션 컨텍스트***
- @Configuration : 애플리케이션 컨텍스트 또는 빈 팩토리 사용할 설정정보를 담당하는 클래스라고 표시하는 애너테이션
- @Bean : 오브젝트 생성을 담당하는 IoC용 메서드라고 표시하는 애너테이션
☛ **두 애너테이션만으로 스프링 프레임워크의 빈 팩토리 또는 애플리케이션 컨텍스트가 IoC 방식의 기능을 제공할 때 사용할 완벽한 설정정보 구성**

- AnnotationConfigApplicationContext: @Configuration이 붙으 자바 코드르 설정정보로 사용하기 위한 클래스
- getBean() : ApplicationContext가 관리하는 오브젝트를 요청하는 메서드
```java
public class UserDaoTest {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
    UserDao dao = context.getBean("userDao", UserDao.class);
    ... 
  }
}
```

### 1.5.2 애플리케이셔 컨텍스트의 동작방식


* * *
</br>  

> ⭐︎
> ## VI. 싱글톤 레지스트리와 오브젝트 스코프
</br>

✎ **IoC**: Inversion of Control   

* * *
</br>  

> ⭐︎
> ## VII. 의존관계 주입 (DI)
</br>

✎ **IoC**: Inversion of Control   

* * *
</br>  

> ⭐︎
> ## VIII. XML을 이용한 설정
</br>

✎ **IoC**: Inversion of Control   

* * *
</br>  
