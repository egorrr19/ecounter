import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Direct {
    ArrayList<Double> xi = new ArrayList<>();
    ArrayList<Double> Ux = new ArrayList<>();
    double R;
    double U;
    double x = 0;
    double S = 0;
    double UPN, TPN, BPN;
    double deltaX, deltaXB, deltaXA, thetaX, deltaXAR, xR;
    public Direct(double[] sample, double theta) throws IOException {
        Arrays.sort(sample);
        R = sample[sample.length - 1] - sample[0];
        BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream("C://Program Files/ecounter/data")));
        String line = data.readLine();
        boolean isTPN = false;
        boolean isBPN = false;
        boolean isUPN = false;
        double[] n = new double[2];
        String[] l = new String[2];
        while (line != null){
            if (isUPN || isTPN || isBPN){
                l = line.split(" ");
                n[0] = Double.parseDouble(l[0]);
                n[1] = Double.parseDouble(l[1]);
                if (isTPN && n[0] == sample.length){
                    TPN = n[1];
                    isTPN = false;
                }
                if (isBPN && n[0] == sample.length){
                    BPN = n[1];
                    isBPN = false;
                }
                if (isUPN && n[0] == sample.length){
                    UPN = n[1];
                    U = UPN * R;
                    isUPN = false;
                }
            } else {
                if (line.equals("TPN")){
                    isTPN = true;
                }
                if (line.equals("BPN")){
                    isBPN = true;
                }
                if (line.equals("UPN")){
                    isUPN = true;
                }
            }
            line = data.readLine();
        }
        data.close();
        //Can be change in various OS
        data = new BufferedReader(new InputStreamReader(new FileInputStream("C://Program Files/ecounter/data")));
        for (int i = 0; i < sample.length - 1; i++){
            Ux.add(sample[i + 1] - sample[i]);
        }
        for (int i = 0; i < sample.length; i++) {
            x += sample[i];
        }
        x /= sample.length;
        for (int i = 0; i < sample.length; i++) {
            xi.add(sample[i]);
            S += Math.pow(sample[i] - x, 2);
        }
        S = Math.sqrt(S / (sample.length * (sample.length - 1)));
        deltaX = TPN * S;
        deltaXB = BPN * R;
        thetaX = theta;
        deltaXA = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(thetaX, 2));
        double scale = 1.0;
        deltaXAR = deltaXA;
        while (deltaXAR < 2 || deltaXAR >= 20){
            if (deltaXAR < 2){
                deltaXAR *= 10;
                scale *= 10;
            } else {
                deltaXAR /= 10;
                scale /= 10;
            }
        }
        deltaXAR = Math.round(deltaXAR) / scale;
        xR = Math.round(x * scale) / scale;
    }

    @Override
    public String toString() {
        return "Отсортированная выборка:\n" + xi +
                "\nR = " + R +
                "\nUxi = Xi+1 - Xi = \n" + Ux +
                "\nUx = Upn * R = " + U +
                "\nСреднее значение выборки = " + x +
                "\nСКО = " + S +
                "\nДельта x (случайная погрешность) = " + deltaX +
                "\nСлучайная погрешность, рассчитанная по размаху выборки = " + deltaXB +
                "\nПриборная погрешность = " + thetaX +
                "\nПолная погрешность = " + deltaXA +
                "\nx = " + xR + " +- " + deltaXAR +
                "\nПогрешность составляет " + Math.round((deltaXAR / xR) * 100) + " %\n"
                ;
    }
}
