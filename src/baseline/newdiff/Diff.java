package baseline.newdiff;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import baseline.collections.IndexedList;
import baseline.core.Project;
import baseline.exceptions.DiffException;
import baseline.model.Data;
import baseline.model.ModelObject;

public class Diff<T>  implements DiffStrategy<T>{
	protected T Left;
	protected T Right;
	protected DiffLog Log;
	protected Project Config;
	
	public Diff() {
	    Log = new DiffLog();
	}
	
	public DiffLog getLog(){
		return Log;
	}
	
	public void compare() throws DiffException{
		if( !eq(Left,Right)){
			if(Left instanceof Collection<?>){ //compare as List
				compareAsList(Left.getClass());
			}else{
					try {
						compare(Left.getClass().getFields());
					} catch (SecurityException e) {
						DiffException newex = new DiffException(e);
						throw newex;
					}
			
			}
		}
	}
	
	private Diff(T left, T right, DiffLog log,Project config) {
		Log = log;
		Left = left; Right = right;
		Config = config;
	}
	

	private void compare(Field[] fields) throws DiffException{
		  for (int i = 0; i < fields.length; i++) {
			if(fields[i].isAnnotationPresent(DiffThis.class)){
				if(((DiffThis)(fields[i].getAnnotation(DiffThis.class))).value().equals(Diff.class)){
					try {
						new Diff(fields[i].get(Left),fields[i].get(Right),Log,Config).compare();
					} catch (IllegalArgumentException e) {
						DiffException newex = new DiffException(e);
						throw newex;
					} catch (IllegalAccessException e) {
						DiffException newex = new DiffException(e);
						throw newex;
					}
				}else{	 
					 if(new HashSet(Arrays.asList(((DiffThis)(fields[i].getAnnotation(DiffThis.class))).value().getInterfaces())).contains(DiffStrategy.class)){
						 Object strategy = null;
						try {
							strategy = ((DiffThis)(fields[i].getAnnotation(DiffThis.class))).value().newInstance();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 	((DiffStrategy) strategy).setSource(Right);
						 	((DiffStrategy) strategy).setTarget(Left);
						 	((DiffStrategy) strategy).configure(Config);
						 	((DiffStrategy) strategy).compare(Log);
					 }
				}
			}else{
				if(!Modifier.isTransient(fields[i].getModifiers())){
					try {
						if(!eq(fields[i].get(Left),fields[i].get(Right)))
							Log.MODIFY(Right,  fields[i].getName());
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			
			
		}
	}
	
	private <E> void compareAsList(Class E) throws DiffException{
		Set<E> set = new HashSet<E>();
		set.addAll( (Collection<? extends E>) Right);
		set.removeAll((Collection<? extends E>) Left);
		Iterator<E> items = (Iterator<E>) set.iterator();
		E item;
		while(items.hasNext()){  item = items.next();
			  if(Left instanceof IndexedList && ((IndexedList)Left).containsObjKey(item)){ 
				  new Diff(((IndexedList)Left).findByObj(item),item,Log,Config).compare();
			  }else{ 
				   Log.ADD(item);
			  }
		}
		set.clear();
		set.addAll( (Collection<? extends E>) Left);
		set.removeAll((Collection<? extends E>) Right);
		items = (Iterator<E>) set.iterator();
		while(items.hasNext()){  item = items.next();
		 if(Right instanceof IndexedList && !((IndexedList)Right).containsObjKey(item)) 
			   Log.REMOVE(item);
		}

	}
	
	private static Boolean eq(Object left,Object right){
		if(left != null)
			return left.equals(right);
		else if(left == null && right == null)
			return true;
		else
			return false;
	}

	@Override
	public void setSource(T src) {
		Right = src;
		
	}

	@Override
	public void setTarget(T trg) {
		Left = trg;
		
	}

	@Override
	public void compare(DiffLog log) throws DiffException{
		Log = log;
		this.compare();
		
	}

	@Override
	public void configure(Project config) {
		Config = config;
		
	}

}

