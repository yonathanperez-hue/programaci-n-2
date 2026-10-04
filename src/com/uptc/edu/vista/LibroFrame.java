package com.uptc.edu.vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import javax.swing.JOptionPane;
import com.uptc.edu.modelo.Libro;

import com.uptc.edu.negocio.ILibroService;
import com.uptc.edu.negocio.LibroService;

public class LibroFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    // Capa de negocio
    private ILibroService libroService;

    // Campos del formulario
    private JTextField txtIsbn;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtAnio;
    private JTextField txtEditorial;
    private JTextField txtPaginas;
    private JTextField txtPrecio;
    private JTextField txtCantidad;

    private JComboBox<String> cmbCategoria;
    private JComboBox<String> cmbFormato;

    // Botones
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    // Tabla
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public LibroFrame() {

        libroService = new LibroService();

        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {

        setTitle("Tienda Virtual de Libros - Gestión de Libros");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void crearComponentes() {

        // Panel principal del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(10, 2, 5, 5));

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Datos del libro"));

        txtIsbn = new JTextField();
        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtAnio = new JTextField();
        txtEditorial = new JTextField();
        txtPaginas = new JTextField();
        txtPrecio = new JTextField();
        txtCantidad = new JTextField();

        cmbCategoria = new JComboBox<>(new String[] {
                "Ciencia Ficción",
                "Historia",
                "Tecnología",
                "Literatura",
                "Educación",
                "Otro"
        });

        cmbFormato = new JComboBox<>(new String[] {
                "Físico",
                "Digital"
        });

        panelFormulario.add(new JLabel("ISBN:"));
        panelFormulario.add(txtIsbn);

        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Año de publicación:"));
        panelFormulario.add(txtAnio);

        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cmbCategoria);

        panelFormulario.add(new JLabel("Editorial:"));
        panelFormulario.add(txtEditorial);

        panelFormulario.add(new JLabel("Número de páginas:"));
        panelFormulario.add(txtPaginas);

        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Cantidad disponible:"));
        panelFormulario.add(txtCantidad);

        panelFormulario.add(new JLabel("Formato:"));
        panelFormulario.add(cmbFormato);

        // Panel de botones
        JPanel panelBotones = new JPanel();

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        String[] columnas = {
                "ISBN",
                "Título",
                "Autor",
                "Año",
                "Categoría",
                "Editorial",
                "Páginas",
                "Precio",
                "Cantidad",
                "Formato"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);

        tablaLibros = new JTable(modeloTabla);

        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        scrollTabla.setBorder(
                BorderFactory.createTitledBorder("Catálogo de libros"));

        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());

        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Agregar componentes a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
        
        btnAgregar.addActionListener(e -> agregarLibro());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void agregarLibro() {

        try {

            // Validar campos obligatorios
            if (txtIsbn.getText().trim().isEmpty()
                    || txtTitulo.getText().trim().isEmpty()
                    || txtAutor.getText().trim().isEmpty()
                    || txtAnio.getText().trim().isEmpty()
                    || txtEditorial.getText().trim().isEmpty()
                    || txtPaginas.getText().trim().isEmpty()
                    || txtPrecio.getText().trim().isEmpty()
                    || txtCantidad.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Todos los campos son obligatorios.",
                        "Validación",
                        JOptionPane.WARNING_MESSAGE);

                return;
            }

            String isbn = txtIsbn.getText().trim();
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();

            int anio = Integer.parseInt(txtAnio.getText().trim());

            String categoria =
                    cmbCategoria.getSelectedItem().toString();

            String editorial = txtEditorial.getText().trim();

            int paginas =
                    Integer.parseInt(txtPaginas.getText().trim());

            double precio =
                    Double.parseDouble(txtPrecio.getText().trim());

            int cantidad =
                    Integer.parseInt(txtCantidad.getText().trim());

            String formato =
                    cmbFormato.getSelectedItem().toString();

            // Validaciones de negocio básicas
            if (anio <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El año de publicación debe ser válido.");
                return;
            }

            if (paginas <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El número de páginas debe ser mayor que cero.");
                return;
            }

            if (precio <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "El precio debe ser mayor que cero.");
                return;
            }

            if (cantidad < 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad no puede ser negativa.");
                return;
            }

            Libro libro = new Libro(
                    isbn,
                    titulo,
                    autor,
                    anio,
                    categoria,
                    editorial,
                    paginas,
                    precio,
                    cantidad,
                    formato);

            boolean registrado =
                    libroService.agregarLibro(libro);

            if (registrado) {

                actualizarTabla();
                limpiarCampos();

                JOptionPane.showMessageDialog(
                        this,
                        "Libro registrado correctamente.");

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe un libro con el ISBN " + isbn,
                        "ISBN duplicado",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Año, páginas, precio y cantidad deben contener valores numéricos válidos.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarTabla() {

        modeloTabla.setRowCount(0);

        for (Libro libro : libroService.listarLibros()) {

            Object[] fila = {
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getAnioPublicacion(),
                    libro.getCategoria(),
                    libro.getEditorial(),
                    libro.getNumeroPaginas(),
                    libro.getPrecio(),
                    libro.getCantidadDisponible(),
                    libro.getFormato()
            };

            modeloTabla.addRow(fila);
        }
    }
    
    private void limpiarCampos() {

        txtIsbn.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtAnio.setText("");
        txtEditorial.setText("");
        txtPaginas.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");

        cmbCategoria.setSelectedIndex(0);
        cmbFormato.setSelectedIndex(0);

        txtIsbn.requestFocus();
    }
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LibroFrame ventana = new LibroFrame();
            ventana.setVisible(true);

        });
    }
}