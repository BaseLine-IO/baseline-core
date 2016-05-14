package baseline.database;

import baseline.core.interfaces.DiffLogSaver;
import baseline.database.ddl.DDL;
import baseline.model.Constraint;
import baseline.model.ModelObject;
import baseline.model.Schema;
import baseline.model.Table;
import baseline.model.types.ConstraintTypes;
import baseline.newdiff.DiffLog;
import baseline.newdiff.DiffLogItem;
import baseline.newdiff.DiffTypes;

import java.io.*;
import java.util.Enumeration;
import java.util.Iterator;

public class SQLScript implements DiffLogSaver {

	public DiffLog Log;
	String FileName = null;
	String delimiter = ";\n";
	String encoding = "UTF-8";
	Writer write_first;
	Writer write_middle;
	Writer write_last;
	
	public SQLScript(DiffLog log) {
		Log = log;
	}

	public SQLScript() {

	}

	public SQLScript setLog(DiffLog log){
		Log = log;
		return this;
	}

	public SQLScript configure(
			String filename,
			String delim,
			String encoding
		) throws FileNotFoundException, UnsupportedEncodingException{


		if(encoding != null) this.encoding = encoding;

		if(filename != null){
			write_first = new PrintWriter(filename+".first.part",this.encoding);
			write_middle = new PrintWriter(filename+".middle.part",this.encoding);
			write_last = new PrintWriter(filename+".last.part",this.encoding);
			FileName = filename;
		}else{
			write_first = new PrintWriter(System.out);
			write_middle = write_first;
			write_last = write_middle;
		}
		if(delim != null) delimiter = delim;

		return this;
	}
	
	public void generate() throws IOException{
		Iterator<DiffLogItem> iterator = Log.iterator();
		DiffLogItem item;
		while(iterator.hasNext()){ item = iterator.next();
			if(item.right instanceof ModelObject){
				 switch (Action2ObjectTypes.valueOf(item.type.name()+"_"+((ModelObject)(item.right)).getObjType().name())) {

				 	case ADD_TABLE: this.TableAdd((Table) item.right);  break;

				 	case ADD_INDEX:
				 	case ADD_COLUMN: write_middle.write(DDL.getDDL((ModelObject) item.right,delimiter).CREATE());  break;

					 case MODIFY_TABLE:
				 	case MODIFY_COLUMN: write_middle.write(DDL.getDDL((ModelObject) item.right,delimiter).ALTER(item.mode));  break;

				 	case MODIFY_INDEX: write_middle.write(DDL.getDDL((ModelObject) item.right,delimiter).DROP());  write_middle.write(DDL.getDDL((ModelObject) item.right,delimiter).CREATE()); break;

					 case ADD_CONSTRAINT:
				 	case MODIFY_CONSTRAINT: WorkWithConstraint((Constraint)item.left,(Constraint)item.right,item.type);  break;

				 	case INSERT_DATA: write_last.write("ADD: "+((ModelObject)item.right).getName()+delimiter); break;
				 	case UPDATE_DATA: write_last.write("MOD: "+((ModelObject)item.right).getName()+delimiter); break;
				 	//case REMOVE_DATA: write_last.write(((ModelObject)item.right).getName()+delimiter); break;

				default:
					break;
				}

			}
		}
	   this.close();
	}

	lic

	void TableAdd(Table new_table) throws IOException {
		write_middle.write(DDL.getDDL(new_table, delimiter).CREATE());

		Iterator<Constraint> i = new_table.getConstraints().iterator();
		while (i.hasNext()) {
			Constraint c = i.next();
			if (c.getType().equals(ConstraintTypes.FOREIGN_KEY))
				write_last.write(DDL.getDDL(c, delimiter).CREATE());
		}
	}


	lic

	void WorkWithConstraint(Constraint old, Constraint _new, DiffTypes action) throws IOException {
		if (action == DiffTypes.ADD && !_new.getIS_USERNAME())
			UnnamedConstraintPatch((Table) old.getParent(), (Table) _new.getParent(), _new);

		if (action == DiffTypes.MODIFY && _new.getType() == ConstraintTypes.FOREIGN_KEY || _new.getType() == ConstraintTypes.UNIQUE)
			RecreatingAllFKsForPK((Schema) old.getParent().getParent(), (Schema) _new.getParent().getParent(), old.getParent().getName(), old);

		Writer w = write_middle;
		if (_new.getType() == ConstraintTypes.FOREIGN_KEY) {
			w = write_last;
		}
		w.write(DDL.getDDL(_new, delimiter).CREATE());
	}


	pub
	private void close() throws IOException{
		write_first.close();
		write_middle.close();
		write_last.close();


		if(FileName != null){
			final String[] filenames = new String[]{
					FileName+".first.part",
					FileName+".middle.part",
					FileName+".last.part"
			};

			Enumeration<FileInputStream> files = new Enumeration<FileInputStream>() {

				public int current = 0;

				@Override
				public boolean hasMoreElements() {
					return current < 3;
				}

				@Override
				public FileInputStream nextElement() {
					try {
						return new FileInputStream(filenames[current++]);
					} catch (FileNotFoundException e) {
						return null;
					}
				}
			};

			SequenceInputStream in = new SequenceInputStream(files);
			FileOutputStream writeTo = new FileOutputStream(new File(FileName));

			BufferedInputStream inStream = new BufferedInputStream(in);
			BufferedOutputStream outStream = new BufferedOutputStream(writeTo);

			byte[] buffer = new byte[8192];
			int nextByte = 0;

			while (true) {
				nextByte = inStream.read(buffer, 0, 8192);
				if (nextByte == -1)
					break;
					outStream.write(buffer, 0, nextByte);
			}

			inStream.close();
			outStream.close();

			for (String s : filenames) {
				new File(s).delete();
			}


		}
	}


	pub
	vate

	void UnnamedConstraintPatch(Table OldTable, Table NewTable, Constraint new_obj) throws IOException {
		if (new_obj.getType() != ConstraintTypes.CHECK) {
			Iterator<Constraint> citer = OldTable.Constraints.iterator();
			while (citer.hasNext()) {
				Constraint old_ = citer.next();
				if (old_.getType() == new_obj.getType() && !old_.getIS_USERNAME()) {
					if (old_.getRefTable() != null && new_obj.getRefTable() != null) {
						if (!old_.getRefTable().equalsIgnoreCase(new_obj.getRefTable())) continue;
					}
					write_first.write(DDL.getDDL(old_, delimiter).DROP());
					break;
				}
			}
		} else {
			//TODO что-то сделать с безимянными чеками... А может и не надо!?
		}
	}

	ppri
	vate

	void RecreatingAllFKsForPK(Schema OldSchema, Schema NewSchema, String tableName, Constraint obj) throws IOException {
		int column_hash = obj.columnsHashCode();
		Iterator<Table> tabs = OldSchema.Tables.iterator();
		while (tabs.hasNext()) {
			Table table = (Table) tabs.next();
			Iterator<Constraint> constrs = table.Constraints.iterator();
			while (constrs.hasNext()) {
				Constraint constraint = (Constraint) constrs.next();
				if(constraint.getType().equals(ConstraintTypes.FOREIGN_KEY) && constraint.getRefTable().equals(tableName) && constraint.refsHashCode() == column_hash){
					  write_first.write(DDL.getDDL(constraint,delimiter).DROP());
					  write_last.write(DDL.getDDL(findConstraint(NewSchema.findTable(table.getName()), constraint),delimiter).CREATE());
				}
			}
			
		}
	}

	pri
	lic Constraint

	findConstraint(Table where, Constraint what) {
		if(what.getIS_USERNAME()) return where.findConstraint(what.getName());
		Iterator<Constraint> iterator = where.Constraints.iterator();
		while (iterator.hasNext()) {
			Constraint constraint = (Constraint) iterator.next();
			if(what.getType() == constraint.getType()){
				switch (what.getType()) {
				case PRIMARY_KEY: return constraint;
				case FOREIGN_KEY: if(what.getRefTable().equalsIgnoreCase(constraint.getRefTable()) && what.refsHashCode() == constraint.refsHashCode()) return constraint; break;
					case CHECK:
						if (what.getExpression() != null && what.getExpression().trim().equalsIgnoreCase(constraint.getExpression().trim()))
							return constraint;
						break;
				case UNIQUE: if(what.columnsHashCode() == constraint.columnsHashCode()) return constraint; break;
					default:
					break;
				}
			}

		}

		return what;
	}

	ub

	private static enum Action2ObjectTypes {
		ADD_TABLE, MODIFY_TABLE, ADD_COLUMN, MODIFY_COLUMN,
		ADD_INDEX, ADD_CONSTRAINT, MODIFY_INDEX, MODIFY_CONSTRAINT, INSERT_DATA, UPDATE_DATA,
		REMOVE_TABLE, REMOVE_INDEX, REMOVE_COLUMN, REMOVE_CONSTRAINT, REMOVE_DATA;
	}
	

}
