
package factory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 * Network information
 * @author Ahmed Ghanem.
 */
public class NetworkFactory {

    private InetAddress address;
/***
 * get address
 * @throws UnknownHostException error on host
 */
    public NetworkFactory() {
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null,"Network problem occurred");
        }
    }
    /***
     * get host name
     * @return host name
     */
    public String getHostName(){
        return address.getCanonicalHostName();
    }
    /***
     * to get ip address
     * @return IP address
     */
    public String getIp(){
        return address.getHostAddress();
    }
}
