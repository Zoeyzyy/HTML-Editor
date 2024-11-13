
package editor;

import document.HTMLDocument;
import command.CommandController;
import command.CommandHistory;
import java.io.IOException;

/**
 * Editor 类，用于管理 HTML 文档的编辑。
 */
public class Editor {

    private String filename;
    private boolean modified;
    private boolean showId;
    private HTMLDocument document;
    private CommandController commandController;
    private CommandHistory commandHistory;

    /**
     * 构造函数，初始化编辑器。
     *
     * @param filename 要编辑的文件名
     */
    public Editor(String filename) {
        this.filename = filename;
        this.modified = false;
        this.showId = false;
        this.document = new HTMLDocument();
        this.commandController = new CommandController();
        this.commandHistory = new CommandHistory();
    }

    /**
     * 加载文档内容。
     */
    public void load() {
        try {
            commandController.loadFromFile(filename);
            System.out.println("文件加载成功。");
        } catch (IOException e) {
            System.out.println("文件加载失败：" + e.getMessage());
        }
    }

    /**
     * 保存文档内容。
     */
    public void save() {
        try {
            commandController.saveToFile(filename);
            modified = false;
            System.out.println("文件保存成功。");
        } catch (IOException e) {
            System.out.println("文件保存失败：" + e.getMessage());
        }
    }

    /**
     * 关闭编辑器。
     */
    public void close() {
        // 执行必要的清理操作
        System.out.println("编辑器已关闭。");
    }

    /**
     * 设置是否显示元素的 ID。
     *
     * @param showId 如果为 true，则显示 ID
     */
    public void setShowId(boolean showId) {
        this.showId = showId;
    }

    /**
     * 切换文档的修改状态。
     */
    public void toggleModified() {
        this.modified = !this.modified;
    }

    /**
     * 显示当前文档的内容。
     */
    public void display() {
        if (showId) {
            document.displayWithIds();
        } else {
            document.display();
        }
    }

    /**
     * 检查文档是否被修改。
     *
     * @return 如果被修改，返回 true
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * 获取当前文件名。
     *
     * @return 文件名
     */
    public String getFileName() {
        return filename;
    }

    /**
     * 执行用户输入的编辑命令。
     *
     * @param commandLine 用户输入的命令行
     */
    public void executeCommand(String commandLine) {
        modified = true; // 执行任何命令都视为文档已修改
        commandController.executeCommand(commandLine, document, commandHistory);
    }

    /**
     * 根据 ID 查找 HTML 元素。
     *
     * @param id 元素的 ID
     * @return 返回对应的 HTML 元素
     */
    public HTMLElement findElementById(String id) {
        return document.findElementById(id);
    }

    /**
     * 插入元素。
     *
     * @param element 要插入的元素
     */
    public void insertElement(HTMLElement element) {
        document.insertElement(element);
        modified = true; // 插入元素后标记为已修改
    }

    /**
     * 根据 ID 删除元素。
     *
     * @param id 要删除的元素 ID
     */
    public void removeElementById(String id) {
        document.removeElementById(id);
        modified = true; // 删除元素后标记为已修改
    }
}

