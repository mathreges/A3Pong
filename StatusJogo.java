package cena;

import com.jogamp.newt.event.KeyEvent;

public class StatusJogo {
    public int status;
    private String resultado;
    public String status() {
        switch (status) {
            case 0:
                resultado = "iniciar";
                break;
            case 1:
                resultado = "pause";
                break;
            case 2:
                resultado = "parar";
                break;
            case 3:
                resultado = "vida";
                break;
            case 4:
                resultado = "perdeu";
                break;
        }

        return resultado;
    }
}