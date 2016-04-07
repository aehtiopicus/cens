package ar.com.midasconsultores.catalogodefiltros.controller;

import ar.com.midasconsultores.catalogodefiltros.service.ActualizacionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
@RequestMapping("/fileUpload")
public class FileController {
 
	
	@Autowired
	private ActivationService activationService;
        
        @Autowired
	private ActualizacionService actualizacionService;
	
    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public @ResponseBody List<HashMap<String,String>> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
 
        //1. build an iterator
         Iterator<String> itr =  request.getFileNames();         
         HashMap<String, String> result = new HashMap<String, String>();
         List<HashMap<String, String>> returnList = new ArrayList<HashMap<String,String>>();
         //2. get each file
         if(itr.hasNext()){
        	 MultipartFile mf=request.getFile(itr.next());
        	 if(activationService.validarCodigos(mf)){
        		 result.put("success", "true");
        		 result.put("fileName", mf.getOriginalFilename());
        	 }else{
        		 result.put("success", "false");
        		 result.put("fileName", mf.getOriginalFilename());
        	 }
         }
         returnList.add(result);
         return returnList;
    }
    
    @RequestMapping(value="/upload/dump", method = RequestMethod.POST)
    public @ResponseBody List<HashMap<String,String>> uploadVolcado(MultipartHttpServletRequest request, HttpServletResponse response) {
         
        //1. build an iterator
         Iterator<String> itr =  request.getFileNames();         
         HashMap<String, String> result = new HashMap<String, String>();
         List<HashMap<String, String>> returnList = new ArrayList<HashMap<String,String>>();
         //2. get each file
         if(itr.hasNext()){
        	 MultipartFile mf=request.getFile(itr.next());
                int wait = actualizacionService.performManualUpdates(mf);
                
        	 if(wait!=-1){
        		 result.put("success", "true");
        		 result.put("fileName", mf.getOriginalFilename());
                         result.put("waitTime",wait+"");
        	 }else{
        		 result.put("success", "false");
        		 result.put("fileName", mf.getOriginalFilename());
        	 }
         }
         returnList.add(result);
         return returnList;
    }
   
}
