package command;

import document.HTMLDocument;

import java.util.Arrays;


/**
 * The Controller of Command
 * 主要用于用户命令的解析
 */
public class CommandController {
    CommandFactory commandFactory;

    public CommandController(HTMLDocument document) {
        commandFactory=new CommandFactory(document);
    }

    /**
     * 执行命令
     * @param input 用户输入的完整命令
     */
    public void run(String input){
        try {
            String[] parts = parseInput(input);
            if (parts.length == 0) {
                throw new IllegalArgumentException("Empty command");
            }

            String commandName = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            Command command = commandFactory.createCommand(commandName,args);
            command.execute();
        } catch (Exception e) {
            // 处理异常
            System.err.println("Error executing command: " + e.getMessage());
        }
    }

    private String[] parseInput(String input) {
        return input.trim().split("\\s+");
    }
}
