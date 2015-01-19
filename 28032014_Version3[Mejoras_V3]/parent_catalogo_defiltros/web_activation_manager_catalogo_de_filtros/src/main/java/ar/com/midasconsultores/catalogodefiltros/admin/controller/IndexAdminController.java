package ar.com.midasconsultores.catalogodefiltros.admin.controller;

import java.security.Principal;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexAdminController {

    private static final Logger logger = LoggerFactory
            .getLogger(IndexAdminController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView inicio(Locale locale, Model model,
            Principal principal, HttpServletRequest request) {

        return new ModelAndView("index");
    }
}
