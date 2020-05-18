package juno.spring.recoding.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public abstract class Juno_ReflectionUtils {

	public static void makeAccessible(Constructor<?> ctor) {
		if (!Modifier.isPublic(ctor.getModifiers())
				|| !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())
						&& !ctor.isAccessible())
			ctor.setAccessible(true);
	}

}
