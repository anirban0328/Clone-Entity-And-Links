package workspan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

public class CloneEntityAndLinksTestCases {

	@SuppressWarnings("unchecked")
	@Test
	public void CreateEntityObjectTest() {
		CreateEntityObj createEntityObj = new CreateEntityObj();
		//String json = "[{\"name\":\"EntityA\",\"entity_id\":3},{\"name\":\"EntityB\",\"entity_id\":5},{\"name\":\"EntityC\",\"description\":\"More details about entity C\",\"entity_id\":7},{\"name\":\"EntityD\",\"entity_id\":11}]";
		JSONArray arrayObj = new JSONArray();
		JSONObject jo = new JSONObject();
		Long id = 3L;
		String name = "EntityA";
		String description = "test";
		jo.put("entity_id", id);
		jo.put("name", name);
		jo.put("description", description);
		arrayObj.add(jo);
		Entity en = new Entity(id,name,description);		
		HashMap<Long, Entity> entityMapping = createEntityObj.createEntityObject(arrayObj);

		assertNotNull(entityMapping);
		assertNotNull(en);
		assertEquals(entityMapping.get(3L).getName(), en.getName());
		assertEquals(entityMapping.get(3L).getDescription(), en.getDescription() );
		assertEquals(entityMapping.get(3L).getEntityId(), en.getEntityId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void CreateLinkTest() {
		CreateLink createLink = new CreateLink();
		JSONArray arrayObj = new JSONArray();
		JSONObject jo = new JSONObject();
		Long source = 3L;
		Long dest = 5L;
		jo.put("from", source);
		jo.put("to", dest);
		arrayObj.add(jo);
		dest = 7L;
		jo.put("from", source);
		jo.put("to", dest);
		arrayObj.add(jo);
		HashMap<Long, ArrayList<Long>> entityMapping = createLink.createLinks(arrayObj);

		assertNotNull(entityMapping);
		assertTrue(entityMapping.size()==1);
		assertTrue(entityMapping.get(3L).size()==2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void addToEntityListTest() {
		CloneEntityAndLinks cloneEntityAndLinks = new CloneEntityAndLinks();
		JSONArray arrayObj = new JSONArray();
		JSONObject jo = new JSONObject();
		Long id = 3L;
		String name = "EntityA";
		String description = "test";
		jo.put("entity_id", id);
		jo.put("name", name);
		jo.put("description", description);
		arrayObj.add(jo);
		
		id = 4L;
		name = "EntityB";
		description = "testB";
		Entity en = new Entity(id,name,description);
		
		assertNotNull(en);
		assertNotNull(arrayObj);
		assertTrue(arrayObj.size()==1);
		
		cloneEntityAndLinks.addToEntityList(en, arrayObj);
		assertTrue(arrayObj.size()==2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void addToLinksListTest() {
		CloneEntityAndLinks cloneEntityAndLinks = new CloneEntityAndLinks();
		HashMap<Long, Long> newOldMapping = new HashMap<Long, Long>();
		newOldMapping.put(2L, 300L);
		
		HashMap<Long, ArrayList<Long>> linksMap = new HashMap<Long, ArrayList<Long>>();
		ArrayList<Long> al = new ArrayList<Long>();
		al.add(6L);
		linksMap.put(2L, al);
		
		JSONArray arrayObj = new JSONArray();
		JSONObject jo = new JSONObject();
		Long from = 2L;
		Long to = 6L;
		jo.put("from", from);
		jo.put("to", to);
		arrayObj.add(jo);
		
		assertNotNull(arrayObj);
		assertTrue(arrayObj.size()==1);
		cloneEntityAndLinks.addToLinksList(newOldMapping,linksMap,arrayObj);
		assertTrue(arrayObj.size()==2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void addLinkForSourceTest() {
		CloneEntityAndLinks cloneEntityAndLinks = new CloneEntityAndLinks();
		HashMap<Long, Long> newOldMapping = new HashMap<Long, Long>();
		newOldMapping.put(2L, 300L);
		
		HashMap<Long, ArrayList<Long>> linksMap = new HashMap<Long, ArrayList<Long>>();
		ArrayList<Long> al = new ArrayList<Long>();
		al.add(2L);
		linksMap.put(8L, al);
		
		Long input = 2L;
		JSONArray arrayObj = new JSONArray();
		JSONObject jo = new JSONObject();
		Long from = 8L;
		Long to = 2L;
		jo.put("from", from);
		jo.put("to", to);
		arrayObj.add(jo);
		
		assertNotNull(arrayObj);
		assertTrue(arrayObj.size()==1);
		cloneEntityAndLinks.addLinkForSource(newOldMapping,linksMap,arrayObj,input);
		assertTrue(arrayObj.size()==2);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void GenerateRandomTest() {
		CreateEntityObj createEntityObj = new CreateEntityObj();
		GenerateRandom generateRandom = new GenerateRandom();
		JSONArray arrayObj = new JSONArray();
		JSONObject jo = new JSONObject();
		Long id = 3L;
		String name = "EntityA";
		String description = "test";
		jo.put("entity_id", id);
		jo.put("name", name);
		jo.put("description", description);
		arrayObj.add(jo);	
		HashMap<Long, Entity> entityMapping = createEntityObj.createEntityObject(arrayObj);
		Long newIdLong = generateRandom.getRandom(entityMapping);
		
		assertNotNull(newIdLong);
		assertTrue(newIdLong < 10000);
	}

}
