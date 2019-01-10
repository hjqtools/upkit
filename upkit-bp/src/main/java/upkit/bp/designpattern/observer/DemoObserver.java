package upkit.bp.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

/**
 *  观察者
 * 
 * @author melody
 */
public class DemoObserver implements Observer {

	private  String observerName;
	
	@Override
	public void update(Observable o, Object obj) {
		System.out.println("当前观察者为："+observerName+"当前一共有 " + o.countObservers()+ "个观察者");
	}

	public String getObserverName() {
		return observerName;
	}


	public void setObserverName(String observerName) {
		this.observerName = observerName;
	}
	
	

}
