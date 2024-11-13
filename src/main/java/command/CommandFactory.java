package command;

import command.commandImpl.IOCommand.InitCommand;
import command.commandImpl.IOCommand.ReadCommand;
import command.commandImpl.IOCommand.SaveCommand;
import command.commandImpl.displayCommand.PrintIndentCommand;
import command.commandImpl.displayCommand.PrintTreeCommand;
import command.commandImpl.displayCommand.SpellCheckCommand;
import command.commandImpl.editCommand.*;
import command.commandImpl.historyCommand.RedoCommand;
import command.commandImpl.historyCommand.UndoCommand;
import document.HTMLDocument;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂模式
 */
public class CommandFactory {
    private final Map<String, CommandCreator> commandMap = new HashMap<>();
    private final HTMLDocument document;

    public CommandFactory(HTMLDocument document) {
        this.document = document;
    }

    /**
     * 手动注册命令
     */
    public void registerCommands() {
        registerCommand("insert",
                args -> InsertCommand.create(document,args[0],args[1],args[2],args[3]));

        registerCommand("append",
                args -> AppendCommand.create(document,args[0],args[1],args[2],args[3]));

        registerCommand("edit-id",
                args -> EditIDCommand.create(document,args[0],args[1]));

        registerCommand("edit-text",
                args -> EditTextCommand.create(document,args[0],args[1]));

        registerCommand("delete",
                args -> DeleteCommand.create(document,args[0]));

        registerCommand("print-indent",
                args -> PrintIndentCommand.create(document,Integer.parseInt(args[0])));

        registerCommand("print-tree",
                args -> PrintTreeCommand.create(document));

        registerCommand("spell-check",
                args -> SpellCheckCommand.create(document));

        registerCommand("read",
                args -> ReadCommand.create(document,args[0]));

        registerCommand("save",
                args -> SaveCommand.create(document,args[0]));

        registerCommand("init",
                args -> InitCommand.create(document));

        registerCommand("redo",
                args -> RedoCommand.create());

        registerCommand("undo",
                args -> UndoCommand.create());
    }


    /**
     * 注册命令
     * @param name 命令名称
     * @param creator 命令创建器
     */
    private void registerCommand(String name, CommandCreator creator) {
        commandMap.put(name, creator);
    }

    /**
     * 创建Command对象
     * @param name Command名称
     * @param args Command需要传入的参数
     * @return
     */
    public Command createCommand(String name,String ...args) {
        CommandCreator creator = commandMap.get(name);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown command: " + name);
        }
        return creator.create(args);
    }

}
