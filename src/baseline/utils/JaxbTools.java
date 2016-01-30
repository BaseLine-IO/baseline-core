package baseline.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class JaxbTools {
	
	public JaxbTools( Class[] marshalebleClasses) throws JAXBException {
			context = JAXBContext.newInstance(marshalebleClasses);
	}
	

	
	private JAXBContext context;
	
	public  void ObjectToFile(File file, Object obj, String XsdLocation) throws JAXBException{
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			if(XsdLocation != null) m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, XsdLocation);
			if(file == null) {
				m.marshal(obj, System.out);
			}else{
				m.marshal(obj, file);
			}
	};
	
	public Object FileToObject(File file) throws JAXBException{
			Unmarshaller um = context.createUnmarshaller();
			Object obj = um.unmarshal(file);
			return obj;
	}
	
	public void XsdToFile(final File file) throws IOException{
		SchemaOutputResolver res = new SchemaOutputResolver() {
			
			@Override
			public Result createOutput(String arg0, String arg1) throws IOException {
				StreamResult r = new StreamResult(file);
				r.setSystemId(file.toURI().toURL().toString());
				return r;
			}
		 };	
		
		 context.generateSchema(res);	
	} 

}
