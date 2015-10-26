package baseline.core.common;

import java.io.File;
import java.util.Properties;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;

import baseline.database.Crawler;
import baseline.model.Schema;
import baseline.model.table.ColumnDataType;
import baseline.xml.JaxbTools;

@XmlAccessorType(XmlAccessType.FIELD)
public class Input {
	private String url;
	private String login;
	private String password;
	@XmlAttribute private InputTypes type = InputTypes.FILE;
	
	public Input() {

	}
	
	public Input(
			String _url,
			String _login,
			String _password,
			InputTypes _type
	) {
		url = _url;
		login = _login;
		password = _password;
		type = _type;
	}
	
	public Input(String urn){
		String[] path = urn.split(":");	
		if(path.length > 1){
			if(path[0].equals("jdbc")){
				String[] user_conn = urn.split("/"); 
				String[] user_pass = user_conn[1].split("@");
				url = user_conn[0];
				login = user_pass[0];
				password = user_pass[1];
				type = InputTypes.JDBC;
			}else if(path[0].equals("jndi")){
				type = InputTypes.JNDI;
				url = path[1];
			}
		}else{
			url = urn;
		}
		
	}
	
	public InputTypes getType(){
		return type;
	}
	
	public Schema loadAsSchema() throws Exception{
		JaxbTools jaxb = new JaxbTools(new Class[] {Schema.class,ColumnDataType.class});
		if(this.getType().equals(InputTypes.FILE)){
			File file = new File(this.getURN());
		
				Schema schema = (Schema)  jaxb.FileToObject(file);
				return schema;
		}else{
				return new Crawler(this).load();

		}
	}
	
	
	public Properties getDataPipeProps(){
		Properties props = new Properties();
		if(type.equals(InputTypes.JDBC)){
			props.setProperty("jdbc","yes");
		}else if(type.equals(InputTypes.JNDI)){
			props.setProperty("jndi","yes");
		}
		props.setProperty("url",url);
		props.setProperty("login",login);
		props.setProperty("password",password);
		return props;
	}
	
	public String getURN(){
		if(type.equals(InputTypes.JDBC)){
			return url+"/"+login+"@"+password;
		}else if(type.equals(InputTypes.JNDI)){
			return "jndi:"+url;
		}
		return url;
	}


}
