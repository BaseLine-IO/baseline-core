package baseline.newdiff;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCodes;
import com.google.common.hash.Hashing;

import java.util.Arrays;
import java.util.List;

public class DiffLogItem {
	public DiffTypes type;
	public Object left;
	public Object right;
	public String mode;
	public List<Integer> IdList;
	
	public DiffLogItem(
		DiffTypes _type,
		Object _left,
		Object _right,
		String _mode
	) {
		type  = _type;
		left = _left;
		right = _right;
		mode = _mode;
	}
	
	public DiffLogItem(
	    DiffTypes _type,
		Object _right,
		List<Integer>  _ids
	) {
		type = _type;
		right = _right;
		left = _right;
		IdList = _ids;

	}
	 
	
	
	public List<Integer> getIdList() {
		return IdList;
	}

	@Override
	public int hashCode() {
		return Hashing.combineOrdered(Arrays.asList(
				 Hashing.goodFastHash(32).hashString(type.name(),Charsets.UTF_8),
				 Hashing.goodFastHash(32).hashString((mode == null?"none":mode),Charsets.UTF_8),
				 HashCodes.fromInt(left.hashCode()),
				 HashCodes.fromInt(right.hashCode())
				)).asInt();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass() != this.getClass()) return false;
	    return obj.hashCode() == this.hashCode();
	}
	
}
