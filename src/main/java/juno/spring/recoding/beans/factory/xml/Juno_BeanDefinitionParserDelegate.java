package juno.spring.recoding.beans.factory.xml;

import org.w3c.dom.Node;

import juno.spring.recoding.util.Juno_StringUtils;

public class Juno_BeanDefinitionParserDelegate {
	
	public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
	
	public boolean isDefaultNamespace(Node node) {
		return isDefaultNamespace(node.getNamespaceURI());
	}

	private boolean isDefaultNamespace(String namespaceURI) {

		//没有指定namespace或者和BEANS_NAMESPACE_URI完全相同
		return (!Juno_StringUtils.hasLength(namespaceURI) || BEANS_NAMESPACE_URI.equals(namespaceURI));
	}

}
