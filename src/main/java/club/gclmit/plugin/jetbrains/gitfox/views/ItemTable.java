package club.gclmit.plugin.jetbrains.gitfox.views;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.JBColor;
import com.intellij.ui.table.JBTable;

import club.gclmit.plugin.jetbrains.gitfox.model.Item;

/**
 * TODO
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/29 13:54
 * @since jdk11
 */
public class ItemTable extends JBTable {
    private static final Logger log = Logger.getInstance(ItemTable.class);

    private static final int NAME_COLUMN = 0;
    private static final int VALUE_COLUMN = 1;
    private final GitfoxServerTableModel foxServerTableModel = new GitfoxServerTableModel();

    protected static List<Item> items = new ArrayList<>();

    /**
     * instantiation AliasTable
     */
    public ItemTable() {
        setModel(foxServerTableModel);
        TableColumn column = getColumnModel().getColumn(NAME_COLUMN);
        TableColumn valueColumn = getColumnModel().getColumn(VALUE_COLUMN);
        column.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
                final Component component =
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                final String macroValue = getGitfoxServerValueAt(row);
                component.setForeground(macroValue.length() == 0 ? JBColor.RED
                    : isSelected ? table.getSelectionForeground() : table.getForeground());
                return component;
            }
        });
        setColumnSize(column, 150, 250, 150);
        setColumnSize(valueColumn, 550, 750, 550);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Set Something ColumnSize
     */
    public static void setColumnSize(TableColumn column, int preferedWidth, int maxWidth, int minWidth) {
        column.setPreferredWidth(preferedWidth);
        column.setMaxWidth(maxWidth);
        column.setMinWidth(minWidth);
    }

    public String getGitfoxServerValueAt(int row) {
        return (String)getValueAt(row, VALUE_COLUMN);
    }

    public void addGitfoxServer() {
        final ItemEditor macroEditor = new ItemEditor("添加Git提交规范", "", "");
        if (macroEditor.showAndGet()) {
            final String name = macroEditor.getKey();
            items.add(new Item(macroEditor.getKey(), macroEditor.getValue()));
            final int index = indexOfGitfoxServerWithName(name);
            log.assertTrue(index >= 0);
            foxServerTableModel.fireTableDataChanged();
            setRowSelectionInterval(index, index);
        }
    }

    private boolean isValidRow(int selectedRow) {
        return selectedRow >= 0 && selectedRow < items.size();
    }

    public void moveUp() {
        int selectedRow = getSelectedRow();
        int index1 = selectedRow - 1;
        if (selectedRow != -1) {
            Collections.swap(items, selectedRow, index1);
        }
        setRowSelectionInterval(index1, index1);
    }

    public void moveDown() {
        int selectedRow = getSelectedRow();
        int index1 = selectedRow + 1;
        if (selectedRow != -1) {
            Collections.swap(items, selectedRow, index1);
        }
        setRowSelectionInterval(index1, index1);
    }

    public void removeSelectedGitfoxServers() {
        final int[] selectedRows = getSelectedRows();
        if (selectedRows.length == 0) {
            return;
        }
        Arrays.sort(selectedRows);
        final int originalRow = selectedRows[0];
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            final int selectedRow = selectedRows[i];
            if (isValidRow(selectedRow)) {
                items.remove(selectedRow);
            }
        }
        foxServerTableModel.fireTableDataChanged();
        if (originalRow < getRowCount()) {
            setRowSelectionInterval(originalRow, originalRow);
        } else if (getRowCount() > 0) {
            final int index = getRowCount() - 1;
            setRowSelectionInterval(index, index);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public void reset(List<Item> data) {
        items.clear();
        items.addAll(data);
        foxServerTableModel.fireTableDataChanged();
    }

    public void resetDefaultGitfoxServers() {
        foxServerTableModel.fireTableDataChanged();
    }

    private int indexOfGitfoxServerWithName(String name) {
        for (int i = 0; i < items.size(); i++) {
            final Item server = items.get(i);
            if (name.equals(server.getKey())) {
                return i;
            }
        }
        return -1;
    }

    public boolean editGitfoxServer() {
        if (getSelectedRowCount() != 1) {
            return false;
        }
        final int selectedRow = getSelectedRow();
        final Item server = items.get(selectedRow);
        final ItemEditor editor = new ItemEditor("修改Git提交规范", server.getKey(), server.getValue());
        if (editor.showAndGet()) {
            server.setKey(editor.getKey());
            server.setValue(editor.getValue());
            foxServerTableModel.fireTableDataChanged();
        }
        return true;
    }

    // ==========================================================================//

    /**
     * EditValidator
     */
    private static class EditValidator implements ItemEditor.Validator {
        @Override
        public boolean isOk(String name, String value) {
            return !name.isEmpty() && !value.isEmpty();
        }
    }

    /**
     * GitfoxServerTableModel
     */
    private static class GitfoxServerTableModel extends AbstractTableModel {
        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            final Item pair = items.get(rowIndex);
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
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case NAME_COLUMN:
                    return "服务Code";
                case VALUE_COLUMN:
                    return "服务地址";
                default:
                    log.error("Wrong indices");
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

}
