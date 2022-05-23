import java.util.Scanner;

public class P1 {
    private static String accountNum;
    private static String accountType;
    private static String amount;
    private static String customerId;
    private static String currentUserId; 
    private static String customerPin;

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        BankingSystem.init("C:\\bankproject1\\src\\db.properties");
        BankingSystem.testConnection();
        
        int choice = 0;
        while(choice != 3){
            System.out.println("1.   New Customer");
            System.out.println("2.   Customer Login");
            System.out.println("3.   Exit");
            System.out.print ("Please enter a number: ");

            while(true){
                String input = scan.next();
                try {
                    choice = Integer.parseInt(input);
                    break;
                } catch (Exception e) {
                    System.out.print("Invalid choice. Please enter a number: ");
                }
            }

            switch (choice) {
                case 1:
                    newCustomer();
                    System.out.println();
                    break;
                case 2:
                    login();
                    System.out.println();
                    break;
                case 3:
                    System.out.println();
                    break;
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 3. ");
                    System.out.println();
                    break;
            }
        }    
        scan.close();
        BankingSystem.close();
        System.out.println("Thanks for choosing our service. We look forward to serving you again soon.");
    }

    private static void newCustomer(){
        String name, gender, age, pin = "";
        Scanner scan = new Scanner(System.in);
        while (true) {            
            System.out.print("Please enter your name: ");
            name = scan.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty");
                System.out.println();
                continue;
            } else if(name.length() > 15){
                System.out.println("Name length cannot exceed 15 characters");
                System.out.println();
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Please enter your gender: ");
            gender = scan.nextLine().trim();
            if (gender.isEmpty()) {
                System.out.println("Gender cannot be empty");
                System.out.println();
                continue;
            } else if (!gender.toUpperCase().equals("M") && !gender.toUpperCase().equals("F")) {
                System.out.println("Gender must be either M or F");
                System.out.println();
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Please enter your age: ");
            age = scan.nextLine().trim();
            if (age.isEmpty()) {
                System.out.println("Age cannot be empty");
                System.out.println();
                continue;
            }

            try{
                int i = Integer.parseInt(age);
                if(i < 0){
                    System.out.println("Age must be greater or equal to 0");
                    System.out.println();
                    continue;
                }
            }catch (Exception e){
                System.out.println("Error: The age is invalid");
                System.out.println();
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Please enter your pin: ");
            pin = scan.nextLine().trim();
            if (pin.isEmpty()) {
                System.out.println("Pin cannot be empty");
                System.out.println();
                continue;
            }

            try{
                int p = Integer.parseInt(pin);
                if(p < 0){
                    System.out.println("Pin must be greater or equal to 0");
                    System.out.println();
                    continue;
                }
            }catch (Exception e){
                System.out.println("Error: The pin is invalid");
                System.out.println();
                continue;
            }
            break;
        }        
        BankingSystem.newCustomer(name, gender.toUpperCase(), age, pin);
        System.out.println("You customer id is " + BankingSystem.getCustomerId());
    }

    private static void login(){
        Scanner scan = new Scanner(System.in);
        while(true) {
            while (true) {
                System.out.print("Please enter your customer id: ");
                customerId = scan.nextLine().trim();
                if (customerId.isEmpty()) {
                    System.out.println("Customer id cannot be empty");
                    System.out.println();
                    continue;
                }
    
                try{
                    Integer.parseInt(customerId);
                }catch (Exception e){
                    System.out.println("Error: The customer id is invalid");
                    System.out.println();
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("Please enter your pin: ");
                customerPin = scan.nextLine().trim();
                if (customerPin.isEmpty()) {
                    System.out.println("Pin cannot be empty");
                    System.out.println();
                    continue;
                }
    
                try{
                    Integer.parseInt(customerPin);
                }catch (Exception e){
                    System.out.println("Error: The pin is invalid");
                    System.out.println();
                    continue;
                }
                break;
            }

            if (customerId.equals("0") && customerPin.equals("0")) {
                administratorMenu();
                break;
            }else if(!BankingSystem.authenticate(customerId, customerPin)) {
                System.out.println("The id or pin is not correct");
                System.out.println();
                continue;
            }else {
                currentUserId = customerId;
                System.out.println();
                customerMenu();
                break;
                }
            }
        }

    public static void customerMenu(){
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        
        while(choice != 7) {
            System.out.println("1.     Open Account");
            System.out.println("2.     Close Account");
            System.out.println("3.     Deposit");
            System.out.println("4.     Withdraw");
            System.out.println("5.     Transfer");
            System.out.println("6.     Account Summary");
            System.out.println("7.     Exit");
            System.out.print("Please enter a number: ");

            while (!scan.hasNextInt()) {
                System.out.print("Invalid choice. Please enter a number:  ");
                scan.next();
            }

            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    openAccount();
                    System.out.println();
                    break;
                case 2:
                    closeAccount();
                    System.out.println();
                    break;
                case 3:
                    deposit();
                    System.out.println();
                    break;
                case 4:
                    withdraw();
                    System.out.println();
                    break;
                case 5:
                    transfer();
                    System.out.println();
                    break;
                case 6:
                    accountSummary();
                    System.out.println();
                    break;
                case 7:
                    System.out.println();
                    break;
                default:
                     System.out.println("Invalid option. Please enter a number: ");
                     System.out.println();
                     break;
            }
        }
    }

    private static void openAccount(){
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("Please enter your customer id: ");
            customerId = scan.nextLine().trim();
            if (customerId.isEmpty()) {
                System.out.println("customerId cannot be empty");
                System.out.println();
                continue;
            }

            try{
                Integer.parseInt(customerId);
            }catch (Exception e){
                System.out.println("Error: The customer id is invalid");
                System.out.println();
                continue;
            }

            if(BankingSystem.validateCustomerId(customerId)){
                break;
            }else{
                System.out.println("The customer id does not exist");
                System.out.println();
            }
        }

        while(true) {
            System.out.print("Please enter the account type: ");
            accountType = scan.next().toUpperCase();
            if(!accountType.equals("C") && !accountType.equals("S")) {
                System.out.println("Error: Account type must be either C or S");
                System.out.println();
                continue;
            }
           break;
        }

        while(true) {
            System.out.print("Please enter your initial deposit: ");
            amount = scan.next();
            try{
                int b = Integer.parseInt(amount);
                if(b < 0){
                    System.out.println("deposit must be greater or equal to 0");
                    System.out.println();
                    continue;
                }
            }catch (Exception e){
                System.out.println("Error: The deposit you entered is invalid");
                System.out.println();
                continue;
            }
            break;
        }
        BankingSystem.openAccount(customerId, accountType, amount);
        System.out.println("The new account number is: " + BankingSystem.getAccountNum());
    }

    private static void closeAccount(){
        Scanner scan = new Scanner(System.in);
        while(true) {
             System.out.print("Please enter your account number: ");
             accountNum = scan.nextLine().trim();
             if (accountNum.isEmpty()) {
                 System.out.println("Account number cannot be empty");
                 System.out.println();
                 continue;
             }

             try{
                 Integer.parseInt(accountNum);
             }catch (Exception e){
                 System.out.println("Error: The account number is invalid ");
                 System.out.println();
                 continue;
             }

            if(!BankingSystem.validateAccountNum(accountNum)){
                System.out.println("The account is either closed or does not exist");
                System.out.println();
            }else if(BankingSystem.validatePermission(currentUserId, accountNum)){
                BankingSystem.closeAccount(accountNum);
                System.out.println("The account has been closed succesfully");
                break;
            }else{
                System.out.println("You can not close someone else’s account.");
                System.out.println();
            }
        }
    }

    private static void deposit(){
        Scanner scan = new Scanner(System.in);
        while(true) {
           System.out.print("Please enter an account number: ");
           accountNum = scan.nextLine().trim();
           if (accountNum.isEmpty()) {
               System.out.println("Account number cannot be empty");
               System.out.println();
               continue;
           }

            try{
                Integer.parseInt(accountNum);
            }catch (Exception e){
                System.out.println("Error: The account number is invalid ");
                System.out.println();
                continue;
            }
            if(!BankingSystem.validateAccountNum(accountNum)){
                System.out.println("The account is either closed or does not exist");
                System.out.println();
                continue;
            }    
            break;
        }

       while(true){
           System.out.print("Please enter the amount you want to deposit: ");
           amount = scan.next();

           try{
               int i = Integer.parseInt(amount);
               if(i <= 0){
                    System.out.println("Deposit must be greater than 0");
                    System.out.println();
                    continue;
               }
           }catch (Exception e){
               System.out.println("Error: The deposit amount is invalid ");
               continue;
           }
           break;
       }
       BankingSystem.deposit(accountNum, amount);
   }

    private static void withdraw() {
        Scanner scan = new Scanner(System.in);

        while (true){
            System.out.print("Please enter your account number: ");
            accountNum = scan.nextLine().trim();
            if (accountNum.isEmpty()) {
                System.out.println("Account number cannot be empty");
                System.out.println();
                continue;
            }
 
            try{
                Integer.parseInt(accountNum);
            }catch (Exception e){
                System.out.println("Error: the account number is invalid");
                System.out.println();
                continue;
            }

            if(!BankingSystem.validateAccountNum(accountNum)){
                System.out.println("The account number is either closed or does not exist. ");
                System.out.println();
                continue;
            }

            if(!BankingSystem.validatePermission(currentUserId, accountNum)){
                System.out.println("You can't withdraw from someone else's account");
                System.out.println();
                continue;
            }
            break;
        } 

        while (true){
            System.out.print("Please enter the amount you want to withdraw: ");
            amount = scan.next();
            try{
                int i = Integer.parseInt(amount);
                if(i <= 0){
                    System.out.println("Withdraw amount must be greater than 0");
                    continue;
                }    
            }catch (Exception e){
                System.out.println("Error: Withdraw amount is invalid");
                System.out.println();
                continue;
            }
            break;
        }
        BankingSystem.withdraw(accountNum, amount);
    }

    private static void transfer(){
        Scanner scan = new Scanner(System.in);

        String srcAccNum, destAccNum = "";
        while(true){
            System.out.print("Please enter your account number: ");
            srcAccNum = scan.nextLine().trim();
            if (srcAccNum.isEmpty()) {
                System.out.println("The source account number cannot be empty");
                System.out.println();
                continue;
            }
 
            try{
                Integer.parseInt(srcAccNum);
            }catch (Exception e){
                System.out.println("The source account number is invalid");
                System.out.println();
                continue;
            }

            if(!BankingSystem.validateAccountNum(srcAccNum)){
                System.out.println("The source account number is either closed or not does not exist. ");
                System.out.println();
                continue;
            }
            if(!BankingSystem.validatePermission(currentUserId, srcAccNum)){
                System.out.println("You can't transfer money from someone else's account.  ");
                System.out.println();
                continue;
            }
            break;
        }

        while(true){
            System.out.print("Please enter the destination account: ");

            destAccNum = scan.nextLine().trim();
            if (destAccNum.isEmpty()) {
                System.out.println("The destination account number cannot be empty");
                System.out.println();
                continue;
            }
            
            try {
                Integer.parseInt(destAccNum);
            }catch (Exception e){
                System.out.println("Error: The destination account number is invalid");
                System.out.println();
                continue;
            }

            if(!BankingSystem.validateAccountNum(destAccNum)){
                System.out.println("The destination account number is either closed or does not exist: ");
                System.out.println();
                continue;
            }
            break;
        }

        while(true){
            System.out.print("Please enter the amount you want to transfer: ");
            amount = scan.next();
            try{
                int i = Integer.parseInt(amount);
                if(i <= 0){
                    System.out.println("Transfer amount must be greater than 0");
                    System.out.println();
                    continue;
                }
            }catch (Exception e){
                System.out.println("Error: The transfer amount is invalid");
                System.out.println();
                continue;
            }
            break;
        }
        BankingSystem.transfer(srcAccNum, destAccNum, amount);
    } 

    private static void accountSummary(){
        System.out.println();
        BankingSystem.accountSummary(currentUserId);
    }

    private static void administratorMenu(){
        Scanner scan = new Scanner(System.in);
        
        int choice = 0;
        while(choice != 4) {
            System.out.println("1.   Account Summary");
            System.out.println("2.   Report A");
            System.out.println("3.   Report B");
            System.out.println("4.   Exit");
            System.out.print("Please enter a number: ");

            while (!scan.hasNextInt()) {
                System.out.print("Invalid input. Please enter a number: ");
                scan.next(); // skip the invalid input
            }
            choice = scan.nextInt();
            switch (choice) {
                case 1:
                    while (true) {
                        scan = new Scanner(System.in);
                        System.out.print("Please enter the customer id: ");

                        customerId = scan.nextLine().trim();
                        if (customerId.isEmpty()) {
                            System.out.println("customerId cannot be empty");
                            System.out.println();
                            continue;
                        }
            
                        try{
                            Integer.parseInt(customerId);
                        }catch (Exception e){
                            System.out.println("Error: The customer id is invalid");
                            System.out.println();
                            continue;
                        }
            
                        if(!BankingSystem.validateCustomerId(customerId)){
                            System.out.println("The customer id does not exist");
                            System.out.println();
                            continue;
                        }else{
                            break;
                        }
                    }
                    BankingSystem.accountSummary(customerId);
                    System.out.println();
                    break;
                case 2:
                    BankingSystem.reportA();
                    System.out.println();
                    break;
                case 3:
                    scan = new Scanner(System.in);
                    String minAge, maxAge = "";

                    while (true) {
                        System.out.print("Please enter the min age: ");
                        minAge = scan.nextLine().trim();
                        if (minAge.isEmpty()) {
                            System.out.println("Min age cannot be empty");
                            System.out.println();
                            continue;
                        }

                        try{
                            int i = Integer.parseInt(minAge);
                            if(i <= 0){
                                 System.out.println("The min age must be greater than or equal to 0");
                                 System.out.println();
                                 continue;
                            }
                        }catch (Exception e){
                            System.out.println("Error: the min age is invalid");
                            System.out.println();
                            continue;
                        }
                        break;
                    }

                    while (true) {
                        System.out.print("Please enter the max age: ");
                        maxAge = scan.nextLine().trim();
                        if (maxAge.isEmpty()) {
                            System.out.println("Max age cannot be empty");
                            System.out.println();
                            continue;
                        }

                        try{
                            int i = Integer.parseInt(maxAge);
                            if(i <= 0){
                                 System.out.println("The max age must be greater than or equal to 0");
                                 System.out.println();
                                 continue;
                            }
                        }catch (Exception e){
                            System.out.println("Error: the max age is invalid");
                            System.out.println();
                            continue;
                        }
                        break;
                    }
                    BankingSystem.reportB(minAge, maxAge);
                    System.out.println();
                    break;
                case 4:
                    break; //exit
                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 4: ");
                    System.out.println();
                    break;
            }
        }
    }
}
