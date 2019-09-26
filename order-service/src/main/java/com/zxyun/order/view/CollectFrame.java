package com.zxyun.order.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @des:
 * @Author: given
 * @Date 2019/8/14 18:47
 */
public class CollectFrame extends JFrame implements ActionListener {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The "OK" button for loading the file.
     */
    private JButton ok;

    /**
     * The cancel button.
     */
    private JButton cancel;

    /**
     * The information label.
     */
    private JLabel info;

    /**
     * The contents label.
     */
    private JLabel contents;

    /**
     * The text field for giving the name of the file that we want to open.
     */
    private JTextField input;

    /**
     * A text area that will keep the contents of the file opened.
     */
    private JTextArea area;

    /**
     * The panel that will hold our widgets.
     */
    private JPanel panel;

//    /**
//     * The Presenter component that the frame will interact with
//     */
//    private FileSelectorPresenter presenter;

    /**
     * The name of the file that we want to read it's contents.
     */
    private String fileName;

    public CollectFrame() {
        super("File Loader");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBounds(100, 100, 1000, 1000);

        /*
         * Add the panel.
         */
        this.panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
        panel.setBounds(0, 0, 1000, 1000);
        panel.setBackground(Color.LIGHT_GRAY);

        /*
         * Add the info label.
         */
        this.info = new JLabel("File Name :");
        this.panel.add(info);
        info.setBounds(30, 10, 100, 30);

        /*
         * Add the contents label.
         */
        this.contents = new JLabel("File contents :");
        this.panel.add(contents);
        this.contents.setBounds(30, 100, 120, 30);

        /*
         * Add the text field.
         */
        this.input = new JTextField(100);
        this.panel.add(input);
        this.input.setBounds(150, 15, 200, 20);

        /*
         * Add the text area.
         */
        this.area = new JTextArea(1000, 1000);
//    this.area.setBounds(150, 15, 200, 200);
        JScrollPane pane = new JScrollPane(area);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.panel.add(pane);
        this.area.setEditable(true);
        pane.setBounds(150, 100, 800, 800);

        /*
         * Add the OK button.
         */
        this.ok = new JButton("OK");
        this.panel.add(ok);
        this.ok.setBounds(250, 50, 100, 25);
        this.ok.addActionListener(this);

        /*
         * Add the cancel button.
         */
        this.cancel = new JButton("Cancel");
        this.panel.add(this.cancel);
        this.cancel.setBounds(380, 50, 100, 25);
        this.cancel.addActionListener(this);

//        this.presenter = null;
        this.fileName = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void open() {
        this.setVisible(true);
    }

    public void close() {
        this.dispose();
    }

    public boolean isOpened() {
        return this.isVisible();
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void displayData(String data) {
        this.area.setText(data);
    }
}
