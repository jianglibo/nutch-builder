package com.jianglibo.nutchbuilder;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
    protected BootUserRepository bootUserRepo;

    @Autowired
    protected RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    protected ObjectMapper objectMapper;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
    
    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

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
    
    protected BootUser loginAsAdmin() {
    	BootUser bu = createBootUser("admin",null,RoleNames.ROLE_ADMINISTRATOR);
        BootUserPrincipal pv = new BootUserPrincipal(bu);
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
        return bu;
    }
    
    protected void loginAs(String name, String...rns) {
        BootUserPrincipal pv = new BootUserPrincipal(createBootUser(name,null,rns));
        BootUserAuthentication saut = new BootUserAuthentication(pv);
        SecurityUtil.doLogin(saut);
    }

    protected BootUser createBootUser(String name,String password, String... rns) {
    	
        List<Role> rnl = Stream.of(rns).map(Role::new).collect(Collectors.toList()); 
        

        if (!rnl.contains(new Role(RoleNames.USER))) {
            rnl.add(new Role(RoleNames.USER));
        }
        
        Set<Role> nroles = rnl.stream().map(rn -> {
        	Role r = roleRepo.findByName(rn.getName());
        	if (r == null) {
        		r = roleRepo.save(rn);
        	}
        	return r;
        }).collect(Collectors.toSet());
        
        if (password == null || password.trim().isEmpty()) {
        	password = "aA^" + new Random().nextDouble() + "0k";
        }

        BootUser p = bootUserRepo.findByName(name);
        
        if (p == null) {
            p = BootUser.newValidPerson();
            p.setName(name);
            p.setEmail(name + "@m3958.com");
            p.setEmailVerified(true);
            p.setPassword(passwordEncoder.encode(password));
            p.setRoles(nroles);
            p = bootUserRepo.save(p);
        } else {
        	p.setRoles(nroles);
        	p = bootUserRepo.save(p);
        }
        return p;
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
