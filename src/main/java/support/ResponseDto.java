package support;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    public String statusCode;
    public String statusMessage;
}
