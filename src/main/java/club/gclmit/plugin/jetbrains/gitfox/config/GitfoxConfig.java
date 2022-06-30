package club.gclmit.plugin.jetbrains.gitfox.config;

import java.util.Objects;

import javax.swing.*;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;

import club.gclmit.plugin.jetbrains.gitfox.views.GitfoxSettingView;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:32
 * @since jdk11
 */
public class GitfoxConfig implements Configurable {

    private final GitfoxState gitfoxState;
    private GitfoxSettingView gitfoxSettingView;

    public GitfoxConfig() {
        gitfoxState = GitfoxState.getInstance();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Gitfox";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (null == gitfoxSettingView) {
            gitfoxSettingView = new GitfoxSettingView(gitfoxState);
        }
        return gitfoxSettingView.getMainPanel();
    }

    @Override
    public boolean isModified() {
        return gitfoxSettingView != null && gitfoxSettingView.isModified(gitfoxState);
    }

    @Override
    public void reset() {
        if (null != gitfoxSettingView) {
            gitfoxSettingView.reset(gitfoxState);
        }
    }

    @Override
    public void apply() {
        gitfoxState.loadState(Objects.requireNonNull(gitfoxSettingView.getState().getState()));
    }
}
