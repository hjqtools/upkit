package upkit.bp.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 需要让disruptor为我们创建事件，我们同时还声明了 一个EventFactory来实例化Event对象。
 * 
 * @author melody
 *
 */
public class LongEventFactory implements EventFactory<LongEvent> {

	@Override
	public LongEvent newInstance() {
		return null;
	}

}
