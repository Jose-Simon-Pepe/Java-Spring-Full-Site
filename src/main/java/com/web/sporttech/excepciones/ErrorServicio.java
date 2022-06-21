/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.sporttech.excepciones;

/**
 *
 * @author simon
 */
public class ErrorServicio extends Exception {

    /**
     * Creates a new instance of <code>WrongInput</code> without detail message.
     */
    public ErrorServicio() {
    }

    /**
     * Constructs an instance of <code>WrongInput</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ErrorServicio(String msg) {
        super(msg);
    }
}
