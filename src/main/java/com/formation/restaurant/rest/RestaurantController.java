package com.formation.restaurant.rest;

import com.formation.restaurant.models.Restaurant;
import com.formation.restaurant.services.RestaurantService;
import com.formation.restaurant.util.CtrlPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restoService;

    @GetMapping
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = restoService.findAll();
        /*Iteration pour l'affichage des liens de la documentation*/
        for(Restaurant restaurant : restaurants){
            Link selflink = WebMvcLinkBuilder.linkTo(RestaurantController.class).slash(restaurant.getId()).withSelfRel();
            restaurant.add(selflink);
        }
        return restoService.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant findById(@PathVariable("id") String identifiant) {
        Restaurant response = restoService.findById(identifiant);
        CtrlPreconditions.checkFound(response);
        /* Lien pour la documentation */
        Link menusLink = WebMvcLinkBuilder.linkTo(RestaurantController.class).slash(response.getId()).slash("menus").withRel("menus");
        response.add(menusLink);
        return response;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String create(@RequestBody Restaurant restaurant){
        return restoService.create(restaurant);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void update(@PathVariable("id") String identifiant, @RequestBody Restaurant restaurant){
        CtrlPreconditions.checkFound(restoService.findById(identifiant));
        restoService.update(identifiant, restaurant);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void partialUpdate(@PathVariable("id") String identifiant, @RequestBody Map<String, Object> updates){
        CtrlPreconditions.checkFound(restoService.findById(identifiant));
        restoService.partialUpdate(identifiant, updates);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteById(@PathVariable("id") String identifiant){
        CtrlPreconditions.checkFound(restoService.findById(identifiant));
        restoService.deleteById(identifiant);
    }
}
