package api.models;

import io.restassured.response.ResponseBodyExtractionOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto {
    private int statusCode;
    private String statusMessage;
    private ResponseBodyExtractionOptions body;
    private String contentType;
}
