package workspan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateLink {
	
	// Creates map of sourceId to its neighboring connected Id
	HashMap<Long, ArrayList<Long>> createLinks(JSONArray links) {
		HashMap<Long, ArrayList<Long>> linkMapping = new HashMap<Long, ArrayList<Long>>();
		Iterator<?> itr = links.iterator(); 
		while (itr.hasNext()) { 
			Object pair = itr.next(); 
    		JSONObject jsonObj = (JSONObject) pair;
    		Long from = (Long)jsonObj.get("from");
    		Long to = (Long)jsonObj.get("to");
    		
    		ArrayList<Long> linksList = new ArrayList<Long>();
    		if(linkMapping.containsKey(from)){
    			linksList = linkMapping.get(from);
    		}
    		linksList.add(to);
    		linkMapping.put(from, linksList);
        }
		return linkMapping;
	}
}
