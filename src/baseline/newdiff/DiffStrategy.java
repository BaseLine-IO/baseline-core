package baseline.newdiff;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import baseline.core.Project;
import baseline.exceptions.DiffException;

public interface DiffStrategy<T> {
	public void setSource(T src);
	public void setTarget(T trg);
	public void compare(DiffLog log) throws DiffException;
	public void compare() throws DiffException;
	public void configure(Project config);
	public DiffLog getLog();
}
