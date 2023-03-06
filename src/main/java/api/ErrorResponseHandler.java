package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.function.Consumer;

public class ErrorResponseHandler implements Consumer<Response> {
    @Override
    public void accept(Response response) {
        String errorMessage = getErrorMessage(response);

        throw new RestAssuredApiClientError(errorMessage, response);
    }

    private String getErrorMessage(Response response) {
        String contextType = response.contentType().split(";")[0];
        if (ContentType.HTML.matches(contextType)) {
            return response.htmlPath().get("html.body").toString();
        }

        if (ContentType.JSON.matches(contextType)) {
            return response.body().asPrettyString();
        }

        if (ContentType.TEXT.matches(contextType)) {
            return response.body().asString();
        }

        return "Error has occurred";
    }
}
