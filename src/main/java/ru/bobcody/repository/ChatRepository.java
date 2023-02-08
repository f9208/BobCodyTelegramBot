package ru.bobcody.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bobcody.domain.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
