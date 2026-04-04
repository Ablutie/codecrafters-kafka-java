public class ErrorResponse {
    int errorCode;

    public ErrorResponse(int errorCode) {

        this.errorCode = errorCode;
    }

    public byte[] toBytes() {
        return ParsingUtils.int16ToByteArray(errorCode);
    }
}

