package livesmart.com.restClient;

/**
 * Created by Dominik Poppek on 26.01.2017.
 */

public class LoginResponse {
    private String result;
    private String message;
    private int userId;

    public LoginResponse(String _result, String _message, int _userId) {
        this.result = _result;
        this.message = _message;
        this.userId = _userId;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
