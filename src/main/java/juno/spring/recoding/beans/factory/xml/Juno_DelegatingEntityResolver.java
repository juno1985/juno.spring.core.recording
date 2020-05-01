package juno.spring.recoding.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import juno.spring.recoding.core.io.Juno_ClassPathResource;
import juno.spring.recoding.core.io.Juno_Resource;
import juno.spring.recoding.util.Juno_ClassUtils;

public class Juno_DelegatingEntityResolver implements EntityResolver {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private volatile Map<String, String> schemaMappings;
	
	//"spring.schemas"
	
	public static final String DEFAULT_SCHEMA_MAPPINGS_LOCATION = "spring.schemas";
	
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		
		logger.trace("Trying to resolve XML entity with public id [" + publicId +
				"] and system id [" + systemId + "]");
		
		ClassLoader classLoader = Juno_ClassUtils.getDefaultClassLoader();
		
		Enumeration<URL> urls = classLoader.getResources(DEFAULT_SCHEMA_MAPPINGS_LOCATION);
		
		Properties props = new Properties();
		
		while(urls.hasMoreElements()) {
			URL url = urls.nextElement();
			logger.trace("found resource: " + url.toString());
			
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			
			try {
				props.load(is);
			} finally {
				is.close();
			}
		}
		
		logger.trace("Loaded schema mappings: " + props);
		/**
		 * schemaMapping 需要在上锁前后两次判断是否为null
		 */
		schemaMappings = new ConcurrentHashMap<String, String>(props.size());
		
		if(props != null) {
			for(Enumeration<?> en = props.propertyNames(); en.hasMoreElements();) {
				String key = (String)en.nextElement();
				Object value = props.get(key);
				if(value == null) {
					value = props.getProperty(key);
				}
				schemaMappings.put(key, (String) value);
			}
			
		}
		
		if(systemId != null) {
			String resourceLocation = schemaMappings.get(systemId);
			if(resourceLocation != null) {
				Juno_Resource resource = new Juno_ClassPathResource(resourceLocation, Juno_ClassUtils.getDefaultClassLoader());
				
				InputSource source = new InputSource(resource.getInputStream());
				
				source.setPublicId(publicId);
				source.setSystemId(systemId);
				
				logger.trace("Found XML schema [" + systemId + "] in classpath: " + resourceLocation);
				
				return source;
			}
		}
		
		return null;
	}

}
