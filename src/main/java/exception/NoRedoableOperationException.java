package exception;

public class NoRedoableOperationException extends RuntimeException {
    public NoRedoableOperationException() {
        super("没有可重做的操作");
    }
}