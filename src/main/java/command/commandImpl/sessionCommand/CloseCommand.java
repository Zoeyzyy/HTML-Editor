package command.commandImpl.sessionCommand;

import command.Command;
import command.commandImpl.IOCommand.SaveCommand;

public class CloseCommand implements Command {
    public CloseCommand() {
    }

    public static Command create() {
        return new ChangeEditorCommand();
    }

    @Override
    public void execute() {
        // return boolean： check active file is modified
        boolean modified = session.confirm();
        while (modified) {
            System.out.println("File is modified, do you want to save?(y/n)");
            String input = new java.util.Scanner(System.in).nextLine();
            if (input.equals("y")) {
                System.out.println("Please input file path:");
                String filePath = new java.util.Scanner(System.in).nextLine();
                Command saveCommand = SaveCommand.create(session.getActiveDocument(), filePath);
                saveCommand.execute();
                break;
            }else{
                System.out.println("File is modified, comfirm not to save?(y/n)");
                String input2 = new java.util.Scanner(System.in).nextLine();
                if (input2.equals("y")) {
                    break;
                }
            }
        }
        session.close();
    }
}
