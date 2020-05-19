package app.funtional;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import juno.app.root.Person;
import juno.app.root.TestBean;
import juno.spring.recoding.beans.factory.support.Juno_BeanDefinitionRegistry;
import juno.spring.recoding.beans.factory.xml.Juno_DelegatingEntityResolver;
import juno.spring.recoding.beans.factory.xml.Juno_XmlBeanDefinitionReader;
import juno.spring.recoding.beans.factory.xml.Juno_XmlBeanFactory;
import juno.spring.recoding.core.io.Juno_ClassPathResource;
import juno.spring.recoding.core.io.Juno_Resource;
import juno.spring.recoding.util.Juno_SimpleSaxErrorHandler;

public class FunctionalTest {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void testGetResolverEntity() throws SAXException, IOException {
		Juno_DelegatingEntityResolver entityResolver = new Juno_DelegatingEntityResolver();
		
		entityResolver.resolveEntity(null, null);
	}

	@Test
	public void testValidateXML() throws Exception {
		Juno_Resource resource = new Juno_ClassPathResource("beans.xml");

		InputStream inputStream = resource.getInputStream();

		InputSource inputSource = new InputSource(inputStream);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		/*
		 * beans.xml不包含DOCTYPE,使用XSD验证而非DTD 强制检查xml中的namespace
		 */
		factory.setNamespaceAware(true);

		try {
			factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");
		} catch (IllegalArgumentException e) {
			ParserConfigurationException pcex = new ParserConfigurationException(
					"Unable to validate using XSD: Your JAXP provider [" + factory
							+ "] does not support XML Schema. Are you running on Java 1.4 with Apache Crimson? "
							+ "Upgrade to Apache Xerces (or Java 1.5) for full XSD support.");
			pcex.initCause(e);
			throw pcex;
		}

		logger.trace("Using JAXP provider [" + factory.getClass().getName() + "]");

		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		
		docBuilder.setEntityResolver(new Juno_DelegatingEntityResolver());
		
		ErrorHandler eh = new Juno_SimpleSaxErrorHandler(logger);
		
		docBuilder.setErrorHandler(eh);
		
		Document doc = docBuilder.parse(inputSource);
		Element root = doc.getDocumentElement();

		NodeList nl = root.getChildNodes();
		for(int i = 0; i< nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node instanceof Element) {
				Element ele = (Element) node;
				System.out.println(ele.getTagName());
				System.out.println(ele.getAttribute("id"));
			}
		}
	}
	

	@Test
	public void testReflectOfConstuctorAccessible() throws NoSuchMethodException, SecurityException {
		Constructor<?> ctor = TestBean.class.getDeclaredConstructor();
		
		System.out.println(ctor.getName());
		System.out.println(ctor.getModifiers());
		System.out.println(Modifier.isPublic(ctor.getModifiers()));
		System.out.println(ctor.isAccessible());
		ctor.setAccessible(true);
		System.out.println(ctor.isAccessible());
	}
	
	@Test
	public void testReflectOfConstructorParameterNum() {
		Constructor<?>[] ctors = TestBean.class.getConstructors();
		for(Constructor c : ctors) {
			Class<?>[] parameterTypes = c.getParameterTypes();
			System.out.println(parameterTypes.length);
			for(Class clzz : parameterTypes)
				System.out.println(clzz.getName());
			System.out.println("------------------------");
		}
	}
	
	@Test
	public void testGetObjectHashCode() {
		Person p = new Person();
		System.out.println(p);
		System.out.println(p.hashCode());
		System.out.println(System.identityHashCode(p));
		System.out.println(Integer.toHexString(System.identityHashCode(p)));
	}

}