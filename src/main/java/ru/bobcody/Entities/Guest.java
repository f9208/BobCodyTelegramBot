package ru.bobcody.Entities;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "guests")
public class Guest {
    @Getter
    @Setter
    @Id
    private Long id;
    @Setter
    @Getter
    @Column(name = "first_name")
    private String firstName;
    @Setter
    @Getter
    @Column(name = "last_name")
    private String lastName;
    @Setter
    @Getter
    @Column(name = "user_name")
    private String userName;
    @Getter
    @Setter
    @Column(name = "language_code")
    private String languageCode;
    @Getter
    @Setter
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<QuoteEntityAbyss> quoteEntityAbysses;
    @Getter
    @Setter
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    List<InputMessage> inputMessages;
    @Getter
    @Setter
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    List<QuoteEntityStorage> quoteEntityStorages;

    public Guest(User user) {
        this.id = Long.valueOf(user.getId());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUserName();
        this.languageCode = user.getLanguageCode();

    }

    public Guest() {
    }

    @Override
    public String toString() {
        return userName + " "
                + lastName + " "
                + firstName + " "
                + id + " "
                + languageCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(getId(), guest.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
