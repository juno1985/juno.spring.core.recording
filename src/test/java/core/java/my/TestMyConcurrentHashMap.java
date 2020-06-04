package core.java.my;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

import core.java.my.concurrenthashmap.MyConcurrentHashMap;

public class TestMyConcurrentHashMap {

	@Test
	public void testInitialCapacityWithNonPowerOf2() {
		
		MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>(3);
	}

	@Test
	public void testInitTable() {
		MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>();
		conHashMap.initTable();
	}
	
	@Test
	public void testMultiThreadInitTable() throws InterruptedException {
		
		final int thread_num = 2;
		
		CountDownLatch latch = new CountDownLatch(thread_num); 
		CyclicBarrier barrier = new CyclicBarrier(thread_num);
		final MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>();
		
		for(int i = 1; i<= thread_num; i++) {
			Thread t = new Thread(() -> {
				
				try {
					barrier.await();
					conHashMap.initTable();
					latch.countDown();
				} catch (InterruptedException | BrokenBarrierException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			t.start();
		}
		
		latch.await();
		
	}
	
	
}
