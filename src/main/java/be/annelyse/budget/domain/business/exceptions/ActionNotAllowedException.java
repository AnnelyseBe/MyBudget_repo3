package be.annelyse.budget.domain.business.exceptions;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(String message) {
        super(message);
    }

    public ActionNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotAllowedException(Throwable cause) {
        super(cause);
    }


}
