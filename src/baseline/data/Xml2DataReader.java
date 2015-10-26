package baseline.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import baseline.model.table.Column;

import com.google.common.base.Charsets;
import com.google.common.collect.TreeMultiset;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

public class  Xml2DataReader extends DefaultHandler  {
	
	boolean inColumn = false;
	boolean isKey = false;
	String FileName;
	
	private DataManagerEvent event = new DataManagerEvent() {
		
		@Override
		public void onEntity(Integer id, Integer keyHash,
				HashMap<String, DataEntity> entity) {
			
		}
	};
	
	Integer ID;
	LinkedHashMap<String, DataEntity> Columns = new LinkedHashMap<String, DataEntity>();
	DataEntity currentItem = null;
	
	LinkedHashSet<String> keyList = new LinkedHashSet<String>();
	HashSet<String> keyNames = new HashSet<String>();
	
	
	public Xml2DataReader(String filename,List<Column> list) {
		 Iterator<Column> i = list.iterator();
		 while(i.hasNext()) keyNames.add(i.next().getName().toLowerCase());
		 FileName = filename;
	}
	
	public void setEvent(DataManagerEvent e){
		event = e;
	}
	
	public Xml2DataReader(String filename,String list) {
		  String[] array = list.toLowerCase().split(",");
		  keyNames.addAll(Arrays.asList(array));
		  FileName = filename;
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributers) throws SAXException {
		if(qName.equalsIgnoreCase("Row")){

			ID = Integer.parseInt(attributers.getValue("id"));
			Columns = new LinkedHashMap<String, DataEntity>();
			
		}else if (qName.equalsIgnoreCase("Column")){
			currentItem = new DataEntity();
			currentItem.name = attributers.getValue("name").toLowerCase();
			currentItem.type = DataEntity.DataTypes.valueOf(attributers.getValue("type").toUpperCase());
			inColumn = true;
			if(keyNames.contains(attributers.getValue("name").toLowerCase())){
				currentItem.isPrimeryKey = true;
				isKey = true;
			}	
			else
				isKey = false;
			
		}
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equalsIgnoreCase("Row")){

			    event.onEntity(ID,keyList.hashCode(),Columns);
				keyList.clear();
			}else if (qName.equalsIgnoreCase("Column")){
				Columns.put(currentItem.name, currentItem);
				inColumn = false;
				isKey = false;
			}
	}
	
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		if(inColumn){
			String x = new String(arg0, arg1, arg2);
			
			currentItem.value = x;
			
			if(isKey) keyList.add(x); 
			
		}
		
	}
	

	public void parse() throws SAXException, ParserConfigurationException, IOException{
		XMLReader r;
			File file = new File(FileName);
			r = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			r.setContentHandler((org.xml.sax.ContentHandler) this);
			r.parse(file.getAbsolutePath());
			r.parse(FileName);
	}
}
