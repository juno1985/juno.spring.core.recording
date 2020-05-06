package juno.spring.recoding.beans.factory.xml;

import juno.spring.recoding.beans.factory.support.Juno_DefaultListableBeanFactory;
import juno.spring.recoding.core.io.Juno_Resource;

public class Juno_XmlBeanFactory extends Juno_DefaultListableBeanFactory{
	
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

}
