package upkit.bp.reentrant.demo01;


/**
 * 亮灯
 * @author melody
 *
 */
public class TurnOn implements Runnable{

	
	private Light light;
	
	public void setLight(Light light) {
		this.light = light;
	}

	@Override
	public void run() {
		if(light != null) {
			light.on();
		}
	}
	
	
}
