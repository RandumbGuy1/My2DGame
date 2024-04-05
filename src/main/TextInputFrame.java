package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class TextInputFrame extends JFrame {
    public JTextField TextField;

    public TextInputFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());

        TextField = new JTextField();
        TextField.setPreferredSize(new Dimension(250, 40));
        this.add(TextField);

        TextField.addActionListener(e -> closeWindow());

        this.pack();
        this.setVisible(true);
    }

    void closeWindow() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
