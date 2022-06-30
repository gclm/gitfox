package club.gclmit.plugin.jetbrains.gitfox.views;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.*;

import org.jetbrains.annotations.NotNull;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;

import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.plugin.jetbrains.gitfox.config.GitfoxState;
import club.gclmit.plugin.jetbrains.gitfox.model.Gitfox;
import club.gclmit.plugin.jetbrains.gitfox.model.GitfoxServer;
import cn.hutool.core.collection.CollectionUtil;

/**
 * gitfox 配置视图
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 13:38
 * @since jdk11
 */
public class GitfoxSettingView {
    private final GitfoxServerTable gitfoxServerTable;
    // 配置
    protected GitfoxState gitfoxState;
    private JComboBox<String> gitfoxServerList;
    private JLabel type;
    private JCheckBox showBranch;
    private JCheckBox useChinese;
    private JPanel mainPanel;
    private JTabbedPane servicePanel;

    public GitfoxSettingView(GitfoxState state) {
        this.gitfoxState = state;
        gitfoxServerTable = new GitfoxServerTable();

        // init otherSettingPanel
        servicePanel.add(ToolbarDecorator.createDecorator(gitfoxServerTable)
            .setAddAction(button -> gitfoxServerTable.addGitfoxServer())
            .setRemoveAction(button -> gitfoxServerTable.removeSelectedGitfoxServers())
            .setEditAction(button -> gitfoxServerTable.editGitfoxServer())
            .setMoveUpAction(button -> gitfoxServerTable.moveUp())
            .setMoveDownAction(button -> gitfoxServerTable.moveDown())
            .addExtraAction(new AnActionButton("Reset Default GitfoxServer", AllIcons.Actions.Rollback) {
                @Override
                public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                    gitfoxServerTable.resetDefaultGitfoxServers();
                }
            }).createPanel(), BorderLayout.CENTER);
        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(@NotNull MouseEvent e) {
                return gitfoxServerTable.editGitfoxServer();
            }
        }.installOn(gitfoxServerTable);
    }

    public GitfoxState getState() {
        Gitfox gitfox = gitfoxState.getState();
        gitfox.setType(Objects.requireNonNull(gitfoxServerList.getSelectedItem()).toString());
        gitfox.setShowBranch(showBranch.isSelected());
        gitfox.setUseChinese(useChinese.isSelected());
        gitfoxServerTable.commit(gitfoxState);
        return gitfoxState;
    }

    public void reset(GitfoxState gitfoxState) {
        Gitfox gitfox = gitfoxState.getState();
        for (GitfoxServer server : gitfox.getGitfoxServers()) {
            gitfoxServerList.addItem(server.getKey());
        }
        if (StringUtils.isNotBlank(gitfox.getType())) {
            gitfoxServerList.setSelectedItem(gitfox.getType());
        }
        showBranch.setSelected(gitfox.getShowBranch());
        useChinese.setSelected(gitfox.getUseChinese());
        gitfoxServerTable.reset(gitfoxState);
    }

    public boolean isModified(GitfoxState state) {
        Gitfox gitfox = state.getState();
        String type = gitfoxServerList.getSelectedItem().toString().trim();
        if (!StringUtils.equalsIgnoreCase(gitfox.getType(), type)) {
            return true;
        }
        if (gitfox.getUseChinese() != useChinese.isSelected()) {
            return true;
        }
        if (gitfox.getShowBranch() != showBranch.isSelected()) {
            return true;
        }
        return !CollectionUtil.isEqualList(gitfoxState.getState().getGitfoxServers(), gitfox.getGitfoxServers());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
