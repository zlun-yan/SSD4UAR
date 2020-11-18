package org.csu.uar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.csu.uar.UARStatic.*;

public class MainFrame extends JFrame {
    private static JLabel UARTitleLabel;
    private static JLabel text1Label;
    private static JLabel text2Label;
    private static JLabel text3Label;
    private static JLabel text4Label;
    private static JLabel text5Label;
    private static JLabel text6Label;
    private static JLabel text7Label;

    private static JLabel descTitleLabel;
    private static JTextArea descTextArea;
    private static JScrollPane descScrollPane;

    private static JLabel displayLabel;
    private static JTextField displayField;
    private static JButton displayButton;
    private static JLabel searchLabel;
    private static JTextField searchField;
    private static JButton searchButton;

    private static JLabel foundAtLabel;
    private static JLabel firstOccurredLabel;
    private static JLabel lastOccurredLabel;
    private static JButton exitButton;


    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                displayField.requestFocus();
            }
        });
        mainFrame.setVisible(true);
    }

    public MainFrame() {
        init();

        setSize(880, 510);
        setTitle("UAR Components");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void init() {
        JPanel panel = new JPanel();

        {
            // Part I
            UARTitleLabel = new JLabel();
            UARTitleLabel.setText("UAR components names:");
            UARTitleLabel.setFont(BoldFont);

            text1Label = new JLabel();
            text2Label = new JLabel();
            text3Label = new JLabel();
            text4Label = new JLabel();
            text5Label = new JLabel();
            text6Label = new JLabel();
            text7Label = new JLabel();

            text1Label.setText(text_1);
            text2Label.setText(text_2);
            text3Label.setText(text_3);
            text4Label.setText(text_4);
            text5Label.setText(text_5);
            text6Label.setText(text_6);
            text7Label.setText(text_7);

            text1Label.setFont(NormalFont);
            text2Label.setFont(NormalFont);
            text3Label.setFont(NormalFont);
            text4Label.setFont(NormalFont);
            text5Label.setFont(NormalFont);
            text6Label.setFont(NormalFont);
            text7Label.setFont(NormalFont);

            // Part II
            descTitleLabel = new JLabel();
            descTitleLabel.setText("UAR component description:");
            descTitleLabel.setFont(BoldFont);

            descTextArea = new JTextArea();
            descScrollPane = new JScrollPane(descTextArea);

            descTextArea.setFont(NormalFont);
            descTextArea.setLineWrap(true);
            descTextArea.setWrapStyleWord(true);
            descScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // Part III
            displayLabel = new JLabel();
            displayLabel.setText("Enter a number");
            displayLabel.setFont(NormalFont);

            displayField = new JTextField();
            displayButton = new JButton("Display");

            displayField.setFont(NormalFont);
            displayButton.setFont(NormalFont);

            searchLabel = new JLabel();
            searchLabel.setText("Enter a search string:");
            searchLabel.setFont(NormalFont);

            searchField = new JTextField();
            searchButton = new JButton("Search");

            searchField.setFont(NormalFont);
            searchButton.setFont(NormalFont);

            displayButton.setMnemonic(KeyEvent.VK_D);
            searchButton.setMnemonic(KeyEvent.VK_S);

            // Part IV
            foundAtLabel = new JLabel("Found At:");
            firstOccurredLabel = new JLabel();
            lastOccurredLabel = new JLabel();

            foundAtLabel.setFont(NormalFont);
            firstOccurredLabel.setFont(NormalFont);
            lastOccurredLabel.setFont(NormalFont);

            firstOccurredLabel.setVisible(false);
            lastOccurredLabel.setVisible(false);

            exitButton = new JButton("Exit");
            exitButton.setMnemonic(KeyEvent.VK_X);
            exitButton.setFont(NormalFont);
        }  // init

        displayField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                descTextArea.setText("");
                searchField.setText("");
                firstOccurredLabel.setVisible(false);
                lastOccurredLabel.setVisible(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                descTextArea.setText("");
                searchField.setText("");
                firstOccurredLabel.setVisible(false);
                lastOccurredLabel.setVisible(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                descTextArea.setText("");
                searchField.setText("");
                firstOccurredLabel.setVisible(false);
                lastOccurredLabel.setVisible(false);
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                firstOccurredLabel.setVisible(false);
                lastOccurredLabel.setVisible(false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                firstOccurredLabel.setVisible(false);
                lastOccurredLabel.setVisible(false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                firstOccurredLabel.setVisible(false);
                lastOccurredLabel.setVisible(false);
            }
        });

        displayButton.addActionListener(event -> {
            try {
                descTextArea.setText(UARStatic.getTextById(Integer.parseInt(displayField.getText())));

                searchField.requestFocus();
            } catch (IndexOutOfBoundsException | NumberFormatException exception) {
                JOptionPane.showMessageDialog(this,
                        "Please enter values between 1 and 7", "Search String", JOptionPane.WARNING_MESSAGE);
                displayField.requestFocus();
                displayField.selectAll();
            }
        });
        searchButton.addActionListener(event -> {
            String text = descTextArea.getText();
            String searchText = searchField.getText();

            String parent = text.toLowerCase();
            String child = searchText.toLowerCase();

            if (parent == null || parent.equals("")) {  // 这个时候是 descArea中无内容
                JOptionPane.showMessageDialog(this,
                        "Please select text", "Search String", JOptionPane.WARNING_MESSAGE);
                String displayContent = displayField.getText();
                if (displayContent == null || displayContent.equals("")) {  // 如果display中无内容 那么就把焦点给 field
                    displayField.requestFocus();
                }
                else displayButton.requestFocus();  // 如果display中有内容  那么就把焦点给按钮
                return;
            }
            if (child == null || child.equals("")) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a search string", "Search String", JOptionPane.WARNING_MESSAGE);
                searchField.requestFocus();
                return;
            }

            int count = matchStringByRegularExpressionCount(parent, child);
            String msg;
            if (count == 0) {  // 没有搜索到内容
                msg = "String '" + searchText + "' not found\nSearch same text again?";
                int result = JOptionPane.showConfirmDialog(this,
                        msg, "Search String", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == 0) {  // 若选择是 则返回 0
                    searchField.requestFocus();
                    searchField.selectAll();
                }
                else {  // 若选择否 则返回 1
                    descTextArea.setText("");
                    displayField.setText("");
                    searchField.setText("");
                    firstOccurredLabel.setVisible(false);
                    lastOccurredLabel.setVisible(false);
                    displayField.requestFocus();
                }
            }
            else {  // 搜索到了内容
                if (count == 1) {  //搜索到了一条内容
                    int firstPos = parent.indexOf(child) + 1;
                    firstOccurredLabel.setText("Occurred 1: " + "Position: " + firstPos);
                    firstOccurredLabel.setVisible(true);
                }
                else {  // 搜索到了多条内容
                    int firstPos = parent.indexOf(child) + 1;
                    int lastPos = parent.lastIndexOf(child) + 1;

                    firstOccurredLabel.setText("Occurred 1: " + "Position: " + firstPos);
                    lastOccurredLabel.setText("Occurred " + count + ": Position: " + lastPos);
                    firstOccurredLabel.setVisible(true);
                    lastOccurredLabel.setVisible(true);
                }

                msg = "The number of occurences of '" + searchText + "' is: " + count + "\nSearch same text?";
                int result = JOptionPane.showConfirmDialog(this,
                        msg, "Search String", JOptionPane.YES_NO_OPTION);
                if (result == 0) {  // 若选择是 则返回 0
                    searchField.requestFocus();
                    searchField.selectAll();
                }
                else {  // 若选择否 则返回 1
                    descTextArea.setText("");
                    displayField.setText("");
                    searchField.setText("");
                    firstOccurredLabel.setVisible(false);
                    lastOccurredLabel.setVisible(false);
                    displayField.requestFocus();
                }
            }
        });
        exitButton.addActionListener(event -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        {
            UARTitleLabel.setBounds(20,10,400,20);
            text1Label.setBounds(20,40,400,20);
            text2Label.setBounds(20,70,400,20);
            text3Label.setBounds(20,100,400,20);
            text4Label.setBounds(20,130,400,20);
            text5Label.setBounds(20,160,400,20);
            text6Label.setBounds(20,190,400,20);
            text7Label.setBounds(20,220,400,20);

            descTitleLabel.setBounds(450,10,380,20);
            descScrollPane.setBounds(450,40,380,220);

            displayLabel.setBounds(20,320,120,20);
            displayField.setBounds(170,320,150,23);
            displayButton.setBounds(330, 320, 90, 20);
            searchLabel.setBounds(20,360,150,20);
            searchField.setBounds(170,360,150,23);
            searchButton.setBounds(330,360,90,20);

            foundAtLabel.setBounds(450,270,200,20);
            firstOccurredLabel.setBounds(450, 310, 300, 20);
            lastOccurredLabel.setBounds(450, 350, 300, 20);
            exitButton.setBounds(760, 430, 90, 24);


            panel.add(UARTitleLabel);
            panel.add(text1Label);
            panel.add(text2Label);
            panel.add(text3Label);
            panel.add(text4Label);
            panel.add(text5Label);
            panel.add(text6Label);
            panel.add(text7Label);

            panel.add(descTitleLabel);
            panel.add(descScrollPane);

            panel.add(displayLabel);
            panel.add(displayField);
            panel.add(displayButton);
            panel.add(searchLabel);
            panel.add(searchField);
            panel.add(searchButton);

            panel.add(foundAtLabel);
            panel.add(firstOccurredLabel);
            panel.add(lastOccurredLabel);
            panel.add(exitButton);

            panel.setLayout(null);
            add(panel);
        }  // setBounds  panel.add()
    }

    private int matchStringByRegularExpressionCount(String parent, String child) {
        int count = 0;
        Pattern pattern = Pattern.compile(child);
        Matcher matcher = pattern.matcher(parent);
        while(matcher.find()) count++;

        return count;
    }
}
