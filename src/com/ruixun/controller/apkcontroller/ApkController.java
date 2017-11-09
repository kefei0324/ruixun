package com.ruixun.controller.apkcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/apkdownload")
public class ApkController {

	
	@RequestMapping(value="/getversion")
	public void getApkVresion(@RequestParam("currentV")int currentV){
		
	}
	
	
}