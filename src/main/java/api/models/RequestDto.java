package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class RequestDto {
    private String contentType;
    private String resourceLink;
    private Map<String, Object> queryParams;
    private Map<String, String> pathParams;
    private Object body;
}
