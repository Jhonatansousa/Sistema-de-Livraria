package Cliente;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cliente {
    private static int idCounter = 1;
    int idCliente;
    String nomeCliente;
    String emailCliente;

    public Cliente(String nomeCliente, String emailCliente) {
        this.idCliente = idCounter ++;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }




}
