//CREDIT: Based on AlarmController example from CPSC-210 at UBC. Provided in week C4 of class instruction:
//https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

package ui;

import model.Account;
import model.DebtAcc;
import model.Source;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.jfree.data.general.DatasetUtils.findMaximumStackedRangeValue;
import static org.jfree.data.general.DatasetUtils.findMinimumStackedRangeValue;

/**
 * Represents applications main UI window frame.
 */
public class BudgeItUI extends JFrame {
    private static final int WIDTH = 1500;
    private static final int HEIGHT = 700;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final String CURRENT = "Current";
    private static final String FUTURE = "Next month";
    private static final Dimension PREFERRED_SIZE = new Dimension(WIDTH / 4, HEIGHT - 300);

    private final String saveLocation = "./data/UserAccount.json";

    private Account userAccount;
    private final JFrame desktop;
    private final JPanel desktopMain;
    private JPanel balancePanel;
    private JLabel balanceLabel;
    private ChartPanel graph;
    private ChartPanel debts;
    private ChartPanel savings;


    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu chartMenu;
    private JMenu quickEditMenu;
    private JMenu sourceEdit;
    private JMenu debtEdit;

    private final GridLayout desktopLayout;
    private ByteArrayOutputStream baos;
    private PrintStream log;
    private PrintStream oldDefault;
    private CategoryDataset sourceDataset;
    private CategoryDataset debtDataset;
    private CategoryDataset savingsDataset;
    private Dimension preferredSize;

    //EFFECTS: Sets up the main window with visual elements and interaction.
    public BudgeItUI() {
        userAccount = new Account();

        desktopLayout = new GridLayout(2, 4);

        desktop = new JFrame();
        desktop.addMouseListener(new DesktopFocusAction());

        desktopMain = new JPanel();
        desktop.add(desktopMain);
        desktopMain.setLayout(desktopLayout);

        setContentPane(desktopMain);
        setTitle("Don't Budge-It!");
        setSize(WIDTH, HEIGHT);

        setSoutRedirect();
        addMenu();
        addGraph();
        addDebtGraph();
        addSavingsGraph();
        //addLog();
        //addSourceButtons();
        //addDebtButtons();
        addSavingsButtons();
        addBalance();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
        pack();
    }

    private void addBalance() {
        BalanceButtons balanceButtonsGrid = new BalanceButtons();
        balancePanel = new JPanel();
        balanceLabel = new JLabel("Balance: " + userAccount.getBalance().toString());

        balancePanel.add(balanceLabel);
        balancePanel.add(balanceButtonsGrid);

        desktopMain.add(balancePanel);
    }

    private void addSavingsButtons() {
        SavingsButtons savingsButtonGrid = new SavingsButtons();

        desktopMain.add(savingsButtonGrid);
    }

    private void setSoutRedirect() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        log = new PrintStream(baos);
        oldDefault = System.out;
        System.setOut(log);
    }

    private void addGraph() {
        sourceDataset = createSurplusDataSet();

        JFreeChart inOutGraph = ChartFactory.createStackedBarChart("Income/Expense Graph",
                "Sources",
                "Value ($)", sourceDataset);

        LegendTitle legend = inOutGraph.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);

        graph = new ChartPanel(inOutGraph);
        graph.setPreferredSize(PREFERRED_SIZE);
        graph.setDomainZoomable(false);
        graph.setRangeZoomable(false);
        graph.setVisible(true);

        desktopMain.add(graph, BorderLayout.PAGE_START);
        pack();
    }

    private void addDebtGraph() {
        debtDataset = createDebtFutureDataSet();

        JFreeChart debtFutureGraph = ChartFactory.createBarChart("Debt Interest Accumulation",
                "Debts",
                "Value ($)",
                debtDataset);

        LegendTitle legend = debtFutureGraph.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);

        debts = new ChartPanel(debtFutureGraph);
        debts.setPreferredSize(PREFERRED_SIZE);
        debts.setDomainZoomable(false);
        debts.setRangeZoomable(false);
        debts.setVisible(true);

        desktopMain.add(debts, BorderLayout.CENTER);
        pack();
    }

    private void addSavingsGraph() {
        savingsDataset = createSavingsFutureDataset();

        JFreeChart savingsFutureGraph = ChartFactory.createBarChart("Savings Interest Accumulation",
                "Savings",
                "Value ($)",
                savingsDataset);

        savingsFutureGraph.removeLegend();

        savings = new ChartPanel(savingsFutureGraph);
        savings.setPreferredSize(PREFERRED_SIZE);
        savings.setDomainZoomable(false);
        savings.setRangeZoomable(false);
        savings.setVisible(true);

        desktopMain.add(savings, BorderLayout.PAGE_END);
        pack();
    }

    private CategoryDataset createSavingsFutureDataset() {
        BigDecimal savingsVal = userAccount.getSavingsBal();
        BigDecimal savingsInt = userAccount.getSavings().getInterest();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(savingsVal, "Savings", CURRENT);
        dataset.addValue(savingsVal.multiply(ONE.add(savingsInt)), "Savings", FUTURE);

        return dataset;
    }

    private CategoryDataset createDebtFutureDataSet() {
        List<DebtAcc> debts = userAccount.getDebts();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (DebtAcc d : debts) {
            BigDecimal val = d.getValue();
            String nameKey = d.getName();
            dataset.addValue(val, nameKey, CURRENT);

            d.calculateInterest();
            BigDecimal futureVal = d.getValue();

            dataset.addValue(futureVal, nameKey, FUTURE);
        }
        return dataset;
    }

    //EFFECTS: Creates a CategoryDataset of the userAccount's income and expense sources.
    private CategoryDataset createSurplusDataSet() {
        final String incomeColumn = "Income";
        final String expenseColumn = "Expense";

        List<Source> sources = userAccount.getSources();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Source s : sources) {
            BigDecimal val = s.getValue();
            String nameKey = s.getName();
            if (val.compareTo(BigDecimal.ZERO) > -1) {
                dataset.addValue(val, nameKey, incomeColumn);
            } else {
                dataset.addValue(val, nameKey, expenseColumn);
            }
        }

        if (dataset.getColumnCount() != 0) {
            createSurplusCategory(dataset);
        }

        return dataset;
    }

    private void createSurplusCategory(DefaultCategoryDataset dataset) {
        Number totalIncomeNum = findMaximumStackedRangeValue(dataset);
        Number totalExpenseNum = findMinimumStackedRangeValue(dataset);

        BigDecimal totalIncome = new BigDecimal(totalIncomeNum.toString());
        BigDecimal totalExpense = new BigDecimal(totalExpenseNum.toString());
        BigDecimal totalSurplus = totalIncome.add(totalExpense);
        dataset.addValue(totalSurplus, "Surplus", "Surplus");
    }

    private void addMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');

        quickEditMenu = new JMenu("QuickEdit");
        quickEditMenu.setMnemonic('Q');

        sourceEdit = new JMenu("Source");
        debtEdit = new JMenu("Debt");

        chartMenu = new JMenu("Chart");

        addInternalMenu();
        addMenuFunctions();

        menuBar.add(fileMenu);
        menuBar.add(quickEditMenu);
        menuBar.add(chartMenu);

        setJMenuBar(menuBar);
    }

    private void addMenuFunctions() {
        addMenuItem(fileMenu, new LoadAction(),
                KeyStroke.getKeyStroke("control F"));
        addMenuItem(fileMenu, new SaveAction(),
                KeyStroke.getKeyStroke("control S"));

        addMenuItem(quickEditMenu, new SetSavingsPercentGoalAction(),
                KeyStroke.getKeyStroke("control G"), "P");

        addMenuItem(chartMenu, new RefreshGraphAction(),
                KeyStroke.getKeyStroke("control R"), "R");

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
            refresh();
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

    private void withdrawSavings() {
        modifySavings("Please enter the amount you'd like to withdraw.", "Enter withdrawal: ", 1);
    }

    private void depositSavings() {
        modifySavings("Please enter the amount you'd like to deposit.", "Enter deposit: ", 0);
    }

    private void modifySavings(String msg, String titleMsg, int command) {
        BigDecimal val = null;

        String valString = parseVal(msg,
                titleMsg);
        try {
            val = new BigDecimal(valString);
            if (val != null) {
                if (command == 0) {
                    userAccount.depositSavings(val);
                } else if (command == 1) {
                    userAccount.depositSavings(val);
                }
            }
        } catch (NumberFormatException exception) {
            showInvalidInputError("Please only enter numbers for this value!");
        }
    }

    private void withdrawBalance() {
        modifyBalance("Please enter the amount you'd like to withdraw.", "Enter withdrawal: ", 1);
    }

    private void depositBalance() {
        modifyBalance("Please enter the amount you'd like to deposit", "Enter deposit: ", 0);
    }

    private void modifyBalance(String msg, String titleMsg, int command) {
        BigDecimal val = null;

        String valString = parseVal(msg, titleMsg);

        try {
            val = new BigDecimal(valString);
            if (val != null) {
                if (command == 0) {
                    userAccount.depositBalance(val);
                } else if (command == 1) {
                    userAccount.withdrawBalance(val);
                }
            }
        } catch (NumberFormatException exception) {
            showInvalidInputError("Please only enter numbers for this value!");
        }
    }

    private void setInterest() {
        BigDecimal intVal;
        String intString = parseVal("What is the monthly interest rate of this debt? (0%-100%)",
                "Enter interest: ");
        if (intString == null) {
            return;
        }
        try {
            intVal = new BigDecimal(intString);
            if (intVal.compareTo(ONE_HUNDRED) > 0) {
                showInvalidInputError("Please enter a value between 0 and 100!");
            } else if (intVal.compareTo(ZERO) < 0) {
                showInvalidInputError("Please enter a value between 0 and 100!");
            } else {
                userAccount.getSavings().setInterest(intVal.movePointLeft(2));
            }
        } catch (NumberFormatException exception) {
            showInvalidInputError("Please only enter numbers for the value!");
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
            setSavingsPercentGoal();
        }
    }

    private abstract static class AddAction extends AbstractAction {
        AddAction() {
            super("Add");
        }
    }

    private abstract static class RemoveAction extends AbstractAction {
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
            String nameString = parseName("What would you like to set as the name of this source?",
                    "Enter name: ");
            if (nameString == null) {
                return;
            }
            String valString = parseVal("What is the monthly balance this source modifies in your account?",
                    "Enter value: ");
            if (valString == null) {
                return;
            }

            try {
                userAccount.addSource(nameString, new BigDecimal(valString));
            } catch (NumberFormatException exception) {
                showInvalidInputError("Please only enter numbers for this value.");
            }
            refresh();
        }
    }

    private String parseVal(String message, String initialSelectionValue) {
        return JOptionPane.showInputDialog(desktop,
                message,
                initialSelectionValue);
    }

    private void setSavingsPercentGoal() {
        BigDecimal spg = null;

        String spgString = parseName("What would you like to set your savings goal to? (0%-100%)",
                "Enter goal: ");
        if (spgString == null) {
            return;
        }

        try {
            spg = BigDecimal.valueOf(Integer.parseInt(spgString));
            if (spg.compareTo(ONE_HUNDRED) == 1) {
                showInvalidInputError("Please enter a value between 0 and 100!");
            } else if (spg.compareTo(BigDecimal.ZERO) == -1) {
                showInvalidInputError("Please enter a value between 0 and 100!");
            } else {
                userAccount.setSavingsPercentGoal(spg);
            }
        } catch (Exception exception) {
            showInvalidInputError("Please enter a value between 0 and 100!");
        }
    }

    private class DebtAddAction extends AddAction {
        DebtAddAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            BigDecimal intVal = null;

            String nameString = parseName("What would you like the name of this debt to be?",
                    "Enter name: ");
            if (nameString == null) {
                return;
            }
            String valString = parseVal("What is the current balance of this debt?",
                    "Enter balance: ");
            checkForAddDebtErrors(intVal, nameString, valString);
            refresh();
        }
    }

    private void checkForAddDebtErrors(BigDecimal intVal, String nameString, String valString) {
        BigDecimal valVal;
        try {
            valVal = new BigDecimal(valString);
            String intString = parseVal("What is the monthly interest rate of this debt? (0%-100%)",
                    "Enter interest: ");
            if (intString == null) {
                return;
            }

            checkForDebtInterestErrors(intVal, nameString, valVal, intString);
        } catch (NumberFormatException exception) {
            showInvalidInputError("Please only enter numbers for the value!");
        }
    }

    private void checkForDebtInterestErrors(BigDecimal intVal, String nameString, BigDecimal valVal, String intString) {
        try {
            intVal = BigDecimal.valueOf(Integer.parseInt(intString));
        } catch (Exception exception) {
            showInvalidInputError("Please enter a value between 0 and 100!");
        }

        if (intVal.compareTo(ONE_HUNDRED) == 1) {
            showInvalidInputError("Please enter a value between 0 and 100!");
        } else if (intVal.compareTo(BigDecimal.ZERO) == -1) {
            showInvalidInputError("Please enter a value between 0 and 100!");
        } else {
            userAccount.addDebt(nameString, valVal,
                    intVal.movePointLeft(2));
        }
    }

    private String parseName(String message, String title) {
        return JOptionPane.showInputDialog(desktop,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE);
    }

    private void showInvalidInputError(String message) {
        JOptionPane.showMessageDialog(desktop, message,
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    private void refresh() {

        setDataset(graph, createSurplusDataSet());
        setDataset(debts, createDebtFutureDataSet());
        setDataset(savings, createSavingsFutureDataset());
        pack();
    }

    private void setDataset(ChartPanel savings, CategoryDataset dataset) {
        savings.getChart().getCategoryPlot().setDataset(dataset);
    }

    private class SourceRemoveAction extends RemoveAction {

        SourceRemoveAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nameString = parseVal("What is the name of the source you'd like to remove?",
                    "Enter name: ");
            if (nameString == null) {
                return;
            }
            boolean result = userAccount.removeSource(nameString);

            if (!result) {
                showInvalidInputError("That name is not present in your account's sources.");
            }
            refresh();
        }
    }

    private class DebtRemoveAction extends RemoveAction {

        DebtRemoveAction() {
            super();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String nameString = parseVal("What is the name of the source you'd like to remove?",
                    "Enter name: ");
            if (nameString == null) {
                return;
            }
            boolean result = userAccount.removeDebt(nameString);

            if (!result) {
                showInvalidInputError("That name is not present in your account's sources.");
            }
            refresh();
        }
    }

    public static void main(String[] args) {
        new BudgeItUI();

    }

    private class RefreshGraphAction extends AbstractAction {
        RefreshGraphAction() {
            super("Refresh");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    private class SavingsButtons extends JPanel implements ActionListener {

        public SavingsButtons() {
            this.setLayout(new FlowLayout());

            JButton deposit = new JButton("Deposit");
            JButton withdraw = new JButton("Withdraw");
            JButton spg = new JButton("Set Savings Goal");
            JButton interest = new JButton("Set Interest Rate");

            add(deposit);
            add(withdraw);
            add(spg);
            add(interest);

            deposit.addActionListener(this);
            withdraw.addActionListener(this);
            spg.addActionListener(this);
            interest.addActionListener(this);

            deposit.setActionCommand("Deposit");
            withdraw.setActionCommand("Withdraw");
            spg.setActionCommand("SPG");
            interest.setActionCommand("Interest");

            add(deposit);
            add(withdraw);
            add(spg);
            add(interest);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();

            if (action.equals("Deposit")) {
                depositSavings();
            } else if (action.equals("Withdraw")) {
                withdrawSavings();
            } else if (action.equals("SPG")) {
                setSavingsPercentGoal();
            } else if (action.equals("Interest")) {
                setInterest();
            }
            refresh();
        }
    }

    private class BalanceButtons extends JPanel implements ActionListener {

        public BalanceButtons() {
            this.setLayout(new FlowLayout());

            JButton deposit = new JButton("Deposit");
            JButton withdraw = new JButton("Withdraw");

            add(deposit);
            add(withdraw);

            deposit.addActionListener(this);
            withdraw.addActionListener(this);

            deposit.setActionCommand("Deposit");
            withdraw.setActionCommand("Withdraw");

            add(deposit);
            add(withdraw);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();

            if (action.equals("Deposit")) {
                depositBalance();
            } else if (action.equals("Withdraw")) {
                withdrawBalance();
            }

            refresh();
        }
    }
}
