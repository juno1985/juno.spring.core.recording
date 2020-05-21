package juno.spring.recoding.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Juno_BeanDefinitionRegistry {
	public final static Map<String, Juno_BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, Juno_BeanDefinition>(256);
	
	 
}
