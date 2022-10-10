package org.launchcode.techjobs.mvc.controllers;

import org.launchcode.techjobs.mvc.models.Job;
import org.launchcode.techjobs.mvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobs.mvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    // post request with value of results (per code from search.html): <form th:action="@{/search/results}" method = "post">
    // 2 additional parameters: searchType, searchTerm (both String)
    // if "all" then use findAll() from JobData; otherwise use findByColumnAndValue; store EITHER result in jobs ArrayList and use model.addAttribute to display the results
    // the view currently displays no actual results but successfully changes to show "Jobs with ____: _____" (whatever search parameters were (i.e Location: St. Louis))
    @PostMapping(value = "results")
    public String displaySearchResults(Model model,
                                       @RequestParam String searchType,
                                       @RequestParam (required = false) String searchTerm)    {
        ArrayList<Job> jobs;
        if (searchType.equalsIgnoreCase("all") && searchTerm.equalsIgnoreCase("all"))   {
            jobs = JobData.findAll();
            model.addAttribute("title", columnChoices.get(searchType) + "Jobs: ");
        }   else if (searchType.equalsIgnoreCase("all") && !searchTerm.equalsIgnoreCase("all")) {
            jobs = JobData.findByValue(searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        }   else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " + columnChoices.get(searchType) + ": " + searchTerm);
        }
        model.addAttribute("columns", columnChoices);
        model.addAttribute("jobs", jobs);
        model.addAttribute("employers", JobData.getAllEmployers());
        model.addAttribute("locations", JobData.getAllLocations());
        model.addAttribute("positions", JobData.getAllPositionTypes());
        model.addAttribute("skills", JobData.getAllCoreCompetency());

        return "search";
    }
}
