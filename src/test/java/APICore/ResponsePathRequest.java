package APICore;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ResponsePathRequest {

    public HttpResponse<JsonNode> getPathRequestResponse(String url, String body, String auth) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.patch(url)
                    .header("content-type", "application/json")
                    .header("Authentication", auth)
                    .body(body)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return response;
    }

}
