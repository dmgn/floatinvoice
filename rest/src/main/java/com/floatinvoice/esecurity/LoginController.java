package com.floatinvoice.esecurity;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.floatinvoice.business.ProfileService;
import com.floatinvoice.common.OrgType;
import com.floatinvoice.common.RegistrationStatusEnum;
import com.floatinvoice.common.UserContext;
import com.floatinvoice.messages.LoginDtlsMsg;
import com.floatinvoice.messages.UserProfile;

@Controller
public class LoginController {

	@Autowired
	ProfileService profileService;
	
	@RequestMapping(value = { "/welcomePage"}, method = RequestMethod.GET)
    public ModelAndView welcomePage(HttpSession session) {
		UserProfile userProfile = profileService.fetchUserProfile(UserContext.getUserName());
        ModelAndView model = new ModelAndView();
        model.addObject("acronym", userProfile.getOrgAcronym());
        if (userProfile.getOrgType().equalsIgnoreCase(OrgType.SELLER.getText())){
        	model.setViewName("welcomePage");
        }else{
        	model.setViewName("financierView");
        }
        return model;
    }
	
	
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET)
    public ModelAndView welcomePageTemp(HttpSession session) {
		UserProfile userProfile = profileService.fetchUserProfile(UserContext.getUserName());
        ModelAndView model = new ModelAndView();
        model.addObject("acronym", userProfile.getOrgAcronym());
        model.setViewName("home");
        return model;
    }
	
/*    @RequestMapping(value = { "/profile/login"}, method = RequestMethod.GET)
    public String login(@RequestParam(value="email") String email, HttpSession session) {
    	if (email != null){
    		initializeSession(email, session);
    		return "redirect:/.html";
    	}else{
    		throw new RuntimeException("No valid user signed in...");
    	}
    	
    }*/
    
/*    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
                                  @RequestParam(value = "logout", required = false) String logout) {
         
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid Credentials provided.");
        }
 
        if (logout != null) {
            model.addObject("message", "Logged out from JournalDEV successfully.");
        }
 
        model.setViewName("loginPage");
        return model;
    }*/
    
    
    
    
    @RequestMapping(value = "/loginSubmit", method = RequestMethod.POST)
    public ModelAndView login(@RequestBody LoginDtlsMsg loginDtlsMsg,
                                  HttpSession session) {         
    	String email = loginDtlsMsg.getEmail();
    	UserProfile uProfile = initializeSession(email, session);

    	int pageFwd = profileService.findUserRegistrationStatus(email);
    	ModelAndView modelAndView = new ModelAndView();
    	if(pageFwd == RegistrationStatusEnum.LOGIN.getCode()){
    		session.setAttribute("remote-user", email);
    		modelAndView.setViewName("registerOrgInfoPage");    		
    	}else if(pageFwd == RegistrationStatusEnum.ORG.getCode()){
    		session.setAttribute("remote-user", email);
    		if( uProfile.getOrgType().equalsIgnoreCase(OrgType.SELLER.getText())){
    			modelAndView.setViewName("registerUserInfoPage");    		
    		}else{
        		modelAndView.addObject("windowLocation", "/floatinvoice/financierView");
    			//modelAndView.setViewName("registerUserInfoPage");
    		}
    	}else if(pageFwd == RegistrationStatusEnum.USER.getCode()){
    		modelAndView.addObject("acronym", uProfile.getOrgAcronym());
    		modelAndView.addObject("windowLocation", "/floatinvoice/welcomePage#/upload");
    		modelAndView.setViewName("welcomePage");
    	}
       	return modelAndView;
    }
    
    @RequestMapping(value = "/financierView", method = RequestMethod.GET)
    public ModelAndView finViewPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("financierView");
        return model;
    }

 
    protected UserProfile initializeSession(String email, HttpSession session){
    	if(session != null){
    		final UserProfile userProfile = profileService.fetchUserProfile(email);
    		session.setAttribute("remote-user", email);
    		session.setAttribute("acronym", userProfile.getOrgAcronym());
    		return userProfile;
    	}
    	return null;
    }
    
    
   @RequestMapping("/logout")
    public String logout(HttpSession session ) {
	   if(session != null)
		   session.invalidate();
       return "redirect:/login";
    }

}

