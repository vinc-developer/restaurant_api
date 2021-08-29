package com.formation.restaurant.util;

import com.formation.restaurant.exceptions.RessourceNotFoundException;

/**
 *  Controle des objets générique, permet de controler tout les objets en base de données, que se soit un Restaurant
 * ou un menu
 * */
public final class CtrlPreconditions {

    public static <T> void checkFound(T object){
        if(object == null){
            throw new RessourceNotFoundException();
        }
    }
}
