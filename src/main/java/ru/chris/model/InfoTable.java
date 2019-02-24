package ru.chris.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "info_table")
public class InfoTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_info")
    @SequenceGenerator(name="seq_info", sequenceName="seq_info", allocationSize=1)
    private long id;

    @Column(name = "tdate")
    private String tdate;

    @Column(name = "code")
    private int code;

    @Column(name = "description")
    private String description;

    @Column(name = "credit")
    private int credit;

    @Column(name = "debit")
    private int debit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acc_id", referencedColumnName="id", nullable=false)
    private AccountNumbers accNumber;

    public InfoTable() {
    }

    public InfoTable(String tdate, int code, String description, int credit, int debit) {
        this.tdate = tdate;
        this.code = code;
        this.description = description;
        this.credit = credit;
        this.debit = debit;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public AccountNumbers getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(AccountNumbers accNumber) {
        this.accNumber = accNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}















