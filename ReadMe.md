#ATM

## Description 
This Service contains an ATM console program 

## Problem Statement understandable from examples.
1. Customer can perform login, deposit, transfer, withdraw, logout functionality by ATM.
2. login - logs customer account to atm , after which he can perform all other functions
3. deposit - pays debt that is owed to other customer first , if any left is deposited to bank balance of customer
4. transfer - transfer money to other customer's account, if Balance is less than amount customer the difference between bank balance and amount transferred to other customer is taken as owed money to customer to which the money is being transferred
5. Withdraw - decrease bank balance with the amount that has been withdrawn
6. logout - customer log's out from ATM.

## Assumption
- This App will be deployed on one ATM  and ATM's do not communicate to each other or a central server.
- Only one customer can login at a time.
- Customer can only withdraw amount less than equal to their balance.
- Another person cannot login if a customer is logged in already.
- To make a transaction customer has to login first.
- when customer deposit money the payable debt i.e. the debt that is owed to other customer , it gets payed randomly , if multiple payables are pending and if any left then it will be deposited to bank balance.
- Only amount less than or equal to balance can be withdrawn from service otherwise it will not withdraw any amount.
- 

## Deviation
None 