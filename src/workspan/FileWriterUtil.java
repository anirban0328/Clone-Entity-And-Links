package workspan;

import java.io.FileWriter;

import org.json.simple.JSONArray;

public class FileWriterUtil {
	
	//Write back to the output file
	void writeToFile(JSONArray entityList, JSONArray links, String filePath) {
		try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("{\"entities\":"+entityList.toJSONString()+",");
            fileWriter.write("\"links\":"+links.toJSONString() + "}");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}