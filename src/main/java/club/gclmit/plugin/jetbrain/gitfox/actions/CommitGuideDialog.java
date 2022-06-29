package club.gclmit.plugin.jetbrain.gitfox.actions;

import club.gclmit.plugin.jetbrain.gitfox.views.CommitGuideView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CommitGuide通知
 *
 * @author gclm
 * @since jdk11
 */
public class CommitGuideDialog extends DialogWrapper {

    private final CommitGuideView panel;

    public CommitGuideDialog(@Nullable Project project) {
        super(project);
        panel = new CommitGuideView(project);
        setTitle("Commit");
        setOKButtonText("OK");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel.getMainPanel();
    }

    public String getCommitMessage() {
        return panel.getCommitMessage();
    }

}
