package app.root;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import juno.app.root.Person;
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
	public void testSimpleLoad() {
		JunoBeanFactory bf = new Juno_XMLBeanFactory(new Juno_ClassPathResource("beans.xml"));
		Person person = (Person) bf.getBean("person");
		assertEquals("laowang", person.getName());
	}

}
