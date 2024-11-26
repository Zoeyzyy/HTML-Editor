package command;

import command.commandImpl.IOCommand.InitCommand;
import command.commandImpl.IOCommand.LoadCommand;
import command.commandImpl.IOCommand.ReadCommand;
import command.commandImpl.IOCommand.SaveCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.displayCommand.SpellCheckCommand;
import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.RedoCommand;
import command.commandImpl.historyCommand.UndoCommand;
import command.commandImpl.sessionCommand.*;
import session.Session;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 工厂模式
 */
public class CommandFactory {
    /**
     * 命令格式类
     */
    private static class CommandFormat {
        private final int minArgs;
        private final int maxArgs;
        private final String format;

        public CommandFormat(int minArgs, int maxArgs, String format) {
            this.minArgs = minArgs;
            this.maxArgs = maxArgs;
            this.format = format;
        }
    }

    /**
     * Command creator interface
     */
    @FunctionalInterface
    private interface CommandCreator {
        Command create(String... args);
    }

    private final Map<String, CommandCreator> commandMap = new HashMap<>();
    private final Map<String, CommandFormat> commandFormats = new HashMap<>();
    private final Session session;
    private final PrintStream out;


    public CommandFactory(Session session, PrintStream out) {
        this.session = session;
        this.out = out;
        this.initCommandFormats();
        this.registerCommands();
    }

    // 初始化命令格式
    private void initCommandFormats() {
        commandFormats.put("insert", new CommandFormat(3, 4, "insert tagName idValue insertLocation [textContent]"));
        commandFormats.put("append", new CommandFormat(3, 4, "append tagName idValue parentElement [textContent]"));
        commandFormats.put("edit-id", new CommandFormat(2, 2, "edit-id oldId newId"));
        commandFormats.put("edit-text", new CommandFormat(1, 2, "edit-text element [newTextContent]"));
        commandFormats.put("delete", new CommandFormat(1, 1, "delete element"));
        commandFormats.put("print-indent", new CommandFormat(0, 1, "print-indent [indent]"));
        commandFormats.put("print-tree", new CommandFormat(0, 0, "print-tree"));
        commandFormats.put("spell-check", new CommandFormat(0, 0, "spell-check"));
        commandFormats.put("read", new CommandFormat(1, 1, "read filepath"));
        commandFormats.put("save", new CommandFormat(1, 1, "save filepath"));
        commandFormats.put("init", new CommandFormat(0, 0, "init"));
        commandFormats.put("redo", new CommandFormat(0, 0, "redo"));
        commandFormats.put("undo", new CommandFormat(0, 0, "undo"));
        commandFormats.put("exit", new CommandFormat(0, 0, "exit"));
        commandFormats.put("load", new CommandFormat(1, 1, "load filepath"));
        commandFormats.put("close", new CommandFormat(0, 0, "close"));
        commandFormats.put("editor-list", new CommandFormat(0, 0, "editor-list"));
        commandFormats.put("edit", new CommandFormat(1, 1, "edit filename.html"));
    }

    // 验证命令格式的函数
    private void validateCommandFormat(String commandName, String[] args) {
        CommandFormat format = commandFormats.get(commandName);
        if (format == null) {
            throw new IllegalArgumentException("Unknown command: " + commandName);
        }

        if (args.length < format.minArgs || args.length > format.maxArgs) {
            throw new IllegalArgumentException(
                    String.format("Invalid number of arguments for command '%s'.\n Format: %s%n" +
                                    "Expected between %d and %d arguments, but got %d",
                            commandName, format.format, format.minArgs, format.maxArgs, args.length));
        }
    }

    /**
     * 手动注册命令
     */
    private void registerCommands() {
        registerCommand("insert",
                args -> InsertCommand.create(this.session.getActiveEditor(), args[0], args[1], args[2], args[3]));

        registerCommand("append",
                args -> AppendCommand.create(this.session.getActiveEditor(), args[0], args[1], args[2], args[3]));

        registerCommand("edit-id",
                args -> EditIDCommand.create(this.session.getActiveEditor(), args[0], args[1]));

        registerCommand("edit-text",
                args -> EditTextCommand.create(this.session.getActiveEditor(), args[0], args[1]));

        registerCommand("delete",
                args -> DeleteCommand.create(this.session.getActiveEditor(), args[0]));

        registerCommand("print-indent",
                args -> PrintIndentCommand.create(this.session.getActiveEditor(), args.length == 1 ? Integer.parseInt(args[0]) : 2, out));

        registerCommand("print-tree",
                args -> PrintTreeCommand.create(this.session.getActiveEditor(), out));

        registerCommand("spell-check",
                args -> SpellCheckCommand.create(this.session.getActiveEditor()));

        registerCommand("read",
                args -> ReadCommand.create(this.session, args[0]));

        registerCommand("save",
                args -> SaveCommand.create(session, args[0]));

        registerCommand("init",
                args -> InitCommand.create(this.session.getActiveEditor()));


        registerCommand("redo",
                args -> RedoCommand.create(this.session.getActiveEditor()));

        registerCommand("undo",
                args -> UndoCommand.create(this.session.getActiveEditor()));

        registerCommand("exit",
                args -> ExitSessionCommand.create(session));

        registerCommand("load",
                args -> LoadCommand.create(session, args[0]));

        registerCommand("close",
                args -> CloseCommand.create(session));

        registerCommand("editor-list",
                args -> EditorListCommand.create(session));

        registerCommand("edit",
                args -> ChangeEditorCommand.create(session, args[0]));

    }


    /**
     * 注册命令
     *
     * @param name    命令名称
     * @param creator 命令创建器
     */
    private void registerCommand(String name, CommandCreator creator) {
        commandMap.put(name, args -> {
            validateCommandFormat(name, args);
            return creator.create(args);
        });
    }

    /**
     * 创建Command对象
     *
     * @param name Command名称
     * @param args Command需要传入的参数
     * @return
     */
    public Command createCommand(String name, String... args) {
        CommandCreator creator = commandMap.get(name);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown command: " + name);
        }
        return creator.create(args);
    }

}
