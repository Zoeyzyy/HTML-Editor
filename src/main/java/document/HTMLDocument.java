package document;

import exception.ElementBadRemoved;
import exception.ElementNotFound;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class HTMLDocument {
    private HTMLElement root;

    @Setter
    private boolean showID;
    private StringBuilder sb;
    private String templatePath;

    public HTMLDocument(HTMLElement root) {
        this.root = root;
        this.showID = false;
        this.sb = new StringBuilder();
        this.templatePath=System.getProperty("user.dir")+"\\src\\main\\resources\\template.html";

    }

    public HTMLDocument() {
        root=null;
        showID = false;
        sb = new StringBuilder();
    }

    public boolean getShowID(){
        return this.showID;
    }

    /**
     * init document
     */
    public void init(){
        root=null;
        read(new File(templatePath));
    }

    /**
     * 读取文件
     */
    public void read(File input){
        try {
            // 读取HTML文件
            Document doc = Jsoup.parse(input, "UTF-8");

            // 获取根元素（HTML标签）
            Element rootElement = doc.children().first(); // 通常是html标签

            // 将JSoup的Element转换为我们的HTMLElement
            this.root = convertJsoupElement(rootElement);

        } catch (IOException e) {
            throw new RuntimeException("Error reading HTML file: " + e.getMessage());
        }
    }

    public String save() {
        return getTreeFormat(false);
    }

    /**
     * 根据Id查找当前HTML下的元素
     * @param id element id
     * @return 满足该id的第一个元素 else null
     */
    public HTMLElement findElementById(String id) {
        return findElementById(id,root);
    }

    /**
     *
     * @param tagName 新建element tag name
     * @param idValue 新建element id value
     * @param textContent new element text content
     * @param parentElement 父元素的id value
     */

    public void appendElement(String tagName, String idValue, String textContent, String parentElement) throws ElementNotFound {
        HTMLElement newElement = HTMLElement.builder()
                                            .setId(idValue)
                                            .setTagName(tagName)
                                            .setTextContent(textContent)
                                            .build();

        insertElementById(parentElement,newElement);
    }

    /**
     * 为Undo设计
     * @param element
     * @throws ElementNotFound
     */
    public void appendElement(HTMLElement element) throws ElementNotFound {

    }


    public void insertElement(String tagName, String idValue, String insertLocation, String textContent) throws ElementNotFound {
        // 创建新元素
        HTMLElement newElement = HTMLElement.builder()
                .setId(idValue)
                .setTagName(tagName)
                .setTextContent(textContent)
                .build();

        // 如果root为空，直接将新元素设为root
        if (root == null) {
            root = newElement;
            return;
        }

        // 使用队列进行BFS搜索
        Queue<HTMLElement> queue = new LinkedList<>();
        // 记录父节点的映射，用于后续插入操作
        Map<HTMLElement, HTMLElement> parentMap = new HashMap<>();

        queue.offer(root);
        HTMLElement targetElement = null;

        // BFS搜索目标位置
        while (!queue.isEmpty()) {
            HTMLElement current = queue.poll();

            // 找到目标位置
            if (current.getId().equals(insertLocation)) {
                targetElement = current;
                break;
            }

            // 将子节点加入队列
            if (current.getChildren() != null) {
                for (HTMLElement child : current.getChildren()) {
                    queue.offer(child);
                    parentMap.put(child, current);
                }
            }
        }

        // 如果没找到目标位置，抛出异常
        if (targetElement == null) {
            throw new ElementNotFound("Element with id " + insertLocation + " not found");
        }

        // 获取目标元素的父节点
        HTMLElement parent = parentMap.get(targetElement);

        // 如果目标元素是root
        if (parent == null) {
            newElement.getChildren().add(root);
            root = newElement;
            return;
        }

        // 在父节点的children列表中找到目标元素的位置
        List<HTMLElement> parentChildren = parent.getChildren();
        int targetIndex = parentChildren.indexOf(targetElement);

        // 在目标元素之前插入新元素
        parentChildren.add(targetIndex, newElement);
    }

    /**
     * 插入element
     * @param parentId 父节点Id
     * @param element 需要插入的元素
     * @throws ElementNotFound
     */
    private void insertElementById(String parentId, HTMLElement element) throws ElementNotFound {
        HTMLElement parent = findElementById(parentId);
        if (parent == null) {
            throw new ElementNotFound("Element: Id "+parentId+" Not Found");
        }
        parent.addChild(element);
    }



    /**
     * 移除元素
     * @param id 需要移除的元素Id
     * @throws ElementBadRemoved
     */
    public void removeElementById(String id) throws ElementBadRemoved {
        if(root.getId().equals(id)){
            throw new ElementBadRemoved("Cannot remove the root element.");
        }
        removeElementById(id,root);
    }

    /**
     * 获取doc 的缩进形式
     * @param indent
     * @return Indent Format html
     */
    public String getIndentFormat(int indent) {
        sb.setLength(0);
        getIndentFormat(this.root,indent,0);
        return sb.toString();
    }

    public String getTreeFormat(boolean spellCheck) {
        sb.setLength(0);
        getTreeFormat(this.root,0);
        return sb.toString();
    }


    private void getIndentFormat(HTMLElement ele, int indent, int level) {
        if(ele==null){
            return;
        }

        printIndent(level, indent);

        String eleTagHeader = String.format("<%s",ele.getTagName());
        if(ele.getTagName()!=null){
            eleTagHeader+=String.format(" id=\"%s\">\n",ele.getId());
        }else
            eleTagHeader+=">\n";

        sb.append(eleTagHeader);

        if(ele.getTextContent()!=null){
            printIndent(level+1, indent);
            sb.append(ele.getTextContent()).append("\n");
        }
        for(HTMLElement child : ele.getChildren()){
            getIndentFormat(child,indent,level+1);
        }

        sb.append(String.format("</%s>\n",ele.getTagName()));
    }

    private void getTreeFormat(HTMLElement element,  int level) {
        // 打印当前元素的标签名和ID
        printIndent(2, level);
        sb.setLength(sb.length() - 1); // 删除printIndent添加的换行符
        sb.append(element.getTagName());

        // 如果有ID，添加ID
        if (element.getId() != null && !element.getId().isEmpty()) {
            sb.append("#").append(element.getId());
        }
        sb.append("\n");

        // 如果有文本内容，添加文本内容
        if (element.getTextContent() != null && !element.getTextContent().isEmpty()) {
            printIndent(2, level + 1);
            sb.append("└── ").append(element.getTextContent()).append("\n");
        }

        // 处理子元素
        List<HTMLElement> children = element.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                HTMLElement child = children.get(i);
                boolean isLast = (i == children.size() - 1);

                // 为子元素添加适当的前缀
                printIndent(2, level + 1);

                if (isLast) {
                    sb.append("└── ");
                } else {
                    sb.append("├── ");
                }

                // 递归处理子元素
                getTreeFormat(child, level + 1);
            }
        }
    }


    public String getSpellCheck() {
        return "";
    }


    private HTMLElement findElementById(String id, HTMLElement ele) {
        if(ele.getId().equals(id)){
            return ele;
        }
        for(HTMLElement child : ele.getChildren()){
            HTMLElement found = findElementById(id, child);
            if(found != null){
                return found;
            }
        }
        return null;
    }

    private void removeElementById(String id, HTMLElement ele) {
        for(HTMLElement child : ele.getChildren()){
            if(child.getId().equals(id)){
                ele.removeChild(child);
            }
        }
    }

    private void printIndent(int indent,int level){
        String format="%" + (level * indent) + "s%s";
        sb.append(String.format(format,"",""));
    }


    /**
     * 将JSoup的Element转换为自定义的HTMLElement
     * @param jsoupElement jsoup 读取出的对象
     * @return HTMLElement
     */
    private HTMLElement convertJsoupElement(Element jsoupElement) {
        if (jsoupElement == null) {
            return null;
        }

        // 创建新的HTMLElement构建器
        HTMLElement.Builder builder = HTMLElement.builder()
                .setTagName(jsoupElement.tagName())
                .setId(jsoupElement.id())
                .setTextContent(jsoupElement.ownText());


        // 构建当前元素
        HTMLElement element = builder.build();

        // 递归处理所有子元素
        for (Element child : jsoupElement.children()) {
            HTMLElement childElement = convertJsoupElement(child);
            if (childElement != null) {
                element.addChild(childElement);
            }
        }

        return element;
    }


    public void editID(String oldID, String newID) {
        findElementById(oldID)
                .setId(newID);
    }

}
