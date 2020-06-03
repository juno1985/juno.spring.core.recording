package core.java.my;

import org.junit.Test;

import core.java.my.concurrenthashmap.MyConcurrentHashMap;

public class TestMyConcurrentHashMap {

	@Test
	public void testInitialCapacityWithNonPowerOf2() {
		
		MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>(3);
	}

}
