public class KafkaResponse {
    private final int correlationId;
    private final int messageSize;
    private final byte[] messageBytes;

    public byte[] getMessageBytes() {

        return messageBytes;
    }

    public KafkaResponse(int messageSize, int correlationId) {

        this.messageSize = messageSize;
        this.correlationId = correlationId;
        this.messageBytes = toBytes();
    }

    public int getCorrelationId() {

        return correlationId;
    }

    public int getMessageSize() {

        return messageSize;
    }

    private byte[] toBytes() {
        byte[] response = new byte[8];

        byte[] messageSizeBytes = ParsingUtils.intToByteArray(getMessageSize());
        System.arraycopy(messageSizeBytes, 0, response, 0, 4);

        byte[] correlationIdBytes = ParsingUtils.intToByteArray(getCorrelationId());
        System.arraycopy(correlationIdBytes, 0, response, 4, 4);

        return response;
    }

}
