package command;

import command.Impl.InsertCommand;

import java.util.HashMap;

/**
 * The Controller of Command
 */
public class CommandController {
    private final HashMap<String,Command> commandFunc;

    /**
     * 注册各种command
     */
    private void register(){
        commandFunc.put("Insert",new InsertCommand());

    }

    public CommandController() {
        this.commandFunc = new HashMap<>();
        register();
    }

    /**
     * 执行命令
     * @param command 用户输入的完整命令
     */
    public void run(String command){
        String[] args = command.split(" ");
        commandFunc.get(args[0]).execute();
    }
}
