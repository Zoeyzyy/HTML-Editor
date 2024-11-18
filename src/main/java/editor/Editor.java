package editor;

import document.HTMLDocument;
import command.CommandController;
import command.CommandHistory;
import java.io.IOException;

public class Editor {
    private String filename;
    private boolean modified;
    private boolean showId;
    private HTMLDocument document;
    private CommandController commandController;
    private CommandHistory commandHistory;

    /**
     * 构造函数
     * @param filename 要编辑的文件名
     */
    public Editor(String filename) {
        this.filename = filename;
        this.modified = false;
        this.showId = false;
        this.document = new HTMLDocument();
        this.commandController = new CommandController(document);
        this.commandHistory = new CommandHistory();
        load();  // 创建编辑器时自动加载文件
    }

    /**
     * 加载文档内容
     */
    public void load() {
        try {
            commandController.run("read " + filename);
            modified = false;
            System.out.println("文件加载成功");
        } catch (Exception e) {
            System.out.println("文件加载失败：" + e.getMessage());
        }
    }

    /**
     * 保存文档内容
     */
    public void save() {
        try {
            commandController.run("save " + filename);
            modified = false;
            System.out.println("文件保存成功");
        } catch (Exception e) {
            System.out.println("文件保存失败：" + e.getMessage());
        }
    }

    /**
     * 显示当前文档内容
     */
    public void display() {
        if (showId) {
            commandController.run("print-tree");
        } else {
            commandController.run("print-indent 2"); // 使用默认缩进值2
        }
    }

    /**
     * 执行编辑命令
     * @param commandLine 命令行字符串
     */
    public void executeCommand(String commandLine) {
        modified = true;
        commandController.run(commandLine);
    }

    /**
     * 撤销上一次操作
     */
    public void undo() {
        if (commandHistory.canUndo()) {
            commandController.run("undo");
            modified = true;
        }
    }

    /**
     * 重做上一次被撤销的操作
     */
    public void redo() {
        if (commandHistory.canRedo()) {
            commandController.run("redo");
            modified = true;
        }
    }

    /**
     * 关闭编辑器
     */
    public void close() {
        if (modified) {
            System.out.println("文档已修改，是否保存？(Y/N)");
        }
        System.out.println("编辑器已关闭");
    }

    /**
     * 设置是否显示元素ID
     * @param showId 是否显示ID
     */
    public void setShowId(boolean showId) {
        this.showId = showId;
    }

    /**
     * 切换文档的修改状态
     */
    public void toggleModified() {
        this.modified = !this.modified;
    }

    /**
     * 检查文档是否被修改
     * @return 如果被修改返回true
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * 获取当前文件名
     * @return 文件名
     */
    public String getFileName() {
        return filename;
    }
}
