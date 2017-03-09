package livesmart.com.restClient;

/**
 * Created by Dominik Poppek on 07.03.2017.
 */

public class SwitchResponse {
    private boolean wasSuccessful;
    private String message;

    public SwitchResponse(boolean _wasSuccessful, String _message) {
        this.wasSuccessful = _wasSuccessful;
        this.message = _message;
    }

    /**
     * @return the wasSuccessful
     */
    public boolean isWasSuccessful() {
        return wasSuccessful;
    }
    /**
     * @param wasSuccessful the wasSuccessful to set
     */
    public void setWasSuccessful(boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
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
}
