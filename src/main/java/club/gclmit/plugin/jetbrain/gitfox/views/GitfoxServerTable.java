package club.gclmit.plugin.jetbrain.gitfox.views;

import club.gclmit.plugin.jetbrain.gitfox.model.GitfoxServer;
import club.gclmit.plugin.jetbrain.gitfox.state.GitfoxState;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 13:54
 * @since jdk11
 */
public class GitfoxServerTable extends JBTable {
    private static final Logger log = Logger.getInstance(GitfoxServerTable.class);

    private static final int NAME_COLUMN = 0;
    private static final int VALUE_COLUMN = 1;
    private final GitfoxTableModel foxTableModel = new GitfoxTableModel();

    private final List<GitfoxServer> gitfoxServers = new LinkedList<>();

    /**
     * instantiation GitfoxServerTable
     */
    public GitfoxServerTable() {
        setModel(foxTableModel);
        TableColumn column = getColumnModel().getColumn(NAME_COLUMN);
        TableColumn valueColumn = getColumnModel().getColumn(VALUE_COLUMN);
        column.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                final String macroValue = getGitfoxServerValueAt(row);
                component.setForeground(macroValue.length() == 0
                        ? JBColor.RED
                        : isSelected ? table.getSelectionForeground() : table.getForeground());
                return component;
            }
        });
        setColumnSize(column, 150, 250, 150);
        setColumnSize(valueColumn, 550, 750, 550);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


    /**
     * Set  Something  ColumnSize
     */
    public static void setColumnSize(TableColumn column, int preferedWidth, int maxWidth, int minWidth) {
        column.setPreferredWidth(preferedWidth);
        column.setMaxWidth(maxWidth);
        column.setMinWidth(minWidth);
    }

    public String getGitfoxServerValueAt(int row) {
        return (String) getValueAt(row, VALUE_COLUMN);
    }

    public void commit(GitfoxState state) {
        state.getGitfox().setGitfoxServers(gitfoxServers);
    }

    public void addGitfoxServer() {
    }

    public void moveUp() {
    }

    public void moveDown() {
    }

    public void removeSelectedGitfoxServers() {
    }

    public void resetDefaultGitfoxServers() {
        foxTableModel.fireTableDataChanged();
    }


    public void reset(GitfoxState state) {
        obtainGitfoxServers(gitfoxServers, state);
        foxTableModel.fireTableDataChanged();
    }

    private void obtainGitfoxServers(@NotNull List<GitfoxServer> gitfoxServers, GitfoxState state) {
        gitfoxServers.clear();
        gitfoxServers.addAll(state.getGitfox().getGitfoxServers());
    }

    public boolean editGitfoxServer() {
        if (getSelectedRowCount() != 1) {
            return false;
        }
        final int selectedRow = getSelectedRow();
        final GitfoxServer gitfoxServer = gitfoxServers.get(selectedRow);
        final SettingEditorView editor = new SettingEditorView("Edit Setting", gitfoxServer.getKey(), gitfoxServer.getValue());
        if (editor.showAndGet()) {
            gitfoxServer.setKey(editor.getKey());
            gitfoxServer.setValue(editor.getValue());
            foxTableModel.fireTableDataChanged();
        }
        return true;
    }

    public boolean isModified(GitfoxState state) {
        final List<GitfoxServer> gitfoxServersModified = new LinkedList<>();
        obtainGitfoxServers(gitfoxServersModified, state);
        return !gitfoxServersModified.equals(gitfoxServers);
    }

    private boolean isValidRow(int selectedRow) {
        return selectedRow >= 0 && selectedRow < gitfoxServers.size();
    }

    private static class EditValidator implements SettingEditorView.Validator {
        @Override
        public boolean isOK(String name, String value) {
            return !name.isEmpty() && !value.isEmpty();
        }
    }

    /**
     * GitfoxTableModel
     */
    private class GitfoxTableModel extends AbstractTableModel {
        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return gitfoxServers.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final GitfoxServer pair = gitfoxServers.get(rowIndex);
            switch (columnIndex) {
                case NAME_COLUMN:
                    return pair.getKey();
                case VALUE_COLUMN:
                    return pair.getValue();
                default:
                    log.error("Wrong indices");
                    return null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case NAME_COLUMN:
                    return "key";
                case VALUE_COLUMN:
                    return "value";
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

}
