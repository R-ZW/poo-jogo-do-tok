import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Inicia o jogo
        SwingUtilities.invokeLater(() -> {
            new Jogo();
        });
    }
}
