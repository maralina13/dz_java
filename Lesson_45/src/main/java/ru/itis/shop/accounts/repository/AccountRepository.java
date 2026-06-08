package ru.itis.shop.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.shop.accounts.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
