package juno.spring.recoding.beans.factory.config;

import juno.spring.recoding.beans.factory.support.Juno_BeanDefinition;
import juno.spring.recoding.util.Juno_Assert;

public class Juno_BeanDefinitionHolder {

	private final Juno_BeanDefinition beanDefinition;
	
	private final String beanName;

	public Juno_BeanDefinitionHolder(Juno_BeanDefinition beanDefinition,
			String beanName) {
		Juno_Assert.notNull(beanDefinition, "Bean Definition must not be null");
		Juno_Assert.notNull(beanName, "Bean name must not be null");
		this.beanDefinition = beanDefinition;
		this.beanName = beanName;
	}
	
	
}
