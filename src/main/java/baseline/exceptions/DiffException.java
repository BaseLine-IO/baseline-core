package baseline.exceptions;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DiffException extends BaseException {
	
	public DiffException(SAXException e){
		super(e);
	}
	
	public DiffException(ParserConfigurationException e){
		super(e);
		
	}
	
	public DiffException(IOException e){
		super(e);
		
	}

	public DiffException(Exception e) {
		super(e);
	}

	public DiffException(IllegalArgumentException e) {
		super(e);
	}
	
	public DiffException(IllegalAccessException e) {
		super(e);
	}

	public DiffException(SecurityException e) {
		 super(e);
	}

}
