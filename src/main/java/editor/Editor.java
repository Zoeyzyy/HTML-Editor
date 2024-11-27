package editor;

import command.commandImpl.historyCommand.UndoCommand;
import command.commandImpl.historyCommand.RedoCommand;
import document.HTMLDocument;
import command.Command;
import document.HTMLElement;
import lombok.Getter;
import history.CommandHistory;
import exception.ElementNotFound;
import exception.ElementBadRemoved;
import lombok.Setter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Editor {
    @Setter
    private String filename;
    /**
     * -- GETTER --
     * 检查文档是否被修改
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
        this.document.init();
    }

    public HTMLDocument getDocument() {
        return this.document;
    }

    /**
     * 加载文档内容
     * 如果文件不存在，则新建文件
     *
     * @param filename 要加载的文件名
     * @throws IOException 如果文件操作失败
     */
    public void load(String filename) throws IOException {
        this.filename = filename;
        File file = new File(filename);

        if (!file.exists()) {
            document.init();
            createNewHtmlFile(file);
        } else {
            try {
                document.read(file);
            } catch (Exception e) {
                document.init();
                throw new IOException("文件读取失败：" + e.getMessage());
            }
        }
        modified = false;
    }

    /**
     * 创建新的HTML文件
     *
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
     *
     * @param filename 要保存的文件名
     * @throws IOException 如果文件操作失败
     */
    public void save(String filename) throws IOException {
        // 如果没有提供新文件名，且当前没有文件名，则抛出异常
        if ((filename == null || filename.startsWith("Untitled")) && this.filename == null) {
            throw new IOException("无法保存：文件名不能为空");
        }


        // 确保文件名存在
        if (filename == null) {
            throw new IOException("无法保存：未指定文件名");
        }

        // 获取文件内容并保存
        String content = document.save();
        File file = new File(filename);

        // 确保父目录存在
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(content);
            writer.flush();  // 确保内容被写入

        }catch (IOException e){
            throw new IOException("文件保存失败：" + e.getMessage());
        }

        // 如果提供了新文件名，则更新文件名
        this.filename = filename;

        modified = false;
    }

    /**
     * 关闭编辑器
     *
     * @throws IOException 如果保存文件时发生错误
     */
    public void close() throws IOException {
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
    }

    /**
     * 设置是否显示元素ID
     *
     * @param showId 是否显示ID
     */
    public void setShowId(boolean showId) {
        this.showId = showId;
        document.setShowID(showId);
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
     *
     * @return 文件名
     */
    public String getFileName() {
        return filename;
    }

    /**
     * 存储命令到历史记录
     *
     * @param command 要存储的命令
     */
    public void storeCommand(Command command) {
        // 如果是Undo或Redo命令，不存入历史记录
        if (command instanceof UndoCommand || command instanceof RedoCommand) {
            return;
        }
        
        commandHistory.push(command);
        updateModifiedState();
    }

    /**
     * 执行命令并记录历史
     *
     * @param command 要执行的命令
     */
    public void executeCommand(Command command) throws Exception {
        command.execute();
        storeCommand(command);
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
    public void undo() throws Exception {
        if (commandHistory.canUndo()) {
            commandHistory.undo();
            updateModifiedState();
        }
    }

    /**
     * 重做上一个被撤销的操作
     */
    public void redo() throws Exception {
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
    }

    /**
     * 拼写检查
     *
     * @return 拼写检查结果字符串
     */
    public String spellCheck() {
        return document.getSpellCheck();
    }

    /**
     * 树形显示
     *
     * @return 树形格式的文档字符串
     */
    public String printTree() {
        return document.getTreeFormat(false);
    }

    /**
     * 缩进显示
     *
     * @param indent 缩进空格数
     * @return 缩进格式的文档字符串
     */
    public String printIndent(int indent) {
        return document.getIndentFormat(indent);
    }

    /**
     * 添加元素
     *
     * @throws ElementNotFound 如果找不到父元素
     */
    public void append(String tagName, String idValue, String parentElement, String textContent) throws ElementNotFound {
        document.appendElement(tagName, idValue, textContent, parentElement);
        updateModifiedState();
    }

    /**
     * 插入元素
     *
     * @throws ElementNotFound 如果找不到目标位置
     */
    public void insert(String tagName, String idValue, String insertLocation, String textContent) throws ElementNotFound {
        document.insertElement(tagName, idValue, insertLocation, textContent);
        updateModifiedState();
    }

    /**
     * 删除元素
     *
     * @throws ElementNotFound   如果找不到要删除的元素
     * @throws ElementBadRemoved 如果元素不能被删除
     */
    public void delete(String id) throws ElementNotFound, ElementBadRemoved {
        document.removeElementById(id);
        updateModifiedState();
    }

    /**
     * 编辑ID
     *
     * @throws ElementNotFound 如果找不到要编辑的元素
     */
    public void editId(String oldId, String newId) throws ElementNotFound {
        document.editID(oldId, newId);
        updateModifiedState();
    }

    /**
     * 编辑文本内容
     *
     * @throws ElementNotFound 如果找不到要编辑的元素
     */
    public void editText(String id, String newText) throws ElementNotFound {
        document.editText(id, newText);
        updateModifiedState();
    }

    /**
     * 初始化文档
     */
    public void init() {
        document.init();
        updateModifiedState();
    }

    /**
     * 读取文档内容
     * 如果文件不存在，则抛出异常
     *
     * @param filename 要读取的文件名
     * @throws IOException 如果文件不存在或操作失败
     */
    public void read(String filename) throws IOException {
        this.filename = filename;
        File file = new File(filename);

        if (!file.exists()) {
            throw new IOException("文件不存在：" + filename);
        }

        try {
            document.read(file);
        } catch (Exception e) {
            document.init();
            throw new IOException("文件读取失败：" + e.getMessage());
        }
        modified = false;
    }
}
