package workspan;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class ParseInput {
	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		if(args.length!=2)
			return;
		String filePath = args[0];
		if(filePath==null || filePath.equals(""))
			return;
		Long input = Long.valueOf(args[1]);
        try (FileReader reader = new FileReader(filePath)) {
            Object inputObject = jsonParser.parse(reader);
            ParseInput parseInputObj = new ParseInput();
            parseInputObj.parseEntityAndLinks(inputObject, input, filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
	
	//Parsing of the input file to get entities and its links
	private void parseEntityAndLinks(Object inputObject, Long input, String filePath) throws FileNotFoundException {
		JSONObject jsonObj = (JSONObject) inputObject;
		JSONArray entityList = (JSONArray) jsonObj.get("entities"); 
		JSONArray links = (JSONArray) jsonObj.get("links"); 
		
		if(entityList==null || links==null)
			return;
		
		CreateEntityObj createEntity = new CreateEntityObj();
		CreateLink createLink = new CreateLink();
		CloneEntityAndLinks cloneEntityLink = new CloneEntityAndLinks();
		HashMap<Long, Entity> entityMap = createEntity.createEntityObject(entityList); //map of entityId to Entity Object
		HashMap<Long, ArrayList<Long>> linksMap = createLink.createLinks(links); // map of sourceId to its neighboring connected Id
		cloneEntityLink.clone(entityList, links, entityMap, linksMap, input, filePath);
    }
}
