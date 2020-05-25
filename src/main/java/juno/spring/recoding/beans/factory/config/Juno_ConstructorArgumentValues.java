package juno.spring.recoding.beans.factory.config;

public class Juno_ConstructorArgumentValues {

	
	public static class Juno_ValueHolder{
		
		private Object value;
		
		private String type;
		
		private String name;

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}
}
