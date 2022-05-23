import javax.swing.*;
import java.awt.*;

public class NewCustomer extends JFrame {

    private JPanel panel;
    private JTextField name;
    private JTextField gender;
    private JTextField age;
    private JTextField pin;
    private JButton createButton;
    private JButton exitButton;

    public NewCustomer(){
          panel = new JPanel();
          this.setTitle("Create New Customer");

          this.setSize(450,300);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          panel.setLayout(null);

          JLabel nameLabel = new JLabel("Username", SwingConstants.CENTER);
          JLabel genderLabel = new JLabel("Gender", SwingConstants.CENTER);
          JLabel ageLabel = new JLabel("Age", SwingConstants.CENTER);
          JLabel pinLabel = new JLabel("Pin", SwingConstants.CENTER);
         
          nameLabel.setBounds(80,30,100,25);
          genderLabel.setBounds(80,60,100,25);
          ageLabel.setBounds(80,90,100,25);
          pinLabel.setBounds(80,120,100,25);

          panel.add(nameLabel);
          panel.add(genderLabel);
          panel.add(pinLabel);
          panel.add(ageLabel);

          name = new JTextField();
          gender = new JTextField();
          age = new JTextField();
          pin = new JTextField();

          name.setBounds(190,30,115,25);
          gender.setBounds(190,60,115,25);
          age.setBounds(190,90,115,25);       
          pin.setBounds(190,120,115,25);

          panel.add(name);
          panel.add(gender);
          panel.add(age);
          panel.add(pin);

          exitButton = new JButton("Exit");
          exitButton.setBounds(120,170,80,25);         
          createButton = new JButton("Create");
          createButton.setBounds(270,170,80,25);
          createButton.setForeground(Color.WHITE);
          createButton.setBackground(Color.BLACK);

          panel.add(createButton);
          panel.add(exitButton);
          this.add(panel, BorderLayout.CENTER);
          this.setVisible(true);

          createButton.addActionListener(e -> {
                String nameText = name.getText().trim();
                String genderText = gender.getText().trim();
                String ageText = age.getText().trim();
                String pinText = pin.getText().trim();

                if (nameText.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Name cannot be empty");
                    name.setText("");
                    return;
                } else if(nameText.length() > 15){
                    JOptionPane.showMessageDialog(null,"Name length cannot exceed 15 characters");
                    return;
                }

                if (genderText.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Gender cannot be empty");
                    gender.setText("");
                    return;
                } else if (!genderText.toUpperCase().equals("M") && !genderText.toUpperCase().equals("F")) {
                    JOptionPane.showMessageDialog(null,"Gender must be either M or F");
                    gender.setText("");
                    return;
                }

                if (ageText.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Age cannot be empty");
                    age.setText("");
                    return;
                }
    
                try{
                    int i = Integer.parseInt(ageText);
                    if(i < 0){
                        JOptionPane.showMessageDialog(null,"Age must be greater or equal to 0");
                        age.setText("");
                        return;
                    }
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null,"Error: The age is invalid");
                    age.setText("");
                    return;
                }

                if (pinText.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Pin cannot be empty");
                    pin.setText("");
                    return;
                }
    
                try{
                    int i = Integer.parseInt(pinText);
                    if(i < 0){
                        JOptionPane.showMessageDialog(null,"Pin must be greater or equal to 0");
                        pin.setText("");
                        return;
                    }
                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null,"Error: The pin is invalid");
                    pin.setText("");
                    return;
                }

                BankingSystem.newCustomer(nameText, genderText.toUpperCase(), ageText, pinText);
                JOptionPane.showMessageDialog(null,"You customer id is " + BankingSystem.getCustomerId());
                new LauchPage();
                this.setVisible(false);
          });

          exitButton.addActionListener(e -> {
               new LauchPage();
               this.setVisible(false);
          });

    }

    public static void main(String[] args) {
        new NewCustomer();
    }
}
