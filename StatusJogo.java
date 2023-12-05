package cena;

public class StatusJogo {
    public int status;
    private String resultado;
    public String status() {
        switch (status) {
            case 0:
                resultado = "jogando";
                break;
            case 1:
                resultado = "pausado";
                break;
            case 2:
                resultado = "parado";
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