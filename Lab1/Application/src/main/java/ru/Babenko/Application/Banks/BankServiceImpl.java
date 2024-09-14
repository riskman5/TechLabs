package ru.babenko.application.banks;

import org.springframework.stereotype.Service;
import ru.babenko.contracts.banks.BankOperationsResultType;
import ru.babenko.contracts.banks.BankService;
import ru.babenko.contracts.current.CurrentUserService;
import ru.babenko.models.bankAccounts.builders.CreditBankAccountBuilder;
import ru.babenko.models.bankAccounts.builders.DebitBankAccountBuilder;
import ru.babenko.models.bankAccounts.builders.DepositBankAccountBuilder;
import ru.babenko.models.bankAccountsOperations.BankAccountOperation;
import ru.babenko.models.banks.BankImpl;
import ru.babenko.models.users.BankWorker;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepository;
import ru.babenko.repositories.bankAccountsOperationsRepositories.BankAccountsOperationsRepositoryResultType;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepositoriesResultType;
import ru.babenko.repositories.bankAccountsRepositories.BankAccountsRepository;
import ru.babenko.repositories.usersRepositories.UsersRepositoriesResultType;
import ru.babenko.repositories.usersRepositories.UsersRepository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Service
public class BankServiceImpl implements BankService {
    private final CurrentUserService currentUser;
    private final BankAccountsOperationsRepository bankAccountsOperationsRepository;
    private final UsersRepository usersRepository;
    private final BankAccountsRepository bankAccountsRepository;

    public BankServiceImpl(CurrentUserService currentUser, BankAccountsOperationsRepository bankAccountsOperationsRepository, UsersRepository usersRepository, BankAccountsRepository bankAccountsRepository) {
        this.currentUser = currentUser;
        this.bankAccountsOperationsRepository = bankAccountsOperationsRepository;
        this.usersRepository = usersRepository;
        this.bankAccountsRepository = bankAccountsRepository;
    }

    @Override
    public BankOperationsResultType setInterestRate(BigDecimal interestRate) {
        var bank = currentUser.getUser().get().getUsersBank();

        if (bank instanceof BankImpl bankImpl) {
            return bankImpl.setInterestRate(interestRate);
        }

        return new BankOperationsResultType.Failure("You don't have permission to set interest rate");
    }

    @Override
    public BankOperationsResultType setCommission(BigDecimal commission) {
        var bank = currentUser.getUser().get().getUsersBank();

        if (bank instanceof BankImpl bankImpl) {
            return bankImpl.setCommission(commission);
        }

        return new BankOperationsResultType.Failure("You don't have permission to set commission");
    }

    @Override
    public BankOperationsResultType setTransferLimit(BigDecimal transferLimit) {
        var bank = currentUser.getUser().get().getUsersBank();

        if (bank instanceof BankImpl bankImpl) {
            return bankImpl.SetTransferLimit(transferLimit);
        }

        return new BankOperationsResultType.Failure("You don't have permission to set transfer limit");
    }

    @Override
    public BankOperationsResultType setUnverifiedAccountWithdrawLimit(BigDecimal limit) {
        var bank = currentUser.getUser().get().getUsersBank();

        if (bank instanceof BankImpl bankImpl) {
            return bankImpl.SetUnverifiedAccountWithdrawLimit(limit);
        }
    
        return new BankOperationsResultType.Failure("You don't have permission to set unverified account withdraw limit");
    }

    @Override
    public BankOperationsResultType setUnverifiedAccountTransferLimit(BigDecimal limit) {
        var bank = currentUser.getUser().get().getUsersBank();

        if (bank instanceof BankImpl bankImpl) {
            return bankImpl.SetUnverifiedAccountTransferLimit(limit);
        }

        return new BankOperationsResultType.Failure("You don't have permission to set unverified account transfer limit");
    }

    @Override
    public BankOperationsResultType getBankAccountOperationsByBankAccountNumber(BigInteger bankAccountNumber) {
        var result = bankAccountsOperationsRepository.findOperationsByAccountNumber(bankAccountNumber);

        if (result instanceof BankAccountsOperationsRepositoryResultType.SuccessWithOperations successResult) {
            return new BankOperationsResultType.SuccessWithOperations(successResult.operations());
        }
        else if (result instanceof BankAccountsOperationsRepositoryResultType.Failure failureResult) {
            return new BankOperationsResultType.Failure(failureResult.message());
        }
        return new BankOperationsResultType.Failure("Something went wrong. Please try again.");
    }

    @Override
    public BankOperationsResultType declineOperationById(UUID operationId) {
        var result = bankAccountsOperationsRepository.findOperationsById(operationId);

        if (result instanceof BankAccountsOperationsRepositoryResultType.SuccessWithOperation successResult) {
            var operation = successResult.operation();
            operation.revertOperation();
            operation.getConjugateOperations().forEach(BankAccountOperation::revertOperation);
            return new BankOperationsResultType.Success();
        } else if (result instanceof BankAccountsOperationsRepositoryResultType.NotFound) {
            return new BankOperationsResultType.Failure("Operation not found");
        }
        return new BankOperationsResultType.Failure("Something went wrong. Please try again.");
    }

    @Override
    public BankOperationsResultType registerBankUser(String name, String surname) {
        var user = currentUser.getUser().get();

        var bank = user.getUsersBank();

        if (bank instanceof BankImpl bankImpl) {
            var bankUser = bankImpl.CreateUser(name, surname);
            var result = usersRepository.addUser(bankUser);

            if (result instanceof UsersRepositoriesResultType.Success) {
                return new BankOperationsResultType.Success();
            }
            else if (result instanceof UsersRepositoriesResultType.Failure failureResult) {
                return new BankOperationsResultType.Failure(failureResult.message());
            }
            return new BankOperationsResultType.Failure("Something went wrong. Please try again.");
        }

        return new BankOperationsResultType.Failure("You don't have permission to register bank user");
    }

    @Override
    public BankOperationsResultType createDebitBankAccount(DebitBankAccountBuilder builder) {
        if (currentUser.getUser().get() instanceof BankWorker bankWorker) {
            var bank = bankWorker.getUsersBank();
            if (bank instanceof BankImpl bankImpl) {
                var unverifiedTransferLimit = bankImpl.GetUnverifiedAccountTransferLimit();
                var unverifiedWithdrawLimit = bankImpl.GetUnverifiedAccountWithdrawLimit();

                unverifiedTransferLimit.ifPresent(builder::withUnverifiedAccountTransferLimit);
                unverifiedWithdrawLimit.ifPresent(builder::withUnverifiedAccountWithdrawLimit);

                var bankAccount = builder
                        .withAccountNumber(bankAccountsRepository.getCurrentFreeAccountNumber())
                        .withBank(bank)
                        .build();

                var result = bankAccountsRepository.addBankAccount(bankAccount);

                if (result instanceof BankAccountsRepositoriesResultType.Success) {
                    return new BankOperationsResultType.SuccessWithBankAccount(bankAccount);
                }
                else if (result instanceof BankAccountsRepositoriesResultType.Failure failureResult) {
                    return new BankOperationsResultType.Failure(failureResult.message());
                }
                return new BankOperationsResultType.Failure("Something went wrong. Please try again.");
            }
        }

        return new BankOperationsResultType.Failure("You don't have permission to create debit bank account");
    }

    @Override
    public BankOperationsResultType createCreditBankAccount(CreditBankAccountBuilder builder) {
        if (currentUser.getUser().get() instanceof BankWorker bankWorker) {
            var bank = bankWorker.getUsersBank();
            if (bank instanceof BankImpl bankImpl) {
                var unverifiedTransferLimit = bankImpl.GetUnverifiedAccountTransferLimit();
                var unverifiedWithdrawLimit = bankImpl.GetUnverifiedAccountWithdrawLimit();
                var yearlyCommission = bankImpl.GetCommission();

                unverifiedTransferLimit.ifPresent(builder::withUnverifiedAccountTransferLimit);
                unverifiedWithdrawLimit.ifPresent(builder::withUnverifiedAccountWithdrawLimit);
                yearlyCommission.ifPresent(builder::withYearlyCommission);

                var bankAccount = builder
                        .withAccountNumber(bankAccountsRepository.getCurrentFreeAccountNumber())
                        .withBank(bank)
                        .build();

                var result = bankAccountsRepository.addBankAccount(bankAccount);

                if (result instanceof BankAccountsRepositoriesResultType.Success) {
                    return new BankOperationsResultType.SuccessWithBankAccount(bankAccount);
                }
                else if (result instanceof BankAccountsRepositoriesResultType.Failure failureResult) {
                    return new BankOperationsResultType.Failure(failureResult.message());
                }
                return new BankOperationsResultType.Failure("Something went wrong. Please try again.");
            }
        }

        return new BankOperationsResultType.Failure("You don't have permission to create credit bank account");
    }

    @Override
    public BankOperationsResultType createDepositBankAccount(DepositBankAccountBuilder builder) {
        if (currentUser.getUser().get() instanceof BankWorker bankWorker) {
            var bank = bankWorker.getUsersBank();
            if (bank instanceof BankImpl bankImpl) {
                var unverifiedTransferLimit = bankImpl.GetUnverifiedAccountTransferLimit();
                var unverifiedWithdrawLimit = bankImpl.GetUnverifiedAccountWithdrawLimit();
                var yearlyInterestRate = bankImpl.GetInterestRate();

                unverifiedTransferLimit.ifPresent(builder::withUnverifiedAccountTransferLimit);
                unverifiedWithdrawLimit.ifPresent(builder::withUnverifiedAccountWithdrawLimit);
                yearlyInterestRate.ifPresent(builder::withYearlyInterestRate);

                var bankAccount = builder
                        .withAccountNumber(bankAccountsRepository.getCurrentFreeAccountNumber())
                        .withBank(bank)
                        .build();

                var result = bankAccountsRepository.addBankAccount(bankAccount);

                if (result instanceof BankAccountsRepositoriesResultType.Success) {
                    return new BankOperationsResultType.SuccessWithBankAccount(bankAccount);
                }
                else if (result instanceof BankAccountsRepositoriesResultType.Failure failureResult) {
                    return new BankOperationsResultType.Failure(failureResult.message());
                }
                return new BankOperationsResultType.Failure("Something went wrong. Please try again.");
            }
        }

        return new BankOperationsResultType.Failure("You don't have permission to create debit bank account");
    }
}
