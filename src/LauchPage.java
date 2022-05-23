import javax.swing.*;
import java.awt.*;  

public class LauchPage extends JFrame{
    private JPanel panel;
    private JButton newCustomer;
    private JButton login;
    private JButton exit;

    public LauchPage(){
          this.setTitle("Welcome");
          this.setSize(450,300);

          panel = new JPanel();
          panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
          
          newCustomer = new JButton("New Customer");
          login = new JButton("Customer login");
          exit = new JButton("Exit");

          newCustomer.setAlignmentX(Component.CENTER_ALIGNMENT);
          login.setAlignmentX(Component.CENTER_ALIGNMENT);
          exit.setAlignmentX(Component.CENTER_ALIGNMENT);

          panel.add(Box.createRigidArea(new Dimension(5, 60)));
          panel.add(newCustomer);
          panel.add(Box.createRigidArea(new Dimension(5, 15)));

          panel.add(login);
          panel.add(Box.createRigidArea(new Dimension(5, 15)));

          panel.add(exit);
          panel.add(Box.createRigidArea(new Dimension(5, 15)));

          this.add(panel);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          this.setVisible(true);

          newCustomer.addActionListener(e ->{
              this.setVisible(false);
              new NewCustomer();
          });

          login.addActionListener(e -> {
               this.setVisible(false);
               new LoginPage();
          });
          
          exit.addActionListener( e -> {
              System.exit(0);
          });

    }

    public static void main(String[] args) {
        BankingSystem.init("C:\\bankproject1\\src\\db.properties");
        BankingSystem.testConnection();
        new LauchPage();
    }
}
