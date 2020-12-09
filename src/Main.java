import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);
        System.out.println("Альфа-версия приложения считает только прямые погрешности.");
        System.out.println("Введите количество элементов выборки, затем приборную погрешность и саму выборку");
        int N = (int) scanner.nextDouble();
        double[] sample = new double[N];
        double theta = scanner.nextDouble();
        for (int i = 0; i < N; i++) {
            sample[i] = scanner.nextDouble();
        }
        scanner.close();
        Direct direct = null;
        try {
            direct = new Direct(sample, theta);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(direct);
    }
}
