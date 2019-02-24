package ru.chris.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "account_numbers")
public class AccountNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_acc")
    @SequenceGenerator(name="seq_acc", sequenceName="seq_acc", allocationSize=1)
    private long id;

    @Column(name = "amount")
    private int amount;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName="id", nullable=false, unique=true)
    private User user;

    @OneToMany(mappedBy = "accNumber", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<InfoTable> infoTable;

    public AccountNumbers() {
    }

    public AccountNumbers(int amount, String number) {
        this.amount = amount;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<InfoTable> getInfoTable() {
        return infoTable;
    }

    public void setInfoTable(Set<InfoTable> infoTable) {
        this.infoTable = infoTable;
    }
}
