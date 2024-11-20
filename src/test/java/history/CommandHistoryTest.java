package history;

import command.Command;
import exception.NoUndoableOperationException;
import exception.NoRedoableOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandHistoryTest {
    private CommandHistory history;
    
    // 创建一个测试用的命令类
    private static class TestCommand implements Command {
        @Override
        public void execute() {
            // 测试用空实现
        }
        
        public void undo() {
            // 测试用空实现
        }
    }

    @BeforeEach
    void setUp() {
        history = new CommandHistory();
    }

    @Test
    void testInitialState() {
        assertFalse(history.canUndo(), "新建的历史记录不应该有可撤销的操作");
        assertFalse(history.canRedo(), "新建的历史记录不应该有可重做的操作");
    }

    @Test
    void testPushCommand() {
        Command cmd = new TestCommand();
        history.push(cmd);
        assertTrue(history.canUndo(), "压入命令后应该可以撤销");
        assertFalse(history.canRedo(), "压入新命令后不应该有可重做的操作");
    }

    @Test
    void testUndoCommand() {
        Command cmd = new TestCommand();
        history.push(cmd);
        
        history.undo();
        assertFalse(history.canUndo(), "撤销后不应该有可撤销的操作");
        assertTrue(history.canRedo(), "撤销后应该有可重做的操作");
    }

    @Test
    void testRedoCommand() {
        Command cmd = new TestCommand();
        history.push(cmd);
        history.undo();
        
        history.redo();
        assertTrue(history.canUndo(), "重做后应该有可撤销的操作");
        assertFalse(history.canRedo(), "重做后不应该有可重做的操作");
    }

    @Test
    void testUndoEmptyHistory() {
        assertThrows(NoUndoableOperationException.class, () -> {
            history.undo();
        }, "空历史记录撤销应该抛出异常");
    }

    @Test
    void testRedoEmptyHistory() {
        assertThrows(NoRedoableOperationException.class, () -> {
            history.redo();
        }, "空历史记录重做应该抛出异常");
    }

    @Test
    void testPushClearsRedoStack() {
        Command cmd1 = new TestCommand();
        Command cmd2 = new TestCommand();
        
        history.push(cmd1);
        history.undo();
        assertTrue(history.canRedo(), "撤销后应该有可重做的操作");
        
        history.push(cmd2);
        assertFalse(history.canRedo(), "执行新命令后重做栈应该被清空");
    }

    @Test
    void testMultipleUndoRedo() {
        Command cmd1 = new TestCommand();
        Command cmd2 = new TestCommand();
        
        history.push(cmd1);
        history.push(cmd2);
        
        history.undo();
        history.undo();
        
        history.redo();
        history.redo();
        
        assertTrue(history.canUndo(), "重做后应该有可撤销的操作");
        assertFalse(history.canRedo(), "重做后不应该有可重做的操作");
    }
}
