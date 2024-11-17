package document;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellChecker {
    private final JLanguageTool languageTool;

    public SpellChecker() {
        // 使用英式英语语言模型
        this.languageTool = new JLanguageTool(new BritishEnglish());
    }

    /**
     * 检查拼写错误
     * @param text 要检查的文本
     * @return 拼写检查结果列表
     * @throws IOException 异常处理
     */
    public List<String> checkSpelling(String text) throws IOException {
        List<String> results = new ArrayList<>();
        List<RuleMatch> matches = languageTool.check(text);
        for (RuleMatch match : matches) {
            results.add(String.format(
                    "Error at position %d-%d: %s. Suggested correction(s): %s",
                    match.getFromPos(), match.getToPos(),
                    match.getMessage(), match.getSuggestedReplacements()
            ));
        }
        return results;
    }
}
