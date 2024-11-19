package history;

import command.Command;
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
        
        @Override
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
        
        Command undoCmd = history.undo();
        assertSame(cmd, undoCmd, "撤销的命令应该是最后压入的命令");
        assertFalse(history.canUndo(), "撤销后不应该有可撤销的操作");
        assertTrue(history.canRedo(), "撤销后应该有可重做的操作");
    }

    @Test
    void testRedoCommand() {
        Command cmd = new TestCommand();
        history.push(cmd);
        history.undo();
        
        Command redoCmd = history.redo();
        assertSame(cmd, redoCmd, "重做的命令应该是最后撤销的命令");
        assertTrue(history.canUndo(), "重做后应该有可撤销的操作");
        assertFalse(history.canRedo(), "重做后不应该有可重做的操作");
    }

    @Test
    void testUndoEmptyHistory() {
        Command cmd = history.undo();
        assertNull(cmd, "空历史记录撤销应该返回null");
        assertFalse(history.canUndo(), "空历史记录不应该有可撤销的操作");
    }

    @Test
    void testRedoEmptyHistory() {
        Command cmd = history.redo();
        assertNull(cmd, "空历史记录重做应该返回null");
        assertFalse(history.canRedo(), "空历史记录不应该有可重做的操作");
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
        
        Command undoCmd2 = history.undo();
        Command undoCmd1 = history.undo();
        assertSame(cmd2, undoCmd2, "应该按照相反顺序撤销命令");
        assertSame(cmd1, undoCmd1, "应该按照相反顺序撤销命令");
        
        Command redoCmd1 = history.redo();
        Command redoCmd2 = history.redo();
        assertSame(cmd1, redoCmd1, "应该按照原始顺序重做命令");
        assertSame(cmd2, redoCmd2, "应该按照原始顺序重做命令");
    }
}
