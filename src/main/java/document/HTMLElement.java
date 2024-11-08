package document;

import lombok.Data;

import java.util.List;

/**
 * base class of html element
 */
@Data
public abstract class HTMLElement {
    private String tagName;
    private String Id;
    private String textContent;
    private List<HTMLElement> children;

    /**
     * Builder模式
     * 外部创建HTMLElement对象就只会调用这个接口里面定义的函数
     */
    interface Builder {
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
    static Builder builder() {
        // TODO
        throw new UnsupportedOperationException("Implementation not yet available");
    }

    /**
     * 插入child
     * @param child HTML element
     */
    abstract void addChild(HTMLElement child);

    /**
     * 移除子元素
     * @param child
     */
    abstract void removeChild(HTMLElement child);

    abstract void removeChild(String id);

    /**
     * @return 该element下的所有一级children
     */
    abstract List<HTMLElement> getChildren();

    /**
     * 打印当前元素
     */
    abstract void display();
}
