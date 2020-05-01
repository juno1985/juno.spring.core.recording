package juno.spring.recoding.beans.factory.xml;

import juno.spring.recoding.core.io.Juno_Resource;

public class Juno_XmlBeanFactory {
	
	private final Juno_XmlBeanDefinitionReader reader = new Juno_XmlBeanDefinitionReader(this);
	
	public Juno_XmlBeanFactory(Juno_Resource resource) {
		this(resource, null);
	}

	public Juno_XmlBeanFactory(Juno_Resource resource, Object object) {
		
	}

}
