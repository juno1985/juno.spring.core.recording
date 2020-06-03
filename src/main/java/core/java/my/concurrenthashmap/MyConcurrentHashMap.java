package core.java.my.concurrenthashmap;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyConcurrentHashMap<K, V> {
	
	static Log logger = LogFactory.getLog(MyConcurrentHashMap.class);
	
	private static final float LOAD_FACTOR = 0.75f;
	
	private volatile AtomicInteger sizeCtl = new AtomicInteger();
	
	public MyConcurrentHashMap() {
		
	}


	public MyConcurrentHashMap(int n) {
		int cap = tableSize(n);
		this.sizeCtl.set(cap);
	}


	private static final int tableSize(int n) {
		int cap = (int) (n/LOAD_FACTOR) + 1;
		//计算二进制前面0的个数
		int lead_zero_bits = Integer.numberOfLeadingZeros(cap);
		cap = (-1 >>> lead_zero_bits) + 1;
		logger.info("调整容量: " + n + " --> " + cap);
		return cap;
	}

}
