package polymorphism;

public class SamsungTV implements TV{
	public SamsungTV() {
		System.out.println("===> SamsungTV 객체 생성");
	}
	public void powerOn() {
		System.out.println("Samsung TV --- 전원 켠다");
	}
	public void powerOff() {
		System.out.println("Samsung TV --- 전원 끈다");
	}
	public void volumeUp() {
		System.out.println("Samsung TV --- 소리 올린다");
	}
	public void volumeDown() {
		System.out.println("Samsung TV --- 소리 내린다");
	}
}
