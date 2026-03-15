public class KafkaApi {

    public int supportsVersion(int version) {
        if (version >= 0 && version <= 3) {
            return 0;
        }
        return ErrorCode.UNSUPPORTED_VERSION.code;
    }
}
