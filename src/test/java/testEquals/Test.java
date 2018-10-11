package testEquals;

public class Test {

//    public static void main(String[] args) throws MalformedURLException {
//        for (int i = 0; i < 100; i++) {
//            double d = Math.random() * 9;
//            System.out.println(d);
//            System.out.println((int) ((d + 1) * 100000));
//        }
//
//        String str = "http://www.aijiayou.com/student/m/study/detail/11111.xhtml?fdomain=MERCER&return_url=http://www.baidu.com";
//        URL url = new URL(str);
//        System.out.println(url.getPath());
//        System.out.println(url.getPath());
//    }

    public static void main(String[] args) {

    }
}


class Annoyance extends Exception {
}

class Sneeze extends Annoyance {
}

class Human {

    public static void main(String[] args)
            throws Exception {
        try {
            try {
                throw new Sneeze();
            } catch (Sneeze a) {
                System.out.println("Caught Sneneze");
                throw a;
            }
        }
        catch (Annoyance s) {
            System.out.println("Caught Annoyance");
            return;
        } finally {
            System.out.println("Hello World!");
        }
    }
}
