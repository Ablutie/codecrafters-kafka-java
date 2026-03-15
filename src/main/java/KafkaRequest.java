public class KafkaRequest {

    private int messageSize;
    private int requestApiKey;
    private int requestApiVersion;
    private int correlationId;
    private byte[] messageBytes;

    public KafkaRequest(byte[] messageBytes) {

        this.messageBytes = messageBytes;
        readFieldsFromByteArray();
    }

    public int getCorrelationId() {

        return correlationId;
    }

    public int getRequestApiVersion() {

        return requestApiVersion;
    }

    private void readFieldsFromByteArray() {

        byte[] messageSizeBytes = new byte[4];
        System.arraycopy(messageBytes, 0, messageSizeBytes, 0, 4);
        this.messageSize = ParsingUtils.fromInt32(messageSizeBytes);

        byte[] requestApiKeyBytes = new byte[2];
        System.arraycopy(messageBytes, 4, requestApiKeyBytes, 0, 2);
        this.requestApiKey = ParsingUtils.fromInt16(requestApiKeyBytes);

        byte[] requestApiVersionBytes = new byte[2];
        System.arraycopy(messageBytes, 6, requestApiVersionBytes, 0, 2);
        this.requestApiVersion = ParsingUtils.fromInt16(requestApiVersionBytes);

        byte[] correlationIdBytes = new byte[4];
        System.arraycopy(messageBytes, 8, correlationIdBytes, 0, 4);
        this.correlationId = ParsingUtils.fromInt32(correlationIdBytes);
    }
}
