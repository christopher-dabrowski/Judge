import java.util.Scanner;

public class ProxyPlayer1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int heigh = Integer.parseInt(scanner.next());
        ok();
        String fields = scanner.next();
        ok();
        String info = scanner.next();
        if (info.compareTo("START") == 0) {
            int[] y = {0, 0}, x = {0, 1};
            print(y[0]++, x[0], y[1]++, x[1]);
            for (int i = 0; scanner.hasNext(); i++) {
                info = scanner.next();
                print(y[0]++, x[0], y[1]++, x[1]);
            }
        } else {
            int[] y = {0, 0}, x = {0, 1};
            for (int i = 0; scanner.hasNext(); i++) {
                info = scanner.next();
                print(y[0]++, x[0], y[1]++, x[1]);
            }
        }
    }

    private static void ok() {
        System.out.println("ok");
    }

    private static void print(int y1, int x1, int y2, int x2) {
        System.out.printf("{%d;%d},{%d;%d}\r\n", y1, x1, y2, x2);
    }
}
