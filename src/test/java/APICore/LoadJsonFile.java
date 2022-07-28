package APICore;

import java.io.FileReader;
import java.io.IOException;

public class LoadJsonFile {

    public String loadFileJson(String jsonFile) {
        String body = null;
        String filepath = System.getProperty("user.dir") + "/src/test/java/data/";
        System.out.println(filepath);
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
        try {
            Object obj = parser.parse(new FileReader(filepath + jsonFile));
            body = obj.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return body;
    }
}
