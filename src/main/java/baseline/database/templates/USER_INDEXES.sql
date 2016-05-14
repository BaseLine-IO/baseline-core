select 
	IX.INDEX_NAME,
	IX.INDEX_TYPE,
	IX.TABLE_NAME,
	CT.COLUMN_NAME,
	DECODE(IX.UNIQUENESS,'UNIQUE',1,0),
	DECODE(CT.DESCEND,'ASC',1,0),
	CD.DATA_DEFAULT
from 
	USER_IND_COLUMNS CT,
	USER_INDEXES IX,
	USER_TAB_COLS CD
where 
	CT.TABLE_NAME = IX.TABLE_NAME
	and CT.INDEX_NAME = IX.INDEX_NAME
	and CT.TABLE_NAME = CD.TABLE_NAME(+)
	and CT.COLUMN_NAME = CD.COLUMN_NAME(+)
	and CD.VIRTUAL_COLUMN(+) = 'YES' 
	and IX.generated = 'N'
	  <#if include?? && include?size != 0> 
	  	and CT.TABLE_NAME in (
	   		<#list include as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	   </#if>	
	<#if exclude?? && exclude?size != 0>
		and CT.TABLE_NAME not in (
	   		<#list exclude as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	</#if>
order by
	CT.TABLE_NAME,CT.INDEX_NAME, CT.COLUMN_POSITION