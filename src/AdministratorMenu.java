import javax.swing.*;
import java.awt.*;

public class AdministratorMenu extends JFrame{
    private JPanel adminPanel;
    private JTable table;
    private JButton summaryButton;
    private JButton reportAButton;
    private JButton reportBButton;
    private JButton exitButton;

    public AdministratorMenu(){
        this.setTitle("Administrator Menu");
        this.setSize(450,300);

        adminPanel = new JPanel();
        adminPanel.setLayout(null);
        table = new JTable();
        table.setRowHeight(25);
        table.setGridColor(Color.DARK_GRAY);
        table.getSelectionBackground();

        summaryButton = new JButton("Customer Summary");
        reportAButton = new JButton("Report A");
        reportBButton = new JButton("Report B");
        exitButton = new JButton("Exit");

        summaryButton.setBounds(140, 60, 150, 25);
        reportAButton.setBounds(140, 95, 150, 25);
        reportBButton.setBounds(140, 130, 150, 25);
        exitButton.setBounds(140, 165, 150, 25);

        adminPanel.add(summaryButton);
        adminPanel.add(reportAButton);
        adminPanel.add(reportBButton);
        adminPanel.add(exitButton);
        
        this.add(adminPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        summaryButton.addActionListener(e -> {
            JTextField customerId =  new JTextField(5);
            Panel sumPanel = new Panel();
            sumPanel.add(new Label("Customer Id"));
            sumPanel.add(customerId);

            int n = JOptionPane.showConfirmDialog(null, sumPanel, "Customer Account Summary", JOptionPane.OK_CANCEL_OPTION);
            if(n == JOptionPane.CANCEL_OPTION) return;

            String idText = customerId.getText();

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

            if(BankingSystem.validateCustomerId(idText)){
                BankingSystem.setTableModel();
                BankingSystem.accountSummary(idText);
                table.setModel(BankingSystem.getTableModel());
                
                JPanel panel = new JPanel();
                panel.add(table);
                JOptionPane.showConfirmDialog(null, panel, "Account Summary", JOptionPane.OK_CANCEL_OPTION);
            }else{
                JOptionPane.showMessageDialog(null,"The customer id does not exist");
            }
        });

        reportAButton.addActionListener(e -> {
            JPanel aPanel = new JPanel();
            BankingSystem.setTableModel();
            BankingSystem.reportA();
            table.setModel(BankingSystem.getTableModel());
            aPanel.add(table);
            JOptionPane.showConfirmDialog(null, aPanel, "Report A", JOptionPane.OK_CANCEL_OPTION);
        });

        reportBButton.addActionListener(e -> {
            Panel bPanel = new Panel();         
            bPanel.add(new Label("Min Age"));
            JTextField minAge = new JTextField(4);
            bPanel.add(minAge);
            bPanel.add(Box.createHorizontalStrut(14));
            bPanel.add(new Label("Max Age"));
            JTextField maxAge = new JTextField(4);
            bPanel.add(maxAge);

            int n = JOptionPane.showConfirmDialog(null, bPanel, "Report B", JOptionPane.OK_CANCEL_OPTION);
            if(n == JOptionPane.CANCEL_OPTION) return;

            String minAgeText = minAge.getText();
            String maxAgeText = maxAge.getText();

            if (minAgeText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Min age cannot be empty");
                return;
            }

            try{
                int b = Integer.parseInt(minAgeText);
                if(b < 0){
                    JOptionPane.showMessageDialog(null,"The min age must be greater than or equal to 0");
                    return;
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: the min age is invalid");
                return;
            }

            if (maxAgeText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Max age cannot be empty");
                return;
            }

            try{
                int b = Integer.parseInt(maxAgeText);
                if(b < 0){
                    JOptionPane.showMessageDialog(null,"The max age must be greater than or equal to 0");
                    return;
                }
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: the max age is invalid");
                return;
            }

            JPanel panel = new JPanel();
            BankingSystem.setTableModel();
            BankingSystem.reportB(minAgeText, maxAgeText);
            table.setModel(BankingSystem.getTableModel());
            panel.add(table);
            JOptionPane.showConfirmDialog(null, panel, "Report B", JOptionPane.OK_CANCEL_OPTION);
        });

        exitButton.addActionListener(e -> {
            this.setVisible(false);
            new LauchPage();
        });
    }

    public static void main(String[] args) {
        new AdministratorMenu();
    }
}
