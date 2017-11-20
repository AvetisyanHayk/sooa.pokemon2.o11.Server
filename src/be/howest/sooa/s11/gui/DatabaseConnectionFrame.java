package be.howest.sooa.s11.gui;

import be.howest.sooa.o11.data.AbstractRepository;
import be.howest.sooa.o11.ex.ServerSocketException;
import be.howest.sooa.o11.socket.EchoServer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 *
 * @author Hayk
 */
public class DatabaseConnectionFrame extends javax.swing.JFrame {

    EchoServer server;

    public DatabaseConnectionFrame() {
        initComponents();
        addListeners();
    }

    private void addListeners() {
        addWindowOnClosingAdapter();
        addExitButtonActionListener();
        addConnectButtonActionListener();
        addDisconnectButtonActionListener();
        addTextFieldApplyOnEnterActionListener();
    }

    private void addWindowOnClosingAdapter() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                if (isConnected()) {
                    disconnectFromDatabaseAndCloseServerSocket();
                }
            }
        });
    }
    
    private boolean isConnected() {
        return server != null;
    }

    private void addExitButtonActionListener() {
        exitButton.addActionListener((ActionEvent e) -> {
            close();
        });
    }

    private void addConnectButtonActionListener() {
        connectButton.addActionListener((ActionEvent e) -> {
            connectButton.setEnabled(false);
            connectToDatabase();
        });
    }

    private void addDisconnectButtonActionListener() {
        disconnectButton.addActionListener((ActionEvent e) -> {
            disconnectButton.setEnabled(false);
            disconnectFromDatabaseAndCloseServerSocket();
        });
    }

    private void addTextFieldApplyOnEnterActionListener() {
        ActionListener actionListener
                = new TextFieldApplyOnEnterActionListener(this);
        hostnameField.addActionListener(actionListener);
        portField.addActionListener(actionListener);
        databaseField.addActionListener(actionListener);
        usernameField.addActionListener(actionListener);
        passwordField.addActionListener(actionListener);
    }

    private void connectToDatabase() {
        try {
            String driver = driversList.getSelectedItem().toString().toLowerCase(Locale.ENGLISH);
            String hostname = hostnameField.getText();
            String port = portField.getText();
            String useSSL = useSSLList.getSelectedItem().toString();
            if ("No".equals(useSSL)) {
                useSSL = "false";
            }
            String database = databaseField.getText();
            String user = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword()).trim();
            AbstractRepository.connect(driver, hostname, port, database, user, password, useSSL);
            confirmAuthenticationAndCreateServerSocket();
            showConnectedMessage();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            showConnectionWarning();
        }
    }

    private void disconnectFromDatabaseAndCloseServerSocket() {
        AbstractRepository.disconnect();
        server.stop();
        server.closeSocket();
        server = null;
        showDisconnectedMessage();
    }

    public void confirmAuthenticationAndCreateServerSocket() {
        try {
            server = new EchoServer(4444);
            server.start();
        } catch (ServerSocketException ex) {
            showWarning(ex.getMessage());
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this,
                message, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    private void showConnectionWarning() {
        setConnected(false);
        showWarning("Could not make connection to the database");
    }

    private void showConnectedMessage() {
        setConnected(true);
        JOptionPane.showMessageDialog(this,
                "Connected to the database", "Connected",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDisconnectedMessage() {
        setConnected(false);
        JOptionPane.showMessageDialog(this,
                "Disonnected from the database", "Disconnected",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void setConnected(boolean connected) {
        connectButton.setEnabled(!connected);
        disconnectButton.setEnabled(connected);
        driversList.setEnabled(!connected);
        hostnameField.setEnabled(!connected);
        portField.setEnabled(!connected);
        databaseField.setEnabled(!connected);
        usernameField.setEnabled(!connected);
        passwordField.setEnabled(!connected);
        useSSLList.setEnabled(!connected);
    }

    private void close() {
        setVisible(false);
        dispose();
    }

    void centerScreen(Window window) {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - window.getWidth()) / 2;
        final int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);
    }

    private void centerScreen() {
        centerScreen(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        driversList = new javax.swing.JComboBox<>();
        driversLabel = new javax.swing.JLabel();
        hostNameLabel = new javax.swing.JLabel();
        hostnameField = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portField = new javax.swing.JTextField();
        useSSLLabel = new javax.swing.JLabel();
        useSSLList = new javax.swing.JComboBox<>();
        usernameLabel = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        connectButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        databaseLabel = new javax.swing.JLabel();
        databaseField = new javax.swing.JTextField();
        disconnectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Connect to Database");
        setBackground(null);
        setResizable(false);

        driversList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MySQL" }));

        driversLabel.setText("Driver");

        hostNameLabel.setText("Host Name");

        hostnameField.setText("localhost");

        portLabel.setText("Port");

        portField.setText("3306");

        useSSLLabel.setText("Use SSL");

        useSSLList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No" }));

        usernameLabel.setText("Username:");

        usernameField.setText("student");

        passwordLabel.setText("Password");

        passwordField.setText("student");
        passwordField.setToolTipText("");

        connectButton.setText("Connect");

        exitButton.setText("Exit");

        databaseLabel.setText("Database");

        databaseField.setText("sooa_o10");

        disconnectButton.setText("Disconnect");
        disconnectButton.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(exitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(disconnectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(usernameLabel)
                            .addComponent(passwordLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(passwordField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(driversList, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(driversLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hostnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hostNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(portLabel)
                            .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(useSSLLabel)
                            .addComponent(useSSLList, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(databaseLabel)
                            .addComponent(databaseField))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driversLabel)
                    .addComponent(hostNameLabel)
                    .addComponent(portLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driversList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hostnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(useSSLLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(useSSLList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(databaseLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(databaseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(usernameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectButton)
                    .addComponent(exitButton)
                    .addComponent(disconnectButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            System.out.println(ex.getMessage());
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            DatabaseConnectionFrame frame = new DatabaseConnectionFrame();
            frame.centerScreen();
            frame.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JTextField databaseField;
    private javax.swing.JLabel databaseLabel;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JLabel driversLabel;
    private javax.swing.JComboBox<String> driversList;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel hostNameLabel;
    private javax.swing.JTextField hostnameField;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField portField;
    private javax.swing.JLabel portLabel;
    private javax.swing.JLabel useSSLLabel;
    private javax.swing.JComboBox<String> useSSLList;
    private javax.swing.JTextField usernameField;
    private javax.swing.JLabel usernameLabel;
    // End of variables declaration//GEN-END:variables

    private static final class TextFieldApplyOnEnterActionListener implements ActionListener {

        final DatabaseConnectionFrame dbConnectionDialog;

        TextFieldApplyOnEnterActionListener(DatabaseConnectionFrame dbConnectionDialog) {
            this.dbConnectionDialog = dbConnectionDialog;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dbConnectionDialog.connectButton.doClick();
        }

    }
}
