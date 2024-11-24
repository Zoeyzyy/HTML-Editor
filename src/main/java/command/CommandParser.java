package command;

import document.HTMLDocument;
import editor.Editor;
import exception.ElementNotFound;
import history.CommandHistory;
import session.Session;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;


/**
 * The Controller of Command
 * 主要用于用户命令的解析
 */
public class CommandParser {
    private final CommandFactory commandFactory;

    public CommandParser(Session session, InputStream in, PrintStream out) {
        commandFactory = new CommandFactory(session, out);
    }

    public Command processCommand(String input) throws ElementNotFound {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty command");
        }

        String[] initialSplit = input.trim().split("\\s+", 2);
        String commandName = initialSplit[0];

        if (initialSplit.length == 1) {
            return commandFactory.createCommand(commandName);
        }

        String[] args = parseCommandArgs(commandName, initialSplit[1]);
        return commandFactory.createCommand(commandName, args);
    }

    private String[] parseCommandArgs(String commandName, String argsString) {
        if (!isCommandWithTextContent(commandName)) {
            return argsString.trim().split("\\s+");
        }

        String[] allParts = argsString.trim().split("\\s+");

        // 特殊处理 edit-text 命令
        if ("edit-text".equals(commandName)) {
            if (allParts.length == 0) {
                return new String[0];
            }

            String[] result = new String[2];
            result[0] = allParts[0];  // element

            // 如果有文本内容，合并剩余部分
            if (allParts.length > 1) {
                StringBuilder textContent = new StringBuilder();
                for (int i = 1; i < allParts.length; i++) {
                    if (i > 1) {
                        textContent.append(" ");
                    }
                    textContent.append(allParts[i]);
                }
                result[1] = textContent.toString();
            } else {
                result[1] = null;  // 没有文本内容时设为null
            }
            return result;
        }

        // 处理 insert 和 append 命令
        int requiredArgs = 3;
        String[] result = new String[requiredArgs + 1];

        // 复制必需参数
        System.arraycopy(allParts, 0, result, 0, Math.min(allParts.length, requiredArgs));

        // 如果参数数量等于或小于必需参数数量，设置textContent为null
        if (allParts.length <= requiredArgs) {
            result[requiredArgs] = null;
            return result;
        }

        // 合并剩余部分作为textContent
        StringBuilder textContent = new StringBuilder();
        for (int i = requiredArgs; i < allParts.length; i++) {
            if (i > requiredArgs) {
                textContent.append(" ");
            }
            textContent.append(allParts[i]);
        }
        result[requiredArgs] = textContent.toString();

        return result;
    }

    private boolean isCommandWithTextContent(String commandName) {
        return "insert".equals(commandName)
                || "append".equals(commandName)
                || "edit-text".equals(commandName);
    }
}