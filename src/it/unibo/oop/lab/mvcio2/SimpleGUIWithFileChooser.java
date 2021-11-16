package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {
    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */

    private final JFrame frame = new JFrame();
    private final Controller ctrl = new Controller();

    private SimpleGUIWithFileChooser() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 3, sh / 3);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // upper panel
        final JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());

        final JTextField filepath = new JTextField(ctrl.getCurrentFilePath());
        filepath.setEditable(false);
        upperPanel.add(filepath, BorderLayout.CENTER);

        final JButton chooseFile = new JButton("Browse...");
        upperPanel.add(chooseFile, BorderLayout.LINE_END);

        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fc = new JFileChooser("Choose where to save");
                fc.setSelectedFile(ctrl.getCurrentFile());
                final int result = fc.showSaveDialog(frame);
                switch (result) {
                case JFileChooser.APPROVE_OPTION:
                    final File newDest = fc.getSelectedFile();
                    ctrl.setDestination(newDest);
                    filepath.setText(newDest.getPath());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, result, "Meh!", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        });

        // lower panel
        final JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());

        final JTextArea text = new JTextArea();
        lowerPanel.add(text, BorderLayout.CENTER);

        final JButton save = new JButton("Save");
        lowerPanel.add(save, BorderLayout.SOUTH);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    ctrl.save(text.getText());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "An error occurred", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        lowerPanel.add(upperPanel, BorderLayout.NORTH);
        frame.setContentPane(lowerPanel);
    }

    private void display() {
        frame.setVisible(true);
    }

    /**
     *  @param args
     *          ignored
     */
    public static void main(final String[] args) {
        final SimpleGUIWithFileChooser gui = new SimpleGUIWithFileChooser();
        gui.display();
    }
}
