package workspan;

import java.util.HashMap;
import java.util.Random;

public class GenerateRandom {
	
	//To generate random entityId
	Long getRandom(HashMap<Long, Entity> entityMapping) {
		int newId = new Random().nextInt(10000);
		while(entityMapping.containsKey(newId)){
			newId = new Random().nextInt(10000);
		}
		Long newIdLong = new Long(newId);
		return newIdLong;
	}
}