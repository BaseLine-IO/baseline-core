package baseline.core.jobs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import baseline.BaseLineContext;
import baseline.utils.collections.Indexable;
import baseline.core.types.JobTypes;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractJob implements Indexable {
	@XmlAttribute public String jobname;
	public abstract JobTypes getType();
	public abstract void run(BaseLineContext context);
	@Override
	public String getKeyForIndex() {
		return jobname;
	}
	@Override
	public void setParent(Object p) {
		
	}
	@Override
	public Object getParent() {
		return null;
	}

}
