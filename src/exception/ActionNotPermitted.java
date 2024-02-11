package exception;

/**
 * ActionNotPermitted
 */
public class ActionNotPermitted extends Exception{
    public ActionNotPermitted(String errorMessage) {
      super(errorMessage);
    }
}