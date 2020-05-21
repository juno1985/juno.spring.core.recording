package juno.spring.recoding.beans.factory.xml;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import juno.spring.recoding.beans.BeanUtils.Juno_BeanUtils;
import juno.spring.recoding.beans.factory.support.Juno_BeanDefinition;
import juno.spring.recoding.beans.factory.support.Juno_BeanDefinitionRegistry;
import juno.spring.recoding.core.io.Juno_Resource;

public class Juno_XmlBeanFactory {
	
	private final Juno_XmlBeanDefinitionReader reader = new Juno_XmlBeanDefinitionReader(this);
	
	public Juno_XmlBeanFactory(Juno_Resource resource) throws Exception {
		this(resource, null);
	}

	public Juno_XmlBeanFactory(Juno_Resource resource, Object object) throws Exception {
		this.reader.loadBeanDefinitions(resource);
	}
	
	/**
	 * For JUNIT Testing
	 */
	
	public Juno_XmlBeanDefinitionReader getReader() {
		return this.reader;
	}
	
	
	public Object getBean(String beanName) {
		
		return doGetBean(beanName);
	}

	private Object doGetBean(String beanName) {
		Juno_BeanDefinition jdb = Juno_BeanDefinitionRegistry.beanDefinitionMap.get(beanName);
		//Todo 需要考虑单例的情况创建bean,直接从ConcurrentHashMap中取
		Class<?> resolvedClass =  jdb.getBeanClass();
		//Todo 这里需要考虑带参构造函数创建bean
		try {
			Object beanInstance = doCreateBean(beanName, jdb);
			return beanInstance;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	private Object doCreateBean(String beanName, Juno_BeanDefinition jdb) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//Todo Spring源码中还考虑是否通过代理创建bean
		//org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(RootBeanDefinition, String, BeanFactory)
		
		//Todo 还有通过工厂创建bean的情况
		
		Class<?> clazz = jdb.getBeanClass();
		
		return Juno_BeanUtils.instantiateClass(clazz);
	}

}
