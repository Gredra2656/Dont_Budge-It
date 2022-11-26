# Don't Budge It

## A simple budget allocation software

### Story
This project arose as a result of my poor financial management. After scrambling around at the end of
every month worrying about whether I can afford to pay all my bills, and getting fed up with all the
solutions I found recommended on the web, I figured that if I can't build the solution myself, then
it's a lost cause.


### Goals
The goal here is to create a budgeting application that simplifies the organization of your finances.
By allowing you to simply input income and expenses, and having the application present the breakdown of your
finances in a visually comprehensible manner. The hope is that this application
can help students, individuals, and anybody like me manage their finance despite their constant worrying.
Opening the application, and at a glance being able to change their incoming funds, or add an emergency expense,
perhaps even allowing you to set a savings goal, with deposits recommended by the application. While this can obviously
be used by anyone, it will focus on financial management for individuals, unless the opportunity presents itself 
to add additional functionality.

TLDR; A budget manager that is:
- *Simple*
- *Fast*
- *Organized*

### User Stories
These are some descriptions of future functionality that should be added:

1. As a user, I want to be able to update my bank balance quickly - DONE
2. As a user, I want to be able to add income sources, and have them represented as a lump sum, *while also* being 
able to see the breakdown - DONE
3. As a user, I want to be able to add expenses to my expense record that subtract from my income. - DONE
4. As a user, I want to be able to see my surplus funds and set a savings goal (%) for the remaining funds. - DONE
5. As a user, I want to be able to categorize the income and expenses freely, even mixing the two if possible. - DONE
6. As a user, I want to be able to set interest rates on debt that I hold and see the effects of paying off early. - DONE
7. As a user, I want monthly receipts showing my financial history. - DONE
8. As a user, I want to be able to add individual expenses that I want to record for future reference. - DONE
9. As a user, I want to be able to plan long term savings goals with interest. - DONE
10. As a user, I want the values to be stored securely, perhaps accessed by PIN/Pass.
11. As a user, I want to be able to save my budget so that I can edit it later. - DONE
12. As a user, I want to be able to open my old budget to pick up where I left off. - DONE
13. As a user, I would like to save my budget for the month to a csv/excel spreadsheet.

## Grading Instructions
1. My first event related to adding x's to a y is demonstrated in the debt management buttons. If you select the "Add"
button below the debt management graph, you can construct a debt account which is then added to the main user account.
By mousing over the column "Current Balance" you can see displayed the current balance of the debt. Then, when you mouse
over the column "Future Balance" you can see the balance that the debt will be next month with interest applied.
2. My second event related to adding x's to a y is demonstrated in the debt management buttons. If you select the 
"Pay Balance" button below the debt management graph, you can enter the name of one of your debtAccounts and enter the
amount you would like to pay off of that debt.
3. For further demonstration of x to a y, look at the source graph, where the add and remove buttons allow you to modify
the sources attached to the account
4. You can locate the visual components by launching the application and adding various elements through add buttons. 
When you add an element to the account it is automatically refreshed and displayed. Should this not function as expected
please push control-R, or go to the Chart menu and select refresh. The graphs and the mascot are my visual components.
5. You can save the state of the application by going to the file menu tab and selecting save. Alternatively, control-S.
6. You can load the state of the application by going to the file menu tab and selecting load. Alternatively, control-F.

**NOTE !! For elements like removing a source, removing/paying debt, etc. the name is case sensitive!!**

## Phase 4: Task 2
Fri Nov 25 18:19:06 PST 2022  
Source added with name: Feeed, and modify balance: 30000  
Fri Nov 25 18:19:10 PST 2022  
Source removed with name: Feeed  
Fri Nov 25 18:19:19 PST 2022  
Adding a debt account with name: Credit Card, balance: 800, and interest: 0.50  
Fri Nov 25 18:19:24 PST 2022  
Could not find debt account with name: 300  
Fri Nov 25 18:19:32 PST 2022  
Successfully paid 300 from Credit Card's debt account balance  
Fri Nov 25 18:19:40 PST 2022  
Removing a debt account with name: Credit Card  
Fri Nov 25 18:19:46 PST 2022  
30000 added to account balance  
Fri Nov 25 18:19:49 PST 2022  
10000 withdrawn from account balance  
Fri Nov 25 18:19:52 PST 2022  
Deposited 5000 to savings account  
Fri Nov 25 18:19:55 PST 2022  
Withdrew 50 from savings account  

## Phase 4: Task 3

- I would have liked to refactor all my inner classes of BudgeItUI to their own classes
- I would have liked to refactor the duplicate methods that are interspersed through Account
, Source, SavingsAcc and DebtAcc
- I likely could have refactored SavingsAcc and DebtAcc to have HasInterest be an abstract class
instead of an interface. Would have made it easier to globally modify.
- I almost certainly could have made an inner abstract class to be used by the button panels. This
would make future editing easier as well.
- I should have removed the old text ui to make the UML graph look cleaner.
- There was a lot of stuff related to saving and loading in the classes being written to a JSON file. I could have
abstracted some commonality to make readability better.