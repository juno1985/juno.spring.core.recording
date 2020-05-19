package juno.spring.recoding.beans.factory.support;

public class Juno_BeanDefinition {
	
	private volatile Object beanClass;

	public void setBeanClassName(String beanClassName) {
		this.beanClass = beanClassName;
	}
}
