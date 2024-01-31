package Model;

import Controller.ControladorAutomoviles;
import View.FrmAutomoviles;
import View.FrmAutomoviles.LookAndFeelUtils;

public class GestorListasAutomoviles {

    public static void main(String[] args) {
        
        LookAndFeelUtils.setLookAndFeel();

        AutomovilDAO automovilDAO = new AutomovilDAO();
        FrmAutomoviles frmAutomoviles = new FrmAutomoviles();
        ControladorAutomoviles controlador = new ControladorAutomoviles(frmAutomoviles, automovilDAO);
        
        java.awt.EventQueue.invokeLater(() -> {
            frmAutomoviles.setVisible(true);
        });
    }
}
