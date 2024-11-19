package exception;

public class NoUndoableOperationException extends RuntimeException {
    public NoUndoableOperationException() {
        super("没有可撤销的操作");
    }
}