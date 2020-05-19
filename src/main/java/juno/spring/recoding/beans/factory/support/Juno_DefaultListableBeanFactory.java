package juno.spring.recoding.beans.factory.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Juno_DefaultListableBeanFactory implements Juno_BeanDefinitionRegistry {

	/** Map of bean definition objects, keyed by bean name. */
	public final static Map<String, Juno_BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, Juno_BeanDefinition>(256);
}
