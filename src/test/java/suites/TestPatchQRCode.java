package suites;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import helpers.PropertiesHelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import APICore.LoadJsonFile;
import APICore.ResponsePatchRequest;


public class TestPatchQRCode {
    public static String PROPERTIES = "config.properties";
    public String url = PropertiesHelper.getConfigValue(PROPERTIES, "URL");
    public String auth = PropertiesHelper.getConfigValue(PROPERTIES, "AUTHENTICATION");
    JSONParser parser = new JSONParser();
    LoadJsonFile loadFile = new LoadJsonFile();
    ResponsePatchRequest patchResponse = new ResponsePatchRequest();
    String jsonFile = "PatchQRCodeBody.Json";


    @DataProvider(name = "data-provider")
    public Object[][] dpMethod() {
        return new Object[][]{
                {"Description auto", "url", 1400, "error message"},
                {"Description auto", "url", 51000000, "error message"},
                {"Description auto", "url", "1234", "error message"},

        };
    }

    @Test
    public void verifyPatchSuccessWithAllField() {
        String body = null;
        int amount = 0;
        String description = null, callBack_Url = null;
        try {
            String file = loadFile.loadFileJson(jsonFile);
            JSONObject data = (JSONObject) parser.parse(file);
            data.put("amount", 1800);
            data.put("Description", "Automation test update");
            data.put("callBack_url", "test url");
            amount = (int) data.get("amount");
            description = data.get("Description").toString();
            callBack_Url = data.get("callBack_url").toString();
            body = new Gson().toJson(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = null;
        response = patchResponse.getPatchRequestResponse(url + "/qr_codes/:qr_code_id", body, auth);

        Assert.assertEquals(response.getStatus(), 200);
        Assert.assertEquals(response.getBody().getObject().getString("amount"), amount);
        Assert.assertEquals(response.getBody().getObject().getString("Description"), description);
        Assert.assertEquals(response.getBody().getObject().getString("callBack_url"), callBack_Url);
    }


    @Test
    public void verifyPatchSuccessWithAmount() {
        String body = null;
        int amount = 0;
        try {
            String file = loadFile.loadFileJson(jsonFile);
            JSONObject data = (JSONObject) parser.parse(file);
            data.put("amount", 200000);
            amount = (int) data.get("amount");
            body = new Gson().toJson(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = null;
        response = patchResponse.getPatchRequestResponse(url + "/qr_codes/:qr_code_id", body, auth);
        Assert.assertEquals(response.getStatus(), 200);
        Assert.assertEquals(response.getBody().getObject().getString("amount"), amount);
    }

    @Test
    public void verifyPatchWithInvalidAuth() {
        String body = null;
        try {
            String file = loadFile.loadFileJson(jsonFile);
            JSONObject data = (JSONObject) parser.parse(file);
            body = new Gson().toJson(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = null;
        response = patchResponse.getPatchRequestResponse(url + "/qr_codes/:qr_code_id", body, auth);
        Assert.assertEquals(response.getStatus(), 401);
    }

    @Test(dataProvider = "data-provider")
    public void verifyPatchValidationError(String description, String url, int amount, String message) {
        String body = null;
        try {
            String file = loadFile.loadFileJson(jsonFile);
            JSONObject data = (JSONObject) parser.parse(file);
            data.put("amount", amount);
            data.put("Description", description);
            data.put("callBack_url", url);
            body = new Gson().toJson(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = null;
        response = patchResponse.getPatchRequestResponse(url + "/qr_codes/:qr_code_id", body, auth);

        Assert.assertEquals(response.getStatus(), "API_VALIDATION_ERROR");
        Assert.assertEquals(response.getBody().getObject().getString("key"), message);
    }


    @DataProvider(name = "data-QRCode")
    public Object[][] dataQRCode() {
        return new Object[][]{
                {123, 400, "Message"},
                {456, 400, "Message"}

        };//I assume code is 400 for case not found and already in use, we can change, just put a value that int type
    }

    @Test(dataProvider = "data-QRCode")
    public void verifyPatchWithValidationQRCode(int qrCode, int statusCode, String message) {
        String body = null;
        try {
            String file = loadFile.loadFileJson(jsonFile);
            JSONObject data = (JSONObject) parser.parse(file);
            body = new Gson().toJson(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HttpResponse<JsonNode> response = null;
        response = patchResponse.getPatchRequestResponse(url + "/qr_codes/" + qrCode, body, auth);
        Assert.assertEquals(response.getStatus(), statusCode);
        Assert.assertEquals(response.getBody().getObject().getString("message"), message);
    }

}
