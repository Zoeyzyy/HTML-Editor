package document;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpellChecker {
    private static final JLanguageTool LANGUAGE_TOOL = new JLanguageTool(new BritishEnglish());

    /**
     * 检查拼写错误并返回修正后的文本
     *
     * @param text 要检查的文本
     * @return 修正后的文本
     * @throws IOException 异常处理
     */
    public String checkSpelling(String text) throws IOException {
        List<RuleMatch> matches = LANGUAGE_TOOL.check(text);
        StringBuilder correctedText = new StringBuilder(text);

        // 从后向前替换以避免位置偏移问题
        for (int i = matches.size() - 1; i >= 0; i--) {
            RuleMatch match = matches.get(i);
            List<String> suggestions = match.getSuggestedReplacements();
            if (!suggestions.isEmpty()) {
                // 使用第一个建议作为修正
                String suggestion = suggestions.get(0);
                correctedText.replace(match.getFromPos(), match.getToPos(), suggestion);
            }
        }

        return correctedText.toString();
    }
}
