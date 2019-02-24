package ru.chris.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chris.model.AccountNumbers;
import ru.chris.model.InfoTable;
import ru.chris.model.Role;
import ru.chris.model.User;
import ru.chris.operation.Code;
import ru.chris.operation.Message;
import ru.chris.repositories.AccountNumberRepository;
import ru.chris.repositories.InfoTableRepository;
import ru.chris.repositories.RoleRepository;
import ru.chris.repositories.UserRepository;
import ru.chris.services.UserService;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AccountNumberRepository accountNumbers;

    private final InfoTableRepository infoTableRepository;

    private User localUser;

    private String pattern  = "yyyy-MM-dd HH:mm:ss";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    private int baseAccNumber = 100000000;

    private int rate = 1;
    private int counter = 1;
    private int localWorkerAmount = 1;
    private long wTime = 0;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AccountNumberRepository accountNumbers, InfoTableRepository infoTableRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.accountNumbers = accountNumbers;
        this.infoTableRepository = infoTableRepository;
    }

    @Override
    public boolean checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null){
            localUser = user;
            return true;
        }
        return false;
    }

    @Override
    public boolean saveUser(String username, String password, String email) {
        int localAccNumber = baseAccNumber + accountNumbers.findAll().size();
        String result = String.valueOf(localAccNumber);
        String part1 = result.substring(0, 3);
        String part2 = result.substring(3, 6);
        String part3 = result.substring(6, 9);
        result = part1 + "-" + part2 + "-" + part3;

        Set<Role> roles = new HashSet<>();
        User user = new User(username, password, email);
        roles.add(roleRepository.getOne(1L));
        user.setRoles(roles);

        // --add acc--
        Set<AccountNumbers> acc = new HashSet<>();
        AccountNumbers accountNumbers = new AccountNumbers(0, result);
        accountNumbers.setUser(user);
        acc.add(accountNumbers);
        user.setAccounts(acc);

        // ----
        userRepository.save(user);
        return true;
    }

    @Override
    public List<String> getUserInfo(){
        List<String> list = new ArrayList<>();
        list.add(localUser.getUsername());
        AccountNumbers ac = localUser.getAccounts().iterator().next();
        list.add(String.valueOf(ac.getAmount()));
        list.add(String.valueOf(ac.getNumber()));
        return list;
    }

    @Override
    public Set<InfoTable> createInfo(){
//        User u  = userRepository.findById(11);
//        System.out.println(u.getUsername());
        Set<InfoTable> tableSet = new HashSet<>();
//        AccountNumbers acc = u.getAccounts().iterator().next();
        AccountNumbers a = accountNumbers.findById(4L);
//        System.out.println(a.getId());
        InfoTable infoTable = new InfoTable("01/08/2019", 1000001, "spam text", 200, 0);
        InfoTable infoTable1 = new InfoTable("01/08/2019", 1000002, "spam text1", 0, 100);
        InfoTable infoTable2 = new InfoTable("01/08/2019", 1000001, "spam text2", 500, 0);
        InfoTable infoTable3 = new InfoTable("01/08/2019", 1000001, "spam text3", 100, 0);


//        list.add(infoTable1);
//        list.add(infoTable2);
//        list.add(infoTable3);


        infoTable.setAccNumber(a);
        infoTable1.setAccNumber(a);
        infoTable2.setAccNumber(a);
        infoTable3.setAccNumber(a);

//        tableSet.add(infoTable);
//        tableSet.add(infoTable1);
//        tableSet.add(infoTable2);
//        tableSet.add(infoTable3);

//        a.setInfoTable(tableSet);

        List<InfoTable> list = new ArrayList<>();
        list.add(infoTable);
        list.add(infoTable1);
        list.add(infoTable2);
        list.add(infoTable3);

        //infoTableRepository.saveAll(list);

        return tableSet;
    }
    @Override
    public List<List<String>> getDataForTable(){
        String  deb = "",
                cred = "",
                status = "";
        User u  = userRepository.getOne(localUser.getId());
        List<List<String>> tables = new ArrayList<>();
        int count = 0;
        for (InfoTable t: u.getAccounts().iterator().next().getInfoTable()) {
            tables.add(new ArrayList<>());
            tables.get(count).add(t.getTdate());
            tables.get(count).add(String.valueOf(t.getCode()));
            tables.get(count).add(t.getDescription());


            if (t.getDebit() != 0){
                deb = " - " + t.getDebit();
                status = "danger";
            } else if (t.getCredit() != 0){
                cred = " + " + t.getCredit();
                status = "success";
            }

            tables.get(count).add(deb);
            tables.get(count).add(cred);
            tables.get(count).add(status);

            deb = "";
            cred = "";
            status = "";
            ++count;
        }

        return tables;
    }

    @Override
    public List<String> getSomeInfo() {
        List<String> info = new ArrayList<>();
        info.add(localUser.getEmail());
        info.add(String.valueOf(localUser.getAccounts().iterator().next().getNumber()));
        return info;
    }

    @Override
    public boolean doDonation(String amount, String password) {

        if (localUser.getPassword().equals(password)){
            int am = localUser.getAccounts().iterator().next().getAmount();
            am += Integer.valueOf(amount);
            localUser.getAccounts().iterator().next().setAmount(am);

            // set Info
            InfoTable infoTable = new InfoTable(
                    simpleDateFormat.format(new Date()),
                    Code.DONATION,
                    Message.DONATION,
                    Integer.valueOf(amount),
                    0
            );
            infoTable.setAccNumber(localUser.getAccounts().iterator().next());


            // do transaction
            infoTableRepository.save(infoTable);
            userRepository.save(localUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean doWithdraw(String amount, String password) {
        if (localUser.getPassword().equals(password)){
            int am = localUser.getAccounts().iterator().next().getAmount();
            if (Integer.valueOf(amount) > am && am == 0){
                return false;
            }
            am -= Integer.valueOf(amount);
            localUser.getAccounts().iterator().next().setAmount(am);

            // set Info
            InfoTable infoTable = new InfoTable(
                    simpleDateFormat.format(new Date()),
                    Code.WITHDRAW,
                    Message.WITHDRAW,
                    0,
                    Integer.valueOf(amount)
            );
            infoTable.setAccNumber(localUser.getAccounts().iterator().next());


            // do transaction
            infoTableRepository.save(infoTable);
            userRepository.save(localUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean doPayment(String otherNumber, String amount, String password) {
        AccountNumbers otherAcc = accountNumbers.findByNumber(otherNumber);
        if (otherAcc != null){
            int am = localUser.getAccounts().iterator().next().getAmount();
            if (Integer.valueOf(amount) > am && am == 0){
                return false;
            }
            am -= Integer.valueOf(amount);
            localUser.getAccounts().iterator().next().setAmount(am);
            int amOther = otherAcc.getAmount();
            amOther += Integer.valueOf(amount);
            otherAcc.setAmount(amOther);

            // set Info
            InfoTable infoTableWithdraw = new InfoTable(
                    simpleDateFormat.format(new Date()),
                    Code.PAYMENT,
                    Message.PAYMENT_W,
                    0,
                    Integer.valueOf(amount)
            );
            InfoTable infoTableDonation = new InfoTable(
                    simpleDateFormat.format(new Date()),
                    Code.PAYMENT,
                    Message.PAYMENT_D,
                    Integer.valueOf(amount),
                    0
            );
            infoTableWithdraw.setAccNumber(localUser.getAccounts().iterator().next());
            infoTableDonation.setAccNumber(otherAcc);


            // do transaction
            infoTableRepository.save(infoTableWithdraw);
            infoTableRepository.save(infoTableDonation);

            userRepository.save(localUser);
            accountNumbers.save(otherAcc);
            return true;
        }

        return false;
    }

    @Override
    public int cnt() {
        accountNumbers.findAll().size();
        return accountNumbers.findAll().size();
    }

    @Override
    public boolean doDeposit(int var, String amount, String password){
        localWorkerAmount = Integer.valueOf(amount);
        switch (var){
            case 1:
                rate = 8;
                counter = 1;
                wTime = 5000;
            break;
            case 2:
                rate = 10;
                counter = 3;
                wTime = 10000;
            break;
            case 3:
                rate = 12;
                counter = 5;
                wTime = 15000;
            break;
        }
        if (localUser.getPassword().equals(password)){
            int am = localUser.getAccounts().iterator().next().getAmount();
            if (Integer.valueOf(amount) > am && am == 0){
                return false;
            }
            am -= Integer.valueOf(amount);
            localUser.getAccounts().iterator().next().setAmount(am);
            // to do
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            for (int i = 0; i < counter; i++) {
                                double wei = localWorkerAmount / 100.0 * rate;
                                localWorkerAmount += wei;
                            }
                            int am = localUser.getAccounts().iterator().next().getAmount();
                            am += localWorkerAmount;
                            localUser.getAccounts().iterator().next().setAmount(am);
                            //set info
                            InfoTable infoTable = new InfoTable(
                                    simpleDateFormat.format(new Date()),
                                    Code.DEPOSIT,
                                    Message.DEPOSIT,
                                    localWorkerAmount,
                                    0
                            );
                            infoTable.setAccNumber(localUser.getAccounts().iterator().next());


                            // do transaction
                            infoTableRepository.save(infoTable);
                            userRepository.save(localUser);
                        }
                    },
                    wTime
            );
            return true;
        }
        return false;
    }
}
