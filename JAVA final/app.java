import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class app {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}


class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public LoginFrame() {
        setTitle("Inicio de Sesión - Proyecto Final");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 247, 250));

        JLabel lblTitulo = new JLabel("Bienvenido al Sistema", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(33, 97, 140));
        panel.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBackground(new Color(245, 247, 250));

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword = new JPasswordField();

        panelCampos.add(lblUsuario);
        panelCampos.add(txtUsuario);
        panelCampos.add(lblPassword);
        panelCampos.add(txtPassword);
        panel.add(panelCampos, BorderLayout.CENTER);

        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(33, 97, 140));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        btnLogin.addActionListener(e -> verificarLogin());
        panel.add(btnLogin, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void verificarLogin() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        if (usuario.equals("admin") && password.equals("071727")) {
            new MainFrame(usuario);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


class MainFrame extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JPanel panelUsuarios, panelConfiguracion, panelAyuda;
    private String usuario;

    public MainFrame(String usuario) {
        this.usuario = usuario;
        setTitle("Sistema de Gestión - Proyecto Final");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemSalir = new JMenuItem("Salir", UIManager.getIcon("OptionPane.errorIcon"));
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Segoe UI", Font.BOLD, 13));

        crearPanelUsuarios();
        crearPanelConfiguracion();
        crearPanelAyuda();

        pestañas.addTab("Usuarios", UIManager.getIcon("FileView.directoryIcon"), panelUsuarios);
        pestañas.addTab("Configuración", UIManager.getIcon("OptionPane.informationIcon"), panelConfiguracion);
        pestañas.addTab("Ayuda", UIManager.getIcon("OptionPane.questionIcon"), panelAyuda);

        add(pestañas);
        setVisible(true);
    }

    
    private void crearPanelUsuarios() {
        panelUsuarios = new JPanel(new BorderLayout(10, 10));
        panelUsuarios.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelUsuarios.setBackground(Color.WHITE);

     
        JLabel lblTitulo = new JLabel("Gestión de Usuarios", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(33, 97, 140));
        panelUsuarios.add(lblTitulo, BorderLayout.NORTH);


        String[] columnas = {"ID", "Nombre", "Edad"};
        Object[][] datos = {
            {1, "Ana", 22},
            {2, "Luis", 25},
            {3, "María", 30}
        };
        modeloTabla = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setRowHeight(22);
        panelUsuarios.add(new JScrollPane(tabla), BorderLayout.CENTER);

      
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(Color.WHITE);

        panelInferior.add(new JLabel("Agregar nombre:"));
        JTextField txtNombre = new JTextField(12);
        panelInferior.add(txtNombre);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(46, 134, 193));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            if (!nombre.isEmpty()) {
                modeloTabla.addRow(new Object[]{modeloTabla.getRowCount() + 1, nombre, 0});
                txtNombre.setText("");
            }
        });

        panelInferior.add(btnAgregar);

        panelInferior.add(new JLabel("Rol:"));
        JRadioButton rbA = new JRadioButton("Admin");
        JRadioButton rbB = new JRadioButton("Invitado");
        rbA.setBackground(Color.WHITE);
        rbB.setBackground(Color.WHITE);
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbA);
        grupo.add(rbB);
        panelInferior.add(rbA);
        panelInferior.add(rbB);

        panelUsuarios.add(panelInferior, BorderLayout.SOUTH);
    }

  
    private void crearPanelConfiguracion() {
        panelConfiguracion = new JPanel(new BorderLayout());
        panelConfiguracion.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelConfiguracion.setBackground(Color.WHITE);

        JLabel lbl = new JLabel("Configuración de Tema", SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelConfiguracion.add(lbl, BorderLayout.NORTH);

        JPanel opciones = new JPanel();
        opciones.setBackground(Color.WHITE);

        JButton btnClaro = new JButton("Modo Claro");
        JButton btnOscuro = new JButton("Modo Oscuro");

        btnClaro.setBackground(new Color(52, 152, 219));
        btnClaro.setForeground(Color.WHITE);
        btnOscuro.setBackground(new Color(44, 62, 80));
        btnOscuro.setForeground(Color.WHITE);

        opciones.add(btnClaro);
        opciones.add(btnOscuro);
        panelConfiguracion.add(opciones, BorderLayout.CENTER);

        btnClaro.addActionListener(e -> cambiarTema(Color.WHITE));
        btnOscuro.addActionListener(e -> cambiarTema(new Color(44, 62, 80)));
    }

    private void cambiarTema(Color colorFondo) {
        getContentPane().setBackground(colorFondo);
        JOptionPane.showMessageDialog(this, "Tema actualizado correctamente.");
    }

    private void crearPanelAyuda() {
        panelAyuda = new JPanel(new BorderLayout());
        panelAyuda.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelAyuda.setBackground(Color.WHITE);

        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        texto.setText("👋 Bienvenido, " + usuario + "!\n\n" +
                "Esta aplicación fue desarrollada como parte del curso 'Construcción de Software I'.\n" +
                "Aquí puedes gestionar usuarios, probar componentes Swing y aplicar GitHub.\n\n" +
                "Equipo de desarrollo:\n - [ESNEIDER CASTRILLÓN]\n - \n\n" +
                "Docente: [IVAN DARIO ZAPATA]\n" +
                "Fecha de presentación: Noviembre 2025.");

        panelAyuda.add(new JScrollPane(texto), BorderLayout.CENTER);
    }
}
