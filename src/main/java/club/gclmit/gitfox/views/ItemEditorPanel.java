package club.gclmit.gitfox.views;

import javax.swing.*;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.ui.DialogWrapper;

/**
 * @author gclm
 */
public class ItemEditorPanel extends DialogWrapper {
    private JPanel myPanel;
    private JTextField valueField;
    private JTextField keyField;

    public ItemEditorPanel(String title, String key, String value) {
        super(true);
        setTitle(title);
        keyField.setText(key);
        valueField.setText(value);
        init();
    }

    public String getKey() {
        return keyField.getText().trim();
    }

    public String getValue() {
        return valueField.getText().trim();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myPanel;
    }

    public interface Validator {
        /**
         * 效验输入格式
         * 
         * @param name key
         * @param value value
         * @return {@link boolean}
         */
        boolean isOk(String name, String value);
    }
}
