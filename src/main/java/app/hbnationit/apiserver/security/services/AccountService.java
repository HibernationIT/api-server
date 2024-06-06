package app.hbnationit.apiserver.security.services;

import app.hbnationit.apiserver.security.models.Account;
import app.hbnationit.apiserver.security.models.dto.AddAccountRequest;
import app.hbnationit.apiserver.security.models.dto.ModifyAccountRequest;
import app.hbnationit.apiserver.security.repositories.AccountRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Page<Account> findAccounts(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Account findAccount(String email) {
        return repository.findById(email).orElseThrow(() ->
                new EntityNotFoundException("Not found Account"));
    }

    @Transactional
    public Account addAccount(AddAccountRequest dto) {
        if (repository.findById(dto.getEmail()).isPresent()) {
            throw new EntityExistsException("Account is already exist");
        }

        Account dao = Account.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
        return repository.save(dao);
    }

    @Transactional
    public Account modifyStack(String email, ModifyAccountRequest dto) {
        Account dao = repository.findById(email).orElseThrow(() ->
                new EntityNotFoundException("Not found Account"));
        dao.setName(dto.getName());
        dao.setImage(dto.getImage());

        return dao;
    }

    @Transactional
    public void removeAccount(String email) {
        repository.deleteById(email);
    }
}
