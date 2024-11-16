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
        this.commandController = new CommandController();
        this.commandHistory = new CommandHistory();
        load();  // 创建编辑器时自动加载文件
    }

    /**
     * 加载文档内容
     */
    public void load() {
        try {
            commandController.loadFromFile(filename, document);
            modified = false;
            System.out.println("文件加载成功");
        } catch (IOException e) {
            System.out.println("文件加载失败：" + e.getMessage());
        }
    }

    /**
     * 保存文档内容
     */
    public void save() {
        try {
            commandController.saveToFile(filename);
            modified = false;
            System.out.println("文件保存成功");
        } catch (IOException e) {
            System.out.println("文件保存失败：" + e.getMessage());
        }
    }

    /**
     * 关闭编辑器
     */
    public void close() {
        if (modified) {
            System.out.println("文档已修改，是否保存？(Y/N)");
            // 这里可以添加用户输入处理逻辑
            // 如果用户选择保存，则调用 save()
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
     * 显示当前文档内容
     */
    public void display() {
        if (showId) {
            commandController.displayWithIds(document);
        } else {
            commandController.display(document);
        }
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

    /**
     * 执行编辑命令
     * @param commandLine 命令行字符串
     */
    public void executeCommand(String commandLine) {
        modified = true;
        commandController.executeCommand(commandLine, document, commandHistory);
    }

    /**
     * 撤销上一次操作
     */
    public void undo() {
        if (commandHistory.canUndo()) {
            commandHistory.undo().undo();
            modified = true;
        }
    }

    /**
     * 重做上一次被撤销的操作
     */
    public void redo() {
        if (commandHistory.canRedo()) {
            commandHistory.redo().execute();
            modified = true;
        }
    }
}
