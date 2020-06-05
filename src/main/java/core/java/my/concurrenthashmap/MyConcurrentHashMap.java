package core.java.my.concurrenthashmap;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyConcurrentHashMap<K, V> {

	static Log logger = LogFactory.getLog(MyConcurrentHashMap.class);

	private static final float LOAD_FACTOR = 0.75f;

	/**
	 * sizeCtl ：默认为0，用来控制table的初始化和扩容操作，具体应用在后续会体现出来。 -1 代表table正在初始化 -N
	 * 表示有N-1个线程正在进行扩容操作 其余情况： 1、如果table未初始化，表示table需要初始化的大小。
	 * 2、如果table初始化完成，表示table的容量，默认是table大小的0.75倍，居然用这个公式算0.75（n - (n >>> 2)）。
	 * ———————————————— 版权声明：本文为CSDN博主「猪杂汤饭」的原创文章，遵循CC 4.0
	 * BY-SA版权协议，转载请附上原文出处链接及本声明。
	 * 原文链接：https://blog.csdn.net/programmer_at/java/article/details/79715177
	 */
	private volatile AtomicInteger sizeCtl = new AtomicInteger();

	private static final int DEFAULT_CAPACITY = 16;
	
	static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

//	volatile Node<K, V>[] table;
	
	volatile AtomicReferenceArray<Node<K,V>> table;

	public MyConcurrentHashMap() {

	}

	public MyConcurrentHashMap(int n) {
		int cap = tableSize(n);
		this.sizeCtl.set(cap);
	}

	private static final int tableSize(int n) {
		int cap = (int) (n / LOAD_FACTOR) + 1;
		// 计算二进制前面0的个数
		int lead_zero_bits = Integer.numberOfLeadingZeros(cap);
		cap = (-1 >>> lead_zero_bits) + 1;
		logger.info("调整容量: " + n + " --> " + cap);
		return cap;
	}

	public final AtomicReferenceArray<Node<K, V>> initTable() {
		AtomicReferenceArray<Node<K, V>> tab;
		int sc;

		while ((tab = table) == null || tab.length() == 0) {
			// 如果一个线程发现sizeCtl<0，意味着另外的线程执行CAS操作成功，当前线程只需要让出cpu时间片
			if ((sc = sizeCtl.get()) < 0) {
				logger.info(Thread.currentThread().getName()
						+ " -> 有其他線程正在初始化數組，當前線程yield...");
				Thread.yield();
			}

			else {
				// 上面已经提到了，-1表示table正在初始化
				sizeCtl.set(-1);
				logger.info(
						Thread.currentThread().getName() + " -> 初始化化數組開始...");
				try {
					if ((tab = table) == null || tab.length() == 0) {
						Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[DEFAULT_CAPACITY];
						table = tab = new AtomicReferenceArray<Node<K, V>>(nt);
						sc = DEFAULT_CAPACITY - (DEFAULT_CAPACITY >>> 2);// 0.75*capacity
						logger.info(Thread.currentThread().getName()
								+ " -> 數組初始化完畢，容量: " + DEFAULT_CAPACITY);
					}
				} finally {
					sizeCtl.set(sc);
					logger.info(Thread.currentThread().getName()
							+ " -> 數組初始化後容量控制為75% -> " + sizeCtl.get());
				}
				break;
			}
		}
		return tab;
	}

	public final V put(K key, V value) {
		if (key == null || value == null)
			throw new NullPointerException();
		int hash = spread(key.hashCode());
		for(AtomicReferenceArray<Node<K, V>> tab = table;;) {
			Node<K,V> f; int n, i, fh; K fk; V fv;
			 if (tab == null || (n = tab.length()) == 0)
	                tab = initTable();
			 else if(tab.get(hash & (n-1)) == null) {
				 logger.info(Thread.currentThread().getName() + " -> 找到空buket: " + (hash & (n-1)));
				 Node<K, V> node = new Node<>(hash, key, value);
				 tab.set(hash & (n-1), node);
				 logger.info(Thread.currentThread().getName() + " -> 完成添加进buket: " + (hash & (n-1)));
				 break;
			 }
		}
		return value;
	}

	static final int spread(int h) {
		return (h ^ (h >>> 16)) & HASH_BITS;
	}

	static class Node<K, V> {
		final int hash;
		final K key;
		V val;
		Node<K, V> next;

		Node(int hash, K key, V val) {
			this.hash = hash;
			this.key = key;
			this.val = val;
		}
	}

}
