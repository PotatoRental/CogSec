package org.security.service;

import org.security.dao.CogletDao;
import org.security.dao.UserDao;
import org.security.exception.InsertExistException;
import org.security.exception.PasswordUnsetException;
import org.security.model.Coglet;
import org.security.model.Cogtag;
import org.security.model.Role;
import org.security.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User: Milky
 * Date: 11/25/13
 * Time: 4:22 PM
 */
@Service
public class AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CogletDao cogletDao;

    public void addDefaultUser(String username) throws InsertExistException {
        if (getUser(username) == null) {
            UserAccount userAccount = new UserAccount();
            List<Coglet> password = cogletDao.getDefaultCoglets();
            Role role = new Role("ROLE_ADMIN");

            userAccount.setUsername(username);
            userAccount.setPassword(password);
            userAccount.setRole(role);
            userDao.addUser(userAccount);
        } else
            throw new InsertExistException();
    }

    public List<Coglet> getDefaultPassword() {
        return cogletDao.getDefaultCoglets();
    }

    public void addUser(UserAccount userAccount) throws InsertExistException, PasswordUnsetException {
        if (userAccount.getPassword() == null)
            throw new PasswordUnsetException();
        else
            userDao.addUser(userAccount);
    }

    public UserAccount getUser(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<UserAccount> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void addCoglet(Coglet coglet) throws InsertExistException {
        if (cogletDao.getCoglet(coglet) == null)
            cogletDao.addCoglet(coglet);
        else
            throw new InsertExistException();
    }

    public void addCoglet(String imagePath, String[] tags) throws InsertExistException {
        Coglet coglet = new Coglet(imagePath);

        List<Cogtag> cogTags = new ArrayList<Cogtag>();
        for (String tag : tags)
            cogTags.add(new Cogtag(tag));

        coglet.setTags(cogTags);
        addCoglet(coglet);
    }

    public List<Coglet> getCogletWithCogtag(String tag) {
        return cogletDao.getCogletsCategory(new Cogtag(tag));
    }

    public List<Coglet> getRandomCogletWithCogtag(String tag, int num) {
        Random rng = new Random();
        Cogtag cogtag = new Cogtag(tag);
        long numHave = cogletDao.getNumCogletCategory(cogtag);

        Set<Integer> generated = new LinkedHashSet<Integer>(num);
        List<Coglet> list = new ArrayList<Coglet>(num);
        while (generated.size() < num)
            generated.add(rng.nextInt((int) numHave));

        for (Integer gen : generated)
            list.add(cogletDao.getCogletWithCategoryPosition(cogtag, gen));

        return list;
    }

    public List<Coglet> getAllCoglets() {
        return cogletDao.getAllImages();
    }

    public List<Cogtag> getAllCogtags() {
        return cogletDao.getAllTags();
    }

}
