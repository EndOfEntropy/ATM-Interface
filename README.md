# ATM-Interface
This project is an ATM program that allow individuals to perform a range of financial transactions without the need for human assistance. 
In this ATM program, the user has to select an operation from the options displayed on the screen.
This ATM program offers a wide array of services such as: the deposit of funds, the withdrawal of funds, check balances, transfer of funds between owned accounts, transfer of funds to another customer.
The first version of the program is a console based-application (Atm.java) that uses the Java Standard Edition libraries. The second version of the ATM application (GuiAtm.java) comes with a Graphical User Interface for more convinience and requires the Swing and Miglayout libraries.
http://www.miglayout.com/

# Functionalities
## ATM operations:
  - The customer can deposit funds
  - The customer can withdraw funds
  - The customer can check his account balance
  - The customer can transfer funds between his accounts
  - The customer can transfer funds to another customer using the account uid

## Disclaimers:
This project is not for production and comes with a few caveats:
  - We will pretend that the user card is read automatically. No user input is required to identify the user access card/uid.
  - Only one customer can log in the console based-application. This means that the transactions between customers cannot be checked for completion.
  - However, this feature works in the version with the Swing Graphical User Interface application.
  - Account and holder UIDs are limited to 9 digit long as they are integer variables (+-2,147,483,647). This can be easily updated with long variables if required.
