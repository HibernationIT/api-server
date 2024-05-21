package app.hbnationit.apiserver.security.services;

import app.hbnationit.apiserver.security.models.Account;
import app.hbnationit.apiserver.security.models.dto.AccountDetails;
import app.hbnationit.apiserver.security.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username).orElseThrow(() ->
                new UsernameNotFoundException("Invalid Username"));

        return new AccountDetails(account);
    }
}
