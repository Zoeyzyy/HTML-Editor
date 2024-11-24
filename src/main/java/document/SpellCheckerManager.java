package document;

import java.io.IOException;

public class SpellCheckerManager {
    private static volatile SpellChecker instance;

    private SpellCheckerManager() {
        // 私有构造函数，防止外部实例化
    }

    public static SpellChecker getInstance() {
        if (instance == null) {
            synchronized (SpellCheckerManager.class) {
                if (instance == null) {
                    instance = new SpellChecker();
                }
            }
        }
        return instance;
    }
}
