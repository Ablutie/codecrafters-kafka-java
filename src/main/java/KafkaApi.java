import java.util.List;

public class KafkaApi {

    public KafkaResponse versionResponse(KafkaRequest request) {
        int version = request.getRequestApiVersion();
        if (version < 0 || version > 4) {
            return new KafkaResponse(request.getCorrelationId(),
                    new ErrorResponse(ErrorCode.UNSUPPORTED_VERSION.code).toBytes());
        }

        ApiVersionsResponse versionResponse = new ApiVersionsResponse(1,
                List.of(new ApiKey(18, 0, 4, 0)),
                        0,
                        0);
        DescribeTopicsPartitionResponse describeResponse = new DescribeTopicsPartitionResponse(
                new ApiKey(75, 0, 0, 0),
                0,
                0
        );

        int finalBodySize = versionResponse.toBytes().length + describeResponse.toBytes().length;
        byte[] finalBody = new byte[finalBodySize];

        System.arraycopy(versionResponse.toBytes(), 0, finalBody, 0, versionResponse.toBytes().length);
        System.arraycopy(describeResponse.toBytes(), 0, finalBody, versionResponse.toBytes().length,
                describeResponse.toBytes().length);
        return new KafkaResponse(request.getCorrelationId(), finalBody);

    }
}
