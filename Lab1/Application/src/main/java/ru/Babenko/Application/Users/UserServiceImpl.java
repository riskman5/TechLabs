package ru.babenko.application.users;

import org.springframework.stereotype.Service;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.banks.BankService;
import ru.babenko.contracts.current.CurrentBankAccountService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.contracts.users.UserOperationsResultType;
import ru.babenko.contracts.users.UserService;
import ru.babenko.models.bankAccounts.builders.CreditBankAccountBuilder;
import ru.babenko.models.bankAccounts.builders.DebitBankAccountBuilder;
import ru.babenko.models.bankAccounts.builders.DepositBankAccountBuilder;
import ru.babenko.models.users.BankUser;

@Service
public class UserServiceImpl implements UserService {
    private final CurrentUserService currentUser;
    private final CurrentBankAccountService currentBankAccount;
    private final BankService bankService;

    public UserServiceImpl(CurrentUserService currentUser, CurrentBankAccountService currentBankAccount, BankService bankService) {
        this.currentUser = currentUser;
        this.currentBankAccount = currentBankAccount;
        this.bankService = bankService;
    }

    @Override
    public UserOperationsResultType addAddress(String address) {
        currentUser.getUser().get().setAddress(address);
        return new UserOperationsResultType.Success();
    }

    @Override
    public UserOperationsResultType addPassport(Integer passportSeries, Integer passportNumber) {
        currentUser.getUser().get().setPassportSeries(passportSeries);
        currentUser.getUser().get().setPassportNumber(passportNumber);

        verifyBankAccounts();
        return new UserOperationsResultType.Success();
    }

    @Override
    public UserOperationsResultType addPhoneNumber(String phoneNumber) {
        currentUser.getUser().get().setPhoneNumber(phoneNumber);
        return new UserOperationsResultType.Success();
    }

    @Override
    public UserOperationsResultType chooseBankAccount(Integer bankAccountId) {
        if (currentUser.getUser().get() instanceof BankUser bankUser) {
            var bankAccounts = bankUser.getBankAccounts();

            for (var bankAccount : bankAccounts) {
                if (bankAccount.getAccountNumber().equals(bankAccountId)) {
                    currentBankAccount.setBankAccount(bankAccount);
                    return new UserOperationsResultType.Success();
                }
            }

            return new UserOperationsResultType.Failure("Bank account not found");
        }

        return new UserOperationsResultType.Failure("User is not a bank user");
    }

    @Override
    public UserOperationsResultType getBankAccounts() {
        if (currentUser.getUser().get() instanceof BankUser bankUser) {
            var bankAccounts = bankUser.getBankAccounts();
            return new UserOperationsResultType.SuccessWithBankAccounts(bankAccounts);
        }

        return new UserOperationsResultType.Failure("User is not a bank user");
    }

    @Override
    public UserOperationsResultType createDebitBankAccount() {
        if (currentUser.getUser().get() instanceof BankUser bankUser) {
            var bank = bankUser.getUsersBank();
            var bankAccount = new DebitBankAccountBuilder()
                    .withBank(bank)
                    .withBankUser(bankUser);

            var result = bankService.createDebitBankAccount(bankAccount);

            if (result instanceof BankOperationsResultType.SuccessWithBankAccount successResult) {
                bankUser.addBankAccount(successResult.bankAccount());
                return new UserOperationsResultType.Success();
            } else if (result instanceof BankOperationsResultType.Failure failureResult) {
                return new UserOperationsResultType.Failure(failureResult.message());
            }
            return new UserOperationsResultType.Failure("Something went wrong. Please try again.");
        }

        return new UserOperationsResultType.Failure("User is not a bank user");
    }

    @Override
    public UserOperationsResultType createCreditBankAccount() {
        if (currentUser.getUser().get() instanceof BankUser bankUser) {
            var bank = bankUser.getUsersBank();
            var bankAccount = new CreditBankAccountBuilder()
                    .withBank(bank)
                    .withBankUser(bankUser);

            var result = bankService.createCreditBankAccount(bankAccount);

            if (result instanceof BankOperationsResultType.SuccessWithBankAccount successResult) {
                bankUser.addBankAccount(successResult.bankAccount());
                return new UserOperationsResultType.Success();
            } else if (result instanceof BankOperationsResultType.Failure failureResult) {
                return new UserOperationsResultType.Failure(failureResult.message());
            }
            return new UserOperationsResultType.Failure("Something went wrong. Please try again.");
        }

        return new UserOperationsResultType.Failure("User is not a bank user");
    }

    @Override
    public UserOperationsResultType createDepositBankAccount() {
        if (currentUser.getUser().get() instanceof BankUser bankUser) {
            var bank = bankUser.getUsersBank();
            var bankAccount = new DepositBankAccountBuilder()
                    .withBank(bank)
                    .withBankUser(bankUser);

            var result = bankService.createDepositBankAccount(bankAccount);

            if (result instanceof BankOperationsResultType.SuccessWithBankAccount successResult) {
                bankUser.addBankAccount(successResult.bankAccount());
                return new UserOperationsResultType.Success();
            } else if (result instanceof BankOperationsResultType.Failure failureResult) {
                return new UserOperationsResultType.Failure(failureResult.message());
            }
            return new UserOperationsResultType.Failure("Something went wrong. Please try again.");
        }

        return new UserOperationsResultType.Failure("User is not a bank user");
    }

    private void verifyBankAccounts() {
        if (currentUser.getUser().get() instanceof BankUser bankUser) {
            var bankAccounts = bankUser.getBankAccounts();

            for (var bankAccount : bankAccounts) {
                bankAccount.verify();
            }
        }
    }
}
