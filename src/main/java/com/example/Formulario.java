package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Formulario extends JFrame implements ActionListener{

    Operaciones operaciones = new Operaciones();
    private JTextArea textarea;
    private JScrollPane scrollpane;
    JButton boton1,boton2,boton3;

    String listasYTareas = operaciones.listarListasYTareas();

    public Formulario() throws IOException {
        textarea = new JTextArea(listasYTareas);
        textarea.setEditable(false);

        setLayout(null);
        boton1 = new JButton("Crear lista");
        boton1.setBounds(0,0,100,30);
        add(boton1);
        boton1.addActionListener(this);

        boton2 = new JButton("Eliminar lista");
        boton2.setBounds(150,0,120,30);
        add(boton2);
        boton2.addActionListener(this);

        boton3 = new JButton("Editar lista");
        boton3.setBounds(300,0,100,30);
        add(boton3);
        boton3.addActionListener(this);

        scrollpane = new JScrollPane(textarea);
        scrollpane.setBounds(10,50,400,300);
        add(scrollpane);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton1){
            try {
                operaciones.crearLista();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (e.getSource() == boton2) {
            operaciones.eliminarLista();
        }
        else if (e.getSource()==boton3) {
            try {
                operaciones.editarLista();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            textarea.setText(operaciones.listarListasYTareas());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
