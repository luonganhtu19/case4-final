package com.practicetest.travel.controller;

import com.practicetest.travel.model.City;
import com.practicetest.travel.model.Country;
import com.practicetest.travel.service.ICityService;
import com.practicetest.travel.service.country.CountryService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class CityController {
    @Autowired
    private ICityService cityService;
    @Autowired
    private CountryService countryService;

    @GetMapping
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView("city/list");
        List<City> cityList=(List<City>)cityService.findAll();
        modelAndView.addObject("cityList",cityList);
        return modelAndView;
    }
    @GetMapping("city/create")
    public ModelAndView createCityForm(){
        List<Country> countryList = (List<Country>) countryService.findAll();
        ModelAndView model= new ModelAndView("city/create", "city", new City());
        model.addObject("countryList",countryList);
        return model;
    }

    @PostMapping("city/create")
    public ModelAndView createCity(@Validated @ModelAttribute City city, BindingResult bindingResult){
        ModelAndView modelAndView=new ModelAndView("city/create");
        if (!bindingResult.hasFieldErrors()) {
            cityService.save(city);
            modelAndView.addObject("city", new City());
            modelAndView.addObject("message", "Success!");
        }
        return modelAndView;
    }



    @GetMapping("city/edit/{id}")
    private ModelAndView cityEditForm(@PathVariable("id") Long id){
        List<Country> countryList = (List<Country>) countryService.findAll();
        Optional<City> cityOptional = cityService.findById(id);
        ModelAndView model = new ModelAndView("city/update","city",cityOptional.get());
        model.addObject("countryList",countryList);
        return model;
    }

    @PostMapping("city/edit/{id}")
    public ModelAndView editCity(@ModelAttribute City city){
        cityService.save(city);
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }
    @GetMapping("city/view/{id}")
    public ModelAndView showListCity(@PathVariable Long id){
        Optional<City> city=cityService.findById(id);
        ModelAndView modelAndView= new ModelAndView("city/detail");
        modelAndView.addObject("city",city.get());
        return modelAndView;
    }
    @GetMapping("/city/delete/{id}")
    private ModelAndView cityDeleteForm(@PathVariable("id") Long id){
        Optional<City> cityOptional = cityService.findById(id);
        ModelAndView model = new ModelAndView("city/delete","city",cityOptional.get());
        return model;
    }

    @PostMapping("/city/delete/{id}")
    private String cityDelete(@PathVariable("id") Long id){
        cityService.delete(id);
        return "redirect:/";
    }
}
