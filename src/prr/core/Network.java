package prr.core;

import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import prr.core.exception.UnrecognizedEntryException;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202208091753L;

    // FIXME define attributes
    private Collection<Client> _clients;
    // private Collection<Terminal> _terminals;


    // FIXME define contructor(s)

    public Network() {
        _clients = new ArrayList<>();
    }



    // FIXME define methods
    /** @return container with all the clients of the network */
    public Collection<Client> getAllClients() {
        return new ArrayList<>(_clients);
    }

    /** Adds a client to the network */
    public void addClient(String key, String name, long ss) {
        _clients.add(new Client(key, name, ss));
    }
    /**
     * Read text input file and create corresponding domain entities.
     *
     * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
     * @throws IOException if there is an IO erro while processing the text file
     */
    void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
        //FIXME implement method
    }
}

