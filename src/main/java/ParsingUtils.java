public class ParsingUtils {

    public static byte[] intToByteArray(int intNumber) {
        byte[] byteArr = new byte[4];
        byteArr[0] = (byte) ((intNumber >> 24) & 0xff);
        byteArr[1] = (byte) ((intNumber >> 16) & 0xff);
        byteArr[2] = (byte) ((intNumber >> 8) & 0xff);
        byteArr[3] = (byte) (intNumber & 0xff);
        return byteArr;
    }

    public static int fromInt32(byte[] intBytes) {
        int result = Byte.toUnsignedInt(intBytes[0]);

        for (int i = 1; i <= 3; i++) {
            result = ((result << 8) + Byte.toUnsignedInt(intBytes[i]));
        }

        return result;
    }
}
