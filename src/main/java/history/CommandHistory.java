package history;

import command.Command;
import java.util.Stack;

public class CommandHistory {
    // 撤销栈，存储可以撤销的命令
    private Stack<Command> undoStack;
    // 重做栈，存储可以重做的命令
    private Stack<Command> redoStack;

    public CommandHistory() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * 执行新命令时调用此方法
     * @param command 要执行的命令
     */
    public void push(Command command) {
        undoStack.push(command);
        // 清空重做栈，因为新命令执行后原来的重做记录就失效了
        redoStack.clear();
    }

    /**
     * 撤销上一个命令
     * @return 被撤销的命令
     */
    public Command undo() {
        if (undoStack.isEmpty()) {
            System.out.println("没有可撤销的操作");
            return null;
        }
        
        Command command = undoStack.pop();
        redoStack.push(command);
        return command;
    }

    /**
     * 重做上一个被撤销的命令
     * @return 被重做的命令
     */
    public Command redo() {
        if (redoStack.isEmpty()) {
            System.out.println("没有可重做的操作");
            return null;
        }

        Command command = redoStack.pop();
        undoStack.push(command);
        return command;
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
}
