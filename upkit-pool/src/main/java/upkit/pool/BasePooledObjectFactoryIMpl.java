package upkit.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePooledObjectFactoryIMpl extends BasePooledObjectFactory{

	private static final Logger logger = LoggerFactory.getLogger(BasePooledObjectFactoryIMpl.class);
	
	@Override
	public Object create() throws Exception {
		return null;
	}

	@Override
	public PooledObject wrap(Object obj) {
		return null;
	}

}
