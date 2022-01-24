package polymorphism;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class TVUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/* 2장. interface를 이용하여 생성
		TV tv = new SamsungTV(); 
		2장. 팩토리 패턴 적용
		BeanFactory factory = new BeanFactory();*/
		
		// 3장
		AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");
		TV tv = (TV)factory.getBean("tv"); 
		
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
		
		factory.close();
	}

}
