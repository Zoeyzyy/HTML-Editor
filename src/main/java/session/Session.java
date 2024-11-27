package session;

import java.util.*;

import editor.Editor;
import lombok.Getter;

import java.io.*;
import java.util.stream.Collectors;

public class Session {
    @Getter
    private String id;
    private static int untitleID = 0;
    private Map<String, Editor> editors;
    @Getter
    private Editor activeEditor;

    public Session(String id) {
        if (!this.recover("./data/session_dump")) {
            this.editors = new HashMap<>();
            this.activeEditor = null;
        }
        this.id = id;
    }

    public void enter (String id) {
        Session session = new Session(id);
        this.id = session.id;
        this.editors = new HashMap<>(session.editors);
        this.activeEditor = session.activeEditor;
    }

    public void dump(String filename) {
        filename = "./data/session_dump";
        File dir = new File("./data/");
        if (!dir.exists()) {
            boolean mkdirSuccess = dir.mkdirs();
            if (mkdirSuccess) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Failed to create the directory.");
            }
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {

            List<DumpType> saved = new ArrayList<>();
            for (String file : this.editors.keySet()) {
                saved.add(new DumpType(file, this.editors.get(file).getDocument().getShowID()));
            }
            if (activeEditor != null) {
                saved.add(0, new DumpType(activeEditor.getFileName(), activeEditor.getDocument().getShowID()));
            }

            out.writeObject(saved);
            System.out.println("Object dumped to file: " + filename);
        } catch (IOException e) {
            System.out.println("fail to dump session: " + e);
        }
    }

    

    public boolean recover(String filename) {
        filename = "./data/session_dump";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            List<DumpType> list = (List<DumpType>) in.readObject();

            System.out.println("Object loaded from file: " + filename);
            if (list.isEmpty()) {
                return false;
            }

            this.editors = new HashMap<>();

            for (DumpType dump : list) {

                Editor editor = new Editor();
                editor.load(dump.getFileName());
                editor.setShowId(dump.isShowID());
                this.editors.put(dump.getFileName(), editor);
            }

            this.activeEditor = this.editors.get(list.get(0).getFileName());

        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            return false;
        }
        File file = new File(filename);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            if (isDeleted) {
                System.out.println("Dump file deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
        return true;
    }

    public void load(String filename) throws IOException {
        if (Objects.equals(filename, "")){
            Editor editor = new Editor();
            filename = "Untitled-" + untitleID++;
            editor.init();
            editor.setFilename(filename);
            editors.put(filename, editor);
            activeEditor = editor;
            return;
        }
        Editor editor = new Editor();
        File file = new File(filename);
        filename = file.getAbsolutePath();
        editor.load(filename);
        editors.put(filename, editor);
        activeEditor = editor;
    }

    public void read(String filename) throws IOException {
        Editor editor = new Editor();
        File file = new File(filename);
        filename = file.getAbsolutePath();
        editor.read(filename);
        editors.put(filename, editor);
        activeEditor = editor;
    }

    public void save(String filename) throws IOException {
        filename = new File(filename).getAbsolutePath();
        activeEditor.save(filename);
        editors.remove(activeEditor.getFileName());
        editors.put(filename, activeEditor);
    }

    public boolean confirm() {
        return activeEditor.isModified();
    }

    public void close() {
        if (activeEditor != null) {
            editors.remove(activeEditor.getFileName());
            Iterator<String> it = editors.keySet().iterator();
            if (it.hasNext()) {
                activeEditor = editors.get(it.next());
            } else {
                activeEditor = null;
            }
        }
    }

    public List<String> getEditorList() {
        return this.editors.keySet().stream().map(file -> {
            File f = new File(file);
            if (isModified(f)) {
                file = file + "*";
            }

            if (isEditing(f)){
                file = ">" + file;
            }
            return file;
        }).collect(Collectors.toList());
    }

    public void activateEditor(String filename) {
        activeEditor = editors.get(filename);
    }

    public String getDirTreeFormat(int level){
        File activeFile = new File(activeEditor.getFileName());
        File dir = activeFile.getAbsoluteFile().getParentFile();
        StringBuilder sb = new StringBuilder();

         getDirTreeFormat(dir, level, sb, "├── ");
         sb.deleteCharAt(sb.length() - 1);
         return sb.toString();
    }

    private void getDirTreeFormat(File folder, int indent,
                                           StringBuilder sb, String prefix) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append(prefix);
        sb.append(folder.getName());
        sb.append("/");
        sb.append("\n");
        int i = 0;
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                if (i == folder.listFiles().length - 1)
                    getDirTreeFormat(file, indent + 1, sb, "└── ");
                else {
                    getDirTreeFormat(file, indent + 1, sb, "├── ");
                }
            } else {
                if (i == folder.listFiles().length - 1) {
                    printFile(file, indent + 1, sb, "└── ");
                } else {
                    printFile(file, indent + 1, sb, "├── ");
                }
            }
            i++;
        }
    }
    private void printFile(File file, int indent, StringBuilder sb, String sep) {
        sb.append(getIndentString(indent));
        sb.append(sep);
        sb.append(file.getName());
        if (isInSession(file)) {
            sb.append("*");
        }
        sb.append("\n");
    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("│   ");
        }
        return sb.toString();
    }

    public String getDirIndentFormat(int indent) {
        File activeFile = new File(activeEditor.getFileName());
        File dir = activeFile.getAbsoluteFile().getParentFile();
        StringBuilder sb = new StringBuilder();
        sb.append(dir.getName()).append("\n");
        printIndent(dir, indent, sb);
        return sb.toString();
    }

    private void printIndent(File dir, int indent, StringBuilder sb) {
        if (!dir.isDirectory()) {
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            // 打印缩进和文件名
            for (int i = 0; i < indent; i++) {
                sb.append(" ");
            }

            sb.append(file.getName()).append(isInSession(file) ? "*" : "");
            sb.append("\n");

            // 递归打印子目录
            if (file.isDirectory()) {
                printIndent(file, indent + 4, sb);
            }
        }
    }

    private boolean isEditing(File file) {
        if (this.activeEditor == null) {
            return false;
        }
        return file.getAbsolutePath().equals(this.activeEditor.getFileName());
    }

    private boolean isModified(File file) {
        if (!editors.containsKey(file.getAbsolutePath())) {
            return false;
        }
        return editors.get(file.getAbsolutePath()).isModified();
    }

    private boolean isInSession(File file) {
        return editors.containsKey(file.getAbsolutePath());
    }

    public void exit() {
        this.dump("./data/session_dump");
    }
}