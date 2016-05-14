package baseline.core.common;

import baseline.core.interfaces.SchemaLoader;
import baseline.core.types.InputTypes;
import baseline.model.Schema;
import org.glassfish.hk2.api.IterableProvider;

import javax.inject.Inject;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class Input {
	public Filter Include;
	public Filter Exclude;
	private String url;
	private String login;
	private String password;
	@XmlAttribute private InputTypes type = InputTypes.FILE;
	@Inject
	transient private IterableProvider<SchemaLoader> SchemaLoaderProvider;
	
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

	
	public InputTypes getType(){
		return type;
	}

	public Filter getInclude() {
		return Include;
	}

	public Filter getExclude() {
		return Exclude;
	}

	public Schema loadAsSchema() throws Exception{
		SchemaLoader loader = SchemaLoaderProvider.named(type.name()).get();
		return loader.setURL(url).setLogin(login).setPassword(password).setExcludeFilter(Exclude).setIncludeFilter(Include).load();
	}






}
