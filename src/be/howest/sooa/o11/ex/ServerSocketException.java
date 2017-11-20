package be.howest.sooa.o11.ex;

/**
 *
 * @author hayk
 */
public class ServerSocketException extends RuntimeException {

    public ServerSocketException(Throwable cause) {
        super("Something went wrong with the Server Socket", cause);
    }
}
