package com.example.command;

import com.example.command.commands.*;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable {                 // когда мы передаем данный класс, сервер прочитает данный класс и узнает что за команда пришла
    // в поле type кладем кладем маркер который рассказывает о том, какая информация находиться в поле data и  мы  по type прочитаем команду ( AUTH, AUTH_OK, ERROR, PUBLIC_MESSAGE
    // PRIVATE_MESSAGE, CLIENT_MESSAGE, UPDATE_USERS_LIST мы из поля data можем прикастить в нужный класс


    private Object data;               // в этом поле будет пересылать информацию которую будем сюриализовывать и десюриализовывать
    private CommandType type;          // исходя от типа будем понимать какая информация содержиться в поле data

    public static Command authCommand(String login, String password) {    // вызываем метод без создания класса который будет создавать команду по авторизации
        Command command = new Command();
        command.data = new AuthCommandData(login, password);             // набор данных об авторизации
        command.type = CommandType.AUTH;                                 // тип команды просто авторизация
        return command;
    }

    public static Command authOkCommand(String userName) {    // возваращает AuthOk команду
        Command command = new Command();
        command.data = new AuthOkCommandData(userName);          //
        command.type = CommandType.AUTH_OK;                      // тип команды authOk
        return command;
    }

    public static Command errorCommand(String errorMessage) {  // команда об ошибке
        Command command = new Command();
        command.type = CommandType.ERROR;
        command.data = new ErrorCommandData(errorMessage);
        return command;
    }

    public static Command publicMessageCommand(String message) {
        Command command = new Command();
        command.type = CommandType.PUBLIC_MESSAGE;
        command.data = new PublicMessageCommandData(message);
        return command;
    }

    public static Command privateMessageCommand(String receiver, String message) {
        Command command = new Command();
        command.type = CommandType.PRIVATE_MESSAGE;
        command.data = new PrivateMessageCommandData(receiver, message);
        return command;
    }

    public static Command clientMessageCommand(String sender, String message) {
        Command command = new Command();
        command.type = CommandType.CLIENT_MESSAGE;
        command.data = new ClientMessageCommandData(sender, message);
        return command;
    }

    public static Command updateUserListCommand(List<String> users) {
        Command command = new Command();
        command.type = CommandType.UPDATE_USERS_LIST;
        command.data = new UpdateUserListCommandData(users);
        return command;
    }

    public Object getData() {
        return data;
    }

    public CommandType getType() {
        return type;
    }

}
