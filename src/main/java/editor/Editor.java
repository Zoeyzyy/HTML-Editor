package editor;

import document.HTMLDocument;
import command.CommandController;
import command.Command;
import history.CommandHistory;

public class Editor {
    private String filename;
    private boolean modified;
    private boolean showId;
    private HTMLDocument document;
    private CommandController commandController;
    private CommandHistory commandHistory;

    /**
     * 构造函数
     */
    public Editor() {
        this.modified = false;
        this.showId = false;
        this.document = new HTMLDocument();
        this.commandHistory = new CommandHistory();
        this.commandController = new CommandController(document);
    }

    /**
     * 加载文档内容
     * @param filename 要加载的文件名
     */
    public void load(String filename) {
        try {
            this.filename = filename;
            executeCommand("read " + filename);
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
        if (filename == null) {
            System.out.println("没有指定文件名");
            return;
        }
        try {
            executeCommand("save " + filename);
            modified = false;
            System.out.println("文件保存成功");
        } catch (Exception e) {
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
        }
        executeCommand("init");  // 初始化文档状态
        this.filename = null;
        System.out.println("编辑器已关闭");
    }

    /**
     * 设置是否显示元素ID
     * @param showId 是否显示ID
     */
    public void setShowId(boolean showId) {
        this.showId = showId;
        executeCommand("showid " + showId);
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
        try {
            if (showId) {
                executeCommand("print-tree");
            } else {
                executeCommand("print-indent 2");
            }
        } catch (Exception e) {
            System.out.println("显示失败：" + e.getMessage());
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
        try {
            // 直接使用commandController的run方法
            commandController.run(commandLine);
            
            // 获取最后执行的命令（从CommandHistory中）
            Command lastCommand = commandController.commandHistory.peekLast();
            
            // 将可撤销的命令添加到历史记录
            if (lastCommand instanceof CanUndoCommand && 
                !commandLine.startsWith("print") && 
                !commandLine.startsWith("display") && 
                !commandLine.startsWith("showid")) {
                commandHistory.push(lastCommand);
                modified = true;
            }
        } catch (Exception e) {
            System.out.println("命令执行失败：" + e.getMessage());
        }
    }

    /**
     * 撤销上一个命令
     */
    public void undo() {
        if (commandHistory.canUndo()) {
            Command command = commandHistory.undo();
            if (command != null) {
                command.undo();
                modified = true;
            }
        }
    }

    /**
     * 重做上一个被撤销的命令
     */
    public void redo() {
        if (commandHistory.canRedo()) {
            Command command = commandHistory.redo();
            if (command != null) {
                command.execute();
                modified = true;
            }
        }
    }
}
