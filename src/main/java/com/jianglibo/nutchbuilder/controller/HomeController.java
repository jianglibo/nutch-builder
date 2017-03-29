package com.jianglibo.nutchbuilder.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jianglibo.nutchbuilder.model.MenuItem;
import com.jianglibo.nutchbuilder.model.MenuItems;
import com.jianglibo.nutchbuilder.util.SecurityUtil;

import io.katharsis.resource.registry.RegistryEntry;
import io.katharsis.resource.registry.ResourceRegistry;

@Controller
@RequestMapping("/")
public class HomeController implements HasMenuItemController {
	
	@Autowired
	private MenuItems menuItemFactory;
	
	@ModelAttribute(name="mainMenus")
	public List<MenuItem> addMenuItemsToModel() {
		return menuItemFactory.CloneMenuItems("home");
	}
	
	@RequestMapping
	public String home(@RequestParam(name="tplName", required=false, defaultValue="index") String tplName, @RequestParam(name="frgTpl", required=false, defaultValue="home") String frgTpl,@RequestParam(name="frgName", required=false, defaultValue = "home") String frgName,Model model, HttpServletRequest req) {
		model.addAttribute("frgTpl", frgTpl);
		model.addAttribute("frgName", "home");
		SecurityUtil.addUserToModel(model);
		return tplName;
	}

	@Override
	public List<MenuItem> getMenuItems() {
		List<MenuItem> mi = new ArrayList<>();
		mi.add(new MenuItem("home", "/"));
		return mi;
	}
}
