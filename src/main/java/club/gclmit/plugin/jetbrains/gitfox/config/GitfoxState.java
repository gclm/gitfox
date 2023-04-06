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
    public static final String DEFAULT_STYLE = "Gitmoji";
    public static final String DEFAULT_LANGUAGE = "English";

    public static List<String> LANGUAGE_List = new ArrayList<>(5);

    static {
        LANGUAGE_List.add("Chinese");
        LANGUAGE_List.add("English");
    }

    private Gitfox gitfox;

    public GitfoxState() {}

    public static GitfoxState getInstance() {
        return ApplicationManager.getApplication().getService(GitfoxState.class);
    }

    @Nullable
    @Override
    public Gitfox getState() {
        if (null == gitfox) {
            gitfox = loadDefaultSettings();
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
    public static Gitfox loadDefaultSettings() {
        Gitfox gitfox = new Gitfox();
        gitfox.setStyle(DEFAULT_STYLE);
        gitfox.setLanguage(DEFAULT_LANGUAGE);
        List<Item> items = new LinkedList<>();
        items.add(new Item("Gitmoji", "https://gitcode.net/gclmit/gitfox/-/raw/master/gitmoji.json"));
        items.add(new Item("Angular", "https://gitcode.net/gclmit/gitfox/-/raw/master/angular.json"));
        gitfox.setItems(items);
        return gitfox;
    }
}
