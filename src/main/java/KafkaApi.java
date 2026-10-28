import java.util.List;

public class KafkaApi {

    public KafkaResponse versionResponse(KafkaRequest request) {
        int version = request.getRequestApiVersion();
        if (version < 0 || version > 4) {
            return new KafkaResponse(request.getCorrelationId(),
                    new ErrorResponse(ErrorCode.UNSUPPORTED_VERSION.code).toBytes());
        }

        ApiVersionsResponse versionResponse = new ApiVersionsResponse(1,
                List.of(new ApiKey(18, 0, 4, 0),
                        new ApiKey(75, 0, 0, 0)),
                        0,
                        0);

        return new KafkaResponse(request.getCorrelationId(), versionResponse.toBytes());

    }
}
