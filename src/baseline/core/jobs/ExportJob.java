package baseline.core.jobs;

import baseline.BaseLineContext;
import baseline.core.common.Input;
import baseline.core.common.Output;
import baseline.model.Schema;

import javax.xml.bind.JAXBException;

public class ExportJob extends AbstractJob {
	
	public Input Source;
	public Output Output;

	

	@Override
	public JobTypes getType() {
		return JobTypes.EXPORT;
	}

	@Override
	public void run(BaseLineContext context) {
		Schema source = null;
		try {
			source = Source.loadAsSchema();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		try {
			Output.saveSchema(source);
		} catch (JAXBException e) {
			e.printStackTrace();
			return;
		}


	}

}
