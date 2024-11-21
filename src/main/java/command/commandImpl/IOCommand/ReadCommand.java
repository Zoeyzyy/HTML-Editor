package command.commandImpl.IOCommand;

import editor.Editor;

import java.io.File;

import command.Command;

public class ReadCommand implements Command {
    private final Editor editor;
    private final String filePath;

    public ReadCommand(Editor editor, String filePath) {
        this.editor = editor;
        this.filePath = filePath;
    }

    public static Command create(Editor editor, String filePath) {
        return new ReadCommand(editor, filePath);
    }

    @Override
    public void execute() {
        // Read the HTML file
        String currentPath = System.getProperty("user.dir");
        String absoluteFilePath = currentPath + filePath;
        File file = new File(absoluteFilePath);

        // 检查文件是否存在
        if (!file.exists()) {
            System.err.println("文件不存在！");
        }

        editor.load(absoluteFilePath);
    }
}
