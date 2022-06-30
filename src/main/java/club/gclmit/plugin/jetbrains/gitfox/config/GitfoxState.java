package club.gclmit.plugin.jetbrains.gitfox.config;

import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.GitfoxServer;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * gitfox 存储模块
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:11
 * @since jdk11
 */

@State(
        name = "club.gclmit.plugin.jetbrain.gitfox.actions.GitfoxState",
        storages = @Storage("GitfoxState.xml")
)
public class GitfoxState implements PersistentStateComponent<Gitfox> {
    private static final Logger log = Logger.getInstance(GitfoxState.class);

    public static final String DEFAULT_STYLE = "gitmoji";

    public GitfoxState() {
    }

    public static GitfoxState getInstance() {
        return ApplicationManager.getApplication().getService(GitfoxState.class);
    }

    private Gitfox gitfox;

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
            gitfox.setType(DEFAULT_STYLE);
            gitfox.setShowBranch(true);
            gitfox.setUseChinese(true);
            List<GitfoxServer> gitfoxServers = new LinkedList<>();
            gitfoxServers.add(new GitfoxServer("gitmoji", "https://gclm.coding.net/p/cdn/d/public/git/raw/master/gitmoji.json"));
            gitfoxServers.add(new GitfoxServer("angular", "https://gclm.coding.net/p/cdn/d/public/git/raw/master/angular.json"));
            gitfox.setGitfoxServers(gitfoxServers);
        } catch (Exception e) {
            log.error("loadDefaultSettings failed", e);
        }
    }

}
