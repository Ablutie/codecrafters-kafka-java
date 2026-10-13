import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class KafkaRequestHandler implements Runnable {
    private final Socket clientSocket;

    public KafkaRequestHandler(Socket clientSocket) {

        this.clientSocket = clientSocket;
    }

    public void run() {
        try (DataOutputStream writer = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream reader = new DataInputStream(clientSocket.getInputStream())) {

            while(true) {
                byte[] inputBytes = readInput(reader);
                System.out.println("Input request: " + ParsingUtils.byteArrayToHex(inputBytes));

                KafkaApi kafkaApi = new KafkaApi();
                KafkaRequest request = new KafkaRequest(inputBytes);

                KafkaResponse response = kafkaApi.supportsVersion(request);
                byte[] responseBuf = response.toBytes();

                System.out.println("Output: " + ParsingUtils.byteArrayToHex(responseBuf));
                writer.write(responseBuf);
                writer.flush();
            }
        } catch (EOFException eof) {
            System.err.println("Client closed connection: " + eof.getMessage());
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private static byte[] readInput(DataInputStream inputStream) throws IOException {
        int messageSize = inputStream.readInt();

        byte[] result = new byte[messageSize + 4];
        byte[] messageSizeAsBytes = ParsingUtils.int32ToByteArray(messageSize);

        System.arraycopy(messageSizeAsBytes, 0, result, 0, 4);
        inputStream.readFully(result, 4, messageSize);

        return result;
    }
}
