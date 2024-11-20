package editor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import editor.Editor;

public class EditorTest {
    private Editor editor;

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
    void testLoadFile() {
        String filename = "test.html";
        editor.load(filename);
        assertEquals(filename, editor.getFileName(), "加载文件后文件名应该匹配");
        assertFalse(editor.isModified(), "加载文件后不应该标记为已修改");
    }

    @Test
    void testExecuteCommand() {
        editor.executeCommand("append p 测试文本");
        assertTrue(editor.isModified(), "执行命令后应该标记为已修改");
    }

    @Test
    void testToggleModified() {
        assertFalse(editor.isModified(), "初始状态应该是未修改");
        editor.toggleModified();
        assertTrue(editor.isModified(), "切换后应该是已修改");
        editor.toggleModified();
        assertFalse(editor.isModified(), "再次切换后应该是未修改");
    }

    @Test
    void testSaveWithoutFilename() {
        editor.executeCommand("append p 测试文本");
        assertTrue(editor.isModified());
        editor.save();  // 尝试保存没有文件名的文档
        assertTrue(editor.isModified(), "没有文件名时保存应该保持修改状态");
    }

    @Test
    void testSaveWithFilename() {
        String filename = "test.html";
        editor.load(filename);
        editor.executeCommand("append p 测试文本");
        assertTrue(editor.isModified());
        editor.save();
        assertFalse(editor.isModified(), "保存后应该取消修改状态");
    }

    @Test
    void testSetShowId() {
        editor.setShowId(true);
        assertTrue(editor.isModified(), "执行命令后应该标记为已修改");
    }

    @Test
    void testDisplay() {
        // 测试普通显示
        editor.display();
        assertFalse(editor.isModified(), "显示不应改变修改状态");

        // 测试显示ID
        editor.setShowId(true);
        editor.display();
        assertTrue(editor.isModified(), "设置显示ID后应该标记为已修改");
    }
}
