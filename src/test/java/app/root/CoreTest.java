package app.root;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import juno.app.root.Person;
import juno.spring.recoding.beans.factory.xml.Juno_XmlBeanDefinitionReader;
import juno.spring.recoding.beans.factory.xml.Juno_XmlBeanFactory;
import juno.spring.recoding.core.io.Juno_ClassPathResource;
import juno.spring.recoding.core.io.Juno_Resource;
import juno.spring.recoding.util.Juno_Assert;

public class CoreTest {
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedExp = ExpectedException.none(); 

	@Test
	public void testCallPerson() {
		Person p = new Person("lao wang");
		System.out.println(p.getName());
	}
	//Junit測試異常
	@Test
	public void testIllegalArgumentException() {
		expectedExp.expect(IllegalArgumentException.class);
		expectedExp.expectMessage("\"Path must not be null\"");
		Juno_Assert.notNull(null, "\"Path must not be null\"");
		
	}
	
	@Test
	public void testLogJ() {
		Log logger = LogFactory.getLog(getClass());
		logger.trace("Test Log4J can print to console");
	}
	
	@Test
	public void testSimpleLoad() throws Exception {
		Juno_XmlBeanFactory bf = new Juno_XmlBeanFactory(new Juno_ClassPathResource("beans.xml"));
		Person person = (Person) bf.getBean("person");
		assertEquals("laowang", person.getName());
	}
	
	@Test
	public void testCreateIntanceWithConsArgs() throws Exception {
		Juno_XmlBeanFactory bf = new Juno_XmlBeanFactory(new Juno_ClassPathResource("beans.xml"));
		Person person = (Person) bf.getBean("person_ctor_args");
		assertEquals("Li Ning", person.getName());
	}
	
	@Test
	public void testCreateInstanceWithMultiArgs() throws Exception {
		Juno_XmlBeanFactory bf = new Juno_XmlBeanFactory(new Juno_ClassPathResource("beans.xml"));
		Person person = (Person)bf.getBean("person_ctor_multi_args");
		assertEquals("Wang Feng", person.getName());
		assertEquals(32, person.getAge());
	}
	
	
	@Test
	public void testJuno_XmlBeanDefinitionReader_doLoadDocument() throws ParserConfigurationException, SAXException, IOException {
		
		Juno_XmlBeanDefinitionReader reader = new Juno_XmlBeanDefinitionReader(null);
		Juno_Resource resource = new Juno_ClassPathResource("beans.xml");
		Document doc = reader.doLoadDocument(new InputSource(resource.getInputStream()), resource);
		
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
	public void testCreateSinglonInstanceInCache() throws Exception {
		final Juno_XmlBeanFactory bf = new Juno_XmlBeanFactory(new Juno_ClassPathResource("beans.xml"));
		final List<Object> list = new ArrayList<>();
		for(int i = 1; i < 10000 ; i++) {
			Runnable thread = new Runnable() {

				@Override
				public void run() {
					
					Person person = (Person)bf.getBean("person_ctor_multi_args");
					list.add(person);
				}};
			thread.run();
		}
		Object obj = list.get(0);
		String result = "same object";
		for(Object o : list) {
			if(obj != o) {
				result = "not same";
				break;
			}
		}
		assertEquals("same object", result);	
	}

}
