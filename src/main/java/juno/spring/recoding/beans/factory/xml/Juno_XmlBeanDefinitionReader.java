package juno.spring.recoding.beans.factory.xml;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import juno.spring.recoding.beans.factory.support.Juno_BeanDefinitionRegistry;
import juno.spring.recoding.core.io.Juno_Resource;
import juno.spring.recoding.util.Juno_Assert;

public class Juno_XmlBeanDefinitionReader {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private Juno_BeanDefinitionParserDelegate delegate;
	
	private final ThreadLocal<Set<Juno_Resource>> resourcesCurrentlyBeingLoaded =
			new ThreadLocal<Set<Juno_Resource>>();
	
	

	public Juno_XmlBeanDefinitionReader(Juno_BeanDefinitionRegistry registry) {
	}

	public int loadBeanDefinitions(Juno_Resource resource) throws Exception {
		Juno_Assert.notNull(resource, "Resource must not be null");
		
		if(logger.isTraceEnabled()) {
			logger.trace("Loading XML bean definitions from " + resource);
		}
		
		Set<Juno_Resource> currentResources = this.resourcesCurrentlyBeingLoaded.get();
		
		if(currentResources == null) {
			currentResources = new HashSet<Juno_Resource>(4);
			this.resourcesCurrentlyBeingLoaded.set(currentResources);
		}
		
		if(!currentResources.add(resource)) {
			throw new Exception("Detected cyclic loading of " + resource);
		}
		
		try {
			InputStream inputStream = resource.getInputStream();
			try {
				//委托给SAX验证XML
				InputSource inputSource = new InputSource(inputStream);
				
				return doLoadBeanDefinitions(inputSource, resource);
			} finally {
				inputStream.close();
			}
			
			
		} catch (IOException e) {
			throw new Exception("IOException parsing XML document from " + resource, e);
		}
	}
	
	protected int doLoadBeanDefinitions(InputSource inputSource, Juno_Resource resource) throws ParserConfigurationException, SAXException, IOException {
		
		Document doc = doLoadDocument(inputSource, resource);
		if(logger.isTraceEnabled()) {
			logger.trace("Loaded bean definitions from " + resource);
		}
	
		int count = registerBeanDefinitions(doc, resource);
		
		//TODO.. 返回已经注册bean的个数
		return -1;
	}

	private int registerBeanDefinitions(Document doc, Juno_Resource resource) {
		Element root  = doc.getDocumentElement();
		
		this.delegate = new Juno_BeanDefinitionParserDelegate(); 
		
		parseBeanDefinitions(root, this.delegate);
		
		return 0;
	}

	private void parseBeanDefinitions(Element root,
			Juno_BeanDefinitionParserDelegate delegate) {
		if(this.delegate.isDefaultNamespace(root)) {
			NodeList nl = root.getChildNodes();
			for(int i = 0; i< nl.getLength(); i++) {
				Node node = nl.item(i);
				if(node instanceof Element) {
					Element ele = (Element) node;
					if(this.delegate.isDefaultNamespace(ele)) {
						parseDefaultElement(ele, delegate);
					}
				}
			}
		}
		
	}

	public Document doLoadDocument(InputSource inputSource, Juno_Resource resource) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		factory.setValidating(true);
		/*
		 * beans.xml不包含DOCTYPE,使用XSD验证而非DTD
		    *      强制检查xml中的namespace
		 */
		factory.setNamespaceAware(true);
		
		try {
			factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
		} catch (IllegalArgumentException e) {
			ParserConfigurationException pcex = new ParserConfigurationException(
					"Unable to validate using XSD: Your JAXP provider [" + factory +
					"] does not support XML Schema. Are you running on Java 1.4 with Apache Crimson? " +
					"Upgrade to Apache Xerces (or Java 1.5) for full XSD support.");
			pcex.initCause(e);
			throw pcex;
		}
		
		logger.trace("Using JAXP provider [" + factory.getClass().getName() + "]");
		
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		
		docBuilder.setEntityResolver(new Juno_DelegatingEntityResolver());
		
		//org.springframework.beans.factory.xml.XmlBeanDefinitionReader.doLoadBeanDefinitions(InputSource, Resource)
		Document doc = docBuilder.parse(inputSource);
		
		return doc;
		
	}
}
