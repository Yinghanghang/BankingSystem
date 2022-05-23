import javax.swing.*;
import java.awt.*;

public class CustomerMenu extends JFrame {
    private JPanel panel;
    private JTable table;
    private JButton newAccountButton;
    private JButton closeAccountButton;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JButton accSumButton;
    private JButton exitButton;

    public CustomerMenu(String customerId) {
        this.setTitle("Customer Menu");
        this.setSize(450,350);

        panel = new JPanel();
        panel.setLayout(null);
        table = new JTable();

        newAccountButton = new JButton("Open Account");
        closeAccountButton = new JButton("Close Account");
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        transferButton = new JButton("Transfer");
        accSumButton = new JButton("Account Summary");
        exitButton = new JButton("Exit");

        newAccountButton.setBounds(140, 40, 140, 25);
        closeAccountButton.setBounds(140, 75, 140, 25);
        depositButton.setBounds(140, 110, 140, 25);
        withdrawButton.setBounds(140, 145, 140, 25);
        transferButton.setBounds(140, 180, 140, 25);
        accSumButton.setBounds(140, 215, 140, 25);
        exitButton.setBounds(140, 250, 140, 25);

        panel.add(newAccountButton);
        panel.add(closeAccountButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transferButton);
        panel.add(accSumButton);
        panel.add(exitButton);
  
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        newAccountButton.addActionListener(e -> {
            JTextField id = new JTextField(5);
            JTextField type = new JTextField(5);
            JTextField deposit = new JTextField(5);

            JPanel newAccountPanel = new JPanel();
            newAccountPanel.add(new JLabel("Customer Id:"));
            newAccountPanel.add(id);
            newAccountPanel.add(Box.createHorizontalStrut(14));
            newAccountPanel.add(new JLabel("Account Type:"));
            newAccountPanel.add(type);
            newAccountPanel.add(Box.createHorizontalStrut(14));
            newAccountPanel.add(new JLabel("Initial Deposit"));
            newAccountPanel.add(deposit);

            int n = JOptionPane.showConfirmDialog(null, newAccountPanel,
                    "Create Account", JOptionPane.OK_CANCEL_OPTION);
            if(n == JOptionPane.CANCEL_OPTION) return;

            String idText = id.getText().trim();
            String typeText = type.getText().trim().toUpperCase();
            String depositText = deposit.getText().trim();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"customerId cannot be empty");
                return;
            }

            try{
                Integer.parseInt(idText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The customer id is invalid");
                return;
            }

            if(!BankingSystem.validateCustomerId(idText)){
                JOptionPane.showMessageDialog(null,"The customer id does not exist");
                return;
            }

            if(!typeText.equals("C") && !typeText.equals("S")) {
                JOptionPane.showMessageDialog(null,"Error: Account type must be either C or S");
                return;
            }

            if (depositText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Deposit cannot be empty");
                return;
            }

            try{
                int b = Integer.parseInt(depositText);
                if(b < 0){
                    JOptionPane.showMessageDialog(null,"deposit must be greater or equal to 0");
                    return;
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The deposit you entered is invalid");
                return;
            }

            BankingSystem.openAccount(idText, typeText, depositText);
            JOptionPane.showMessageDialog(null,"The new account number is: " + BankingSystem.getAccountNum());
        });

        closeAccountButton.addActionListener(e -> {
             JTextField accountNum = new JTextField(5);             
             JPanel closeAccountPanel = new JPanel();
             closeAccountPanel.add(new Label("Account Number"));
             closeAccountPanel.add(accountNum);

             int n =  JOptionPane.showConfirmDialog(null, closeAccountPanel,
                    "Close Account", JOptionPane.OK_CANCEL_OPTION);
             if(n == JOptionPane.CANCEL_OPTION) return;

             String accNumText = accountNum.getText().trim();

             if (accNumText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Account number cannot be empty");
                return;
            }

            try{
                Integer.parseInt(accNumText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The account number is invalid");
                return;
            }

             if(!BankingSystem.validateAccountNum(accNumText)){
                JOptionPane.showMessageDialog(null, "The account is either closed or does not exist");
            }else if(BankingSystem.validatePermission(customerId, accNumText)){
                BankingSystem.closeAccount(accNumText);
                JOptionPane.showMessageDialog(null,"Close account: success");
            }else{
                JOptionPane.showMessageDialog(null, "You can not close someone else’s account.");
            }
        });

        depositButton.addActionListener(e -> {
            JTextField accountNum = new JTextField(5);
            JTextField amount = new JTextField(5);
            JPanel depositPanel = new JPanel();

            depositPanel.add(new Label("Account Number"));
            depositPanel.add(accountNum);
            depositPanel.add(Box.createHorizontalStrut(14)); 
            depositPanel.add(new JLabel("Amount"));
            depositPanel.add(amount);

            int n = JOptionPane.showConfirmDialog(null, depositPanel,
                    "Deposit", JOptionPane.OK_CANCEL_OPTION);
            if(n == JOptionPane.CANCEL_OPTION) return;

            String accNumText = accountNum.getText().trim();
            String amountText = amount.getText().trim();

            if (accNumText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Account number cannot be empty");
                return;
            }

            try{
                Integer.parseInt(accNumText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The account number is invalid");
                return;
            }

            if(!BankingSystem.validateAccountNum(accNumText)){
                JOptionPane.showMessageDialog(null, "The account is either closed or does not exist");
                return;
            }

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Deposit cannot be empty");
                return;
            }

            try{
                int b = Integer.parseInt(amountText);
                if(b <= 0){
                    JOptionPane.showMessageDialog(null,"Deposit must be greater than 0");
                    return;
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The deposit amount is invalid");
                return;
            }

            BankingSystem.deposit(accNumText, amountText);
            JOptionPane.showMessageDialog(null,"DEPOSIT: SUCCESS");
        });

        withdrawButton.addActionListener(e-> {
            JTextField accountNum = new JTextField(5);
            JTextField amount = new JTextField(5);
            JPanel withdrawPanel = new JPanel();

            withdrawPanel.add(new Label("Account Number"));
            withdrawPanel.add(accountNum);
            withdrawPanel.add(Box.createHorizontalStrut(14)); 
            withdrawPanel.add(new JLabel("Amount"));
            withdrawPanel.add(amount);

            int n = JOptionPane.showConfirmDialog(null, withdrawPanel,
                    "Withdraw", JOptionPane.OK_CANCEL_OPTION);
            if(n == JOptionPane.CANCEL_OPTION) return;

            String accNumText = accountNum.getText().trim();
            String amountText = amount.getText().trim();

            if (accNumText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Account number cannot be empty");
                return;
            }

            try{
                Integer.parseInt(accNumText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The account number is invalid");
                return;
            }

            if(!BankingSystem.validateAccountNum(accNumText)){
                JOptionPane.showMessageDialog(null, "The account is either closed or does not exist");
                return;
            }

            if(!BankingSystem.validatePermission(customerId, accNumText)){
                JOptionPane.showMessageDialog(null, "You can’t withdraw from someone else's account");
                return;
            }

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Withdraw amount cannot be empty");
                return;
            }

            try{
                int b = Integer.parseInt(amountText);
                if(b <= 0){
                    JOptionPane.showMessageDialog(null,"Withdraw amount must be greater than 0");
                    return;
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: Withdraw amount is invalid");
                return;
            }

            if(BankingSystem.isEnoughFund(accNumText, amountText)) {
                BankingSystem.withdraw(accNumText, amountText);
                JOptionPane.showMessageDialog(null,"WITHDRAW: SUCCESS");
            }else{
                JOptionPane.showMessageDialog(null, "WITHDRAW - ERROR - NOT ENOUGH FUNDS");
            }
        });

        transferButton.addActionListener(e -> {
            JTextField srcAccNum = new JTextField(5);
            JTextField destAccNum = new JTextField(5);
            JTextField amount = new JTextField(5);

            JPanel transferPanel = new JPanel();
            transferPanel.add(new Label("Src Account"));
            transferPanel.add(srcAccNum);
            transferPanel.add(Box.createHorizontalStrut(14));
            transferPanel.add(new Label("Dest Account"));
            transferPanel.add(destAccNum);
            transferPanel.add(Box.createHorizontalStrut(14));
            transferPanel.add(new Label("Amount"));
            transferPanel.add(amount);

            int n = JOptionPane.showConfirmDialog(null, transferPanel,
                    "Transfer", JOptionPane.OK_CANCEL_OPTION);
            if(n == JOptionPane.CANCEL_OPTION) return;
            
            String srcAccNumText = srcAccNum.getText().trim();
            String destAccNumText = destAccNum.getText().trim();
            String amountText = amount.getText().trim();

            if (srcAccNumText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"The source account number cannot be empty");
                return;
            }

            try{
                Integer.parseInt(srcAccNumText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"The source account number is invalid");
                return;
            }

            if(!BankingSystem.validateAccountNum(srcAccNumText)){
                JOptionPane.showMessageDialog(null, "The source account number is either closed or not does not exist");
                return;
            }

            if(!BankingSystem.validatePermission(customerId, srcAccNumText)){
                JOptionPane.showMessageDialog(null, "You can't transfer money from someone else's account");
                return;
            }

            if (destAccNumText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"The  destination account number cannot be empty");
                return;
            }

            try{
                Integer.parseInt(destAccNumText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"The  destination account number is invalid");
                return;
            }

            if(!BankingSystem.validateAccountNum(destAccNumText)){
                JOptionPane.showMessageDialog(null, "The  destination account number is either closed or not does not exist");
                return;
            }

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Transfer amount cannot be empty");
                return;
            }

            try{
                int b = Integer.parseInt(amountText);
                if(b <= 0){
                    JOptionPane.showMessageDialog(null,"Transfer amount must be greater than 0");
                    return;
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: Transfer amount is invalid");
                return;
            }

            if(BankingSystem.isEnoughFund(srcAccNumText, amountText)) {
                BankingSystem.transfer(srcAccNumText, destAccNumText, amountText);
                JOptionPane.showMessageDialog(null,"TRANSFER: SUCCESS");
            }else{
                JOptionPane.showMessageDialog(null, "TRANSFER - ERROR - NOT ENOUGH FUNDS");
            }           
        });

        accSumButton.addActionListener(e -> {
            BankingSystem.setTableModel();
            BankingSystem.accountSummary(customerId);
            table.setModel(BankingSystem.getTableModel());

            JPanel accSumPanel = new JPanel();
            accSumPanel.add(table);
            JOptionPane.showConfirmDialog(null, accSumPanel,
                    "Account Summary", JOptionPane.OK_CANCEL_OPTION);
        });

        exitButton.addActionListener(e -> {
             this.setVisible(false);
             new LauchPage();
        });

    }

    public static void main(String[] args) {
        new CustomerMenu("100");
    }

}
