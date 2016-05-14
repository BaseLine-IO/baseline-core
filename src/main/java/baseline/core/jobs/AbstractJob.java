package baseline.core.jobs;

import baseline.BaseLineContext;
import baseline.core.types.JobTypes;
import baseline.utils.collections.Indexable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

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
	public Object getParent() {
		return null;
	}

	@Override
	public void setParent(Object p) {

	}

}
