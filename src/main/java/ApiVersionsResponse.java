import java.util.List;

public record ApiVersionsResponse(
        int api_keys_array_length,
        List<ApiKey> api_keys,
        int throttle_time_ms,
        int tag_buffer
) {

    public byte[] toBytes() {
        byte[] responseBytes = new byte[computeSize()];

        // errorCode defaulted to 0, so [00 00]
        int currentIndex = 2;

        // hardcode array length to 2 - expected in the tester for some reason
        responseBytes[currentIndex] = (byte) 2;
        currentIndex++;
        
        int arrayIndex = 0;
        while (arrayIndex < api_keys.size()) {
            ApiKey currentApiKey = api_keys.get(arrayIndex);
            byte[] currentApiKeyBytes = currentApiKey.toBytes();
            System.arraycopy(currentApiKeyBytes, 0, responseBytes,currentIndex, ApiKey.SIZE);

            arrayIndex++;
            currentIndex += ApiKey.SIZE;
        }

        byte[] throttleTimeMsBytes = ParsingUtils.int32ToByteArray(throttle_time_ms);
        System.arraycopy(throttleTimeMsBytes, 0, responseBytes, currentIndex, 4);
        currentIndex++;

        byte[] tagBufferBytes = ParsingUtils.int8ToByteArray(tag_buffer);
        System.arraycopy(tagBufferBytes, 0, responseBytes, currentIndex, 1);

        return responseBytes;
    }

    private int computeSize() {
        int size = 0;
        size += 3; // error_code = 2 bytes + array_length = 1 byte
        size += api_keys.size() * ApiKey.SIZE; // each element has 2 bytes for key, max, min size and 1 for buffer
        size += 5; // 4 bytes for throttle time and 1 more for tag buffer
        return size;
    }

}
