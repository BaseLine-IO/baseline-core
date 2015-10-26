package baseline.core.common;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import baseline.SchemaManager;
import baseline.model.Schema;
import baseline.model.table.ColumnDataType;
import baseline.newdiff.DiffLog;
import baseline.output.SQLScript;
import baseline.xml.JaxbTools;

@XmlAccessorType(XmlAccessType.FIELD)
public class Output {
		@XmlAttribute public String url;
		@XmlAttribute public String xsdLocation;
		@XmlAttribute public OutputTypes type;
		public String delimiter;
		public String encoding;
		
		
		public String getUrl(){
			return url;
		}
		
		public String getXsdLocation(){
			return xsdLocation;
		}
		
		public void saveDiffLog(DiffLog log) throws IOException{
			switch (type) {
			case SQLSCRIPT:
				new SQLScript(log).configure(url, delimiter, encoding).generate(); 
				break;

			default:
				break;
			}
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
