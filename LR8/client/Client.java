package client;

import model.Participant;
import rmi.ConfReg;
import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client extends JFrame {
    private JTextField txtFirstName, txtLastName, txtOrg, txtTitle, txtSection;
    private ConfReg registryStub;

    public Client() {
        setTitle("RMI Conference Registration");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initRMI();
        initUI();
    }

    private void initRMI() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 8080);
            registryStub = (ConfReg) registry.lookup("ConfReg");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to RMI server:\n" + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("First Name:"));
        txtFirstName = new JTextField(); panel.add(txtFirstName);

        panel.add(new JLabel("Last Name:"));
        txtLastName = new JTextField(); panel.add(txtLastName);

        panel.add(new JLabel("Organization:"));
        txtOrg = new JTextField(); panel.add(txtOrg);

        panel.add(new JLabel("Presentation Title:"));
        txtTitle = new JTextField(); panel.add(txtTitle);

        panel.add(new JLabel("Section:"));
        txtSection = new JTextField(); panel.add(txtSection);

        JButton btnSubmit = new JButton("Submit Registration");
        btnSubmit.addActionListener(e -> sendRegistration());
        
        panel.add(new JLabel("")); // Empty cell for alignment
        panel.add(btnSubmit);

        add(panel);
    }

    private void sendRegistration() {
        try {
            // Forming the JavaBean component
            Participant participant = new Participant(
                    txtFirstName.getText().trim(),
                    txtLastName.getText().trim(),
                    txtOrg.getText().trim(),
                    txtTitle.getText().trim(),
                    txtSection.getText().trim()
            );

            // Calling the remote method
            registryStub.registerParticipant(participant);
            
            JOptionPane.showMessageDialog(this, "Participant successfully registered on the server!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error occurred during registration:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtFirstName.setText("");
        txtLastName.setText("");
        txtOrg.setText("");
        txtTitle.setText("");
        txtSection.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client().setVisible(true));
    }
}