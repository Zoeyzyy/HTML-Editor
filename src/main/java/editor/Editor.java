package editor;

import document.HTMLDocument;
import command.Command;
import document.HTMLElement;
import lombok.Getter;
import history.CommandHistory;
import exception.ElementNotFound;
import exception.ElementBadRemoved;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Editor {
    private String filename;
    /**
     * -- GETTER --
     *  检查文档是否被修改
     *
     * @return 如果被修改返回true
     */
    @Getter
    private boolean modified;
    private boolean showId;
    private final HTMLDocument document;
    private final CommandHistory commandHistory;

    /**
     * 构造函数
     */
    public Editor() {
        this.modified = false;
        this.showId = false;
        this.document = new HTMLDocument();
        this.commandHistory = new CommandHistory();
    }

    public HTMLDocument getDocument(){
        return this.document;
    }

    /**
     * 加载文档内容
     * 如果文件不存在，则新建文件
     * @param filename 要加载的文件名
     */
    public void load(String filename) {
        this.filename = filename;
        File file = new File(filename);
        
        if (!file.exists()) {
            // 如果文件不存在，初始化新文档并创建文件
            document.init();
            try {
                createNewHtmlFile(file);
                System.out.println("新建文件：" + filename);
            } catch (IOException e) {
                System.out.println("创建文件失败：" + e.getMessage());
                return;
            }
        } else {
            try {
                document.read(file);
                System.out.println("文件加载成功");
            } catch (Exception e) {
                // 如果读取失败，初始化新文档
                document.init();
                System.out.println("文件读取失败，已创建新文档");
            }
        }
        modified = false;
    }

    /**
     * 创建新的HTML文件
     * @param file 文件对象
     * @throws IOException 如果创建文件失败
     */
    private void createNewHtmlFile(File file) throws IOException {
        String initialContent = "<!DOCTYPE html>\n<html>\n<head>\n    <title>New Document</title>\n</head>\n<body>\n</body>\n</html>";
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(initialContent);
        }
    }

    /**
     * 保存文档内容到指定文件
     * @param filename 要保存的文件名
     */
    public void save(String filename) {
        if (filename != null) {
            this.filename = filename;
        }

        try {
            String content = document.save();
            File file = new File(this.filename);
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
                writer.write(content);
            }
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
                save(filename);
            }
        }
        document.init();
        this.filename = null;
        System.out.println("编辑器已关闭");
    }

    /**
     * 设置是否显示元素ID
     * @param showId 是否显示ID
     */
    public void setShowId(boolean showId) {
        this.showId = showId;
        document.setShowID(showId);
        updateModifiedState();
    }

    /**
     * 切换文档的修改状态
     */
    public void toggleModified() {
        this.modified = commandHistory.canUndo();
    }

    /**
     * 显示当前文档内容
     */
    public void display() {
        if (showId) {
            System.out.println(document.getTreeFormat(false));
        } else {
            System.out.println(document.getIndentFormat(2));
        }
    }

    /**
     * 获取当前文件名
     * @return 文件名
     */
    public String getFileName() {
        return filename;
    }

    /**
     * 执行命令并记录历史
     * @param command 要执行的命令
     */
    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);
        updateModifiedState();
    }

    /**
     * 更新修改状态
     */
    private void updateModifiedState() {
        modified = commandHistory.canUndo();
    }

    /**
     * 撤销上一个操作
     */
    public void undo() {
        if (commandHistory.canUndo()) {
            commandHistory.undo();
            updateModifiedState();
        }
    }

    /**
     * 重做上一个被撤销的操作
     */
    public void redo() {
        if (commandHistory.canRedo()) {
            commandHistory.redo();
            updateModifiedState();
        }
    }

    /**
     * 显示/隐藏ID
     */
    public void showId(boolean show) {
        document.setShowID(show);
        updateModifiedState();
    }

    /**
     * 拼写检查
     * @return 拼写检查结果字符串
     */
    public String spellCheck() {
        return document.getSpellCheck();
    }

    /**
     * 树形显示
     * @return 树形格式的文档字符串
     */
    public String printTree() {
        return document.getTreeFormat(false);
    }

    /**
     * 缩进显示
     * @param indent 缩进空格数
     * @return 缩进格式的文档字符串
     */
    public String printIndent(int indent) {
        return document.getIndentFormat(indent);
    }

    /**
     * 添加元素
     */
    public void append(String tagName, String idValue, String parentElement, String textContent) {
        try {
            document.appendElement(tagName, idValue, textContent, parentElement);
            updateModifiedState();
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 插入元素
     */
    public void insert(String tagName, String idValue, String insertLocation, String textContent) {
        try {
            document.insertElement(tagName, idValue, insertLocation, textContent);
            updateModifiedState();
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 删除元素
     */
    public void delete(String id) {
        try {
            document.removeElementById(id);
            updateModifiedState();
        } catch (ElementBadRemoved | ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 编辑ID
     */
    public void editId(String oldId, String newId) {
        try {
            document.editID(oldId, newId);
            updateModifiedState();
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 编辑文本内容
     */
    public void editText(String id, String newText) {
        try {
            document.editText(id, newText);
            updateModifiedState();
        } catch (ElementNotFound e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 初始化文档
     */
    public void init() {
        document.init();
        updateModifiedState();
    }
}
