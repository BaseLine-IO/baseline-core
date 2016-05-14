package baseline.core;

import baseline.BaseLineContext;
import baseline.core.jobs.AbstractJob;
import baseline.utils.collections.IndexedList;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Jobs")
public class Project {
	    @XmlAttribute public String DefaultJob = "main"; 
		@XmlElements({
			@XmlElement(name="Diff", type=baseline.core.jobs.DiffJob.class),
			@XmlElement(name="Export", type=baseline.core.jobs.ExportJob.class)

		})
		public IndexedList<AbstractJob> Jobs;
		
		public Project() {
			Jobs = new IndexedList<AbstractJob>(this);
		}
		
		public void execute(BaseLineContext context){
			AbstractJob job = Jobs.find(DefaultJob);
			job.run(context);
			
		}

		
}
