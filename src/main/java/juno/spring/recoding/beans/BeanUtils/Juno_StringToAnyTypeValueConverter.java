package juno.spring.recoding.beans.BeanUtils;

public class Juno_StringToAnyTypeValueConverter {

	public static Object strToAnyType(String desiredType, String value){
		switch (desiredType) {
			case "long":
			case "java.lang.Long" : return Long.parseLong(value);
			case "int" :
			case "java.lang.Integer" : return Integer.parseInt(value);
			
			default:
				return value;
		}
			
	}
}
