package command.commandImpl.sessionCommand;

import command.Command;

public class LoadCommand implements Command {
    private String filePath;

    public LoadCommand(String filePath) {
        this.filePath = filePath;
    }

    public static Command create(String filePath) {
        return new LoadCommand(filePath);
    }

    @Override
    public void execute() {
        session.load(filePath);
    }
}
