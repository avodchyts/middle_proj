package support;

import lombok.Data;

import java.util.List;
@Data
public class ParamResponseDto implements IResponseDto {
    private String id;
    private List<String> data;
}
