package juno.spring.recoding.util;

public abstract class Juno_Assert {

	public static void notNull(Object obj, String message) {
		if(obj == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isTrue(boolean expression, String message) {
		if(!expression) {
			throw new IllegalArgumentException(message);
		}
	}
}
