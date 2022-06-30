package club.gclmit.plugin.jetbrains.gitfox.views;

import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.plugin.jetbrains.gitfox.model.CommitGuide;
import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.GitfoxServer;
import club.gclmit.plugin.jetbrains.gitfox.services.CommitGuideService;
import club.gclmit.plugin.jetbrains.gitfox.config.GitfoxState;
import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.RepositoryImpl;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.WordUtils;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


/**
 * @author gclm
 */
public class CommitGuideView {

    private JPanel mainPanel;
    private JComboBox<String> commitTemplateList;
    private JTextField gitBranch;
    private JTextField shortDescription;
    private JTextArea longDescription;

    private static final Integer MAX_LINE_LENGTH = 72;
    private CommitGuide currentMessage;
    private final GitfoxState gitfoxState;

    public CommitGuideView(Project project) {
        gitfoxState = GitfoxState.getInstance();
        Gitfox gitfox = gitfoxState.getState();
        String type = gitfox.getType();
        GitfoxServer gitfoxServer = gitfox.getGitfoxServers().stream().filter(server -> type.equals(server.getKey())).findFirst().get();
        String url = gitfoxServer.getValue();
        List<CommitGuide> templateList = CommitGuideService.getCommitGuideRule(url);
        currentMessage = templateList.get(0);

        for (CommitGuide message : templateList) {
            String content = gitfox.getUseChinese() ? message.getCode() + "(" + message.getDescription() + ")" : message.getCode() + "(" + message.getDescriptionEn() + ")";
            commitTemplateList.addItem(content);
        }

        Collection<Repository> repositories = VcsRepositoryManager.getInstance(project).getRepositories();
        if (!repositories.isEmpty()) {
            String currentBranchName = ((RepositoryImpl) ((ArrayList<?>) repositories).get(0)).getCurrentBranchName();
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
        Gitfox gitfox = gitfoxState.getState();
        String branch = gitBranch.getText().trim();
        if (StringUtils.isNotBlank(branch) && Objects.requireNonNull(gitfox).getShowBranch()) {
            return String.format(CommitGuide.COMMIT_GUIDE_BRANCH_TEMPLATE, currentMessage.getCode(), shortDescription.getText(), WordUtils.wrap(longDescription.getText(), MAX_LINE_LENGTH), branch);
        } else {
            return String.format(CommitGuide.COMMIT_GUIDE_TEMPLATE, currentMessage.getCode(), shortDescription.getText(), WordUtils.wrap(longDescription.getText(), MAX_LINE_LENGTH));
        }
    }

}
