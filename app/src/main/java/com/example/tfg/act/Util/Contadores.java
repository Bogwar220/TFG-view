package com.example.tfg.act.Util;

public class Contadores {
    public static int dia;

    public static String getDia(int dia){
        String diaStr = "";
        switch(dia){
            case 1:
                diaStr = "Lunes";
                break;
            case 2:
                diaStr = "Martes";
                break;
            case 3:
                diaStr = "Miercoles";
                break;
            case 4:
                diaStr = "Jueves";
                break;
            case 5:
                diaStr = "Viernes";
                break;
            case 6:
                diaStr = "Sabado";
                break;
            case 7:
                diaStr = "Domingo";
                break;
        }
        return diaStr;
    }
}
