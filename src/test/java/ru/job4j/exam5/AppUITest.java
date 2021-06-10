package ru.job4j.exam5;

import org.junit.Test;
import ru.job4j.exam5.io.*;

import static org.junit.Assert.*;

public class AppUITest {

    @Test
    public void whenUser3User5Same() {
        Input in = new StubInput(new String[] {
                "5",
                "user1 -> xxx@ya.ru, foo@abc.net",
                "user2 -> foo@gmail.com, ups@nix.ru",
                "user3 -> xyz@pisem.net, vasya@rambler.ru",
                "user4 -> ups@pisem.net, aaa@gmail.com",
                "user5 -> xyz@pisem.net"
        });
        StubOutput out = new StubOutput();
        Input vin = new ValidateInput(in, out);
        AppUI ui = new AppUI(vin, out);
        String expected =
                "user1 -> foo@abc.net, xxx@ya.ru\r\n"
                + "user2 -> ups@nix.ru, foo@gmail.com\r\n"
                + "user5 -> vasya@rambler.ru, xyz@pisem.net\r\n"
                + "user4 -> aaa@gmail.com, ups@pisem.net\r\n";
        ui.execute();
        assertEquals(expected, out.toString());
    }

    @Test
    public void whenWrongEmail() {
        Input in = new StubInput(new String[] {
                "2",
                "user1 -> xxx, foo@abc.net",
                "user1 -> xxx@ya.ru, foo@abc.net",
                "user2 -> foo@gmail.com, ups@nix.ru"
        });
        StubOutput out = new StubOutput();
        Input vin = new ValidateInput(in, out);
        AppUI ui = new AppUI(vin, out);
        String expected =
                "Ошибка синтаксиса в строке!\r\n"
                + "Повторите ввод в формате: userName -> email1, email2, ... emailN\r\n"
                + "user1 -> foo@abc.net, xxx@ya.ru\r\n"
                + "user2 -> ups@nix.ru, foo@gmail.com\r\n";
        ui.execute();
        assertEquals(expected, out.toString());
    }
}