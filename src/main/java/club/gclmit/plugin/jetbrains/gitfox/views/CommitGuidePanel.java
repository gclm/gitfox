package club.gclmit.plugin.jetbrains.gitfox.views;

import club.gclmit.gear4j.core.utils.StringUtils;
import club.gclmit.plugin.jetbrains.gitfox.config.GitfoxState;
import club.gclmit.plugin.jetbrains.gitfox.model.CommitGuide;
import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.Item;
import club.gclmit.plugin.jetbrains.gitfox.services.CommitGuideService;
import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.RepositoryImpl;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.WordUtils;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author gclm
 */
public class CommitGuidePanel {

    private JPanel mainPanel;
    private JComboBox<String> commitTemplateList;
    private JTextField gitBranch;
    private JTextField shortDescription;
    private JTextArea longDescription;
    private JCheckBox skipCiCheckBox;
    private JCheckBox showBranchCheckBox;
    private CommitGuide currentMessage;

    public CommitGuidePanel(Project project) {
        Gitfox gitfox = GitfoxState.getInstance().getState();
        Item item = Item.getItem(gitfox.getItems(), gitfox.getStyle(), true);
        String url = null != item ? item.getValue() : null;

        List<CommitGuide> templateList = CommitGuideService.getCommitGuideRule(url);
        currentMessage = templateList.get(0);
        for (CommitGuide message : templateList) {
            String content = GitfoxState.DEFAULT_LANGUAGE.equals(gitfox.getLanguage())
                ? message.getCode() + "(" + message.getDescriptionEn() + ")"
                : message.getCode() + "(" + message.getDescription() + ")";
            commitTemplateList.addItem(content);
        }

        Collection<Repository> repositories = VcsRepositoryManager.getInstance(project).getRepositories();
        if (!repositories.isEmpty()) {
            String currentBranchName = ((RepositoryImpl)((ArrayList<?>)repositories).get(0)).getCurrentBranchName();
            gitBranch.setText(currentBranchName);
        }

        commitTemplateList.addItemListener(event -> {
            if (ItemEvent.SELECTED == event.getStateChange()) {
                String content = StringUtils.subBetween(event.getItem().toString(), "(", ")");
                for (CommitGuide message : templateList) {
                    if (content.equals(message.getDescription()) || content.equals(message.getDescriptionEn())) {
                        currentMessage = message;
                        break;
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getCommitMessage() {
        String branch = gitBranch.getText().trim();
        String commitMessage = String.format(CommitGuide.COMMIT_GUIDE_TEMPLATE, currentMessage.getCode(),
            shortDescription.getText(), WordUtils.wrap(longDescription.getText(), CommitGuide.MAX_LINE_LENGTH));

        if (StringUtils.isNotBlank(branch) && showBranchCheckBox.isSelected()) {
            commitMessage = String.format(CommitGuide.COMMIT_GUIDE_BRANCH_TEMPLATE, currentMessage.getCode(),
                shortDescription.getText(), WordUtils.wrap(longDescription.getText(), CommitGuide.MAX_LINE_LENGTH),
                branch);
        }
        return skipCiCheckBox.isSelected() ? commitMessage + CommitGuide.CI_TEMPLATE : commitMessage;
    }
}
