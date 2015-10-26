package baseline;

public class BaseLineContext {
	
		SchemaManager schemaManager;
		
		public BaseLineContext() {
			schemaManager = new SchemaManager(this);
			
		}
		
		public SchemaManager getSchemaManager() {
			return schemaManager;
		}
	

}
