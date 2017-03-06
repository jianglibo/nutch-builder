package com.jianglibo.nutchbuilder;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;
import com.jianglibo.nutchbuilder.repository.RoleRepository;
import com.jianglibo.nutchbuilder.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest({ "appmisc.in-testing=true"})
public abstract class Tbase extends M3958TsBase {

    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected BootUserRepository personRepo;

    @Autowired
    protected RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

//    protected void deleteAllUsers() {
//        loginPersonDeletor();
//        personRepo.deleteAll();
//        logout();
//    }
    
//    public void loginPersonDeletor() {
//        loginAs("deleter", RoleNames.PERSON_DELETER);
//    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public Pageable createApageable(int page, int size) {
        return new PageRequest(page, size);
    }

    public Pageable createApageable(int size) {
        return createApageable(0, size);
    }

    public Pageable createApageable() {
        return createApageable(10);
    }
    
//    private void loginAsCreator() {
//        loginAs("creator", RoleNames.PERSON_CREATOR);
//    }
    
//    protected void loginAs(String name, String...rns) {
//        PersonVo pv = createPersonVo(name, rns);
//        M3958LoginAuthenticationToken saut = new M3958LoginAuthenticationToken(pv);
//        M3958SecurityUtil.doLogin(saut);
//    }
//    
//    public Authentication getAdminAuthentication() {
//        PersonVo pv = createPersonVoAndSave("admin", RoleNames.PERSON_MANAGER);
//        M3958LoginAuthenticationToken saut = new M3958LoginAuthenticationToken(pv);
//        saut.setAuthenticated(true);
//        return saut;
//    }
//
//    public Authentication getOrdinaryAuthentication() {
//        PersonVo pv = createPersonVoAndSave("ordinary");
//        M3958LoginAuthenticationToken saut = new M3958LoginAuthenticationToken(pv);
//        saut.setAuthenticated(true);
//        return saut;
//    }
//    
//    public Authentication getAuthentication(String name, String...rns) {
//        PersonVo pv = createPersonVoAndSave(name, rns);
//        M3958LoginAuthenticationToken saut = new M3958LoginAuthenticationToken(pv);
//        saut.setAuthenticated(true);
//        return saut;
//    }
//    
//    public void createFullPerson(String name, String password, String...rns) {
//        createPersonVo(name, password, true, rns);
//    }
//
//    public PersonVo createPersonVo(String name, String... rns) {
//        return createPersonVo(name, false, rns);
//    }
//    
//    public PersonVo createPersonVoAndSave(String name, String... rns) {
//        return createPersonVo(name, true, rns);
//    }
//
//    private PersonVo createPersonVo(String name,String password, boolean createDomain, String... rns) {
//        List<String> rnl = new ArrayList<>();
//        rnl.addAll(Arrays.asList(rns));
//
//        if (!rnl.contains(RoleNames.USER)) {
//            rnl.add(RoleNames.USER);
//        }
//
//        Person p = personRepo.findByName(name);
//
//        if (p == null) {
//            p = Person.newValidPerson();
//            p.setName(name);
//            p.setEmail(name + "@m3958.com");
//            p.setEmailVerified(true);
//            p.setPassword(passwordEncoder.encode(password));
//            Set<Role> roles = new HashSet<>(rnl.stream().map(rn -> roleRepo.findByName(rn)).collect(Collectors.toList()));
//            p.setRoles(roles);
//
//            if (createDomain) {
//                loginAsCreator();
//                p = personRepo.save(p);
//                logout();
//            }
//        }
//        return new PersonVo(p);
//    }
//    
//    private PersonVo createPersonVo(String name,boolean createDomain, String... rns) {
//        return createPersonVo(name,name, createDomain, rns);
//    }

    public String getRestUri(String uri) {
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        return getApiPrefix() + uri;
    }

    public String getApiPrefix() {
        return "/api/v1";
    }
}
