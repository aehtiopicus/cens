package ar.com.midasconsultores.catalogodefiltros.admin.controller;

import ar.com.midasconsultores.catalogodefiltros.admin.dto.ListaMarcaFiltroPrioridadDTO;
import ar.com.midasconsultores.catalogodefiltros.admin.mapper.MarcaFiltroPrioridadMapper;
import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;
import ar.com.midasconsultores.catalogodefiltros.service.FiltroService;
import ar.com.midasconsultores.catalogodefiltros.service.MarcaFiltroPrioridadService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = MarcaFiltroPrioridadController.PRIORIDADES_PATH)
public class MarcaFiltroPrioridadController {

    @Autowired
    MarcaFiltroPrioridadService marcaFiltroPrioridadService;
    @Autowired
    MarcaFiltroPrioridadMapper marcaFiltroPrioridadMapper;
    @Autowired
    FiltroService filtroService;
    public static final String PRIORIDADES_PATH = "prioridades";
    @Autowired
    Validator validator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list(Locale locale, Model model, Principal principal,
            HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("prioridades");
        ListaMarcaFiltroPrioridadDTO listaMarcaFiltroPrioridadDTO = new ListaMarcaFiltroPrioridadDTO();
        List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad = new ArrayList();
        listaMarcaFiltroPrioridad.addAll(marcaFiltroPrioridadService.list());
        listaMarcaFiltroPrioridadDTO.setMarcaFiltroPrioridadDTOs(marcaFiltroPrioridadMapper.mapToDTOList(listaMarcaFiltroPrioridad));

        mav.addObject("listaMarcaFiltroPrioridadDTO", listaMarcaFiltroPrioridadDTO);

        return mav;

    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(Locale locale,
            @Valid @ModelAttribute("listaMarcaFiltroPrioridadDTO") ListaMarcaFiltroPrioridadDTO listaMarcaFiltroPrioridadDTO,
            BindingResult result,
            Principal principal, HttpServletRequest request, HttpServletResponse response) {

//        validator.validate(listaMarcaFiltroPrioridadDTO, result);

        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("prioridades");
            return mav;
        }

        List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad = new ArrayList();
        listaMarcaFiltroPrioridad.addAll(marcaFiltroPrioridadMapper.mapToObjList(listaMarcaFiltroPrioridadDTO.getMarcaFiltroPrioridadDTOs()));
        filtroService.updatePrioridades(listaMarcaFiltroPrioridad);
        marcaFiltroPrioridadService.saveAll(listaMarcaFiltroPrioridad);

        ModelAndView mav = new ModelAndView("redirect:prioridades");
        return mav;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.setBindingErrorProcessor(new DefaultBindingErrorProcessor() {
            @Override
            public void processPropertyAccessException(PropertyAccessException ex, BindingResult bindingResult) {
                String propertyName = ex.getPropertyName();
                Object value = ex.getValue();
                if (ex instanceof TypeMismatchException) {
                    bindingResult.addError(new FieldError(bindingResult.getObjectName(), propertyName, value, true,
                            new String[]{"moderation.field.error"}, new Object[]{propertyName, value},
                            "El valor debe estar entre 0 y 2147483647"));
                }
            }
        });
    }
}
