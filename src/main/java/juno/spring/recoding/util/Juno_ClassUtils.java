package juno.spring.recoding.util;

public abstract class Juno_ClassUtils {

	public static ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		}
		/**
		 * 	并不是捕获Exception,Throwable是Exception和Error的父类
		 *  Exception是程序逻辑抛出的
		 *  Error是JVM抛出的，所以证明在获得类的加载器时可能有来自JVM的错误
		 */
		catch(Throwable ex) {
			
		}
		if(cl == null) {
			//线程上下文类加载器获得失败，尝试获得该类的类加载器
			cl = Juno_ClassUtils.class.getClassLoader();
			if(cl == null) {
				//尝试获得bootstap类加载器，即JVM核心类加载器
				try {
					cl = ClassLoader.getSystemClassLoader();
				}
				catch(Throwable ex) {
					//cannot access bootstrap classloader, maybe the caller can live with null...
				}
			}
		}
		return cl;
	}
}
