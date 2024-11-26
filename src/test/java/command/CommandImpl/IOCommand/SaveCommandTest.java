package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.SaveCommand;
import command.commandImpl.editCommand.AppendCommand;
import command.commandImpl.editCommand.EditTextCommand;
import editor.Editor;
import session.Session;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveCommandTest {
    @Test
    public void execute() {
        Session session = new Session("");
        String currentPath = System.getProperty("user.dir");
        String fileName = "\\src\\main\\resources\\example.html";
        String filePath = currentPath + fileName;
        try {
            session.load(filePath);
        } catch (Exception e) {

        }
        EditTextCommand editTextCommand = new EditTextCommand(session.getActiveEditor(), "body", "Hello World");
        try {
            editTextCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        SaveCommand saveCommand = new SaveCommand(session, fileName);
        try {
            saveCommand.execute();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("<html>\n" +
                "  <head>\n" +
                "    <title>\n" +
                "    </title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    Hello World\n" +
                "  </body>\n" +
                "</html>", content.toString());
    }
}
