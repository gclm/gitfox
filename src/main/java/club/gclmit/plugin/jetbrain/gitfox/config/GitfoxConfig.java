package club.gclmit.plugin.jetbrain.gitfox.config;

import club.gclmit.plugin.jetbrain.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrain.gitfox.views.GitfoxSettingComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 14:32
 * @since jdk11
 */
public class GitfoxConfig implements Configurable {

    private GitfoxSettingComponent gitfoxSettingComponent;

    private final GitfoxState gitfoxState;

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
        if (null == gitfoxSettingComponent) {
            gitfoxSettingComponent = new GitfoxSettingComponent();
        }
        return gitfoxSettingComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        Gitfox gitfox = gitfoxState.getState();
        boolean modified = gitfoxSettingComponent.getShowBranchStatus() != gitfox.isShowBranch();
        modified |= gitfoxSettingComponent.getUseChineseStatus() != gitfox.isUseChinese();
        return modified;
    }

    @Override
    public void apply() {
        Gitfox gitfox = gitfoxState.getState();
        gitfox.setShowBranch(gitfoxSettingComponent.getShowBranchStatus());
        gitfox.setUseChinese(gitfoxSettingComponent.getUseChineseStatus());
        gitfoxState.loadState(gitfox);
    }

    @Override
    public void reset() {
        Gitfox gitfox = gitfoxState.getState();
        gitfoxSettingComponent.setShowBranchStatus(gitfox.isShowBranch());
        gitfoxSettingComponent.setUseChineseStatus(gitfox.isUseChinese());
    }

    @Override
    public void disposeUIResources() {
        gitfoxSettingComponent = null;
    }

}
