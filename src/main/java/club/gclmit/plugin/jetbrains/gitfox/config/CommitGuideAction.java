package club.gclmit.plugin.jetbrains.gitfox.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.VcsDataKeys;
import com.intellij.openapi.vcs.ui.Refreshable;

import club.gclmit.plugin.jetbrains.gitfox.views.CommitGuideDialog;

/**
 * 提交规范Action
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 11:45
 */
public class CommitGuideAction extends DumbAwareAction {

    @Nullable
    private static CommitMessageI getCommitPanel(@Nullable AnActionEvent event) {
        if (event == null) {
            return null;
        }
        Refreshable data = Refreshable.PANEL_KEY.getData(event.getDataContext());
        if (data instanceof CommitMessageI) {
            return (CommitMessageI)data;
        }
        return VcsDataKeys.COMMIT_MESSAGE_CONTROL.getData(event.getDataContext());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {

        final CommitMessageI commitPanel = getCommitPanel(event);
        if (commitPanel == null) {
            return;
        }
        CommitGuideDialog dialog = new CommitGuideDialog(event.getProject());
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            commitPanel.setCommitMessage(dialog.getCommitMessage());
        }
    }
}
