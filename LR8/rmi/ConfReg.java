package rmi;

import model.Participant;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ConfReg extends Remote {
    void registerParticipant(Participant participant) throws RemoteException;
    List<Participant> getParticipants() throws RemoteException;
}