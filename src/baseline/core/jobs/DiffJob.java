package baseline.core.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import baseline.BaseLineContext;
import baseline.collections.IndexedList;
import baseline.core.common.Input;
import baseline.core.common.Output;
import baseline.exceptions.DiffException;
import baseline.model.ModelObjectTypes;
import baseline.model.Schema;
import baseline.newdiff.Diff;

@XmlAccessorType(XmlAccessType.FIELD)
public class DiffJob extends AbstractJob{
	ArrayList<ModelObjectTypes> Compare;
	@XmlAttribute public String Include;
	@XmlAttribute public String Exclude;
	public Input Source;
	public Input Taret;
	public Output Output;
	
	

	public boolean needToCompare(ModelObjectTypes type){
		if(ModelObjectTypes.ALL.equals(type) || Compare.contains(type)) return true;
		return false;
	}




	@Override
	public JobTypes getType() {
		return JobTypes.DIFF;
	}




	@Override
	public void run(BaseLineContext context) {
		Schema source = null;
		try {
			source = Source.loadAsSchema();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Schema target = null;
		try {
			target = Taret.loadAsSchema();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Diff<Schema> diff = new Diff<Schema>();
		diff.setSource(source);
		diff.setTarget(target);
		try {
			diff.compare();
		} catch (DiffException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(Output != null){
			try {
				Output.saveDiffLog(diff.getLog());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
