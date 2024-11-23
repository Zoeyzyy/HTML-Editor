package editor;

import command.Command;
import command.commandImpl.editCommand.*;
import exception.ElementNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class EditorTest {
    private Editor editor;
    @TempDir
    File tempDir;

    @BeforeEach
    void setUp() {
        editor = new Editor();
    }

    @Test
    void testInitialState() {
        assertFalse(editor.isModified(), "新建编辑器应该未被修改");
        assertNull(editor.getFileName(), "新建编辑器不应该有文件名");
    }

    @Test
    void testLoadFile() throws IOException {
        File testFile = new File(tempDir, "Test.html");
        editor.load(testFile.getAbsolutePath());
        assertEquals(testFile.getAbsolutePath(), editor.getFileName(), "文件名应该匹配");
        assertFalse(editor.isModified(), "加载文件后不应该标记为已修改");
    }

    @Test
    void testExecuteCommand() throws ElementNotFound {
        editor.init();
        
        Command command = AppendCommand.create(editor, "p", "p1", "body", "测试文本");
        editor.executeCommand(command);
        assertTrue(editor.isModified(), "执行命令后应该标记为已修改");
    }

    @Test
    void testModifiedState() throws ElementNotFound {
        editor.init();
        
        assertFalse(editor.isModified(), "初始状态应该是未修改");
        Command command = AppendCommand.create(editor, "p", "p1", "body", "测试文本");
        editor.executeCommand(command);
        assertTrue(editor.isModified(), "执行命令后应该是已修改");
    }

    @Test
    void testSaveWithoutFilename() throws IOException {
        Command command = AppendCommand.create(editor, "p", "p1", "body", "测试文本");
        editor.executeCommand(command);
        assertTrue(editor.isModified());
        assertThrows(IOException.class, () -> editor.save(null), 
            "没有文件名时保存应该抛出异常");
    }

    @Test
    void testSaveWithFilename() throws IOException {
        File testFile = new File(tempDir, "test.html");
        editor.load(testFile.getAbsolutePath());
        Command command = AppendCommand.create(editor, "p", "p1", "body", "测试文本");
        editor.executeCommand(command);
        assertTrue(editor.isModified(), "执行命令后应该标记为已修改");
        editor.save(testFile.getAbsolutePath());
        assertFalse(editor.isModified(), "保存后修改状态应该被重置");
    }

    @Test
    void testShowId() {
        // 初始状态
        assertFalse(editor.isModified(), "初始状态应该是未修改");
        
        // 测试 showId 方法 - 不应该改变修改状态
        editor.showId(true);
     assertFalse(editor.isModified(), "showId不应该改变修改状态");
        
        // 验证显示功能
        String result = editor.printTree();
        assertNotNull(result, "树形显示不应返回null");
    }

    @Test
    void testDisplayFormats() {
        String treeFormat = editor.printTree();
        assertNotNull(treeFormat, "树形显示不应返回null");

        String indentFormat = editor.printIndent(2);
        assertNotNull(indentFormat, "缩进显示不应返回null");
    }

    @Test
    void testUndoRedo() throws ElementNotFound {
        Command command = AppendCommand.create(editor, "p", "p1", "body", "测试文本");
        editor.executeCommand(command);
        assertTrue(editor.isModified(), "执行命令后应该标记为已修改");
        
        editor.undo();
        assertFalse(editor.isModified(), "撤销后应该标记为未修改");
        
        editor.redo();
        assertTrue(editor.isModified(), "重做后应该标记为已修改");
    }

    @Test
    void testSpellCheck() {
        String result = editor.spellCheck();
        assertNotNull(result, "拼写检查不应返回null");
    }
}
