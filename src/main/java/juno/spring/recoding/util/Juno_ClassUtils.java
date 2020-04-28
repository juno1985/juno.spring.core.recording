package juno.spring.recoding.util;

public abstract class Juno_ClassUtils {
	
	private static final char PACKAGE_SEPARATOR = '.';
	private static final char PATH_SEPARATOR = '/';
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
	
	//获得类的包路径
	public static String classPackageAsResourcePath(Class<?> clazz) {
		
		if(clazz == null) {
			return "";
		}
		
		String className = clazz.getName();
		int packageEndIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
		if(packageEndIndex == -1) {
			return "";
		}
		String packageName = className.substring(0, packageEndIndex);
		return packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
		
	}
}
