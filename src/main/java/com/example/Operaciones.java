package com.example;

import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Operaciones {
    String archivoElegido;

    //esto sirve para saber cuál es el último elemento del hashMap
    private int ultimoElemento(HashMap hashMap){
        Map.Entry<Integer, String> lastEntry = null;
        lastEntry = (Map.Entry<Integer, String>) hashMap.entrySet().stream().reduce((one, two) -> two).get();

        return lastEntry.getKey();
    }

    // devuelve un hashMap con todas las tareas contenidas en una lista
    private HashMap listarTareas(String opcion) throws IOException {
        //esto es lo que lee la lista
        String cadena;
        HashMap<Integer,String> cadenaEnumerada = new HashMap<Integer, String>();

        FileReader f = new FileReader(rutaListaElegida(opcion));
        BufferedReader b = new BufferedReader(f);

        int contador=1;
        while((cadena = b.readLine())!=null) {
            cadenaEnumerada.put(contador, cadena);
            contador++;
        }
        b.close();

        //si la lista no tiene nada, llena el hashMap con una clave 0 y un valor null para evitar futuros errores en agregarTarea()
        if (cadenaEnumerada.isEmpty()){
            cadenaEnumerada.put(0,"lista vacia");
        }

        return cadenaEnumerada;
    }

    private HashMap listarTareas2(String nombreLista) throws IOException {
        //esto es lo que lee la lista
        String cadena;
        HashMap<Integer,String> cadenaEnumerada = new HashMap<Integer, String>();

        FileReader f = new FileReader("src/main/resources/ficheros/"+nombreLista);
        BufferedReader b = new BufferedReader(f);

        int contador=1;
        while((cadena = b.readLine())!=null) {
            cadenaEnumerada.put(contador, cadena);
            contador++;
        }
        b.close();

        //si la lista no tiene nada, llena el hashMap con una clave 0 y un valor null para evitar futuros errores en agregarTarea()
        if (cadenaEnumerada.isEmpty()){
            cadenaEnumerada.put(0,"lista vacia");
        }

        return cadenaEnumerada;
    }

    // Muestra el contenido de un HashMap de una forma más estética.
    private String embellecedor(HashMap hashMap){
        String listadoFinal = "";
        for (int i=0; i<hashMap.size(); i++){
            listadoFinal = listadoFinal.concat(i+1 +". "+ hashMap.get(i+1)) + "\n" ;
        }

        return listadoFinal;
    }

    // Muestra una lista de opciones al momento de querer editar una lista
    private int elegirAccion(){
        String listaOpciones = "1. Agregar tarea \n 2.Eliminar tarea";

        int opcionElegida = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la acción que desea realizar \n"+listaOpciones));

        return opcionElegida;
    }

    // Lista las listas y devuelve la ruta de una en específico
    private String rutaListaElegida(String opcion){
        File file = new File("src/main/resources/ficheros/");

        String listas[] = file.list();

        // Lista cada elemento que hay en el directorio "src/main/resources/ficheros/" y los vincula con un número
        HashMap<Integer,String> listadoEnumerado = new HashMap<Integer, String>();

        for (int i=0; i < listas.length; i++){
            listadoEnumerado.put(i+1,listas[i]);
        }


        int opcionElegida = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de la lista que desea "+opcion+"\n" + embellecedor(listadoEnumerado)));

        archivoElegido = "src/main/resources/ficheros/" + listadoEnumerado.get(opcionElegida);


        return archivoElegido;
    }

    private String rutaListaElegida2(String opcionElegida){
        File file = new File("src/main/resources/ficheros/");

        String listas[] = file.list();

        // Lista cada elemento que hay en el directorio "src/main/resources/ficheros/" y los vincula con un número
        HashMap<Integer,String> listadoEnumerado = new HashMap<Integer, String>();

        for (int i=0; i < listas.length; i++){
            listadoEnumerado.put(i+1,listas[i]);
        }

        archivoElegido = "src/main/resources/ficheros/" + listadoEnumerado.get(opcionElegida);


        return archivoElegido;
    }
    public void agregarTarea() throws IOException {

        HashMap listaVieja = listarTareas("editar");

        String nuevaTarea = JOptionPane.showInputDialog(null, "Ingrese la tarea que desea agregar a la lista \n");
        if (nuevaTarea.isBlank()){
            JOptionPane.showMessageDialog(null,"Por favor ingrese una tarea");
            agregarTarea();
        }

        int ultimaClave = ultimoElemento(listaVieja);

       //con esto eliminamos cualquier valor null que haya surgido de una lista vacía
        if (listaVieja.containsKey(0)){
            listaVieja.remove(0);
        }

        //agregamos la nueva tarea al hashMap
        listaVieja.put(ultimaClave+1 , nuevaTarea);


        //esto escribe en una lista determinada el hashMap con la nueva tarea
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoElegido));

        for (int i = 1; i <= ultimoElemento(listaVieja); i++) {
            bw.write((String) listaVieja.get(i) + "\n");
        }

        bw.close();

        JOptionPane.showMessageDialog(null,"Lista agregada correctamente!");

    }

    public void eliminarTarea() throws IOException {
        //en desarrollo
        HashMap listaVieja = listarTareas("editar");

        int tareaElegida = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la tarea que desea borrar \n"+embellecedor(listaVieja)));

        listaVieja.remove(tareaElegida);

        //esto escribe en una lista determinada el hashMap con la nueva tarea
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoElegido));

        for (int i = 1; i <= ultimoElemento(listaVieja); i++) {
            if (!(listaVieja.get(i)==null)){
                bw.write((String) listaVieja.get(i) + "\n");
            }
        }
        bw.close();


    }

    public void crearLista() throws IOException {
        String nombreLista = JOptionPane.showInputDialog(null,"Ingrese el nombre de la lista que desea crear");

File file = new File("src/main/resources/ficheros/"+nombreLista+".txt");

        if (file.exists()){
            JOptionPane.showMessageDialog(null, "Ya existe una lista con el nombre '"+nombreLista+ "'. Por favor eliga otro.");
        }
        else {
            file.createNewFile();
            JOptionPane.showMessageDialog(null,"La lista "+nombreLista+" fue creada exitosamente");
        }

    }

    public void eliminarLista(){
        File archivo =  new File(rutaListaElegida("borrar"));

        if (archivo.exists()){
            archivo.delete();
        }else {
            JOptionPane.showMessageDialog(null,"La lista no existe. Intente con otra");
        }
    }

    public void leerLista() throws IOException {

        //esto verifica que la lista elegida tenga contenido, en caso contrario muestra un mensaje
        if (!listarTareas("leer").isEmpty()){
            JOptionPane.showMessageDialog(null,embellecedor(listarTareas("leer")));
        }else {
            JOptionPane.showMessageDialog(null,"Esta lista se encuentra vacia!");
        }

    }

    public String leerLista2() throws IOException {

                //esto verifica que la lista elegida tenga contenido, en caso contrario muestra un mensaje
        if (!listarTareas("leer").isEmpty()){
            return embellecedor(listarTareas("leer"));
        }else {
            return "Esta lista se encuentra vacia!";
        }

    }

    public void editarLista() throws IOException {

        int accionElegida = elegirAccion();


        switch (accionElegida){

            case 1:
                agregarTarea();
                break;

            case 2:
                eliminarTarea();
                break;

            default:
                JOptionPane.showMessageDialog(null,"Has ingresado una opción incorrecta, vuelve a intentarlo");
                elegirAccion();
        }



    }

    public String listarListasYTareas() throws IOException {
        String contenido = "";

        File file = new File("src/main/resources/ficheros/");

        String listas[] = file.list();

        for (int i = 0; i < listas.length; i++) {

            contenido = contenido + listas[i] +"\n" + embellecedor(listarTareas2(listas[i])) + "\n";
        }

        return contenido;
    }

}
