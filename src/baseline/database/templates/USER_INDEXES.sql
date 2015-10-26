select 
	IX.INDEX_NAME,
	IX.INDEX_TYPE,
	IX.TABLE_NAME,
	IC.COLUMN_NAME,
	DECODE(IX.UNIQUENESS,'UNIQUE',1,0),
	DECODE(IC.DESCEND,'ASC',1,0),
	CD.DATA_DEFAULT
from 
	USER_IND_COLUMNS IC,
	USER_INDEXES IX,
	USER_TAB_COLS CD
where 
	IC.TABLE_NAME = IX.TABLE_NAME
	and IC.INDEX_NAME = IX.INDEX_NAME
	and IC.TABLE_NAME = CD.TABLE_NAME(+)
	and IC.COLUMN_NAME = CD.COLUMN_NAME(+)
	and CD.VIRTUAL_COLUMN(+) = 'YES' 
	and IX.generated = 'N'
	  <#if include?? && include?size != 0> 
	  	and IC.TABLE_NAME in (
	   		<#list include as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	   </#if>	
	<#if exclude?? && exclude?size != 0>
		and IC.TABLE_NAME not in (
	   		<#list exclude as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	</#if>
order by
	IC.TABLE_NAME,IC.INDEX_NAME, IC.COLUMN_POSITION