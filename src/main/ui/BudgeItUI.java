//CREDIT: Based on AlarmController example from CPSC-210 at UBC. Provided in week C4 of class instruction:
//https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

package ui;

import model.Account;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

/**
 * Represents applications main UI window frame.
 */
public class BudgeItUI extends JFrame {
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 700;
    private static final int space = 5;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    private final String saveLocation = "./data/UserAccount.json";

    private Account userAccount;
    private JDesktopPane desktop;
    private JInternalFrame graph;
    private JInternalFrame sources;
    private JInternalFrame debts;
    private JInternalFrame account;
    private JInternalFrame savings;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu quickEditMenu;
    private JMenu sourceEdit;
    private JMenu debtEdit;

    private BorderLayout desktopLayout;
    private ByteArrayOutputStream baos;
    private PrintStream log;
    private PrintStream oldDefault;

    //EFFECTS: Sets up the main window with visual elements and interaction.
    public BudgeItUI() {
        userAccount = new Account();

        desktopLayout = new BorderLayout();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());;
        createInternalFrames();

        setContentPane(desktop);
        setTitle("Don't Budge-It!");
        setSize(WIDTH, HEIGHT);

        setSoutRedirect();
        addMenu();

        graph.pack();
        graph.setVisible(true);
        desktop.add(graph);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    private void setSoutRedirect() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        log = new PrintStream(baos);
        oldDefault = System.out;
        System.setOut(log);
    }

    private void addMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        quickEditMenu = new JMenu("QuickEdit");
        quickEditMenu.setMnemonic('Q');
        sourceEdit = new JMenu("Source");
        debtEdit = new JMenu("Debt");

        addInternalMenu();
        addMenuFunctions();

        setJMenuBar(menuBar);
    }

    private void addMenuFunctions() {
        addMenuItem(fileMenu, new LoadAction(),
                KeyStroke.getKeyStroke("control F"));
        addMenuItem(fileMenu, new SaveAction(),
                KeyStroke.getKeyStroke("control S"));

        addMenuItem(quickEditMenu, new SetSavingsPercentGoalAction(),
                KeyStroke.getKeyStroke("control G"), "P");

        addMenuItem(sourceEdit, new SourceAddAction());
        addMenuItem(sourceEdit, new SourceRemoveAction());
        addMenuItem(debtEdit, new DebtAddAction());
        addMenuItem(debtEdit, new DebtRemoveAction());
    }

    private void addInternalMenu() {
        quickEditMenu.add(sourceEdit);
        quickEditMenu.add(debtEdit);
        sourceEdit.setMnemonic('S');
        debtEdit.setMnemonic('D');

        menuBar.add(fileMenu);
        menuBar.add(quickEditMenu);
    }

    private void addMenuItem(JMenu theMenu, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        theMenu.add(menuItem);
    }

    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        if (accelerator != null) {
            menuItem.setAccelerator(accelerator);
        }

        theMenu.add(menuItem);
    }

    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator, String mnemonic) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        if (accelerator != null) {
            menuItem.setAccelerator(accelerator);
        }

        menuItem.setMnemonic(mnemonic.charAt(0));

        theMenu.add(menuItem);
    }

    private void createInternalFrames() {
        graph = new JInternalFrame();
        graph.setLayout(desktopLayout);
        sources = new JInternalFrame();
        sources.setLayout(desktopLayout);
        debts = new JInternalFrame();
        debts.setLayout(desktopLayout);
        account = new JInternalFrame();
        account.setLayout(desktopLayout);
        savings = new JInternalFrame();
        savings.setLayout(desktopLayout);
    }

    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }

        //EFFECTS: Saves a JSON object with the details of the current account object
        private void save() {
            try {
                System.out.println("File saved to " + saveLocation);
                JsonWriter writer = new JsonWriter(saveLocation);
                writer.open();
                writer.write(userAccount);
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file " + saveLocation);
            }
        }
    }

    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            load();
        }

        //MODIFIES: this
        //EFFECTS: Loads the saved JSONObject and updates account to what was saved
        private void load() {
            try {
                System.out.println("File loading from " + saveLocation);
                JsonReader reader = new JsonReader(saveLocation);
                userAccount = reader.read();
            } catch (IOException e) {
                System.out.println("Unable to read file " + saveLocation);
            }
        }
    }

    //EFFECTS: Brings the application into focus when user clicks window
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            BudgeItUI.this.requestFocusInWindow();
        }
    }

    private class SetSavingsPercentGoalAction extends AbstractAction {

        SetSavingsPercentGoalAction() {
            super("Savings Percent Goal");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            BigDecimal spg = null;

            String spgString = JOptionPane.showInputDialog(null,
                    "What would you like to set your savings goal to? (0%-100%)",
                    "Enter goal: ",
                    JOptionPane.QUESTION_MESSAGE);

            try {
                spg = BigDecimal.valueOf(Integer.parseInt(spgString));
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(desktop, "Please enter a value between 0 and 100!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }

            if (spg.compareTo(ONE_HUNDRED) == 1) {
                JOptionPane.showMessageDialog(desktop, "Please enter a value between 0 and 100!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else if (spg.compareTo(BigDecimal.ZERO) == -1) {
                JOptionPane.showMessageDialog(desktop, "Please enter a value between 0 and 100!",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                userAccount.setSavingsPercentGoal(spg);
            }
        }
    }

    private abstract class AddAction extends AbstractAction {
        AddAction() {
            super("Add");
        }
    }

    private abstract class RemoveAction extends AbstractAction {
        RemoveAction() {
            super("Remove");
        }
    }

    private class SourceAddAction extends AddAction {

        SourceAddAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    private class DebtAddAction extends AddAction {
        DebtAddAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    private class SourceRemoveAction extends RemoveAction {

        SourceRemoveAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    private class DebtRemoveAction extends RemoveAction {

        DebtRemoveAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO
        }
    }

    public static void main(String[] args) {
        new BudgeItUI();
    }
}
