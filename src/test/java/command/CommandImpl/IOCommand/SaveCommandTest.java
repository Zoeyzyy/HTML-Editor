package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.SaveCommand;
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
        session.load("\\src\\main\\resources\\Test.html");

        SaveCommand saveCommand = new SaveCommand(session, "\\src\\main\\resources\\test.html");
        saveCommand.execute();

        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + "\\src\\main\\resources\\test.txt";
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
                " <head>\n" +
                "  <title></title>\n" +
                " </head>\n" +
                " <body>\n" +
                " </body>\n" +
                "</html>", content.toString());
    }
}
