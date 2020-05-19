package juno.spring.recoding.beans.factory.xml;

import org.w3c.dom.Document;

public interface Juno_BeanDefinitionDocumentReader {
	
	void registerBeanDefinitions(Document doc, XmlReaderContext readerContext)
			throws BeanDefinitionStoreException;


}
