package editor;

import document.HTMLDocument;
import command.CommandController;
import java.util.Scanner;

public class Editor {
    private String filename;
    private boolean modified;
    private boolean showId;
    private final HTMLDocument document;
    private final CommandController commandController;

    /**
     * 构造函数
     */
    public Editor() {
        this.modified = false;
        this.showId = false;
        this.document = new HTMLDocument();
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
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine().trim().toUpperCase();
            
            if (input.equals("Y")) {
                save();
            }
            // 如果输入 N 则直接关闭，不保存
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
            // 直接执行命令，不需要保存历史
            commandController.run(commandLine);
            modified = true;
        } catch (Exception e) {
            System.out.println("命令执行失败：" + e.getMessage());
        }
    }
}
