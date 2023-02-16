package incometaxcalculator.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import incometaxcalculator.data.management.*;

import incometaxcalculator.exceptions.*;
/*
 * Created by JFormDesigner on Sun Oct 23 18:50:00 CEST 2022
 */



/**
 * @author unknown
 */
public class GraphicalInterface extends JFrame {

    private final TaxpayerManager taxpayerManager = new TaxpayerManager();
    private Taxpayer currentTaxPayer;
    private final DefaultListModel<String> listOfTaxPayersAfm = new DefaultListModel<>();
    private final DefaultListModel<String> listOfTaxPayersReceipts = new DefaultListModel<>();
    private String fileTypeToLoad = "txt"; //Default value
    private static GraphicalInterface singleInstance = null;

    public GraphicalInterface() {

        initComponents();
    }

    public static GraphicalInterface getSingletonView(){
        if (singleInstance == null)
            singleInstance = new GraphicalInterface();
        return singleInstance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GraphicalInterface GUI = new GraphicalInterface();
                GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GUI.setVisible(true);
            }
        });
    }

    private void viewTaxPayer(ActionEvent e) {

        if (taxPayersList.isSelectionEmpty()){
        }else{
            loadTaxPayerInformation( (String) taxPayersList.getSelectedValue());
            taxPayerTabbedPane.setSelectedIndex(0);


        }

    }

    private void loadByAFM(ActionEvent e) {

        String taxRegistrationNumberFile;
        JFrame fframe = new JFrame();
        int afm = Integer.parseInt(JOptionPane.showInputDialog(
                "Insert tax payers afm." ));
        if (taxpayerManager.containsTaxpayer(afm)){
            return;
        }
        taxRegistrationNumberFile = ("AFMs/" + afm + "_INFO." + fileTypeToLoad);
        try {

            taxRegistrationNumberFile = ("AFMs/" + afm + "_INFO." + fileTypeToLoad);
            taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
            listOfTaxPayersAfm.addElement(String.valueOf(afm));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (WrongFileFormatException ex) {
            throw new RuntimeException(ex);
        } catch (WrongFileEndingException ex) {
            throw new RuntimeException(ex);
        } catch (WrongTaxpayerStatusException ex) {
            throw new RuntimeException(ex);
        } catch (WrongReceiptKindException ex) {
            throw new RuntimeException(ex);
        } catch (WrongReceiptDateException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadTaxPayerFromFile(int afm) {
        String taxRegistrationNumberFile;

        if (taxpayerManager.containsTaxpayer(afm)) {
            JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
        } else {
            try {
                taxRegistrationNumberFile = ("AFMs/" + afm + "_INFO." + fileTypeToLoad);
                taxpayerManager.loadTaxpayer(taxRegistrationNumberFile);
                listOfTaxPayersAfm.addElement(String.valueOf(afm));
                // textPane.setText(taxpayersTRN);
            } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null,
                        "The tax registration number must have only digits.");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "The file doesn't exists.");
            } catch (WrongFileFormatException e1) {
                JOptionPane.showMessageDialog(null, "Please check your file format and try again.");
            } catch (WrongFileEndingException e1) {
                JOptionPane.showMessageDialog(null, "Please check your file ending and try again.");
            } catch (WrongTaxpayerStatusException e1) {
                JOptionPane.showMessageDialog(null, "Please check taxpayer's status and try again.");
            } catch (WrongReceiptKindException e1) {
                JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
            } catch (WrongReceiptDateException e1) {
                JOptionPane.showMessageDialog(null,
                        "Please make sure your date is " + "DD/MM/YYYY and try again.");
            }

        }
    }

    private void loadAllTaxPayer(ActionEvent e) {

        int afm = 0;

        final File folder = new File("AFMs" );
        for (final File fileEntry : folder.listFiles()) {
            afm = Integer.parseInt( fileEntry.getName().split("_")[0] );
            if(!listOfTaxPayersAfm.contains(String.valueOf(afm))){
                loadTaxPayerFromFile(afm);
            }

        }

    }

    private void addNewReceipt(ActionEvent e) {

        NewReceiptDialog addReceiptJDialog = new NewReceiptDialog(GraphicalInterface.this);
        addReceiptJDialog.setModal(true);
        addReceiptJDialog.setVisible(true);
        try {
            taxpayerManager.addReceipt( addReceiptJDialog.getReceiptID(),
                    addReceiptJDialog.getDate(),
                    addReceiptJDialog.getAmount(),
                    addReceiptJDialog.getKind(),
                    new Company(addReceiptJDialog.getCompany(),
                    addReceiptJDialog.getCountry(),
                    addReceiptJDialog.getCity(),
                    addReceiptJDialog.getStreet(),
                    addReceiptJDialog.getNumber() ),
                    getSelectedAFM());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (WrongReceiptKindException ex) {
            throw new RuntimeException(ex);
        } catch (WrongReceiptDateException ex) {
            throw new RuntimeException(ex);
        } catch (ReceiptAlreadyExistsException ex) {
            throw new RuntimeException(ex);
        }
        addReceiptJDialog.dispose();
    }

    public int getSelectedAFM(){
        if (taxPayersList.isSelectionEmpty()){
            return -1;
        }else{
            return Integer.valueOf( (String) taxPayersList.getSelectedValue());
        }
    }
    private void showGraphs(ActionEvent e) {
        int taxRegistrationNumber = getSelectedAFM();

        short ENTERTAINMENT =0;
        short BASIC = 1;
        short TRAVEL = 2;
        short HEALTH = 3;
        short OTHER  = 4;
        ChartDisplay.createBarChart(taxpayerManager.getTaxpayerBasicTax(taxRegistrationNumber),
                taxpayerManager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber),
                taxpayerManager.getTaxpayerTotalTax(taxRegistrationNumber));
        ChartDisplay.createPieChart(
                taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT),
                taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC),
                taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL),
                taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH),
                taxpayerManager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER));
    }


    private void viewSelectedReceipt(ActionEvent e) {
        taxPayerTabbedPane.setSelectedIndex(1);
    }

    private void saveChangesToReceipt(ActionEvent e) {
        // TODO add your code here
        int selectedReceipt;
        if (receiptsList.isSelectionEmpty()){
            //plese select a receipt
        }else{
             selectedReceipt = Integer.valueOf( (String) receiptsList.getSelectedValue() );
            Map<Integer, Receipt> receipts = currentTaxPayer.getReceiptHashMap();
            Receipt currentReceipt = receipts.get(Integer.valueOf(selectedReceipt) );
            try {
                taxpayerManager.removeReceipt(selectedReceipt);
                taxpayerManager.addReceipt(Integer.valueOf(receiptIdTextField.getText()),
                        dateTextField.getText(),
                        Float.valueOf( amountTextField.getText()),
                        kindTextField.getText(),
                        new Company(companyTextField.getText(),
                        countryTextField.getText(),
                        cityTextField.getText(),
                        streetTextField.getText(),
                        Integer.valueOf( numberTextField.getText()) )
                ,getSelectedAFM());

            } catch (WrongReceiptDateException | IOException |
                     WrongReceiptKindException | ReceiptAlreadyExistsException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    private void loadTaxPayerInformation(String afm){
        currentTaxPayer = taxpayerManager.getTaxpayer(Integer.parseInt(afm) );
        nameTextfield.setText(currentTaxPayer.getFullname());
        afmTextField.setText(afm);
        incomeTextField.setText(String.valueOf(currentTaxPayer.getIncome()));
        if(currentTaxPayer instanceof MarriedFilingSeparatelyTaxpayer){
            statusComboBox.setSelectedItem("Married Filing Separately");
        }else if(currentTaxPayer instanceof  MarriedFilingJointlyTaxpayer){
            statusComboBox.setSelectedItem("Married Filing Jointly");

        }else if(currentTaxPayer instanceof HeadOfHouseholdTaxpayer){
            statusComboBox.setSelectedItem("HeadOfHouseholdTaxpayer");

        }else{
            statusComboBox.setSelectedItem("Single");

        }
        Map<Integer, Receipt> receipts = currentTaxPayer.getReceiptHashMap();
        listOfTaxPayersReceipts.clear();
        for(Integer id : receipts.keySet()){
            listOfTaxPayersReceipts.addElement(String.valueOf(id));
        }

    }

    public void loadReceiptInformation(String receiptID){
        if (receiptID == null){
            return;
        }
        Map<Integer, Receipt> receipts = currentTaxPayer.getReceiptHashMap();
        Receipt currentReceipt = receipts.get(Integer.valueOf(receiptID) );
        receiptIdTextField.setText(String.valueOf(currentReceipt.getId()));
        dateTextField.setText(currentReceipt.getIssueDate());
        kindTextField.setText(currentReceipt.getKind());
        amountTextField.setText(String.valueOf(currentReceipt.getAmount()));
        companyTextField.setText(currentReceipt.getCompany().getName());
        countryTextField.setText(currentReceipt.getCompany().getAddress().getCountry());
        //city street number
        cityTextField.setText(currentReceipt.getCompany().getAddress().getCity());
        streetTextField.setText(currentReceipt.getCompany().getAddress().getStreet());
        numberTextField.setText( String.valueOf(currentReceipt.getCompany().getAddress().getNumber() ));
    }
    private void DeleteSelectedReceipt(ActionEvent e) {
        // TODO add your code here
        if (receiptsList.isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "No receipt is selected.");
        }else{
            try {
                taxpayerManager.removeReceipt(
                        Integer.valueOf((String)receiptsList.getSelectedValue() ));
                loadTaxPayerInformation( (String) taxPayersList.getSelectedValue());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (WrongReceiptKindException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void deleteSelectedTaxPayer(ActionEvent e) {

        if ( taxPayersList.isSelectionEmpty()){
        }else{
            taxpayerManager.removeTaxpayer( 
                    getSelectedAFM() );
        }

    }

    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        menuBar1 = new JMenuBar();
        taxPayerLoadMenu = new JMenu();
        loadByAFMMenuItem = new JMenuItem();
        loadAllTaxPayerMenuItem = new JMenuItem();
        taxPayerOptionsMenu = new JMenu();
        viewTaxPayerMenuItem = new JMenuItem();
        deleteSelectedTaxPayerMenuItem = new JMenuItem();
        receiptMenu = new JMenu();
        viewSelectedReceipt = new JMenuItem();
        saveChangesToReceiptMenuItem = new JMenuItem();
        addNewReceiptMenuItem = new JMenuItem();
        DeleteSelectedReceiptMenuItem = new JMenuItem();
        showGraphsMenu = new JMenu();
        showGraphsMenuItem = new JMenuItem();
        textMenuItem = new JMenuItem();
        xmlMenuItem = new JMenuItem();
        exitMenu = new JMenu();
        taxPayerTabbedPane = new JTabbedPane();
        taxPayerInformationPane = new JPanel();
        nameLabel = new JLabel();
        nameTextfield = new JTextField();
        afmLabel = new JLabel();
        afmTextField = new JTextField();
        statusLabel = new JLabel();
        String[] status = {"Single","Married Filing Jointly","Married Filing Separately","Head οf Household"};
        statusComboBox = new JComboBox(status);
        incomeLabel = new JLabel();
        incomeTextField = new JTextField();
        receiptInformationSplitPane = new JSplitPane();
        panel2 = new JPanel();
        receiptIdLabel = new JLabel();
        dateLabel = new JLabel();
        kindLabel = new JLabel();
        amountLabel = new JLabel();
        companyLabel = new JLabel();
        countryLabel = new JLabel();
        cityLabel = new JLabel();
        streetLabel = new JLabel();
        numberLabel = new JLabel();
        panel3 = new JPanel();
        receiptIdTextField = new JTextField();
        dateTextField = new JTextField();
        kindTextField = new JTextField();
        amountTextField = new JTextField();
        companyTextField = new JTextField();
        countryTextField = new JTextField();
        cityTextField = new JTextField();
        streetTextField = new JTextField();
        numberTextField = new JTextField();
        tabbedPane1 = new JTabbedPane();
        taxPayerScrollPane = new JScrollPane();
        taxPayersList = new JList( listOfTaxPayersAfm );
        //taxPayersList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        taxPayersList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                loadTaxPayerInformation( (String) taxPayersList.getSelectedValue() );
            }
        });
        receiptScrollPane = new JScrollPane();
        receiptsList = new JList(listOfTaxPayersReceipts);
        receiptsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                loadReceiptInformation((String) receiptsList.getSelectedValue());
            }
        });

        //======== this ========
        var contentPane = getContentPane();

        //======== menuBar1 ========
        {

            //======== taxPayerLoadMenu ========
            {
                taxPayerLoadMenu.setText("Load tax payer");

                //---- loadByAFMMenuItem ----
                loadByAFMMenuItem.setText("Load tax payer by AFM");
                loadByAFMMenuItem.addActionListener(e -> loadByAFM(e));
                taxPayerLoadMenu.add(loadByAFMMenuItem);

                //---- loadAllTaxPayerMenuItem ----
                loadAllTaxPayerMenuItem.setText("Load all tax payers");
                loadAllTaxPayerMenuItem.addActionListener(e -> loadAllTaxPayer(e));
                taxPayerLoadMenu.add(loadAllTaxPayerMenuItem);
            }
            menuBar1.add(taxPayerLoadMenu);

            //======== taxPayerOptionsMenu ========
            {
                taxPayerOptionsMenu.setText("Tax payer");

                //---- viewTaxPayerMenuItem ----
                viewTaxPayerMenuItem.setText("View selected tax payer");
                viewTaxPayerMenuItem.addActionListener(e -> viewTaxPayer(e));
                taxPayerOptionsMenu.add(viewTaxPayerMenuItem);

                //---- deleteSelectedTaxPayerMenuItem ----
                deleteSelectedTaxPayerMenuItem.setText("Delete current tax payer");
                deleteSelectedTaxPayerMenuItem.addActionListener(e -> deleteSelectedTaxPayer(e));
                taxPayerOptionsMenu.add(deleteSelectedTaxPayerMenuItem);
            }
            menuBar1.add(taxPayerOptionsMenu);

            //======== receiptMenu ========
            {
                receiptMenu.setText("Receipt");

                //---- viewSelectedReceipt ----
                viewSelectedReceipt.setText("View selected receipt");
                viewSelectedReceipt.addActionListener(e -> viewSelectedReceipt(e));
                receiptMenu.add(viewSelectedReceipt);

                //---- saveChangesToReceiptMenuItem ----
                saveChangesToReceiptMenuItem.setText("Save changes to receipt");
                saveChangesToReceiptMenuItem.addActionListener(e -> saveChangesToReceipt(e));
                receiptMenu.add(saveChangesToReceiptMenuItem);

                //---- addNewReceiptMenuItem ----
                addNewReceiptMenuItem.setText("Add a new receipt");
                addNewReceiptMenuItem.addActionListener(e -> addNewReceipt(e));
                receiptMenu.add(addNewReceiptMenuItem);

                //---- DeleteSelectedReceiptMenuItem ----
                DeleteSelectedReceiptMenuItem.setText("Delected selected receipt");
                DeleteSelectedReceiptMenuItem.addActionListener(e -> DeleteSelectedReceipt(e));
                receiptMenu.add(DeleteSelectedReceiptMenuItem);
            }
            menuBar1.add(receiptMenu);

            //======== showGraphsMenu ========
            {
                showGraphsMenu.setText("Tax payer grpahs");
                showGraphsMenu.addActionListener(e -> showGraphs(e));

                //---- showGraphsMenuItem ----
                showGraphsMenuItem.setText("Show graphs");
                showGraphsMenuItem.addActionListener(e -> showGraphs(e));
                showGraphsMenu.add(showGraphsMenuItem);
            }
            menuBar1.add(showGraphsMenu);

            //======== exitMenu ========
            {
                exitMenu.setText("File type");

                textMenuItem.setText("Text file");
                textMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        fileTypeToLoad = "txt";
                    }
                });
                exitMenu.add(textMenuItem);

                xmlMenuItem.setText("Xml file");
                xmlMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        fileTypeToLoad= "xml";
                    }
                });
                exitMenu.add(xmlMenuItem);
            }
            menuBar1.add(exitMenu);
        }
        setJMenuBar(menuBar1);

        //======== taxPayerTabbedPane ========
        {

            //======== taxPayerInformationPane ========
            {
                taxPayerInformationPane.setLayout(new GridLayout(8, 1));

                //---- nameLabel ----
                nameLabel.setText("Name");
                taxPayerInformationPane.add(nameLabel);
                taxPayerInformationPane.add(nameTextfield);

                //---- afmLabel ----
                afmLabel.setText("AFM");
                taxPayerInformationPane.add(afmLabel);
                taxPayerInformationPane.add(afmTextField);

                //---- statusLabel ----
                statusLabel.setText("Status");
                taxPayerInformationPane.add(statusLabel);
                taxPayerInformationPane.add(statusComboBox);

                //---- incomeLabel ----
                incomeLabel.setText("Income");
                taxPayerInformationPane.add(incomeLabel);
                taxPayerInformationPane.add(incomeTextField);
            }
            taxPayerTabbedPane.addTab("Tax payers informations", taxPayerInformationPane);

            //======== receiptInformationSplitPane ========
            {
                receiptInformationSplitPane.setResizeWeight(0.5);

                //======== panel2 ========
                {
                    panel2.setLayout(new GridLayout(9, 1));

                    //---- receiptIdLabel ----
                    receiptIdLabel.setText("Receipt ID");
                    panel2.add(receiptIdLabel);

                    //---- dateLabel ----
                    dateLabel.setText("Date");
                    panel2.add(dateLabel);

                    //---- kindLabel ----
                    kindLabel.setText("Kind");
                    panel2.add(kindLabel);

                    //---- amountLabel ----
                    amountLabel.setText("Amount");
                    panel2.add(amountLabel);

                    //---- companyLabel ----
                    companyLabel.setText("Company");
                    panel2.add(companyLabel);

                    //---- countryLabel ----
                    countryLabel.setText("Country");
                    panel2.add(countryLabel);

                    //---- cityLabel ----
                    cityLabel.setText("City");
                    panel2.add(cityLabel);

                    //---- streetLabel ----
                    streetLabel.setText("Street");
                    panel2.add(streetLabel);

                    //---- numberLabel ----
                    numberLabel.setText("Number");
                    panel2.add(numberLabel);
                }
                receiptInformationSplitPane.setLeftComponent(panel2);

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayout(9, 1));
                    panel3.add(receiptIdTextField);
                    panel3.add(dateTextField);
                    panel3.add(kindTextField);
                    panel3.add(amountTextField);
                    panel3.add(companyTextField);
                    panel3.add(countryTextField);
                    panel3.add(cityTextField);
                    panel3.add(streetTextField);
                    panel3.add(numberTextField);
                }
                receiptInformationSplitPane.setRightComponent(panel3);
            }
            taxPayerTabbedPane.addTab("Receipt information", receiptInformationSplitPane);
        }

        //======== tabbedPane1 ========
        {

            //======== taxPayerScrollPane ========
            {
                taxPayerScrollPane.setViewportView(taxPayersList);
            }
            tabbedPane1.addTab("Tax payers", taxPayerScrollPane);

            //======== receiptScrollPane ========
            {
                receiptScrollPane.setViewportView(receiptsList);
            }
            tabbedPane1.addTab("Receipts", receiptScrollPane);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(taxPayerTabbedPane, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                        .addComponent(tabbedPane1, GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(taxPayerTabbedPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JMenuBar menuBar1;
    private JMenu taxPayerLoadMenu;
    private JMenuItem loadByAFMMenuItem;
    private JMenuItem loadAllTaxPayerMenuItem;
    private JMenu taxPayerOptionsMenu;
    private JMenuItem viewTaxPayerMenuItem;
    private JMenuItem deleteSelectedTaxPayerMenuItem;
    private JMenu receiptMenu;
    private JMenuItem viewSelectedReceipt;
    private JMenuItem saveChangesToReceiptMenuItem;
    private JMenuItem addNewReceiptMenuItem;
    private JMenuItem DeleteSelectedReceiptMenuItem;
    private JMenu showGraphsMenu;
    private JMenuItem showGraphsMenuItem;
    private JMenuItem textMenuItem;
    private JMenuItem xmlMenuItem;
    private JMenu exitMenu;
    private JTabbedPane taxPayerTabbedPane;
    private JPanel taxPayerInformationPane;
    private JLabel nameLabel;
    private JTextField nameTextfield;
    private JLabel afmLabel;
    private JTextField afmTextField;
    private JLabel statusLabel;
    private JComboBox statusComboBox;
    private JLabel incomeLabel;
    private JTextField incomeTextField;
    private JSplitPane receiptInformationSplitPane;
    private JPanel panel2;
    private JLabel receiptIdLabel;
    private JLabel dateLabel;
    private JLabel kindLabel;
    private JLabel amountLabel;
    private JLabel companyLabel;
    private JLabel countryLabel;
    private JLabel cityLabel;
    private JLabel streetLabel;
    private JLabel numberLabel;
    private JPanel panel3;
    private JTextField receiptIdTextField;
    private JTextField dateTextField;
    private JTextField kindTextField;
    private JTextField amountTextField;
    private JTextField companyTextField;
    private JTextField countryTextField;
    private JTextField cityTextField;
    private JTextField streetTextField;
    private JTextField numberTextField;
    private JTabbedPane tabbedPane1;
    private JScrollPane taxPayerScrollPane;
    private JList taxPayersList;
    private JScrollPane receiptScrollPane;
    private JList receiptsList;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
