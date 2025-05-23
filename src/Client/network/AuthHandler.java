package Client.network;

import java.io.Console;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import Common.models.User;
import Common.responses.AuthResponse;

public class AuthHandler {
    private final TCPClient client;

    public AuthHandler(TCPClient client) {
        this.client = client;
    }

    // Убрано хеширование пароля на клиенте
    public User authenticate() {
        try {
            String login = promptLogin();
            char[] password = promptPassword();

            client.sendAuthRequest(login, new String(password));
            Arrays.fill(password, ' ');

            AuthResponse response = client.receiveAuthResponse();

            if (response.getAuthStatus() == AuthResponse.AuthStatus.AUTH_SUCCESS) {
                User user = new User();
                user.setLogin(login);
                user.setId(response.getUserId());
                user.setUid(response.getUid());
                return user;
            } else {
                System.err.println("Ошибка аутентификации: " + response.getError());
            }
        } catch (IOException | ClassNotFoundException e) { // Добавлено ClassNotFoundException
            System.err.println("Ошибка при аутентификации: " + e.getMessage());
        }

        return null;
    }


    private String promptLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        return scanner.nextLine().trim();
    }

    private char[] promptPassword() {
        Console console = System.console();
        if (console != null) {
            return console.readPassword("Введите пароль: ");
        } else {
            System.out.print("Введите пароль: ");
            return new Scanner(System.in).nextLine().toCharArray();
        }
    }

}