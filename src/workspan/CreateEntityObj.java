package workspan;

import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateEntityObj {
	
	//creates Map of entityId and Entity object
	HashMap<Long, Entity> createEntityObject(JSONArray entityList) {
		HashMap<Long, Entity> entityMapping = new HashMap<Long, Entity>();
		Iterator<?> itr = entityList.iterator(); 
        
        while (itr.hasNext()) { 
        	Object pair = itr.next(); 
        	JSONObject jsonObj = (JSONObject) pair;
        	Long entityId = (Long)jsonObj.get("entity_id");
        	String name = (String)jsonObj.get("name");
        	String description = (String)jsonObj.get("description");	
        	entityMapping.put(entityId, new Entity(entityId,name,description));
        }
        return entityMapping;
    }
}
