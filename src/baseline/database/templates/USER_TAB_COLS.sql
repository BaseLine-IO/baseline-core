select 
	CT.TABLE_NAME,
	decode(tb.duration,'SYS$SESSION','TEMP_SESSION','SYS$TRANSACTION','TEMP_TRANSACTION','NORMAL'),
	CT.COLUMN_NAME,
	CT.DATA_TYPE,
	DECODE(CT.DATA_TYPE
		,'NUMBER',CT.DATA_PRECISION
		,'NVARCHAR2',CT.char_col_decl_length
		,'VARCHAR2',CT.CHAR_LENGTH
		,'CHAR',CT.CHAR_LENGTH
		,CT.DATA_LENGTH) DATA_SIZE,
	CT.DATA_SCALE ,
	CT.DATA_DEFAULT DEFAULT_VAL,
	DECODE(CT.CHAR_USED,'C','CHAR','B','BYTE') CHAR_UNITS,
	CT.INTERNAL_COLUMN_ID ORDINAL_POSITION,
	DECODE(CT.NULLABLE,'Y',1,0) IS_NULLABLE,
	DECODE(CT.VIRTUAL_COLUMN,'YES',1,0) IS_VIRTUAL,
	CM.COMMENTS,
	TC.COMMENTS
from 
    USER_TAB_COMMENTS TC,
	USER_TAB_COLS CT,
	USER_ALL_TABLES TB,
	USER_COL_COMMENTS CM
where 
	CT.TABLE_NAME = TB.TABLE_NAME
	and CT.VIRTUAL_COLUMN = 'NO'
	and CT.COLUMN_NAME = CM.COLUMN_NAME(+)
	and CT.TABLE_NAME = CM.TABLE_NAME(+)
	 and CT.TABLE_NAME = TC.TABLE_NAME(+)
	 <#if include?? && include?size != 0> 
	 	and CT.TABLE_NAME in (
	   		<#list include as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	   </#if>	
	<#if exclude?? && exclude?size != 0> and CT.TABLE_NAME not in  (
	   		<#list exclude as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	</#if>
order by 
	TABLE_NAME, 
	ORDINAL_POSITION