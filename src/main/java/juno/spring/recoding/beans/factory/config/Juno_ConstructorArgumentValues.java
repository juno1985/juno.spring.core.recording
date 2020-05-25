package juno.spring.recoding.beans.factory.config;

import java.util.LinkedHashMap;
import java.util.Map;

public class Juno_ConstructorArgumentValues {

	private final Map<Integer, Juno_ValueHolder> indexedArgumentValues = new LinkedHashMap<Integer, Juno_ValueHolder>();
	
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

	public void addIndexedArgumentValue(String indexAttr,
			Juno_ValueHolder valueHolder) {
		this.indexedArgumentValues.put(Integer.parseInt(indexAttr), valueHolder);
		
	}
}
