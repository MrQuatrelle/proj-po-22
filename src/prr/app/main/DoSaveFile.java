package prr.app.main;

import prr.app.exception.FileOpenFailedException;
import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

import java.io.IOException;

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

    DoSaveFile(NetworkManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() throws FileOpenFailedException {
        try {
            if (_receiver.getFilename() != null) {
                _receiver.save();
            } else {
                _receiver.saveAs(Form.requestString(Message.newSaveAs()));
            }
        } catch (IOException | MissingFileAssociationException e) {
            throw new FileOpenFailedException(e);
        }
    }
}
