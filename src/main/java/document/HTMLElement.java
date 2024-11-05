package document;

import java.util.List;

/**
 * Interface of html element
 */
public interface HTMLElement {
    /**
     * @return 返回当前元素Id
     */
    String getId();

    /**
     * 插入child
     * @param child HTML element
     */
    void addChild(HTMLElement child);

    /**
     * 移除子元素
     * @param child
     */
    void removeChild(HTMLElement child);

    void removeChild(String id);

    /**
     *
     * @return 该element下的所有一级children
     */
    List<HTMLElement> getChildren();

    /**
     * 打印当前元素
     */
    void display();
}
