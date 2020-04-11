package datvtp.utils;

import java.io.Serializable;
import java.util.Random;

public class RandomCode implements Serializable {

    static Random rd = new Random();

    public static String getRandomCode() {
        String rdCode = "";
        for (int i = 0; i < 6; i++) {
            int temp = rd.nextInt(10);
            rdCode += temp;
        }
        return rdCode;
    }
}
