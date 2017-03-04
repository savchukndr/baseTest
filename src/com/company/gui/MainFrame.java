package com.company.gui;

import javax.swing.*;

/**
 * Created by savch on 04.03.2017.
 */
public class MainFrame {
    private JFrame f;
    private JTextArea textArea;

    public MainFrame(){
        initialize();
    }

    private void initialize(){


        /*textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        frame.getContentPane().add(textArea);*/

        f = new JFrame("Base Test");
        f.setBounds(100, 100, 800, 600);
        JPanel lowerPanel = new JPanel();
        lowerPanel.setBounds(30, 72, 784, 489);
        f.getContentPane().add(lowerPanel, "South");

        lowerPanel.add(new JScrollPane(new JTextArea(content, 10, 20)));
        JTextArea ta = new JTextArea(content, 6, 8);
        ta.setLineWrap(true);
        lowerPanel.add(new JScrollPane(ta));

        f.pack();
        f.setVisible(true);

    }

    static String content = "Here men from the planet Earth\n"
            + "first set foot upon the Moon,\n" + "July 1969, AD.\n"
            + "We came in peace for all mankind.";

    public void show(){
        f.setVisible(true);
    }
}
