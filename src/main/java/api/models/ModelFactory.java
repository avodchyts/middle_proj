package api.models;

import io.restassured.response.Response;
import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.function.Supplier;

import static java.lang.String.format;

public class ModelFactory implements Supplier<IResponseDto> {
    private  static  final Logger LOGGER = Logger.getLogger(ModelFactory.class);
    Supplier<IResponseDto> iResponseDtoSupplier;

    ModelFactory (Supplier<IResponseDto> iResponseDtoSupplier){
        this.iResponseDtoSupplier = iResponseDtoSupplier;
    }

    public IResponseDto get() {
        return iResponseDtoSupplier.get();
    }

    public static Supplier<IResponseDto> selectResponseDtoType(Response response, String typeName) {
        Supplier<IResponseDto> specificSupplier = null;
        if (response.getHeader("content-type").contains("application/json")) {
            try {
                if (!Objects.isNull(typeName))
                    switch (typeName) {
                        case ("Param_Response_Dto"):
                            specificSupplier = ParamResponseDto::new;
                            break;
                        case ("Response_dto"):
                            specificSupplier = (Supplier<IResponseDto>) ResponseDto.builder().statusCode(response.statusCode()).statusMessage(response.getStatusLine()).build();
                            break;
                        case ("User_Dto"):
                            specificSupplier = (Supplier<IResponseDto>) UserDTO.builder().userId(response.jsonPath().getString("userId")).build();
                            break;
                        default:
                            LOGGER.info("Unknown name");
                    }

            } catch (NullPointerException e) {
                LOGGER.info(e.getMessage());
                throw new IllegalArgumentException("Unsupported class");
            }

        } else {
            throw new IllegalArgumentException("Unsupported content-type");
        }
        return specificSupplier;
    }
}
