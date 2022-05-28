package com.example.command;

public enum CommandType {       // тип команды
    AUTH,
    AUTH_OK,
    PUBLIC_MESSAGE,
    PRIVATE_MESSAGE,
    CLIENT_MESSAGE,
    ERROR,
    UPDATE_USERS_LIST
}
