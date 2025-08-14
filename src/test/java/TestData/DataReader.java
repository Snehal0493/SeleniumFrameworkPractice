package TestData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	
	@SuppressWarnings("deprecation")
	public void getJsonData() throws IOException
	{
		//read json to string
		String JsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/java/TestData/login.json"));
		
		//String to hashmap 
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<HashMap<String, String>> data = mapper.readValue(
		        JsonContent, new TypeReference<List<HashMap<String, String>>>() {}
		    );
		
	}

}
