package incometaxcalculator.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;


public class NewReceiptDialog extends JDialog {
	public NewReceiptDialog(Window owner) {
		super(owner);
		initComponents();
	}
	private int receiptID;
	private float amount;
	private String kind;

	public int getReceiptID() {
		return receiptID;
	}

	public float getAmount() {
		return amount;
	}

	public String getCompany() {
		return company;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public int getNumber() {
		return number;
	}

	public String getDate() {
		return date;
	}

	private String company;
	private String date;

	private String country;
	private String city;
	private String street;
	private int number;

	private void save(ActionEvent e) {
		// TODO add your code here
		receiptID = Integer.valueOf( receiptIDTextField.getText() );
		kind = kindTextField.getText();
		date = dateTextField.getText();
		company = companyTextField.getText();
		city = cityTextField.getText();
		street = streetTextField.getText();
		amount = Float.valueOf( amountTextField.getText() );
		number = Integer.valueOf( numberTextField.getText());
		setVisible(false);
	}

	private void cancel(ActionEvent e) {
		// TODO add your code here
	}

	public JTextField getDateTextField() {
		return dateTextField;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
		// Generated using JFormDesigner Evaluation license - Duran
		dateLabel = new JLabel();
		kindLabel = new JLabel();
		amountLabel = new JLabel();
		companyLabel = new JLabel();
		countryLabel = new JLabel();
		cityLabel = new JLabel();
		streetLabel = new JLabel();
		numberLabel = new JLabel();
		dateTextField = new JTextField();
		kindTextField = new JTextField();
		amountTextField = new JTextField();
		companyTextField = new JTextField();
		countryTextField = new JTextField();
		cityTextField = new JTextField();
		streetTextField = new JTextField();
		numberTextField = new JTextField();
		saveButton = new JButton();
		cancelButton = new JButton();
		receiptIDJLabel = new JLabel();
		receiptIDTextField = new JTextField();

		//======== this ========
		Container contentPane = getContentPane();

		//---- dateLabel ----
		dateLabel.setText("Date");

		//---- kindLabel ----
		kindLabel.setText("Kind");

		//---- amountLabel ----
		amountLabel.setText("Amount");

		//---- companyLabel ----
		companyLabel.setText("Company");

		//---- countryLabel ----
		countryLabel.setText("Country");

		//---- cityLabel ----
		cityLabel.setText("City");

		//---- streetLabel ----
		streetLabel.setText("Street");

		//---- numberLabel ----
		numberLabel.setText("Number");

		//---- saveButton ----
		saveButton.setText("Save");
		saveButton.addActionListener(e -> save(e));

		//---- cancelButton ----
		cancelButton.setText("Cancel");
		cancelButton.addActionListener(e -> cancel(e));

		//---- receiptIDJLabel ----
		receiptIDJLabel.setText("Receipt ID");

		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
												.addContainerGap()
												.addComponent(receiptIDJLabel)
												.addGap(92, 92, 92)
												.addComponent(receiptIDTextField))
										.addGroup(contentPaneLayout.createParallelGroup()
												.addGroup(contentPaneLayout.createSequentialGroup()
														.addGap(107, 107, 107)
														.addComponent(saveButton)
														.addGap(18, 18, 18)
														.addComponent(cancelButton))
												.addGroup(contentPaneLayout.createSequentialGroup()
														.addContainerGap()
														.addGroup(contentPaneLayout.createParallelGroup()
																.addGroup(contentPaneLayout.createSequentialGroup()
																		.addGroup(contentPaneLayout.createParallelGroup()
																				.addComponent(kindLabel)
																				.addComponent(amountLabel)
																				.addComponent(companyLabel)
																				.addComponent(countryLabel)
																				.addComponent(cityLabel)
																				.addComponent(streetLabel)
																				.addComponent(numberLabel))
																		.addGap(122, 122, 122))
																.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
																		.addComponent(dateLabel, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
														.addGroup(contentPaneLayout.createParallelGroup()
																.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(kindTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(amountTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(companyTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(countryTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(streetTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
																.addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)))))
								.addContainerGap(12, Short.MAX_VALUE))
		);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(contentPaneLayout.createParallelGroup()
										.addComponent(receiptIDJLabel, GroupLayout.Alignment.TRAILING)
										.addComponent(receiptIDTextField, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(dateLabel)
										.addComponent(dateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(contentPaneLayout.createParallelGroup()
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addGap(6, 6, 6)
												.addComponent(kindTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(6, 6, 6)
												.addComponent(amountTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(6, 6, 6)
												.addComponent(companyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(6, 6, 6)
												.addComponent(countryTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(6, 6, 6)
												.addComponent(cityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(6, 6, 6)
												.addComponent(streetTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(6, 6, 6)
												.addComponent(numberTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(contentPaneLayout.createSequentialGroup()
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(kindLabel)
												.addGap(12, 12, 12)
												.addComponent(amountLabel)
												.addGap(12, 12, 12)
												.addComponent(companyLabel)
												.addGap(12, 12, 12)
												.addComponent(countryLabel)
												.addGap(12, 12, 12)
												.addComponent(cityLabel)
												.addGap(12, 12, 12)
												.addComponent(streetLabel)
												.addGap(12, 12, 12)
												.addComponent(numberLabel)))
								.addGap(18, 18, 18)
								.addGroup(contentPaneLayout.createParallelGroup()
										.addComponent(saveButton)
										.addComponent(cancelButton))
								.addContainerGap())
		);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
	// Generated using JFormDesigner Evaluation license - Duran
	private JLabel dateLabel;
	private JLabel kindLabel;
	private JLabel amountLabel;
	private JLabel companyLabel;
	private JLabel countryLabel;
	private JLabel cityLabel;
	private JLabel streetLabel;
	private JLabel numberLabel;
	private JTextField dateTextField;
	private JTextField kindTextField;
	private JTextField amountTextField;
	private JTextField companyTextField;
	private JTextField countryTextField;
	private JTextField cityTextField;
	private JTextField streetTextField;
	private JTextField numberTextField;
	private JButton saveButton;
	private JButton cancelButton;
	private JLabel receiptIDJLabel;
	private JTextField receiptIDTextField;

	public String getKind() {
		return kind;
	}
	// JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
