package club.gclmit.gitfox.views;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;


import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.RepositoryImpl;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.project.Project;

import club.gclmit.gitfox.config.GitfoxState;
import club.gclmit.gitfox.model.CommitGuide;
import club.gclmit.gitfox.model.Gitfox;
import club.gclmit.gitfox.model.Item;
import club.gclmit.gitfox.services.CommitGuideService;
import cn.hutool.core.util.StrUtil;

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
            String content = GitfoxState.DEFAULT_LANGUAGE.equals(gitfox.getLanguage()) ? message.getCode() + "("
                + message.getDescriptionEn() + ")" : message.getCode() + "(" + message.getDescription() + ")";
            commitTemplateList.addItem(content);
        }

        Collection<Repository> repositories = VcsRepositoryManager.getInstance(project).getRepositories();
        if (!repositories.isEmpty()) {
            String currentBranchName = ((RepositoryImpl) ((ArrayList<?>) repositories).get(0)).getCurrentBranchName();
            gitBranch.setText(currentBranchName);
        }

        commitTemplateList.addItemListener(event -> {
            if (ItemEvent.SELECTED == event.getStateChange()) {
                String content = StrUtil.subBetween(event.getItem().toString(), "(", ")");
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
            shortDescription.getText(), longDescription.getText());

        if (StrUtil.isNotBlank(branch) && showBranchCheckBox.isSelected()) {
            commitMessage = String.format(CommitGuide.COMMIT_GUIDE_BRANCH_TEMPLATE, currentMessage.getCode(),
                shortDescription.getText(), longDescription.getText(), branch);
        }
        return skipCiCheckBox.isSelected() ? commitMessage + CommitGuide.CI_TEMPLATE : commitMessage;
    }
}
