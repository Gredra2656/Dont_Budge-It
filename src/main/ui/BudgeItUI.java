//CREDIT: Based on AlarmController example from CPSC-210 at UBC. Provided in week C4 of class instruction:
//https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

package ui;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Represents applications main UI window frame.
 */
public class BudgeItUI extends JFrame {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static final int space = 5;

    private Account ac;
    private JDesktopPane initWindow;
    private JInternalFrame optionsMenu;
    private JPanel frogPanel;
    private JLabel title;
    private BufferedImage frogImg;

    //EFFECTS: Sets up the main window with visual elements and interaction.
    public BudgeItUI() {
        ac = new Account();

        initWindow = new JDesktopPane();
        initWindow.addMouseListener(new DesktopFocusAction());
        optionsMenu = new JInternalFrame("Options",
                false, false, false, false);
        optionsMenu.setLayout(new BorderLayout());

        setContentPane(initWindow);
        setTitle("Don't Budge-It!");
        setSize(WIDTH, HEIGHT);

        addOptionsButtons();

        optionsMenu.pack();
        optionsMenu.setVisible(true);
        initWindow.add(optionsMenu);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    private void addOptionsButtons() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(5, 1));
        buttons.add(new JButton(new DisplayAccountMenu()));
        buttons.add(new JButton(new DisplaySavingsMenu()));
        buttons.add(new JButton(new DisplayDebtMenu()));
        buttons.add(new JButton(new DisplayEndPeriodMenu()));
        buttons.add(new JButton(new DisplayFileMenu()));

        optionsMenu.add(buttons, BorderLayout.WEST);
    }

    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    //EFFECTS: Brings the application into focus when user clicks window
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            BudgeItUI.this.requestFocusInWindow();
        }
    }

    private class DisplayAccountMenu extends AbstractAction {
        public DisplayAccountMenu() {
            super("Account Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DisplaySavingsMenu extends AbstractAction {
        public DisplaySavingsMenu() {
            super("Savings Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DisplayDebtMenu extends AbstractAction {
        public DisplayDebtMenu() {
            super("Debt Menu");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DisplayEndPeriodMenu extends AbstractAction {
        public DisplayEndPeriodMenu() {
            super("End Period Options");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DisplayFileMenu extends AbstractAction {
        public DisplayFileMenu() {
            super("File Options");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    //MODIFIES: this
    //EFFECTS: Starts the application
    public static void main(String[] args) {
        new BudgeItUI();
    }
}
