public class KafkaResponse {
    private final int correlationId;
    private final byte[] body;

    public KafkaResponse(int correlationId, byte[] body) {

        this.correlationId = correlationId;
        this.body = body;
    }

    public int getCorrelationId() {

        return correlationId;
    }

    public int computeMessageSize() {

        return 4 + body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public byte[] toBytes() {
        byte[] body = getBody();
        byte[] response = new byte[8 + body.length];

        byte[] messageSizeBytes = ParsingUtils.int32ToByteArray(computeMessageSize());
        System.arraycopy(messageSizeBytes, 0, response, 0, 4);

        byte[] correlationIdBytes = ParsingUtils.int32ToByteArray(getCorrelationId());
        System.arraycopy(correlationIdBytes, 0, response, 4, 4);

        System.arraycopy(body, 0, response, 8, body.length);

        return response;
    }

}
