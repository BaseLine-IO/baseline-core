package baseline.xml;

import baseline.core.Project;
import baseline.core.common.Filter;
import baseline.core.interfaces.SchemaLoader;
import baseline.model.Schema;
import baseline.model.table.ColumnDataType;
import baseline.utils.JaxbTools;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by Home on 09.11.15.
 */
public class XmlService implements SchemaLoader {
    String url;

    @Override
    public Schema load() throws Exception {
            JaxbTools jaxb = new JaxbTools(new Class[] {Schema.class,ColumnDataType.class});
            File file = new File(url);
            Schema schema = (Schema)  jaxb.FileToObject(file);
            return schema;
    }

    @Override
    public SchemaLoader setURL(String url) {
        this.url = url;

        return this;
    }

    @Override
    public SchemaLoader setIncludeFilter(Filter f) {
        return this;
    }

    @Override
    public SchemaLoader setExcludeFilter(Filter f) {
        return this;
    }

    @Override
    public SchemaLoader setLogin(String login) {
        return this;
    }

    @Override
    public SchemaLoader setPassword(String password) {
        return this;
    }


    public Project loadProject(File file) throws JAXBException {
        JaxbTools tools = new JaxbTools(new Class[]{Project.class});
        Project project = (Project) tools.FileToObject(file);
        return project;
    }
}
