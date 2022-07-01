package club.gclmit.plugin.jetbrains.gitfox.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;

import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.Item;

/**
 * gitfox 存储模块
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:11
 * @since jdk11
 */

@State(name = "club.gclmit.plugin.jetbrains.gitfox.config.GitfoxState", storages = @Storage("gitfoxState.xml"))
public class GitfoxState implements PersistentStateComponent<Gitfox> {
    public static final String DEFAULT_STYLE = "gitmoji";
    public static final String DEFAULT_LANGUAGE = "中文";

    public static List<String> LANGUAGE_List = new ArrayList<>(5);

    static {
        LANGUAGE_List.add("中文");
        LANGUAGE_List.add("English");
    }

    private static final Logger log = Logger.getInstance(GitfoxState.class);
    private Gitfox gitfox;

    public GitfoxState() {}

    public static GitfoxState getInstance() {
        return ApplicationManager.getApplication().getService(GitfoxState.class);
    }

    @Nullable
    @Override
    public Gitfox getState() {
        if (null == this.gitfox) {
            loadDefaultSettings();
        }
        return gitfox;
    }

    @Override
    public void loadState(@NotNull Gitfox gitfox) {
        this.gitfox = gitfox;
    }

    /**
     * 加载默认配置
     */
    private void loadDefaultSettings() {
        gitfox = new Gitfox();
        try {
            gitfox.setStyle(DEFAULT_STYLE);
            gitfox.setLanguage(DEFAULT_LANGUAGE);
            List<Item> items = new LinkedList<>();
            items.add(new Item("gitmoji", "https://gclm.coding.net/p/cdn/d/public/git/raw/master/gitmoji.json"));
            items.add(new Item("angular", "https://gclm.coding.net/p/cdn/d/public/git/raw/master/angular.json"));
            gitfox.setItems(items);
        } catch (Exception e) {
            log.error("loadDefaultSettings failed", e);
        }
    }

}
