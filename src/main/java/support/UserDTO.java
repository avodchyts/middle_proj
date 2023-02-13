package support;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO implements IResponseDto {
    String userId;
}
