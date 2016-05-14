package baseline.utils.collections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class IndexedList<E> implements Collection<E> {
	
	private LinkedHashMap<Object, E> map = new LinkedHashMap<Object, E>();
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
		}else if(e.getClass().isAnnotationPresent(AllowedForIndexing.class)) {
			setParent(e,parent);
			map.put(this.getIndexFromMethodsOfClass(e),e);
		}else{
			map.put(String.valueOf(e.hashCode()), e);
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		Object  key = (String.valueOf(o.hashCode()));
		if(o instanceof Indexable) {
			key = ((Indexable) o).getKeyForIndex();

		}else if(o.getClass().isAnnotationPresent(AllowedForIndexing.class)) {
			key = this.getIndexFromMethodsOfClass((E)o);
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

	private void setParent(E e, Object _parent) {
		for (Method m : e.getClass().getMethods()) {
			if (m.isAnnotationPresent(SetParent.class)) {
				try {
					m.invoke(e, _parent);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private Object getIndexFromMethodsOfClass(E e) {
		for (Method m : e.getClass().getMethods()) {
			if (m.isAnnotationPresent(KeyForIndex.class)) {
				try {
					return m.invoke(e, null);
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
		}

		return String.valueOf(e.hashCode());
	}

	public E find(Object name){
			return map.get(name);
	}

	public E findByObj(E e){
	       if(e instanceof Indexable){
			   return map.get(((Indexable) e).getKeyForIndex());
		   }else if(e.getClass().isAnnotationPresent(AllowedForIndexing.class)){
				   return  map.get(getIndexFromMethodsOfClass(e));
		   }
		   return map.get(String.valueOf(e.hashCode()));
	   }
       
       public boolean containsKey(Object name){
       	return map.containsKey(name);
       }

	public boolean containsObjKey(E e){
    	   if(e instanceof Indexable) {
			   	return map.containsKey(((Indexable) e).getKeyForIndex());
		   }else if(e.getClass().isAnnotationPresent(AllowedForIndexing.class)){
				return  map.containsKey(getIndexFromMethodsOfClass(e));
    	   }else{
    		   	return false;
    	   }
        }

	public Set<Object> keySet(){
       	return map.keySet();
       }
}
