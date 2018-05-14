package testSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketTest {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 6789);
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入内容：");
        String msg = scanner.nextLine();
        scanner.close();

        PrintWriter pw = new PrintWriter(client.getOutputStream());
        pw.println(msg);
        pw.flush();
        BufferedReader bufr = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println(bufr.readLine());
        bufr.close();
    }
}

class EchoServer{
    private static final int ECHO_SERVER_PORT=6789;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(ECHO_SERVER_PORT)) {
            System.out.println("服务已启动。。。");
            while(true){
                final Socket client = server.accept();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BufferedReader bufr = null;
                        PrintWriter pw = null;
                        try {
                            bufr = new BufferedReader(new InputStreamReader(client.getInputStream()));
                            pw = new PrintWriter(client.getOutputStream());
                            String msg = bufr.readLine();
                            System.out.println("收到" + client.getInetAddress() + "发送的：" + msg);
                            pw.println(msg);
                            pw.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        } catch (IOException e) {

        }
    }
}
