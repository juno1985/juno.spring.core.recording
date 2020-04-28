package juno.spring.recoding.core.io;

import java.io.IOException;
import java.io.InputStream;

public interface Juno_Resource {

	/*
	 * 实际在springframework中，下面的函数在InputStreamSource里
	 * 这里做了简化 
	 */
	InputStream getInputStream() throws IOException;
}
