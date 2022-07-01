package club.gclmit.plugin.jetbrains.gitfox.views;

import javax.swing.*;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;

/**
 * CommitGuide通知
 *
 * @author gclm
 * @since jdk11
 */
public class CommitGuideDialog extends DialogWrapper {

    private final CommitGuidePanel panel;

    public CommitGuideDialog(@Nullable Project project) {
        super(project);
        panel = new CommitGuidePanel(project);
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
