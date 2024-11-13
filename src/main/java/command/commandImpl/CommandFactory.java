package command.commandImpl;

import command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂模式
 */
public class CommandFactory {
    private static final Map<String, Supplier<Command>> commandMap = new HashMap<>();

    /**
     * 根据name生成相应的command对象
     * @param commandName name
     * @return command object
     */
    public static Command createCommand(String commandName) {
        Supplier<Command> commandSupplier = commandMap.get(commandName.toLowerCase());
        if (commandSupplier == null) {
            throw new IllegalArgumentException("Unknown command: " + commandName);
        }
        return commandSupplier.get();
    }

    /**
     * 动态增加新命令，感觉不会被用到
     * @param commandName
     * @param commandSupplier
     */
    public static void registerCommand(String commandName, Supplier<Command> commandSupplier) {
        commandMap.put(commandName.toLowerCase(), commandSupplier);
    }
}
