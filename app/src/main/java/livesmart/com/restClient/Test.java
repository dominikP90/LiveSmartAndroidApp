package livesmart.com.restClient;

/**
 * Created by Dominik Poppek on 26.01.2017.
 */

public class Test {

    public static void main(String[] args) {
        Communicator c = new Communicator();
        c.loginPost("Admin", "Admin");
        c.getClass();
    }
}
