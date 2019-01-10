package upkit.bp.reentrant.demo01;

/**
 * 关灯
 * 
 * @author melody
 *
 */
public class TurnOff implements Runnable {

	private Light light;

	public void setLight(Light light) {
		this.light = light;
	}

	@Override
	public void run() {
		if (light != null) {
			light.off();
		}
	}

}
