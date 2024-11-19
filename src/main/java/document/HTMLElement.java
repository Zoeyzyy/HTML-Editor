package document;

import document.documentImpl.HTMLElementImpl;
import lombok.Data;
import lombok.Getter;

import java.io.IOException;
import java.util.List;

/**
 * base class of html element
 */
@Data
public abstract class HTMLElement {
    private String tagName; // such as <html> <la> and so on 只要存储tag 名称，<>不用存储
    private String Id;
    private String textContent;
    private List<HTMLElement> children; // child elements

    // 用于存储拼写检查结果
    @Getter
    private List<String> spellCheckResults;

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
     * @return 该element下的所有一级children
     */
    public List<HTMLElement> getChildren() {
        return null;
    }

    /**
     * 打印当前元素
     */
    public abstract void display();

    /**
     * 拼写检查
     */
    public abstract List<String> checkSpelling(SpellChecker spellChecker) throws IOException;

    public void updateSpellCheckResults(SpellChecker spellChecker) throws IOException {
        this.spellCheckResults = checkSpelling(spellChecker);
    }

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
