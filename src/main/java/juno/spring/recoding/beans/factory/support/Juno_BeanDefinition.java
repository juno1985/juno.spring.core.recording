package juno.spring.recoding.beans.factory.support;

import juno.spring.recoding.beans.factory.config.Juno_ConstructorArgumentValues;

public class Juno_BeanDefinition {
	
	private volatile Class<?> beanClass;
	
	private Juno_ConstructorArgumentValues constructorArgumentValues;

	public Juno_BeanDefinition() {
		this.constructorArgumentValues = new Juno_ConstructorArgumentValues();
		
		
	}

	public Juno_ConstructorArgumentValues getConstructorArgumentValues() {
		return constructorArgumentValues;
	}

	public void setConstructorArgumentValues(
			Juno_ConstructorArgumentValues constructorArgumentValues) {
		this.constructorArgumentValues = constructorArgumentValues;
	}

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
