package com.massurl.url;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MassUrlController {

	@Autowired
	public MassUrlService massUrlService;

	@PostMapping("/api/massurls")
	public String shortenUrl(@Valid MassUrl massUrl, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "homepage";
		}
		model.addAttribute("mass_url", massUrlService.shortenUrl(massUrl));
		return "homepage";
	}

//	@PostMapping("/api/massurls")
//	@ResponseBody
//	public Map<String, Object> shortenUrl(@RequestBody MassUrl massUrl) {
//		Map<String, Object> result = new HashMap<>();
//		result.put("mass_url", massUrlService.shortenUrl(massUrl));
//		return result;
//	}

	@GetMapping("/api/massurls")
	@ResponseBody
	public List<MassUrl> getUrls() {
		return massUrlService.getUrls();
	}

	@GetMapping("/api/massurls/{mass_url_base10_id}")
	@ResponseBody
	public MassUrl getUrlDetails(@PathVariable("mass_url_base10_id") long massUrlID) throws Exception {
		return massUrlService.getUrlDetails(massUrlID);
	}

	@GetMapping("/")
	public String getHomePage(MassUrl massUrl) {
		return "homepage";
	}

	@GetMapping("/{mass_url_base62_id}")
	public String getOriginalUrl(@PathVariable("mass_url_base62_id") String massUrlID, Model model) throws Exception {
		model.addAttribute("original_url", massUrlService.getOriginalUrl(massUrlID));
		return "mass_url_page";
	}

}
