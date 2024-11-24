package document;

import document.documentImpl.HTMLElementImpl;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * base class of html element
 */
@Data
public abstract class HTMLElement {
    private String tagName; // such as <html> <la> and so on 只要存储tag 名称，<>不用存储
    private String Id;
    private String textContent;

    private List<HTMLElement> children = null; // child elements
    private HTMLElement parent; // Parent element pointer
    private HTMLElement previousSibling; // Previous sibling pointer
    private HTMLElement nextSibling; // Next sibling pointer
    private int index = -1; // Index in the parent's children list

    // 用于存储拼写检查结果
    @Getter
    private List<String> spellCheckResults;

    // 是否已经初始化了子节点，避免stackoverflow
    private boolean childrenInitialized = false;
    //默认需要初始化，对于start和tail特殊节点不进行Children的初始化
    @Getter
    private boolean requiresInitialization = true; // 默认需要初始化

    /**
     * Builder模式
     * 外部创建HTMLElement对象就只会调用这个接口里面定义的函数
     */
    public interface Builder {
        Builder setTagName(String tagName);
        Builder setId(String id);
        Builder setClassName(String className);
        Builder setTextContent(String content);
        Builder addChild(HTMLElement child);
        HTMLElement build();
    }

    /**
     * 创建element对象时调用的builder
     * @return 一个HTMLElement builder对象
     */
    public static Builder builder() {
        return new HTMLElementImpl.BuilderImpl();
    }

    /**
     * Initialize with head and tail elements.
     * 用父节点的id进行命名
     */
    public void initializeChildren() {
        if (!requiresInitialization ||children != null) return; // 防止重复初始化

//        System.out.println("Initializing children for element: " + this.getTagName());

        children = new ArrayList<>(); // 初始化列表

        HTMLElement start = HTMLElement.builder()
                .setTagName("start")
                .setId(getId() + "-start")
                .build();
        start.setRequiresInitialization(false);
        start.setParent(this); // 设置父节点
        HTMLElement tail = HTMLElement.builder()
                .setTagName("tail")
                .setId(getId() + "-tail")
                .build();
        tail.setRequiresInitialization(false);
        tail.setParent(this); // 设置父节点

        start.setNextSibling(tail);
        tail.setPreviousSibling(start);

        children.add(start);
        children.add(tail);
    }

    /**
     * 获取子元素列表，延迟初始化
     *
     * @return 子元素列表
     */
    public List<HTMLElement> getChildren(){
        if (!childrenInitialized) {
            synchronized (this) { // 确保多线程安全
                if (!childrenInitialized) {
                    initializeChildren();
                    childrenInitialized = true; // 设置为已初始化
                }
            }
        }
        return children != null ? children : new ArrayList<>();
    }

    /**
     * 插入child
     * @param child HTML element
     */
    public abstract void addChild(HTMLElement child);

    /**
     * 移除子元素
     * @param child HTML element
     */
    public abstract void removeChild(HTMLElement child);

    public abstract void removeChild(String id);

    /**
     * 打印当前元素
     */
    public abstract void display();

    /**
     * 拼写检查
     */
    public abstract List<String> checkSpelling(SpellChecker spellChecker) throws IOException;

    /**
     * 在指定id的元素之前插入新元素
     * @param element 要插入的元素
     * @param targetId 目标元素的id
     */
    public abstract void insertElementBefore(HTMLElement element, String targetId);

    /**
     * 获取指定元素的下一个兄弟元素的 ID
     *
     * @param element 指定的元素
     * @return 下一个兄弟元素的 ID，如果不存在则返回 null
     */
    public abstract String getInsertLocation(HTMLElement element);

}
