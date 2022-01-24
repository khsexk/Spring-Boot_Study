package polymorphism;

public class TVUser {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//interface를 이용하여 생
		//TV tv = new SamsungTV();
		
		BeanFactory factory = new BeanFactory();
		TV tv = (TV)factory.getBean(args[0]);
		
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
	}

}
