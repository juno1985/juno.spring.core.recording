package juno.spring.recoding.util;

import org.apache.commons.logging.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Juno_SimpleSaxErrorHandler implements ErrorHandler {
	
	private final Log logger;
	
	public Juno_SimpleSaxErrorHandler(Log logger) {
		this.logger = logger;
	}

	public void warning(SAXParseException ex) throws SAXException {
		logger.warn("Ignored XML validation warning", ex);
	}

	public void error(SAXParseException ex) throws SAXException {
		throw ex;
	}

	public void fatalError(SAXParseException ex) throws SAXException {
		throw ex;
	}

}
