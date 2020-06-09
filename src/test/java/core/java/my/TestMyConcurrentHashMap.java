package core.java.my;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

import core.java.my.concurrenthashmap.MyConcurrentHashMap;

public class TestMyConcurrentHashMap {

	private final Random rand = new Random();

	@Test
	public void testInitialCapacityWithNonPowerOf2() {

		MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>(
				3);
	}

	@Test
	public void testInitTable() {
		MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>();
		conHashMap.initTable();
	}

	@Test
	public void testMultiThreadInitTable() throws InterruptedException {

		final int thread_num = 5;

		CountDownLatch latch = new CountDownLatch(thread_num);
		CyclicBarrier barrier = new CyclicBarrier(thread_num);
		final MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>();

		for (int i = 1; i <= thread_num; i++) {
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

	@Test
	public void testMultiThreadPut() throws InterruptedException {
		final int thread_num = 20;
		CountDownLatch latch = new CountDownLatch(thread_num);
		CyclicBarrier barrier = new CyclicBarrier(thread_num);
		final MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>();
		for (int i = 1; i <= thread_num; i++) {
			Thread t = new Thread(() -> {
				try {
					Integer key = rand.nextInt(5) + 1;
					String val = getRandomStr(3);
					barrier.await();
					conHashMap.put(key, val);
					latch.countDown();
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			});
			t.start();
		}
		latch.await();
	}

	private String getRandomStr(int length) {
		String str = "abcdefghijklmnopqrstuvwxyz1234567890";
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int number = rand.nextInt(36);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
	@Test
	public void testPut() {
		MyConcurrentHashMap<Integer, String> conHashMap = new MyConcurrentHashMap<>();
		conHashMap.put(1, "aaa");
		conHashMap.put(1, "bbb");
		conHashMap.put(2, "ccc");
		conHashMap.put(3, "ccc");
		conHashMap.put(4, "ccc");
		conHashMap.put(5, "ccc");
		conHashMap.put(6, "ccc");
		conHashMap.put(7, "ccc");
	}

}
