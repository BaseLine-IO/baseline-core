select 
	CD.TABLE_NAME,
	decode(tb.duration,'SYS$SESSION','TEMP_SESSION','SYS$TRANSACTION','TEMP_TRANSACTION','NORMAL'),
	CD.COLUMN_NAME,
	CD.DATA_TYPE,
	DECODE(CD.DATA_TYPE
		,'NUMBER',CD.DATA_PRECISION
		,'NVARCHAR2',cd.char_col_decl_length
		,'VARCHAR2',CD.CHAR_LENGTH
		,'CHAR',CD.CHAR_LENGTH
		,CD.DATA_LENGTH) DATA_SIZE,
	CD.DATA_SCALE ,
	CD.DATA_DEFAULT DEFAULT_VAL,
	DECODE(CD.CHAR_USED,'C','CHAR','B','BYTE') CHAR_UNITS,
	CD.INTERNAL_COLUMN_ID ORDINAL_POSITION,
	DECODE(CD.NULLABLE,'Y',1,0) IS_NULLABLE,
	DECODE(CD.VIRTUAL_COLUMN,'YES',1,0) IS_VIRTUAL,
	CM.COMMENTS,
	TC.COMMENTS
from 
    USER_TAB_COMMENTS TC,
	USER_TAB_COLS CD,
	USER_ALL_TABLES TB,
	USER_COL_COMMENTS CM
where 
	CD.TABLE_NAME = TB.TABLE_NAME
	and CD.VIRTUAL_COLUMN = 'NO'
	and CD.COLUMN_NAME = CM.COLUMN_NAME(+)
	and CD.TABLE_NAME = CM.TABLE_NAME(+)
	 and CD.TABLE_NAME = TC.TABLE_NAME(+)
	 <#if include?? && include?size != 0> 
	 	and CD.TABLE_NAME in (
	   		<#list include as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	   </#if>	
	<#if exclude?? && exclude?size != 0> and CD.TABLE_NAME not in  (
	   		<#list exclude as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	</#if>
order by 
	TABLE_NAME, 
	ORDINAL_POSITION