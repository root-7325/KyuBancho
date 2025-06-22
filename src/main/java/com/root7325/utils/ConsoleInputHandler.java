package com.root7325.utils;

import com.google.inject.Inject;
import com.root7325.dao.UserDAO;
import com.root7325.dao.UserDAOImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author kate on 12.05.2025
 */
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ConsoleInputHandler {
    private final UserDAO userDAO;
    private final Scanner scanner;

    public void start() {
        log.info("Waiting for commands.");

        while (true) {
            String command = scanner.nextLine().trim();
            String[] args = command.split(" ");

            switch (args[0]) {
                case "useradd":
                    if (args.length < 3) {
                        log.warn("Not enough arguments! useradd {username} {password}");
                        continue;
                    }
                    userDAO.addUser(args[1], args[2]);
                    break;
                default:
                    log.warn("Invalid command call: {}", Arrays.toString(args));
            }
        }
    }
}
