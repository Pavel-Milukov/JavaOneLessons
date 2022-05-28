package com.example.command.commands;

import java.io.Serializable;

public class AuthOkCommandData implements Serializable {       // содержит команду об успешной авторизации, когда мы успешно авторизовались нам нужно передать только userName который успешно авторизовался

   private final String userName;

    public AuthOkCommandData(String userName) {
        this.userName = userName;
    }

    public String getUserName () {
        return this.userName;
    }
}
