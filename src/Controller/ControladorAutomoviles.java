package Controller;

import Model.Automovil;
import Model.AutomovilDAO;
import Model.KmFueraDeRangoException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import View.FrmAutomoviles;
import java.util.List;

public class ControladorAutomoviles implements ActionListener {

    private Automovil objetoAutomovil = new Automovil();
    private AutomovilDAO objetoDAO = new AutomovilDAO();
    private FrmAutomoviles objetoVista = new FrmAutomoviles();

    @SuppressWarnings("LeakingThisInConstructor")
    public ControladorAutomoviles(FrmAutomoviles vista, AutomovilDAO dao) {
        objetoVista = vista;
        objetoDAO = dao;
        objetoVista.btnAgregar.addActionListener(this);
        objetoVista.btnMostrar.addActionListener(this);
        objetoVista.btnEliminar.addActionListener(this);
        objetoVista.btnModificar.addActionListener(this);
        objetoVista.btnBuscar.addActionListener(this);
    }

    public void llenarTabla(JTable tablaDatos) {
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaDatos.setModel(modeloT);
        modeloT.addColumn("Matricula");
        modeloT.addColumn("Marca");
        modeloT.addColumn("Modelo");
        modeloT.addColumn("Kilometraje");

        Object[] columna = new Object[4];
        int numReg = objetoDAO.obtenerAutomoviles().size();
        for (int i = 0; i < numReg; i++) {
            objetoAutomovil = objetoDAO.obtenerAutomoviles().get(i);
            columna[0] = objetoAutomovil.getMatricula();
            columna[1] = objetoAutomovil.getMarca();
            columna[2] = objetoAutomovil.getModelo();
            columna[3] = objetoAutomovil.getKm();
            modeloT.addRow(columna);
        }
    }

    private void eliminarAutomovil(JTable tablaDatos) {
        DefaultTableModel modeloT = (DefaultTableModel) tablaDatos.getModel();
        int filaSeleccionada = tablaDatos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(objetoVista, "Selecciona un automóvil para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String matricula = (String) modeloT.getValueAt(filaSeleccionada, 0);
            objetoDAO.eliminarAutomovil(matricula);
            modeloT.removeRow(filaSeleccionada);
        }
    }

    private void modificarAutomovil(JTable tablaDatos) {
        DefaultTableModel modeloT = (DefaultTableModel) tablaDatos.getModel();
        int filaSeleccionada = tablaDatos.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(objetoVista, "Selecciona un automóvil para modificar", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String matriculaAntigua = (String) modeloT.getValueAt(filaSeleccionada, 0);

            String nuevaMarca = JOptionPane.showInputDialog(objetoVista, "Nueva marca:", "Modificar Automóvil", JOptionPane.QUESTION_MESSAGE);
            String nuevoModelo = JOptionPane.showInputDialog(objetoVista, "Nuevo modelo:", "Modificar Automóvil", JOptionPane.QUESTION_MESSAGE);
            String nuevoKmStr = JOptionPane.showInputDialog(objetoVista, "Nuevo kilometraje:", "Modificar Automóvil", JOptionPane.QUESTION_MESSAGE);

            if (nuevaMarca == null || nuevoModelo == null || nuevoKmStr == null) {
                return;
            }

            if (nuevaMarca.trim().isEmpty() || nuevoModelo.trim().isEmpty() || nuevoKmStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(objetoVista, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int nuevoKm = Integer.parseInt(nuevoKmStr);
                    // Modificar el automóvil
                    objetoDAO.modificarAutomovil(matriculaAntigua, nuevaMarca.trim(), nuevoModelo.trim(), nuevoKm);

                    llenarTabla(objetoVista.tblAutos);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(objetoVista, "Ingrese un valor válido para el kilometraje", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    
    private void buscarAutomovil() {
    String[] opciones = { "Información del auto con mayor km", "Información de autos que superan un km ingresado",
                          "Información de autos de una marca específica", "Información de todos los autos ordenados por km" };

    String seleccion = (String) JOptionPane.showInputDialog(objetoVista, "Selecciona una opción:", "Buscar Automóvil", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

    switch (seleccion) {
        case "Información del auto con mayor km" -> {
            Automovil autoConMayorKm = objetoDAO.encontrarMayorKm();
            mostrarInformacion(autoConMayorKm);
            }

        case "Información de autos que superan un km ingresado" -> {
            int kmIngresado = obtenerKmIngresado();
            List<Automovil> autosSuperanKm = objetoDAO.obtenerAutosSuperanKm(kmIngresado);
            mostrarInformacion(autosSuperanKm);
            }

        case "Información de autos de una marca específica" -> {
            String marcaEspecifica = obtenerMarcaEspecifica();
            List<Automovil> autosMarcaEspecifica = objetoDAO.obtenerAutosPorMarca(marcaEspecifica);
            mostrarInformacion(autosMarcaEspecifica);
            }

        case "Información de todos los autos ordenados por km" -> {
            List<Automovil> autosOrdenadosPorKm = objetoDAO.obtenerAutomovilesOrdenadosPorKm();
            mostrarInformacion(autosOrdenadosPorKm);
            }

        default -> {
            }
    }
}

private int obtenerKmIngresado() {
    String kmStr = JOptionPane.showInputDialog(objetoVista, "Ingrese la cantidad de km:", "Buscar por Kilometraje", JOptionPane.QUESTION_MESSAGE);
    try {
        return Integer.parseInt(kmStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(objetoVista, "Ingrese un valor válido para el kilometraje", "Error", JOptionPane.ERROR_MESSAGE);
        return obtenerKmIngresado();
    }
}

private String obtenerMarcaEspecifica() {
    return JOptionPane.showInputDialog(objetoVista, "Ingrese la marca:", "Buscar por Marca", JOptionPane.QUESTION_MESSAGE);
}

private void mostrarInformacion(Automovil automovil) {
    if (automovil != null) {
        JOptionPane.showMessageDialog(objetoVista, obtenerInformacionAutomovil(automovil), "Información del Automóvil", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(objetoVista, "No se encontraron automóviles", "Información del Automóvil", JOptionPane.INFORMATION_MESSAGE);
    }
}

private void mostrarInformacion(List<Automovil> listaAutomoviles) {
    if (!listaAutomoviles.isEmpty()) {
        StringBuilder mensaje = new StringBuilder("Información de los Automóviles:\n\n");
        for (Automovil automovil : listaAutomoviles) {
            mensaje.append(obtenerInformacionAutomovil(automovil)).append("\n\n");
        }
        JOptionPane.showMessageDialog(objetoVista, mensaje.toString(), "Información de los Automóviles", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(objetoVista, "No se encontraron automóviles", "Información de los Automóviles", JOptionPane.INFORMATION_MESSAGE);
    }
}

private String obtenerInformacionAutomovil(Automovil automovil) {
    return "Matrícula: " + automovil.getMatricula() + "\nMarca: " + automovil.getMarca() + "\nModelo: " + automovil.getModelo() + "\nKilometraje: " + automovil.getKm();
}


   @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == objetoVista.btnAgregar) {
            String matricula = objetoVista.txtMatricula.getText();
            String marca = objetoVista.txtMarca.getText();
            String modelo = objetoVista.txtModelo.getText();
            String kmStr = objetoVista.txtKilometraje.getText();

            // Verificar si los campos no están vacíos
            if (matricula.trim().isEmpty() || marca.trim().isEmpty() || modelo.trim().isEmpty() || kmStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(objetoVista, "Por favor, completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int km = Integer.parseInt(kmStr);

                if (km < 1 || km > 1000) {
                    throw new KmFueraDeRangoException("Es necesario cuidar que la cantidad de Km debe ser entre 1 y 1000 km");
                }
                
                Automovil nuevoAutomovil = new Automovil(matricula, marca, modelo, km);
                objetoDAO.insertarAutomovil(nuevoAutomovil);
                
                llenarTabla(objetoVista.tblAutos);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(objetoVista, "Ingrese un valor válido para el kilometraje", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (KmFueraDeRangoException ex) {
                JOptionPane.showMessageDialog(objetoVista, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == objetoVista.btnMostrar) {
            llenarTabla(objetoVista.tblAutos);

        } else if (e.getSource() == objetoVista.btnEliminar) {
            eliminarAutomovil(objetoVista.tblAutos);

        } else if (e.getSource() == objetoVista.btnModificar) {
            modificarAutomovil(objetoVista.tblAutos);

        } else if (e.getSource() == objetoVista.btnBuscar) {
            buscarAutomovil();
        }
    }
}
