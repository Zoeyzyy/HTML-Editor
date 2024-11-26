package history;

import command.CanUndoCommand;
import command.Command;
import command.commandImpl.historyCommand.UndoCommand;
import exception.NoRedoableOperationException;
import exception.NoUndoableOperationException;
import java.util.Stack;

public class CommandHistory {
    // 撤销栈，存储可以撤销的命令
    private Stack<CanUndoCommand> undoStack;
    // 重做栈，存储可以重做的命令
    private Stack<CanUndoCommand> redoStack;

    public CommandHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * 执行新命令时调用此方法
     * @param command 要执行的命令
     */
    public void push(Command command) {
        if(command instanceof CanUndoCommand) {
            undoStack.push((CanUndoCommand) command);
        }
        // 清空重做栈，因为新命令执行后原来的重做记录就失效了
        redoStack.clear();
    }

    /**
     * 撤销上一个命令
     */
    public void undo() throws Exception {
        if (undoStack.isEmpty()) {
            throw new NoUndoableOperationException();
        }
        
        CanUndoCommand command = undoStack.pop();
        redoStack.push(command);
        command.undo();
    }

    /**
     * 重做上一个被撤销的命令
     */
    public void redo() throws Exception {
        if (redoStack.isEmpty()) {
            throw new NoRedoableOperationException();
        }

        CanUndoCommand command = redoStack.pop();
        undoStack.push(command);
        command.execute();
    }

    /**
     * 检查是否有可撤销的操作
     * @return 如果有可撤销的操作返回true，否则返回false
     */
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    /**
     * 检查是否有可重做的操作
     * @return 如果有可重做的操作返回true，否则返回false
     */
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    /**
     * 获取最后执行的命令，但不移除它
     * @return 最后执行的命令
     * @throws NoUndoableOperationException 如果没有可撤销的命令
     */
    public Command peekLast() {
        if (undoStack.isEmpty()) {
            throw new NoUndoableOperationException();
        }
        return undoStack.peek();
    }
}
