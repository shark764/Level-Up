package com.focus.levelup.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.focus.levelup.model.ProgrammingLanguage;
import com.focus.levelup.model.Users;
import com.focus.levelup.services.ProgrammingLanguageService;
import com.focus.levelup.services.QuizLevelsService;
import com.focus.levelup.services.QuizzesServices;
import com.focus.levelup.services.TestsService;



@Controller
@RequestMapping("Quizz")
public class QuizzController {
	
	@Autowired
	ProgrammingLanguageService languagesServices;
	
	@Autowired
	QuizLevelsService QlevelServices;
	
	@Autowired
	QuizzesServices quizzesServices;
	
	@Autowired
	TestsService testServices;

	@RequestMapping("index")
	public String index(Model model) {
		
		int countLanguages = (int) languagesServices.count();
		int countQLevel = (int) QlevelServices.count();
		int countQuizz = (int) quizzesServices.count();
		int countPendingTest = (int) testServices.count();
				
		model.addAttribute("totalLanguages", countLanguages);
		model.addAttribute("totalLevels", countQLevel);
		model.addAttribute("countQuizz", countQuizz);
		model.addAttribute("countPendingTest", countPendingTest);
		
		return "quizz/indexLanguages";
	}
	
	@RequestMapping("addLanguages")
	public String addLanguages(Model model) {
		
		List<ProgrammingLanguage> pl = (List<ProgrammingLanguage>) languagesServices.findAll();
		
		model.addAttribute("pl",pl);
		
		// Basic Count 
		int countLanguages = (int) languagesServices.count();
		int countQLevel = (int) QlevelServices.count();
		int countQuizz = (int) quizzesServices.count();
		int countPendingTest = (int) testServices.count();
				
		model.addAttribute("totalLanguages", countLanguages);
		model.addAttribute("totalLevels", countQLevel);
		model.addAttribute("countQuizz", countQuizz);
		model.addAttribute("countPendingTest", countPendingTest);
		// End Basic Count
		
		return "quizz/addLanguages";
	}
	
	/*
	 * SAVE NEW PROGRAMMING LANGUAGES
	 */
	@RequestMapping("saveLanguage")
	public ModelAndView saveLanguage(@ModelAttribute("ProgrammingLanguage") ProgrammingLanguage languages, BindingResult result) {
		
		ProgrammingLanguage pl = new ProgrammingLanguage();
		pl.setLanguage(languages.getLanguage());
		pl.setStatus(languages.getStatus());
		
		languagesServices.save(pl);
		
		return new ModelAndView("redirect:/Quizz/addLanguages");
	}	
	
	/*
	 * EDIT PROGRAMMING LANGUAGES
	 */
	@RequestMapping(value ="editLanguages/{id}", method= RequestMethod.GET)
	public String editLanguage(Model model,@PathVariable int id) {
		
		List<ProgrammingLanguage> pl = (List<ProgrammingLanguage>) languagesServices.findAll();			
		ProgrammingLanguage pl_edit = languagesServices.findOne(id);
		
		model.addAttribute("pl",pl);
		model.addAttribute("languages", pl_edit);
		
		return ("quizz/editLanguages");
	}
	
	
	/*
	 * UPDATE PROGRAMMING LANGUAGES
	 */
	@RequestMapping("updateLanguage")
	public ModelAndView updateLanguage(@ModelAttribute("ProgrammingLanguage") ProgrammingLanguage languages, BindingResult result) {
		
		ProgrammingLanguage pl = languagesServices.findOne(languages.getIdLanguage());
		pl.setLanguage(languages.getLanguage());
		pl.setStatus(languages.getStatus());
		
		languagesServices.save(pl);		
		
		return new ModelAndView("redirect:/Quizz/addLanguages");
	}	
	
}
