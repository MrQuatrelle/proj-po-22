package prr.core;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import prr.core.exception.*;

//FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Manage access to network and implement load/save operations.
 */
public class NetworkManager implements Serializable {

    /** The network itself. */
    private Network _network = new Network();
    //FIXME  addmore fields if needed
    private String _filename;
    public Network getNetwork() {
        return _network;
    }

    public void setFilename(String filename){
        _filename = filename;
    }

    public String getFilename() {
        return _filename;
    }
    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException, FileNotFoundException,
            ClassNotFoundException, ImportFileException {

        try(FileInputStream fileIn = new FileInputStream(filename)) {
            try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
                _network = (Network) in.readObject();
                setFilename(filename);
            } catch (IOException e) {
                throw new ImportFileException(filename, e);
            }
        } catch (IOException e) {
            throw new UnavailableFileException(filename);
        }
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        this.saveAs(this.getFilename());
    }

    /**
     * Saves the serialized application's state into the specified file. The current network is
     * associated to this file.
     *
     * @param filename the name of the file.
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        try(FileOutputStream fileOut = new FileOutputStream(filename)){
            try(ObjectOutputStream out = new ObjectOutputStream(fileOut)){
                out.writeObject(_network);
                setFilename(filename);
            }
        }
    }

    /**
     * Read text input file and create domain entities..
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            _network.importFile(filename);
        } catch (IOException | UnrecognizedEntryException | UnallowedTypeException |
                 DuplicateException | UnallowedKeyException e) {
            throw new ImportFileException(filename, e);
        }
    }
}
