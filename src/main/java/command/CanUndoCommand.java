package command;

public interface CanUndoCommand extends Command {
    void undo() throws Exception;
}
