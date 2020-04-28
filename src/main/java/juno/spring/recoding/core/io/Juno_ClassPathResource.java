package juno.spring.recoding.core.io;


import juno.spring.recoding.util.Juno_Assert;
import juno.spring.recoding.util.Juno_ClassUtils;
import juno.spring.recoding.util.Juno_StringUtils;

public class Juno_ClassPathResource implements Juno_Resource{
	
	private final String path;
	private ClassLoader classLoader;
	
	public Juno_ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public Juno_ClassPathResource(String path, ClassLoader classLoader) {
		Juno_Assert.notNull(path, "Path must not be null");
		String pathToUse = Juno_StringUtils.cleanPath(path);
		if(pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		this.path = pathToUse;
		this.classLoader = (classLoader != null ? classLoader : Juno_ClassUtils.getDefaultClassLoader());
	}

}
