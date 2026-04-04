import java.util.List;

public class KafkaApi {

    public KafkaResponse supportsVersion(KafkaRequest request) {
        int version = request.getRequestApiVersion();
        if (version >= 0 && version <= 4) {
            ApiVersionsResponse body = new ApiVersionsResponse(1,
                    List.of(new ApiKey(18, 0, 4, 0)),
                            0,
                            0);
            return new KafkaResponse(request.getCorrelationId(), body.toBytes());
        }
        return new KafkaResponse(request.getCorrelationId(),
                new ErrorResponse(ErrorCode.UNSUPPORTED_VERSION.code).toBytes());
    }
}
