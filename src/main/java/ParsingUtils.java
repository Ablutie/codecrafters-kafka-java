public class ParsingUtils {

    public static byte[] int32ToByteArray(int intNumber) {
        return intToByteArray(intNumber, 4);
    }

    public static byte[] int16ToByteArray(int intNumber) {
        return intToByteArray(intNumber, 2);
    }

    private static byte[] intToByteArray(int intNumber, int arraySize) {
        byte[] byteArr = new byte[arraySize];
        for (int i = 0; i < arraySize; i++) {
            byteArr[i] = (byte) ((intNumber >> (8 * (arraySize - 1 - i)) & 0xff));
        }
        return byteArr;
    }

    public static int intFromBytes(byte[] intBytes, int arraySize) {
        int result = Byte.toUnsignedInt(intBytes[0]);

        for (int i = 1; i < arraySize; i++) {
            result = ((result << 8) + Byte.toUnsignedInt(intBytes[i]));
        }

        return result;
    }

    public static int fromInt32(byte[] intBytes) {
//        int result = Byte.toUnsignedInt(intBytes[0]);
//
//        for (int i = 1; i <= 3; i++) {
//            result = ((result << 8) + Byte.toUnsignedInt(intBytes[i]));
//        }
//
//        return result;
        return intFromBytes(intBytes, 4);
    }

    public static int fromInt16(byte[] intBytes) {
        return intFromBytes(intBytes, 2);
    }
}
