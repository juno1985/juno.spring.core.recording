package juno.spring.recoding.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import juno.spring.recoding.util.Juno_Assert;
import juno.spring.recoding.util.Juno_ReflectionUtils;

public abstract class BeanUtils {
	
	private static final Map<Class<?>, Object> DEFAULT_TYPE_VALUES;
	
	static {
		Map<Class<?>, Object> values = new HashMap<Class<?>, Object>();
		values.put(boolean.class, false);
		values.put(byte.class, (byte)0);
		values.put(int.class, 0);
		values.put(short.class, (short)0);
		values.put(long.class, (long)0);
		DEFAULT_TYPE_VALUES = Collections.unmodifiableMap(values);
	}
	
	public static <T> T instantiateClass(Class<T> clazz){
		Juno_Assert.notNull(clazz, "Class must not be null");
		if(clazz.isInterface()) {
			throw new RuntimeException("Specified class is an interface");
		}
		
		try {
			return instantiateClass(clazz.getDeclaredConstructor());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Juno_Assert.notNull(ctor, "Constructor must not be null");
		Juno_ReflectionUtils.makeAccessible(ctor);
		
		Class<?>[] parameterTypes = ctor.getParameterTypes();
		Juno_Assert.isTrue(args.length <= parameterTypes.length,  "Can't specify more arguments than constructor parameters");
		
		Object[] argsWithDefaultValues = new Object[args.length];
		for(int i = 0; i<args.length;i++) {
			if(args[i]==null) {
				Class<?> parameterType = parameterTypes[i];
				argsWithDefaultValues[i] = parameterType.isPrimitive() ?  DEFAULT_TYPE_VALUES.get(parameterType) : null;
			}else {
				argsWithDefaultValues[i]=args[i];
			}
				
		}
		
		return ctor.newInstance(argsWithDefaultValues);
	}

}
