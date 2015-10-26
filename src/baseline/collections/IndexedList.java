package baseline.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.sun.tools.jxc.gen.config.Classes;

public class IndexedList<E> implements Collection<E> {
	
	private LinkedHashMap<String, E> map = new LinkedHashMap<String, E>();
	private Object parent;
	

	public IndexedList(Object p) {
		parent = p;
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return map.containsValue(o);
	}

	@Override
	public Iterator<E> iterator() {
		return map.values().iterator();
	}

	@Override
	public Object[] toArray() {
		return map.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return map.values().toArray(a);
	}

	@Override
	public boolean add(E e) {
		if(e instanceof Indexable){
			((Indexable) e).setParent(parent);
			map.put(((Indexable) e).getKeyForIndex(), e);
		}else{
			map.put(String.valueOf(e.hashCode()), e);
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		String key;
		if(o instanceof Indexable){
			key = ((Indexable) o).getKeyForIndex();
		}else{
			key = (String.valueOf(o.hashCode()));
		}
		if(map.remove(key) != null) 
			return true; 
		else 
			return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		
		return map.values().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		Iterator<? extends E> i = c.iterator();
		while(i.hasNext()) add(i.next());
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<?> i = c.iterator();
		while(i.hasNext()) remove(i.next());
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		map.clear();
		
	}
	
	   public E find(String name){
       	return map.get(name);
       }
	   
	   public E findByObj(E e){
	       	return map.get(((Indexable) e).getKeyForIndex());
	       }
       
       public boolean containsKey(String name){
       	return map.containsKey(name);
       }
       
       public boolean containsObjKey(E e){
    	   if(e instanceof Indexable){
    		   return map.containsKey(((Indexable) e).getKeyForIndex());
    	   }else{
    		   return false;
    	   }
        }
       
       public Set<String> keySet(){
       	return map.keySet();
       }

}
