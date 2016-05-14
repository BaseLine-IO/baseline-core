package baseline.database;

import baseline.core.common.Filter;
import baseline.core.interfaces.SchemaLoader;
import baseline.model.*;
import baseline.model.types.ConstraintTypes;
import baseline.model.types.TableTypes;
import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import sbt.datapipe.DataPipe;
import sbt.datapipe.fetchers.Fetchable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Crawler implements Fetchable, SchemaLoader {
	private Schema schema;

	private String URL;

	private Filter Exclude;
	private Filter Include;

	private String Login;
	private String Password;
	private State state = State.TABLES;

	public Crawler() {

	}

	public Schema load() throws Exception{
		schema = new Schema(DatabaseFabric.createURN(URL,Login,Password));

	   	HashMap<String,Object> tabelsParams = new HashMap<String,Object>(2);

	  	if(Include != null && Include.getTabels() != null){
			System.out.println(
			Joiner.on("").join("'",
				Joiner.on("','").join(Include.getTabels().split(","))
			,"'"
			)
			);


			tabelsParams.put("include",Include.getTabels().split(","));
		}



		if(Exclude != null && Exclude.getTabels() != null){
			tabelsParams.put("exclude", Exclude.getTabels().split(","));
		}


		 DataPipe data = DatabaseFabric.fromUrl(URL,Login,Password);

		 data.onErrorStop(true);


		data.
		select(QueryBuilder.queryFromTemplate("USER_TAB_COLS.sql", tabelsParams)).fetch(this.state(State.TABLES)).

        select(QueryBuilder.queryFromTemplate("USER_CONSTR.sql", tabelsParams)).fetch(this.state(State.CONSTRS)).

        select(QueryBuilder.queryFromTemplate("USER_INDEXES.sql", tabelsParams)).fetch(this.state(State.INDEXES));



		data .close();

		return schema;
}

	@Override
	public SchemaLoader setURL(String url) {
		URL = url;
		return this;
	}

	@Override
	public SchemaLoader setIncludeFilter(Filter f) {
		Include = f;
		return this;
	}

	@Override
	public SchemaLoader setExcludeFilter(Filter f) {
		Exclude = f;
		return this;
	}

	@Override
	public SchemaLoader setLogin(String login) {
		Login = login;
		return this;
	}

	@Override
	public SchemaLoader setPassword(String password) {
		Password = password;
		return this;
	}

	private Crawler state(State s) {
		state = s;
		return this;
	}

	@Override
	public Object fetch(ResultSet rs) throws SQLException {
		switch (state) {
			case TABLES: tabels(rs); break;
			case INDEXES: indexes(rs); break;
			case CONSTRS: constraints(rs); break;

			default:
				break;
		}
		return null;
	}
	
	@Override
	public Object getResult() {	return null;}
	
	private void tabels(ResultSet rs) throws SQLException{
		while (rs.next()) {
			String tableName = rs.getString(1);
            String name = rs.getString(3);
            String def = readStream(rs.getBinaryStream(7));
			Table t = schema.getTable(tableName);
			t.setTable(TableTypes.valueOf(rs.getString(2)), rs.getString(13));
					t.getColumn(name).setColumn(
							rs.getString(4), //type,
							rs.getInt(9), //orderNum,
							rs.getObject(5) != null ? rs.getInt(5) : null, //size,
							rs.getObject(6) != null ? rs.getInt(6) : null, //scale,
							rs.getString(8), //units,
							(rs.getInt(10) == 1) ? true : false, //is_NULLABLE,
							def, //defaultValue,
							rs.getString(12) //comment
					 );

			Data d = schema.getData(tableName);
			d.setURL(schema.URL());
		}
	}
	
	private void indexes(ResultSet rs) throws SQLException{
		while (rs.next()) {
		    Table t = schema.findTable(rs.getString(3));
		      if(t == null) continue;
              String name = rs.getString(1);
              if(t.findConstraint(name) != null) continue;
              String col_name = rs.getString(4);
              String def = readStream(rs.getBinaryStream(7));
              Index idx = t.getIndex(name);
              idx.setIndex(
					  rs.getString(2), //type,
					  (rs.getInt(5) == 1) ? true : false,// is_Unique,
					  (rs.getInt(6) == 1)?true:false //is_AscDescend
            	);

			idx.addColumn(col_name, def);
			}
	}
	
	private void constraints(ResultSet rs) throws SQLException{
		while (rs.next()) {
		    Table t = schema.findTable(rs.getString(1));
		    if(t == null) continue;
              String name = rs.getString(2);
              ConstraintTypes ct = ConstraintTypes.valueOf(rs.getString(3));
              String col_name = rs.getString(5);
              String exp = readStream(rs.getBinaryStream(7));
              if(ct == ConstraintTypes.CHECK && exp.equalsIgnoreCase("\""+col_name+"\" IS NOT NULL"))
				  continue;
			Constraint constraint = null;
              switch (ct) {
              		case PRIMARY_KEY: constraint = t.getPrimaryKey(name);	break;
              		case FOREIGN_KEY: constraint = t.getForeignKey(name);	break;
              		case CHECK: constraint = t.getCheck(name);				break;
              		case UNIQUE: constraint = t.getUnique(name);			break;
              		default: constraint = t.getConstraint(name);			break;
			}

			constraint.setConstraint((rs.getInt(4) == 1) ? true : false, exp, rs.getString(8));
			if(exp == null) constraint.addColumn(col_name,rs.getString(9));
              else constraint.setOnColumn(col_name);
              constraint.setOnDelete(rs.getString(10));


		}
	}

	public static String readStream(InputStream io) {
		String q = null;
		if (io == null) return null;
		try {
			q = CharStreams.toString(new InputStreamReader(io));
			Closeables.close(io, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return q;
	}

	private static enum State {
		TABLES,
		INDEXES,
		CONSTRS;
	}



}

