package juno.spring.recoding.beans.factory.support;

public class Juno_BeanDefinition {
	
	private volatile Class<?> beanClass;

	public void setBeanClassName(String beanClassName) {
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Class<?> getBeanClass() throws IllegalStateException{
		Object beanClassObject = this.beanClass;
		if(beanClassObject == null) {
			throw new IllegalStateException("No bean class specified on bean definition");
		}
		if(!(beanClassObject instanceof Class)) {
			throw new IllegalStateException("Bean class name ["+beanClassObject+"] has not been resolved into an actual Class");
		}
		return (Class<?>)beanClassObject;
	}
}
