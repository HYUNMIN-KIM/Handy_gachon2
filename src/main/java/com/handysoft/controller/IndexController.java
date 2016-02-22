package com.handysoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index(@RequestParam(value = "userid", required = true) String userid, Model model){
		model.addAttribute("userid", userid);
		return "ConditionGraph";
	}
	
	
	@RequestMapping("/cluster")
	public String clusterIndex(@RequestParam(value = "userid", required = true) String userid, Model model){
		model.addAttribute("userid", userid);
		return "ClusterGraph";
	}
	
	
}
