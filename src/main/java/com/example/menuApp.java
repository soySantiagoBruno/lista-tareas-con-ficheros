package com.example;

import java.io.IOException;

public class menuApp {

    Operaciones operaciones = new Operaciones();
    public menuApp() throws IOException {

        Formulario formulario = new Formulario();

        formulario.setBounds(0,0,600,400);
        formulario.setVisible(true);
        formulario.setLocationRelativeTo(null);

    }
}
