import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    public static byte[] int32ToByteArray(int intNumber) {
        return intToByteArray(intNumber, 4);
    }

    public static byte[] int16ToByteArray(int intNumber) {
        return intToByteArray(intNumber, 2);
    }

    public static byte[] int8ToByteArray(int intNumber) {
        return intToByteArray(intNumber, 1);
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
        return intFromBytes(intBytes, 4);
    }

    public static int fromInt16(byte[] intBytes) {
        return intFromBytes(intBytes, 2);
    }

    public static List<String> byteArrayToHex(byte[] arr) {
        List<String> hexList = new ArrayList<>();
        for (byte currentByte : arr) {
            char[] hexDigits = new char[2];
            hexDigits[0] = Character.forDigit((currentByte >> 4) & 0xf, 16);
            hexDigits[1] = Character.forDigit(currentByte & 0xf, 16);
            hexList.add(new String(hexDigits));
        }
        return hexList;
    }
}
