package baseline.core.jobs;

import baseline.BaseLineContext;
import baseline.core.common.Input;
import baseline.core.common.Output;
import baseline.core.types.JobTypes;
import baseline.exceptions.DiffException;
import baseline.model.Schema;
import baseline.model.types.ModelObjectTypes;
import baseline.newdiff.Diff;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class DiffJob extends AbstractJob{
	public Input Source;
	public Input Taret;
	public Output Output;
	ArrayList<ModelObjectTypes> Compare;
	
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
