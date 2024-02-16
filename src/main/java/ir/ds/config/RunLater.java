/**
 *
 */
package ir.ds.config;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ahmadReza
 */
public class RunLater {

    private static RunLater instance;

    private RunLater() {
    }

    public static synchronized RunLater getInstance() {
        if (instance == null) {
            instance = new RunLater();
        }
        return instance;
    }

    public ExecutorService executor = Executors.newFixedThreadPool(10);

    public static String generateCode() {
        String SALTCHARS = "0123456789";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
