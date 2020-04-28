package juno.spring.recoding.core.io;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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

	public InputStream getInputStream() throws IOException {
		InputStream is = null;
		if(this.classLoader != null) {
			//通过类加载器获得文件路径
			is = this.classLoader.getResourceAsStream(this.path);
		}
		if(is == null) {
			throw new FileNotFoundException(getDescription() + " cannot be opened because it dose not exist");
		}
		return is;
	}
	
	public String getDescription() {
		/**
		 * String - 每次都会new一个对象
		 * StringBuilder - append("xx")后，还是在原来对象后面加字符串，不会生成新的String对象所以比较快,非线程安全
		 * StringBuffer - 和StringBuilder相同，但是所有方法前面使用synchronized关键字,线程安全
		 */
		StringBuilder builder = new StringBuilder("class path resource [");
		String pathToUse = this.path;
		
		builder.append(pathToUse);
		builder.append(']');
		return builder.toString();
		
	}

}
