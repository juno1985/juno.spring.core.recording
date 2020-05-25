package juno.spring.recoding.beans.BeanUtils;

public class Juno_StringToAnyTypeValueConverter {

	public static Object strToAnyType(String desiredType, String value){
		switch (desiredType) {
			case "long":
			case "Long" : return Long.parseLong(value);
			case "int" :
			case "Integer" : return Integer.parseInt(value);
			
			default:
				return value;
		}
			
	}
}
