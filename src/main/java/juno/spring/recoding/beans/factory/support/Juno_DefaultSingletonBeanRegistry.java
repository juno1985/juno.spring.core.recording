package juno.spring.recoding.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Juno_DefaultSingletonBeanRegistry {
	
	public final static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

}
