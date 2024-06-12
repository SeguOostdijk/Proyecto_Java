package reservas.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private boolean placeholderVisible;

    public PlaceholderTextField(String text) {
        this.placeholder = text;
        this.placeholderVisible = true;
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (placeholderVisible) {
                    setText("");
                    setForeground(Color.BLACK);
                    placeholderVisible = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                    placeholderVisible = true;
                }
            }
        });
        setText(placeholder);
        setForeground(Color.GRAY);
    }

    @Override
    public String getText() {
        if (placeholderVisible) {
            return "";
        }
        return super.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && placeholderVisible) {
            Font font = getFont().deriveFont(Font.ITALIC);
            FontMetrics fm = g.getFontMetrics(font);
            g.setColor(Color.GRAY);
            g.setFont(font);
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g.drawString(placeholder, 2, y);
        }
    }
}
