import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    private TextField id;
    private TextField pin;
    private JButton loginButton;
    private JButton exitButton;
    private JPanel panel;

    public LoginPage(){
        panel = new JPanel();
        this.setTitle("Login");

        this.setSize(450,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);

        JLabel idLabel = new JLabel("Customer Id");
        JLabel pinLabel = new JLabel("Customer Pin");
        idLabel.setBounds(80,40,100,30);
        pinLabel.setBounds(80,85,100,30);
        panel.add(idLabel);
        panel.add(pinLabel);

        id = new TextField();
        pin = new TextField();
        id.setBounds(190,40,120,25);
        pin.setBounds(190,85,120,25);
        panel.add(id);
        panel.add(pin);

        exitButton = new JButton("Exit");
        exitButton.setBounds(100,150,80,25);
        loginButton = new JButton("Login");
        loginButton.setBounds(250,150,80,25);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.BLACK);
        panel.add(loginButton);
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);

        loginButton.addActionListener(e -> {
             String idText = id.getText().trim();
             String pinText = pin.getText().trim();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Customer id cannot be empty");
                id.setText("");
                return;
            }

            try{
                Integer.parseInt(idText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The customer id is invalid");
                id.setText("");
                return;
            }

            if (pinText.isEmpty()) {
                JOptionPane.showMessageDialog(null,"Pin cannot be empty");
                pin.setText("");
                return;
            }

            try{
                Integer.parseInt(pinText);
            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Error: The pin is invalid");
                pin.setText("");
                return;
            }

            if (idText.equals("0") && pinText.equals("0")) {
                new AdministratorMenu();
                this.setVisible(false);
                return;
            }else if(!BankingSystem.authenticate(idText, pinText)) {
                JOptionPane.showMessageDialog(null,"The id or pin is not correct");
                id.setText("");
                pin.setText("");
            }else {
                this.setVisible(false);
                new CustomerMenu(idText);
            }
         });

        exitButton.addActionListener(e -> {
              this.setVisible(false);
              new LauchPage();
        });
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
