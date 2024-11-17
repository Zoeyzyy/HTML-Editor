package command.CommandImpl.IOCommand;

import command.commandImpl.IOCommand.SaveCommand;
import document.HTMLDocument;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveCommandTest {
    @Test
    public void execute() {
        HTMLDocument document = new HTMLDocument(null);
        document.init();

        SaveCommand saveCommand = new SaveCommand(document, "test.html");
        saveCommand.execute();

        try {
            Path filePath = Path.of("test.html");
            String content = Files.readString(filePath);
            assertEquals("<html>\n" +
                    " <head>\n" +
                    "  <title></title>\n" +
                    " </head>\n" +
                    " <body>\n" +
                    " </body>\n" +
                    "</html>", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
