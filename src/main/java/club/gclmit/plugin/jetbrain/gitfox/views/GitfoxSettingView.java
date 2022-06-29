package club.gclmit.plugin.jetbrain.gitfox.views;

import club.gclmit.plugin.jetbrain.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrain.gitfox.state.GitfoxState;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 13:38
 * @since jdk11
 */
public class GitfoxSettingView {
    private JComboBox gitfoxServerList;
    private JLabel type;
    private JTabbedPane tabbedPane;
    private JPanel serverPanel;
    private JCheckBox showBranch;
    private JCheckBox useChinese;
    private JPanel mainPanel;

    // 配置
    protected GitfoxState gitfoxState;
    private final GitfoxServerTable gitfoxServerTable;

    public GitfoxSettingView(GitfoxState state) {
        this.gitfoxState = state;
        gitfoxServerTable = new GitfoxServerTable();

        //init otherSettingPanel
//        serverPanel.add(
//                ToolbarDecorator.createDecorator(gitfoxServerTable)
//                        .setAddAction(button -> gitfoxServerTable.addGitfoxServer())
//                        .setRemoveAction(button -> gitfoxServerTable.removeSelectedGitfoxServers())
//                        .setEditAction(button -> gitfoxServerTable.editGitfoxServer())
//                        .setMoveUpAction(button -> gitfoxServerTable.moveUp())
//                        .setMoveDownAction(button -> gitfoxServerTable.moveDown())
//                        .addExtraAction
//                                (new AnActionButton("Reset Default Other Setting", AllIcons.Actions.Rollback) {
//                                    @Override
//                                    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
//                                        gitfoxServerTable.resetDefaultGitfoxServers();
//                                    }
//                                }).createPanel(), BorderLayout.CENTER);
//        new DoubleClickListener() {
//            @Override
//            protected boolean onDoubleClick(MouseEvent e) {
//                return gitfoxServerTable.editGitfoxServer();
//            }
//        }.installOn(gitfoxServerTable);
    }

    public GitfoxState getState() {
        gitfoxServerTable.commit(gitfoxState);
        return gitfoxState;
    }

    public void reset(GitfoxState gitfoxState) {
        Gitfox gitfox = gitfoxState.getGitfox();
//        ApplicationManager.getApplication().runWriteAction(() -> templateEditor.getDocument().setText(settings.getDateSettings().getTemplate()));
        gitfox.setShowBranch(showBranch.isSelected());
        gitfox.setUseChinese(useChinese.isSelected());
        gitfox.setType(type.getText());
        gitfoxServerTable.reset(gitfoxState);
    }


    public boolean isSettingsModified(GitfoxState data) {
        if (gitfoxServerTable.isModified(data)) {
            return true;
        }
        return isModified(data);
    }

    public boolean isModified(GitfoxState data) {
        if (!StringUtil.equals(gitfoxState.getGitfox().getType(), data.getGitfox().getType())) {
            return true;
        }
        if (gitfoxState.getGitfox().isShowBranch() == data.getGitfox().isShowBranch()) {
            return true;
        }
        if (gitfoxState.getGitfox().isUseChinese() == data.getGitfox().isUseChinese()) {
            return true;
        }
        return gitfoxState.getGitfox().getGitfoxServers() == data.getGitfox().getGitfoxServers();
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
