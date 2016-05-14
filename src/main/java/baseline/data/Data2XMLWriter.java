package baseline.data;

import sbt.datapipe.fetchers.AbstractFetchAs;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Data2XMLWriter extends AbstractFetchAs{	
	XMLStreamWriter writer = null;
	String fileName = "";
	Integer currentRow = 0;
	
	public Data2XMLWriter(String filename) {
		fileName = filename;
	
	}

	@Override
	protected void put(Integer row, int i, String columnName, Timestamp timestamp) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			writerow(row, i, columnName, dateTimeFormat.format(timestamp), "Date");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void put(Integer row, Integer col, String name) {
		try {
			writerow(row, col, name, "", "NULL");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void writerow(Integer row, Integer col, String name, String value, String type) throws XMLStreamException, FactoryConfigurationError, IOException {
		if (currentRow.equals(0)) {
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(new FileWriter(fileName));
			writer.writeStartDocument();
			writer.writeStartElement("Data");
			writer.writeStartElement("Row");
			writer.writeAttribute("id", Integer.toString(row));
			currentRow = row;
		}

		if (!currentRow.equals(row)) {
			writer.writeEndElement();
			writer.writeStartElement("Row");
			writer.writeAttribute("id", Integer.toString(row));
		}

		writer.writeStartElement("Column");
		writer.writeAttribute("name", name);
		writer.writeAttribute("type", type);
		writer.writeCharacters(value);
		writer.writeEndElement();

		currentRow = row;
	}
	
	@Override
	public void put(Integer row, Integer col, String name, String value) {
		try {
			writerow(row, col, name, value, "String");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void put(Integer row, Integer col, String name, BigDecimal value) {
		try {
			writerow(row, col, name, value.toPlainString(), "Number");
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Object result()  {
		try {
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		return null;
	}


}
