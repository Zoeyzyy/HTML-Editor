package command;

import document.HTMLDocument;
import history.CommandHistory;

import java.util.Arrays;


/**
 * The Controller of Command
 * 主要用于用户命令的解析
 */
public class CommandController {
    CommandFactory commandFactory;
    CommandHistory commandHistory;

    public CommandController(HTMLDocument document) {
        commandHistory=new CommandHistory();
        commandFactory=new CommandFactory(document,commandHistory);
    }

    /**
     * 执行命令
     * @param input 用户输入的完整命令
     */
    public void run(String input){
            String[] parts = parseInput(input);
            if (parts.length == 0) {
                throw new IllegalArgumentException("Empty command");
            }

            String commandName = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            Command command = commandFactory.createCommand(commandName,args);
            command.execute();
    }

    private String[] parseInput(String input) {
        return input.trim().split("\\s+");
    }
}
