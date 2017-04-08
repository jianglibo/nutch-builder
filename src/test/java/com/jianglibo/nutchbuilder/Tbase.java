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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jianglibo.nutchbuilder.config.ApplicationConfig;
import com.jianglibo.nutchbuilder.domain.BootUser;
import com.jianglibo.nutchbuilder.domain.Role;
import com.jianglibo.nutchbuilder.repository.BootUserRepository;
import com.jianglibo.nutchbuilder.repository.RoleRepository;
import com.jianglibo.nutchbuilder.util.SecurityUtil;
import com.jianglibo.nutchbuilder.vo.BootUserAuthentication;
import com.jianglibo.nutchbuilder.vo.BootUserPrincipal;
import com.jianglibo.nutchbuilder.vo.RoleNames;

/**
 * @author jianglibo@gmail.com
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "appmisc.in-testing=true"}, webEnvironment = WebEnvironment.DEFINED_PORT)
public abstract class Tbase extends M3958TsBase {

    protected MockMvc mvc;
    
	@Autowired
	protected TestRestTemplate restTemplate;
    
    @Autowired
    protected TestUtil testUtil;

    @Autowired
    protected WebApplicationContext context;
    
    @Autowired
    protected ApplicationConfig applicationConfig;

    @Autowired
    protected BootUserRepository personRepo;

    @Autowired
    protected RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    protected ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
	@TestConfiguration
	static class Config {
		// does't work
		@Bean
		public TestRestTemplate testTemplate() {
			return new TestRestTemplate();
		}
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
    
    private void loginAsCreator() {
        loginAs("creator", RoleNames.PERSON_CREATOR);
    }
    
    protected void loginAs(String name, String...rns) {
        BootUserPrincipal pv = createBootUserPrincipal(name, rns);
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
    }
    
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
    
    public void createFullPerson(String name, String password, String...rns) {
        createPersonVo(name, password, true, rns);
    }

    public BootUserPrincipal createBootUserPrincipal(String name, String... rns) {
        return createPersonVo(name, false, rns);
    }
    
    public BootUserPrincipal createBootUserAndSave(String name, String... rns) {
        return createPersonVo(name, true, rns);
    }
    
    public void createUserWithUserRole() {
    	createFullPerson("user", "123456");
    }

    private BootUserPrincipal createPersonVo(String name,String password, boolean createDomain, String... rns) {
        List<String> rnl = new ArrayList<>();
        rnl.addAll(Arrays.asList(rns));

        if (!rnl.contains(RoleNames.USER)) {
            rnl.add(RoleNames.USER);
        }

        BootUser p = personRepo.findByName(name);

        if (p == null) {
            p = BootUser.newValidPerson();
            p.setName(name);
            p.setEmail(name + "@m3958.com");
            p.setEmailVerified(true);
            p.setPassword(passwordEncoder.encode(password));
            Set<Role> roles = new HashSet<>(rnl.stream().map(Role::new).collect(Collectors.toList()));
            p.setRoles(roles);

            if (createDomain) {
                loginAsCreator();
                p = personRepo.save(p);
                logout();
            } else {
            	p.setId(55L);
            }
        }
        return new BootUserPrincipal(p);
    }
    
    private BootUserPrincipal createPersonVo(String name,boolean createDomain, String... rns) {
        return createPersonVo(name,name, createDomain, rns);
    }

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
