package com.practicetest.travel.controller;

import com.practicetest.travel.model.City;
import com.practicetest.travel.model.Country;
import com.practicetest.travel.service.ICityService;
import com.practicetest.travel.service.country.CountryService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public ModelAndView createCity(@ModelAttribute City city){
        cityService.save(city);
        ModelAndView modelAndView = new ModelAndView("city/create","message","Tạo Thành Phố Thành Công");
        modelAndView.addObject("city",new City());
        return modelAndView;
    }

    @GetMapping("/view")
    private ModelAndView cityView(@RequestParam("id") Long id){
        Optional<City> cityOptional = cityService.findById(id);
        ModelAndView model = new ModelAndView("viewcity","city",cityOptional.get());
        return model;
    }

    @GetMapping("/edit")
    private ModelAndView cityEditForm(@RequestParam("id") Long id){
        List<Country> countryList = (List<Country>) countryService.findAll();
        Optional<City> cityOptional = cityService.findById(id);
        ModelAndView model = new ModelAndView("edit","city",cityOptional.get());
        model.addObject("countryList",countryList);
        return model;
    }

    @PostMapping("/edit")
    public ModelAndView editCity(@ModelAttribute City city){
        cityService.save(city);
        ModelAndView modelAndView = new ModelAndView("city","message","Tạo Thành Phố Thành Công");
        return modelAndView;
    }
}
