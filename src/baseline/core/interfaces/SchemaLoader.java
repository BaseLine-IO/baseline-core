package baseline.core.interfaces;

import baseline.core.common.Filter;
import baseline.model.Schema;



import javax.inject.*;
import org.jvnet.hk2.annotations.Contract;


/**
 * Created by Home on 07.11.15.
 */
@Contract
public interface SchemaLoader {
    public Schema load() throws Exception;
    public SchemaLoader setURL(String url);
    public SchemaLoader setIncludeFilter(Filter f);
    public SchemaLoader setExcludeFilter(Filter f);
    public SchemaLoader setLogin(String login);
    public SchemaLoader setPassword(String password);

}
