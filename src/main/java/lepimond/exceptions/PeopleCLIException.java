package lepimond.exceptions;

public class PeopleCLIException extends Exception {

    private static final long serialVersionUID = 178904553634513138L;

    public PeopleCLIException() {
        super();
    }

    public PeopleCLIException(String message) {
        super(message);
    }

    public PeopleCLIException(String message, Exception e) {
        super(message, e);
    }

}
