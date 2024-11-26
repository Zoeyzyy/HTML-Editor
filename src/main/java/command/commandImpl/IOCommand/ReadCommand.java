package command.commandImpl.IOCommand;

import editor.Editor;

import java.io.File;
import java.io.IOException;

import command.Command;
import session.Session;

public class ReadCommand implements Command {
    private final Session session;
    private final String filePath;

    public ReadCommand(Session session, String filePath) {
        this.session = session;
        this.filePath = filePath;
    }

    public static Command create(Session session, String filePath) {
        return new ReadCommand(session, filePath);
    }

    @Override
    public void execute() throws Exception {
        // let editor to check filepath
        session.load(filePath);
    }
}
