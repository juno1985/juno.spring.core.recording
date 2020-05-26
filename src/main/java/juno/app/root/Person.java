package juno.app.root;

public class Person {
	
	private String name;
	
	private int age;

	public Person() {
		this.name = "laowang";
	}

	public Person(String name) {
		super();
		this.name = name;
	}
	
	

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
