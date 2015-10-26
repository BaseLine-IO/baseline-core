package baseline;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;


import baseline.core.common.Input;
import baseline.core.common.InputTypes;
import baseline.core.common.Output;
import baseline.database.Crawler;
import baseline.model.Schema;
import baseline.model.table.ColumnDataType;
import baseline.xml.JaxbTools;

public class SchemaManager {
	
	private BaseLineContext Context = null;
	private JaxbTools jaxb;
	
	public SchemaManager(BaseLineContext baseline) {
		Context = baseline; 
		try {
			jaxb = new JaxbTools(new Class[] {Schema.class,ColumnDataType.class});
		} catch (JAXBException e) {

			e.printStackTrace();
		}
	}
	

	
	public Schema load(Input source){
		if(source.getType().equals(InputTypes.FILE)){
			File file = new File(source.getURN());
			try {
				Schema schema = (Schema)  jaxb.FileToObject(file);
				return schema;
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				return new Crawler(source).load();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}

		return null;
	}
	
	public void save(Schema schema, Output out){
		File file = new File(out.getUrl());
		try {
			jaxb.ObjectToFile(file, schema, out.getXsdLocation());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	   


	public void saveXSD(Output out){
		final File file = new File(out.getUrl());
		try {
			jaxb.XsdToFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	};
		
			


}


