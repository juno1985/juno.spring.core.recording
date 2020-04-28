package app.root;

import org.junit.Test;
import juno.app.root.Person;

public class CoreTest {

	@Test
	public void callPerson() {
		Person p = new Person("lao wang");
		System.out.println(p.getName());
	}

}
