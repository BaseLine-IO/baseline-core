package baseline;

import baseline.core.interfaces.DiffLogSaver;
import baseline.core.interfaces.SchemaLoader;
import baseline.database.Crawler;
import baseline.database.SQLScript;
import baseline.xml.XmlService;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.BuilderHelper;

public class BaseLineContext {

    public synchronized static void baseContextConfiguration(){

        DynamicConfigurationService dcs = getLocator().getService(DynamicConfigurationService.class);

        DynamicConfiguration config = dcs.createDynamicConfiguration();


        config.bind(
                BuilderHelper.link(Crawler.class).named("JDBC").to(SchemaLoader.class).build()
        );

        config.bind(
                BuilderHelper.link(Crawler.class).named("JNDI").to(SchemaLoader.class).build()
        );

        config.bind(
                BuilderHelper.link(XmlService.class).named("FILE").to(SchemaLoader.class).build()
        );
        config.bind(
                BuilderHelper.link(SQLScript.class).named("SQL").to(DiffLogSaver.class).build()
        );


        config.commit();

    }

    public static ServiceLocator getLocator() {
       return  getLocator("baseline");
    }

    public static synchronized ServiceLocator getLocator(String name) {
        ServiceLocator locator =  ServiceLocatorFactory.getInstance().find(name);
        if(locator == null);
             locator =  ServiceLocatorFactory.getInstance().create(name);
        return locator;
    }

    public static void inject(Object o){
        getLocator().inject(o);

    }


}
