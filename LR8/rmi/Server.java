package rmi;

import model.Participant;
import xml.DOMConverter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server implements ConfReg {
    private final List<Participant> participants;
    private static final String XML_FILE_PATH = "participants.xml";

    public Server() {
        // Use a synchronized list to ensure thread safety when multiple clients register participants concurrently
        this.participants = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized void registerParticipant(Participant participant) throws RemoteException {
        participants.add(participant);
        System.out.println("Registered participant: " + participant.getFirstName() + " " + participant.getLastName());
        
        // After registering a participant, export the updated list to XML
        try {
            DOMConverter.exportToXML(participants, "conference", "participant", XML_FILE_PATH);
            System.out.println("Data successfully exported to " + XML_FILE_PATH);
        } catch (Exception e) {
            System.err.println("Error exporting to XML: " + e.getMessage());
        }
    }

    @Override
    public List<Participant> getParticipants() throws RemoteException {
        return new ArrayList<>(participants);
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            ConfReg stub = (ConfReg) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.createRegistry(8080);
            registry.rebind("ConfReg", stub);

            System.out.println("RMI Server started.");
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}