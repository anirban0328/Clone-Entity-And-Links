package workspan;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CloneEntityAndLinks {	
	
	 //Main logic which takes care of cloning the input Entity and updating the links
	 void clone(JSONArray entityList, JSONArray links, HashMap<Long, Entity> entityMapping, HashMap<Long, ArrayList<Long>> linksMap, Long input, String filePath) 
			throws FileNotFoundException {
		Entity source = entityMapping.get(input);
		if(source==null)
			return;
		
		CloneEntityAndLinks cloneEntityLinks = new CloneEntityAndLinks();
		FileWriterUtil fileWriterUtil = new FileWriterUtil();
		HashMap<Long, Long> newOldMapping = new HashMap<Long, Long>();
		Queue<Entity> entityQueue = new LinkedList<Entity>(); //using queue to do BFS
		HashSet<Long> visited = new HashSet<Long>(); //to keep track of visited entities to avoid infinite loop in case of cycles
		entityQueue.add(source);
		
		while(!entityQueue.isEmpty()) {
			Entity target = entityQueue.remove();
			visited.add(target.getEntityId());
			
			GenerateRandom generateRandom = new GenerateRandom();
			Long newIdLong = generateRandom.getRandom(entityMapping);
			Entity cloneTarget = new Entity(newIdLong,target.getName(),target.getDescription());
			cloneEntityLinks.addToEntityList(cloneTarget, entityList);
			newOldMapping.put(target.getEntityId(),newIdLong); //Storing a map of entityId and its cloned entityId for adding new links for cloned entity
			
			ArrayList<Long> childrenLinks = linksMap.get(target.getEntityId());
			if(childrenLinks != null){
				for(int i=0; i<childrenLinks.size(); i++){
					if(!visited.contains(childrenLinks.get(i)))
						entityQueue.add(entityMapping.get(childrenLinks.get(i)));
				}
			}
			
		}
		cloneEntityLinks.addLinkForSource(newOldMapping,linksMap,links,input); 
		cloneEntityLinks.addToLinksList(newOldMapping,linksMap,links);
		fileWriterUtil.writeToFile(entityList,links,filePath); //write to the output file
	}
	
	//Adding the new cloned entity to the Entity List
	@SuppressWarnings("unchecked")
	void addToEntityList(Entity cloneTarget, JSONArray entityList) {
		JSONObject jo = new JSONObject(); 
		jo.put("entity_id", cloneTarget.getEntityId()); 
		jo.put("name", cloneTarget.getName());
		if(cloneTarget.getDescription()!=null)
			jo.put("description", cloneTarget.description);
		entityList.add(jo);
	}
	
	//Adding links from source to destination for cloned entity
	@SuppressWarnings("unchecked")
	void addToLinksList(HashMap<Long, Long> newOldMapping, HashMap<Long, ArrayList<Long>> linksMap, JSONArray links) {
		for (Entry<Long, Long> entry : newOldMapping.entrySet()){
			Long id = entry.getKey();
			Long newId = entry.getValue();
			
			ArrayList<Long> connectingEntities = linksMap.get(id);
			if(connectingEntities != null){
				for(int i=0; i<connectingEntities.size(); i++){
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("from", newId);
					jsonObj.put("to", newOldMapping.get(connectingEntities.get(i)));
					links.add(jsonObj);
				}
			}
		}
	}
	
	//Adding incoming links to source for cloned entity
	@SuppressWarnings("unchecked")
	void addLinkForSource(HashMap<Long, Long> newOldMapping, HashMap<Long, ArrayList<Long>> linksMap, JSONArray links, Long input) {
		Long newIdSource = newOldMapping.get(input);
		for (Entry<Long, ArrayList<Long>> entry : linksMap.entrySet()){
			ArrayList<Long> al = entry.getValue();
			if(al.contains(input)){
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("from", entry.getKey());
				jsonObj.put("to", newIdSource);
				links.add(jsonObj);
			}		
		}
	}
}
