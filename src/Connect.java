import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect implements Closeable {

    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;

    public Connect(ServerSocket serverSocket) throws IOException {
        this.socket = serverSocket.accept();
        System.out.println("client connected");
        this.writer = createWriter();
        this.reader = createReader();
    }

    public Connect(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        this.writer = createWriter();
        this.reader = createReader();
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            //to do log
            throw new RuntimeException();
        }
    }

    public void write(String str) {
        try {
            writer.write(str);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            //to do log
            throw new RuntimeException();
        }
    }

    private BufferedWriter createWriter() {
        try {
            return new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            //to do log
            throw new RuntimeException();
        }
    }

    private BufferedReader createReader() {
        try {
            return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            //to do log
            throw new RuntimeException();
        }
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
        this.writer.close();
        this.reader.close();
    }
}
