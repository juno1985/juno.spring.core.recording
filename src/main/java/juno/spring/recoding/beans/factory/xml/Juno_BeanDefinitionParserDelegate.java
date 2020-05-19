package juno.spring.recoding.beans.factory.xml;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import juno.spring.recoding.beans.factory.config.Juno_BeanDefinitionHolder;
import juno.spring.recoding.beans.factory.support.Juno_BeanDefinition;
import juno.spring.recoding.util.Juno_StringUtils;

public class Juno_BeanDefinitionParserDelegate {
	
	public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
	public static final String ID_ATTRIBUTE = "id";
	public static final String CLASS_ATTRIBUTE = "class";
	//解析xml的时候存储bean name用来判断bean name的唯一性
	private final Set<String> userNames = new HashSet<String>();
	
	public boolean isDefaultNamespace(Node node) {
		return isDefaultNamespace(node.getNamespaceURI());
	}

	private boolean isDefaultNamespace(String namespaceURI) {

		//没有指定namespace或者和BEANS_NAMESPACE_URI完全相同
		return (!Juno_StringUtils.hasLength(namespaceURI) || BEANS_NAMESPACE_URI.equals(namespaceURI));
	}

	public Juno_BeanDefinitionHolder parseBeanDefinitionElement(Element ele) {
		
		String id = ele.getAttribute(ID_ATTRIBUTE);
		
		String beanName = id;
		
		//todo 需要判断bean name的唯一性
		//checkNameUniqueness(beanName, ele);
		
		
		Juno_BeanDefinition beanDefinition = parseBeanDefinitionElement(ele, beanName);
		
		return new Juno_BeanDefinitionHolder(beanDefinition, beanName);
	}

	private Juno_BeanDefinition parseBeanDefinitionElement(Element ele,
			String beanName) {
		
		String className = null;
		if(ele.hasAttribute(CLASS_ATTRIBUTE)) {
			className = ele.getAttribute(CLASS_ATTRIBUTE).trim();
		}
		
		Juno_BeanDefinition bd = new Juno_BeanDefinition();
		if(className != null) {
			bd.setBeanClassName(className);
		}
		//Todo 解析其他属性
		//parseBeanDefinitionAttributes(ele, beanName, containingBean, bd);
		
		return bd;
	}

	public boolean nodeNameEquals(Element ele, String desiredName) {
	
		return desiredName.equals(ele.getNodeName())||desiredName.equals(ele.getLocalName());
	}
	

}
