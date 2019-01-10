package upkit.bp.reentrant.demo01;

public class Main {

	
	public static void main(String[] args) {
	
		Light light = new Light();
		
		
		TurnOff off = new TurnOff();
		off.setLight(light);
		
		TurnOn on = new TurnOn();
		on.setLight(light);
		
		
		for(int i = 0; i< 100; i++) {
			Thread offThread = new Thread(off);
			Thread onThread = new Thread(on);
			onThread.start();
			offThread.start();
			
		}
		
		
	}
	
	
	
}
