package baseline.domain.model;

import baseline.domain.support.Nodes;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * Created by Home on 30.01.16.
 */
public class Schema {


    private Nodes<Table> Tables;

    private Nodes<Data> Data;

    transient private String URL;


    @XmlElement(name = "Table")
    public Nodes<Table> getTables() {
        return Tables;
    }

    public void setTables(Nodes<Table> tables) {
        Tables = tables;
    }

    @XmlElement(name = "Data")
    public Nodes<Data> getData() {
        return Data;
    }

    public void setData(Nodes<Data> data) {
        Data = data;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Tables, Data, URL);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Schema other = (Schema) obj;
        return Objects.equals(this.Tables, other.Tables)
                && Objects.equals(this.Data, other.Data)
                && Objects.equals(this.URL, other.URL);
    }
}
