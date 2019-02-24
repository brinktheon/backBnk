package ru.chris.services;

import ru.chris.model.InfoTable;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean checkUser(String username, String password);

    boolean saveUser(String username, String password, String email);

    List<String> getUserInfo();

    Set<InfoTable> createInfo();

    List<List<String>> getDataForTable();

    List<String> getSomeInfo();

    boolean doDonation(String amount, String password);

    boolean doWithdraw(String amount, String password);

    boolean doPayment(String otherNumber, String amount, String password);

    int cnt();

    boolean doDeposit(int var, String amount, String password);
}
