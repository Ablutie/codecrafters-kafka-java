public class KafkaResponse {
    private final int correlationId;
    private final int messageSize;
    private final byte[] body;
    private final byte[] messageBytes;

    public byte[] getMessageBytes() {

        return messageBytes;
    }

    public KafkaResponse(int messageSize, int correlationId, byte[] body) {

        this.messageSize = messageSize;
        this.correlationId = correlationId;
        this.body = body;
        this.messageBytes = toBytes();
    }

    public int getCorrelationId() {

        return correlationId;
    }

    public int getMessageSize() {

        return messageSize;
    }

    public byte[] getBody() {
        return body;
    }

    private byte[] toBytes() {
        byte[] body = getBody();
        byte[] response = new byte[8 + body.length];

        byte[] messageSizeBytes = ParsingUtils.int32ToByteArray(getMessageSize());
        System.arraycopy(messageSizeBytes, 0, response, 0, 4);

        byte[] correlationIdBytes = ParsingUtils.int32ToByteArray(getCorrelationId());
        System.arraycopy(correlationIdBytes, 0, response, 4, 4);

        System.arraycopy(body, 0, response, 8, body.length);

        return response;
    }

}
