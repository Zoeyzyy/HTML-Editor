package session;

import java.util.*;

import editor.Editor;
import lombok.Getter;

import java.io.*;

public class Session {
    @Getter
    private String id;
    private List<String> files;
    private Map<String, Editor> editors;
    @Getter
    private Editor activeEditor;

    public Session(String id) {
        if (this.recover("./data/" + id) == null) {
            this.files = new ArrayList<>();
            this.editors = new HashMap<>();
            this.activeEditor = null;
        }
        this.id = id;
    }

    public void dump(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            List<DumpType> saved = new ArrayList<>();
            for (String file : this.files) {
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

    public List<String> recover(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            @SuppressWarnings("unchecked")
            List<DumpType> list = (List<DumpType>) in.readObject();

            System.out.println("Object loaded from file: " + filename);
            if (list.isEmpty()) {
                return null;
            }
            this.files = new ArrayList<>();
            this.editors = new HashMap<>();

            for (DumpType dump : list) {
                this.files.add(dump.getFileName());
                Editor editor = new Editor();
                editor.load(dump.getFileName());
                editor.setShowId(dump.isShowID());
                this.editors.put(dump.getFileName(), editor);
            }

            this.activeEditor = this.editors.get(list.get(0).getFileName());
            return this.files;
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
            return null;
        }
    }

    public void load(String filename) throws IOException {
        Editor editor = new Editor();
        File file = new File(filename);
        filename = file.getAbsolutePath();
        editor.load(filename);

        editors.put(filename, editor);
        files.add(filename);
        activeEditor = editor;
    }

    public void save(String filename) throws IOException {
        activeEditor.save(filename);
    }

    public boolean confirm() {
        return activeEditor.isModified();
    }

    public void close() {
        if (activeEditor != null) {
            editors.remove(activeEditor.getFileName());
            files.remove(activeEditor.getFileName());
            activeEditor = editors.isEmpty() ? null : editors.get(files.get(0));
        }
    }

    public List<String> getEditorList() {
        return this.files;
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
        if(isEditing(file)){
            sb.append(">");
        }
        sb.append(file.getName());
        if (isModified(file)) {
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
            if (isEditing(file)) {
                sb.append(">");
            }
            sb.append(file.getName()).append(isModified(file) ? "*" : "");
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

    public Session enter(String id) {
        return new Session(id);
    }

    public void exit() {
        this.dump("./data/" + this.id);
    }
}