package baseline.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import baseline.BaseLineContext;
import baseline.utils.collections.IndexedList;
import baseline.core.jobs.AbstractJob;

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
