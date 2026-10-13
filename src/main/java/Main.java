import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        // You can use print statements as follows for debugging, they'll be visible when running tests.
        System.err.println("Logs from your program will appear here!");

        // TODO: Uncomment the code below to pass the first stage
        //
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        int port = 9092;
        try {
            serverSocket = new ServerSocket(port);
            // Since the tester restarts your program quite often, setting SO_REUSEADDR
            // ensures that we don't run into 'Address already in use' errors
            serverSocket.setReuseAddress(true);

            clientSocket = serverSocket.accept();
            System.out.println("Accepted new connection: " + clientSocket.toString());
            // Wait for connection from client.
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
//                reader.close();
//                writer.close();
                }
            } catch (EOFException eof) {
                System.err.println("Client closed connection: " + eof.getMessage());
            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
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
