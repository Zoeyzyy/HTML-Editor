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
    private boolean showID=false;
    private final boolean[] isLastChild = new boolean[100];
    private final StringBuilder sb=new StringBuilder();
    private final String templatePath=System.getProperty("user.dir")+"\\src\\main\\resources\\template.html";

    public HTMLDocument(HTMLElement root) {
        this.root = root;

    }

    public HTMLDocument() {
        root=null;
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
    public HTMLElement findElementById(String id) throws ElementNotFound {
        HTMLElement element=findElementById(id,root);
        if (element == null) {
            throw new ElementNotFound("Element: Id " +id+" Not Found");
        }
        return element;
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

        HTMLElement parent = findElementById(parentElement);

        if (parent == null) {
            throw new ElementNotFound("Element: Id "+parentElement+" Not Found");
        }
        parent.addChild(newElement);
    }

    /**
     * 为Undo设计
     * @param element
     * @throws ElementNotFound
     */
    public void appendElement(HTMLElement element) {
        element.getParent().addChild(element);
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
        HTMLElement sibling = findElementById(insertLocation);
        if (sibling == null) {
            throw new ElementNotFound("Element: Id "+insertLocation+" Not Found");
        }

        sibling.getParent().insertElementBefore(newElement,insertLocation);
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
        HTMLElement element = findElementById(id);
        element.getParent().removeChild(element);
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
        if(ele.getId()!=null && getShowID()){
            eleTagHeader+=String.format(" id=\"%s\">\n",ele.getId());
        }else
            eleTagHeader+=">\n";

        sb.append(eleTagHeader);

        if(ele.getTextContent()!=null && !ele.getTextContent().isEmpty()){
            printIndent(level, indent);
            sb.append(ele.getTextContent()).append("\n");
        }
        for(HTMLElement child : ele.getChildren()){
            getIndentFormat(child,indent,level+1);
        }

        printIndent(level, indent);
        sb.append(String.format("</%s>",ele.getTagName()));
        if(level!=0)
            sb.append("\n");
    }

    private void getTreeFormat(HTMLElement element, int level) {
        // 打印当前元素的标签名和ID
        if (level > 0) {
            // 添加正确的缩进和连接符
            for (int i = 0; i < level - 1; i++) {
                if (isLastChild[i]) {
                    sb.append("  ");
                } else {
                    sb.append("│ ");
                }
            }
            if (isLastChild[level - 1]) {
                sb.append("└── ");
            } else {
                sb.append("├── ");
            }
        }

        sb.append(element.getTagName());

        // 如果有ID，添加ID
        if (element.getId() != null && !element.getId().isEmpty() && getShowID()) {
            sb.append("#").append(element.getId());
        }
        sb.append("\n");

        // 处理子元素
        List<HTMLElement> children = element.getChildren();
        if (children != null && !children.isEmpty()) {
            for (int i = 0; i < children.size(); i++) {
                boolean isLast = (i == children.size() - 1);
                isLastChild[level] = isLast;  // 记录当前层级是否是最后一个子元素
                getTreeFormat(children.get(i), level + 1);
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


    private void printIndent(int indent,int level){
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < level * indent; i++) {
            spaces.append(" ");
        }
        sb.append(spaces);
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
                .setId(jsoupElement.id().isEmpty() ?jsoupElement.tagName():jsoupElement.id())
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


    public void editID(String oldID, String newID) throws ElementNotFound {
        findElementById(oldID).setId(newID);
    }

    /**
     * 编辑元素的文本内容
     * @param id 要编辑的元素ID
     * @param newText 新的文本内容
     * @throws ElementNotFound 如果找不到指定ID的元素
     */
    public void editText(String id, String newText) throws ElementNotFound {
        HTMLElement element = findElementById(id);
        element.setTextContent(newText);
    }

}
