package baseline.core.common;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import baseline.core.interfaces.DiffLogSaver;
import baseline.core.interfaces.SchemaLoader;
import baseline.core.types.OutputTypes;
import baseline.model.Schema;
import baseline.model.table.ColumnDataType;
import baseline.newdiff.DiffLog;
import baseline.database.SQLScript;
import baseline.xml.JaxbTools;
import org.glassfish.hk2.api.IterableProvider;

@XmlAccessorType(XmlAccessType.FIELD)
public class Output {
		@XmlAttribute public String url;
		@XmlAttribute public String xsdLocation;
		@XmlAttribute public OutputTypes type;
		public String delimiter;
		public String encoding;

	@Inject
	transient private IterableProvider<DiffLogSaver> DiffProvider;
		
		
		public String getUrl(){
			return url;
		}
		
		public String getXsdLocation(){
			return xsdLocation;
		}
		
		public void saveDiffLog(DiffLog log) throws Exception{
			DiffLogSaver saver = DiffProvider.named(type.name()).get();
			saver.setLog(log).configure(url, delimiter, encoding).generate();
		}

		public void saveSchema(Schema schema) throws JAXBException {
			switch (type){
				case XML:
					JaxbTools jaxb = new JaxbTools(new Class[] {Schema.class,ColumnDataType.class});
					File file = new File(this.getUrl());
					try {
						jaxb.ObjectToFile(file, schema, this.getXsdLocation());
					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				break;

				default:
					break;

			}

		}
		
}
