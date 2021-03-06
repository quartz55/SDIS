package Utils;

import java.io.File;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.Random;

public class Utils {
    public static int random(int low, int upper) {
        if(low > upper) throw new IllegalArgumentException("Low("+low+") needs to be lower or equal to upper("+upper+")");
        Random rand = new Random();
        return rand.nextInt(upper+1-low) + low;
    }
    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
    public static String getFileID(File f) {
        return sha256(f.getName() + f.lastModified());
    }

    public static String getIPv4() {
        try {
            return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                    .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                    .filter(ip -> ip instanceof Inet4Address && ip.isSiteLocalAddress())
                    .findFirst().orElseThrow(RuntimeException::new)
                    .getHostAddress();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
