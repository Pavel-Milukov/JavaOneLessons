package com.geekbrains.clientchat.model;

import com.example.command.Command;

public interface ReadMessageListener {    // контракт по обработке сообщений, что мы должны сделать с сообщением которое получили от сервера

    void processReceivedCommand(Command command);
}
