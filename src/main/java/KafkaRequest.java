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

    private void readFieldsFromByteArray() {

        this.messageSize = 15;
        this.requestApiKey = 18;
        this.requestApiVersion = 4;

        byte[] correlationIdBytes = new byte[4];
        System.arraycopy(messageBytes, 8, correlationIdBytes, 0, 4);
        this.correlationId = ParsingUtils.fromInt32(correlationIdBytes);
    }
}
