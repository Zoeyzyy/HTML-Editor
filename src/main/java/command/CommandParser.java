package command;

import document.HTMLDocument;
import editor.Editor;
import exception.ElementNotFound;
import history.CommandHistory;
import session.Session;

import java.util.Arrays;


/**
 * The Controller of Command
 * 主要用于用户命令的解析
 */
public class CommandParser {
    CommandFactory commandFactory;

    public CommandParser(Session session) {
        commandFactory=new CommandFactory(session, session.getActiveEditor());
    }

    /**
     * 执行命令
     * @param input 用户输入的完整命令
     */
    public Command processCommand(String input) throws ElementNotFound {
            String[] parts = parseInput(input);
            if (parts.length == 0) {
                throw new IllegalArgumentException("Empty command");
            }

            String commandName = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            return commandFactory.createCommand(commandName,args);
    }

    private String[] parseInput(String input) {
        return input.trim().split("\\s+");
    }
}
