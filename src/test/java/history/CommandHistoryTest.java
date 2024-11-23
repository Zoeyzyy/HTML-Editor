package history;

import command.Command;
import command.CanUndoCommand;
import exception.NoUndoableOperationException;
import exception.NoRedoableOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandHistoryTest {
    private CommandHistory history;
    
    // 修改为实现 CanUndoCommand 接口
    private static class TestCommand implements CanUndoCommand {
        private boolean executed = false;
        
        @Override
        public void execute() {
            executed = true;
        }
        
        @Override
        public void undo() {
            executed = false;
        }
    }
    
    // 添加一个不可撤销的命令类
    private static class NonUndoableCommand implements Command {
        @Override
        public void execute() {
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
        CanUndoCommand cmd = new TestCommand();
        history.push(cmd);
        assertTrue(history.canUndo(), "压入可撤销命令后应该可以撤销");
        assertFalse(history.canRedo(), "压入新命令后不应该有可重做的操作");
    }

    @Test
    void testPushNonUndoableCommand() {
        Command cmd = new NonUndoableCommand();
        history.push(cmd);
        assertFalse(history.canUndo(), "压入不可撤销命令后不应该可以撤销");
        assertFalse(history.canRedo(), "压入新命令后不应该有可重做的操作");
    }

    @Test
    void testUndoCommand() {
        CanUndoCommand cmd = new TestCommand();
        history.push(cmd);
        
        history.undo();
        assertFalse(history.canUndo(), "撤销后不应该有可撤销的操作");
        assertTrue(history.canRedo(), "撤销后应该有可重做的操作");
    }

    @Test
    void testRedoCommand() {
        CanUndoCommand cmd = new TestCommand();
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
        CanUndoCommand cmd1 = new TestCommand();
        CanUndoCommand cmd2 = new TestCommand();
        
        history.push(cmd1);
        history.undo();
        assertTrue(history.canRedo(), "撤销后应该有可重做的操作");
        
        history.push(cmd2);
        assertFalse(history.canRedo(), "执行新命令后重做栈应该被清空");
    }

    @Test
    void testMultipleUndoRedo() {
        CanUndoCommand cmd1 = new TestCommand();
        CanUndoCommand cmd2 = new TestCommand();
        
        history.push(cmd1);
        history.push(cmd2);
        
        history.undo();
        history.undo();
        
        history.redo();
        history.redo();
        
        assertTrue(history.canUndo(), "重做后应该有可撤销的操作");
        assertFalse(history.canRedo(), "重做后不应该有可重做的操作");
    }

    @Test
    void testPeekLast() {
        CanUndoCommand cmd = new TestCommand();
        history.push(cmd);
        assertEquals(cmd, history.peekLast(), "peekLast应该返回最后压入的命令");
    }

    @Test
    void testPeekLastEmptyHistory() {
        assertThrows(NoUndoableOperationException.class, () -> {
            history.peekLast();
        }, "空历史记录peekLast应该抛出异常");
    }
}
