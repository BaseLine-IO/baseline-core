package baseline.data;

import com.google.common.hash.Hashing;

public class DataEntity {
	Integer id;
	String name;
	DataTypes type;
	String value = "";
	Boolean isPrimeryKey = false;

	@Override
	public int hashCode() {
		return Hashing.goodFastHash(32).hashString(value).asInt();
	}

	public static enum DataTypes {STRING, DATE, NUMBER, NULL}
	

}
