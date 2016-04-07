package pt.it.av.atnog.utils;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

/**
 * @author Mário Antunes
 * @version 1.0
 */
public class NetworkUtils {
    /**
     * Returns true if the specified port is available on this host.
     *
     * @param port the port to check
     * @return true if the port is available, false otherwise
     */
    private static boolean available(final int port) {
        ServerSocket serverSocket = null;
        DatagramSocket dataSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            dataSocket = new DatagramSocket(port);
            dataSocket.setReuseAddress(true);
            return true;
        } catch (final IOException e) {
            return false;
        } finally {
            if (dataSocket != null) {
                dataSocket.close();
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (final IOException e) {
                    // can never happen
                }
            }
        }
    }

    /**
     * Finds a free port between minPort and maxPort.
     *
     * @return free port
     * @throws RuntimeException if a port could not be found
     */
    public static int findFreePort(int minPort, int maxPort) throws RuntimeException {
        for (int i = minPort; i <= maxPort; i++) {
            if (available(i)) {
                return i;
            }
        }
        throw new RuntimeException("Could not find an available port between " + minPort + " and " + maxPort);
    }

    public static String ip(String dev) {
        String ip = null;
        try {
            NetworkInterface it = NetworkInterface.getByName(dev);
            if (it.isUp()) {
                boolean done = false;
                Enumeration<InetAddress> addresses = it.getInetAddresses();
                while (addresses.hasMoreElements() && !done) {
                    InetAddress addr = addresses.nextElement();
                    if (addr instanceof Inet4Address) {
                        ip = addr.getHostAddress();
                        done = true;
                    }
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    public static String loopback() {
        String localhost = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface
                    .getNetworkInterfaces();
            boolean done = false;
            while (interfaces.hasMoreElements() && !done) {
                NetworkInterface it = interfaces.nextElement();
                if (it.isLoopback()) {
                    localhost = it.getDisplayName();
                    done = true;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return localhost;
    }

    public static String device(String address) {
        return device(parse(address));
    }

    public static String device(InetSocketAddress address) {
        String dev = null;
        if (address.getAddress().isLoopbackAddress())
            dev = loopback();
        else if (isSelf(address.getAddress())) {
            try {
                dev = NetworkInterface.getByInetAddress(address.getAddress())
                        .getDisplayName();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            dev = validNetworkInterface();
        }
        return dev;
    }

    private static String validNetworkInterface() {
        String dev = null;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface
                    .getNetworkInterfaces();
            boolean done = false;
            while (interfaces.hasMoreElements() && !done) {
                NetworkInterface it = interfaces.nextElement();
                if (!it.isLoopback() && it.isUp()) {
                    dev = it.getDisplayName();
                    done = true;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return dev;
    }

    public static boolean isSelf(InetAddress addr) {
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
            return true;
        try {
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }

    public static InetSocketAddress parse(String address) {
        String segments[] = address.split(":");
        return new InetSocketAddress(segments[1].replaceAll("/", ""), Integer.parseInt(segments[2]));
    }
}
