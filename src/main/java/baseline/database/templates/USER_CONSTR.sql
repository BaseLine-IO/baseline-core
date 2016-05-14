SELECT
    CT.TABLE_NAME,
    CT.CONSTRAINT_NAME,
    DECODE(CT.CONSTRAINT_TYPE,
	    'P','PRIMARY_KEY',
	    'R','FOREIGN_KEY',
	    'C','CHECK',
	    'U','UNIQUE',   
    ''),
    DECODE(CT.GENERATED,'USER NAME',1,0), 
    CC.COLUMN_NAME,
    CC.POSITION,
    CT.SEARCH_CONDITION,
    cx.table_name ,
    cx.COLUMN_NAME ,
    CT.DELETE_RULE
FROM
    USER_CONSTRAINTS CT,
    USER_CONS_COLUMNS CC,
    (SELECT 
    		x1.table_name,
		    x2.column_name,
		    x1.constraint_name
		  FROM USER_CONSTRAINTS x1,
		    USER_CONS_COLUMNS x2
		  WHERE x1.CONSTRAINT_TYPE = 'P'
			  AND x1.TABLE_NAME        = x2.TABLE_NAME
			  AND x1.CONSTRAINT_NAME   = x2.CONSTRAINT_NAME
 	 ) CX
WHERE
    CT.CONSTRAINT_TYPE NOT IN ('O')
    AND CT.TABLE_NAME = CC.TABLE_NAME
    AND CT.CONSTRAINT_NAME = CC.CONSTRAINT_NAME
    AND CX.CONSTRAINT_NAME(+) = ct.r_constraint_name
    <#if include?? && include?size != 0>
    	AND CT.TABLE_NAME in (
    		<#list include as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
		)
    </#if>
    <#if exclude?? && exclude?size != 0>
	   AND CT.TABLE_NAME not in (
	   		<#list exclude as x>
    			'${x}'<#if x_has_next>,</#if>
    		</#list>
	   ) 
	</#if>
ORDER BY
    CT.TABLE_NAME,
    CT.CONSTRAINT_NAME,
    CC.POSITION