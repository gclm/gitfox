package club.gclmit.plugin.jetbrain.gitfox.views;

import club.gclmit.plugin.jetbrain.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrain.gitfox.config.GitfoxState;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;

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
    private JCheckBox showBranch;
    private JCheckBox useChinese;
    private JPanel mainPanel;
    private JTable table1;

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
        Gitfox gitfox = gitfoxState.getState();
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
        Gitfox gitfox = gitfoxState.getState();
        if (!StringUtil.equals(gitfoxState.getState().getType(), data.getState().getType())) {
            return true;
        }
        if (gitfoxState.getState().isShowBranch() == data.getState().isShowBranch()) {
            return true;
        }
        if (gitfoxState.getState().isUseChinese() == data.getState().isUseChinese()) {
            return true;
        }
        return gitfoxState.getState().getGitfoxServers() == data.getState().getGitfoxServers();
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}
