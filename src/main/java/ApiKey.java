public record ApiKey (
        int api_key,
        int min_version,
        int max_version,
        int tag_buffer
){
    public static int SIZE = 7;

    public byte[] toBytes() {
        byte[] responseBytes = new byte[7];

        byte[] apiKeyBytes = ParsingUtils.int16ToByteArray(api_key);
        System.arraycopy(apiKeyBytes, 0, responseBytes, 0, 2);

        byte[] minVersionBytes = ParsingUtils.int16ToByteArray(min_version);
        System.arraycopy(minVersionBytes, 0, responseBytes, 2, 2);

        byte[] maxVersionBytes = ParsingUtils.int16ToByteArray(max_version);
        System.arraycopy(maxVersionBytes, 0, responseBytes, 4, 2);

        byte[] tagBufferByte = ParsingUtils.int8ToByteArray(tag_buffer);
        System.arraycopy(tagBufferByte, 0, responseBytes, 6, 1);

        return responseBytes;
    }
}
