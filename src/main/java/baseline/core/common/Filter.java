package baseline.core.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Created by Home on 28.10.15.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Filter {
    String Tabels;
    String Data;

    public String getTabels() {

        return Tabels;
    }

    public void setTabels(String tabels) {
        Tabels = tabels;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }


}
