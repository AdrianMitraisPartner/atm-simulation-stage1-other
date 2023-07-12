package org.example.operations.menus;

import org.example.model.Account;
import org.example.model.TransactionHistory;
import org.example.operations.utils.ConstantsUtils;
import org.example.operations.utils.InputValidationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WithdrawMenu {

    public static void displayMenus(Scanner scanner, Account account, List<Account> accountList) {
        String[] options = {"1. $10", "2. $50", "3. $100", "4. Other", "5. Back"};
        printMenus(options);
        String selectMenu = scanner.next();
        int index = accountList.indexOf(account);
        int newBalance = 0;
        switch (selectMenu) {
            case "1" -> {
                newBalance = calculateBalance(ConstantsUtils.TEN_QUIDS, account.getBalance());
                account.setBalance(newBalance);
                accountList.get(index).setBalance(newBalance);
                addTransactionHistory(ConstantsUtils.TEN_QUIDS, account, accountList);
                SummaryMenu.displayWithdrawalSummaryMenu(ConstantsUtils.TEN_QUIDS, account, scanner, accountList);
            }
            case "2" -> {
                newBalance = calculateBalance(ConstantsUtils.FIFTY_QUIDS, account.getBalance());
                account.setBalance(newBalance);
                accountList.get(index).setBalance(newBalance);
                addTransactionHistory(ConstantsUtils.FIFTY_QUIDS, account, accountList);
                SummaryMenu.displayWithdrawalSummaryMenu(ConstantsUtils.FIFTY_QUIDS, account, scanner, accountList);
            }
            case "3" -> {
                newBalance = calculateBalance(ConstantsUtils.HUNDRED_QUIDS, account.getBalance());
                account.setBalance(newBalance);
                accountList.get(index).setBalance(newBalance);
                addTransactionHistory(ConstantsUtils.HUNDRED_QUIDS, account, accountList);
                SummaryMenu.displayWithdrawalSummaryMenu(ConstantsUtils.HUNDRED_QUIDS, account, scanner, accountList);
            }
            case "4" -> otherWithdrawalMenus(account, scanner, accountList);
            case "5" -> MainMenu.displayMenus(account, scanner, accountList);
            default -> {
                if (InputValidationUtils.isInputContainLettersAndSpecial(selectMenu) ||
                        InputValidationUtils.isInputWithinMenuCount(options.length, selectMenu)) {
                    System.out.println("Invalid Input Detected");
                }
                displayMenus(scanner, account, accountList);
            }
        }

    }

    public static void printMenus(String[] options) {

        for(int index = 0; index < options.length; index++) {
            System.out.println(options[index]);
        }
        System.out.print("Choose Option: ");
    }

    public static Integer calculateBalance(Integer withdrawAmount, Integer balance) {
        return balance - withdrawAmount;
    }

    private static void otherWithdrawalMenus(Account account, Scanner scanner, List<Account> accountList) {
        System.out.println("Other Withdraw");
        System.out.println("Enter amount to withdraw:");
        int withdrawAmount = scanner.nextInt();
        Integer accountBalance = account.getBalance();
        int index = accountList.indexOf(account);
        int newBalance = calculateBalance(withdrawAmount, accountBalance);
        account.setBalance(newBalance);
        accountList.get(index).setBalance(newBalance);
        addTransactionHistory(withdrawAmount, account, accountList);
        SummaryMenu.displayWithdrawalSummaryMenu(withdrawAmount, account, scanner, accountList);

    }

    private static void addTransactionHistory(int withdrawAmmount, Account account, List<Account> accountList) {
        List<TransactionHistory> transactionHistories = account.getTransactionHistory() == null
                || account.getTransactionHistory().isEmpty() ? new ArrayList<>() : account.getTransactionHistory();

        TransactionHistory newHistory = new TransactionHistory();
        int accountIndex = accountList.indexOf(account);
        newHistory.setAmount("-" + withdrawAmmount);
        newHistory.setTransactionDate(LocalDate.now().toString());
        newHistory.setTransactionType(ConstantsUtils.WITHDRAWAL);

        transactionHistories.add(newHistory);
        accountList.get(accountIndex).setTransactionHistory(transactionHistories);
        account.setTransactionHistory(transactionHistories);
    }
}