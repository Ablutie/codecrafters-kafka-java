public class KafkaResponse {
    private int correlationId;
    private int messageSize;

    public KafkaResponse(int correlationId, int messageSize) {

        this.correlationId = correlationId;
        this.messageSize = messageSize;
    }

    public int getCorrelationId() {

        return correlationId;
    }

    public void setCorrelationId(int correlationId) {

        this.correlationId = correlationId;
    }

    public int getMessageSize() {

        return messageSize;
    }

    public void setMessageSize(int messageSize) {

        this.messageSize = messageSize;
    }

    public byte[] toByteArray() {
        byte[] response = new byte[8];

        byte[] messageSizeBytes = intToByteArray(getMessageSize());
        System.arraycopy(messageSizeBytes, 0, response, 0, 4);

        byte[] correlationIdBytes = intToByteArray(getCorrelationId());
        System.arraycopy(correlationIdBytes, 0, response, 3, 4);

        return response;
    }

    private byte[] intToByteArray(int intNumber) {
        byte[] byteArr = new byte[4];
        byteArr[0] = (byte) ((intNumber >> 24) & 0xff);
        byteArr[1] = (byte) ((intNumber >> 16) & 0xff);
        byteArr[2] = (byte) ((intNumber >> 8) & 0xff);
        byteArr[3] = (byte) ((intNumber >> 24) & 0xff);
        return byteArr;
    }
}
