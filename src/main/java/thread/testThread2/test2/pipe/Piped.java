package thread.testThread2.test2.pipe;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 测试6：管道输入、输出流
 * <p>
 * main线程输入，PrintThread线程输出
 */
public class Piped {

    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while (-1 != (receive = in.read())) {
                    System.out.print((char) receive);
                }

                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive = 0;
        while (-1 != (receive = System.in.read())) {
            out.write(receive);
        }

        out.close();
    }
}
