package club.gclmit.plugin.jetbrains.gitfox.config;

import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.Item;
import club.gclmit.plugin.jetbrains.gitfox.views.GitfoxSettingPanel;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GitfoxConfig
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:32
 * @since jdk11
 */
public class GitfoxConfig implements SearchableConfigurable {

    private final GitfoxState gitfoxState;
    private GitfoxSettingPanel gitfoxSettingPanel;

    public GitfoxConfig() {
        gitfoxState = GitfoxState.getInstance();
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "club.gclmit.plugin.jetbrains.gitfox.config.GitfoxConfig";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Gitfox";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == gitfoxSettingPanel) {
            gitfoxSettingPanel = new GitfoxSettingPanel();
        }
        return gitfoxSettingPanel.getMainPanel();
    }

    @Override
    public boolean isModified() {
        if (gitfoxSettingPanel != null) {
            Gitfox gitfox = gitfoxSettingPanel.getGitfox();
            if (gitfox != null) {
                System.out.println("1:" + gitfox);
                System.out.println("2:" + gitfoxState.getState());
                System.out.println("3:" + ObjectUtil.equals(gitfox, gitfoxState.getState()));
                if (!ObjectUtil.equals(gitfox, gitfoxState.getState())) {
                    List<String> updateStyles =
                        gitfox.getItems().stream().map(Item::getKey).collect(Collectors.toList());
                    int itemCount = gitfoxState.getState().getItems().size();
                    if (CollectionUtil.isNotEmpty(updateStyles)
                        && (!updateStyles.contains(gitfox.getStyle()) || updateStyles.size() != itemCount)) {
                        gitfoxSettingPanel.fixStyle(updateStyles, gitfox.getStyle());
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 复写重置方法
     */
    @Override
    public void reset() {
        if (null != gitfoxSettingPanel) {
            gitfoxSettingPanel.reset(gitfoxState.getState());
        }
    }

    /**
     * 复写保存设置
     */
    @Override
    public void apply() {
        if (gitfoxSettingPanel != null) {
            Gitfox gitfox = gitfoxSettingPanel.getGitfox();
            if (gitfox != null) {
                gitfoxState.loadState(CglibUtil.copy(gitfox, Gitfox.class));
            }
        }
    }
}
