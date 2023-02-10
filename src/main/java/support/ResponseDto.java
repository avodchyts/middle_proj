package support;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    public int statusCode;
    public String statusMessage;
}
