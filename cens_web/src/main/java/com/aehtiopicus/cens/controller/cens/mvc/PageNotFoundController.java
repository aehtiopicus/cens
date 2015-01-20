package com.aehtiopicus.cens.controller.cens.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aehtiopicus.cens.utils.PageNotFoundException;

@Controller
public class PageNotFoundController extends AbstractController{


	@RequestMapping(value="/errors/404")
	public void pageNotFound(HttpServletResponse response) throws PageNotFoundException{
		throw new PageNotFoundException();
	}
	
}
