package baseline.database;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;


import com.google.common.io.Resources;

import sbt.datapipe.helpers.ConfigLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class QueryBuilder {
	        private static Configuration cfg;
	        static{
	        	cfg = new Configuration(Configuration.VERSION_2_3_22);
				cfg.setClassForTemplateLoading(QueryBuilder.class, "templates");
	    		cfg.setDefaultEncoding("UTF-8");
	    		cfg.setLocale(Locale.ENGLISH);
	    		cfg.setNumberFormat("0.#####");
	    		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);  
	    		cfg.setWhitespaceStripping(true);
	        }
	        
			public static String queryFromTemplate(String tmplName, Map<String,Object> root) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
				Template temp = cfg.getTemplate(tmplName);
				Writer out = new StringWriter();
				temp.process(root, out);
				return out.toString();
			}

}
