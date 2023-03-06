package api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto<T> {
    private String contentType;
    private String resourceLink;
    private Map<String, String> headers;
    private Map<String, Object> queryParams;
    private Map<String, String> pathParams;
    private T body;
}
