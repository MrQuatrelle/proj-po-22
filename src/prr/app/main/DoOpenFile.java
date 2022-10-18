package prr.app.main;

import prr.core.NetworkManager;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.ImportFileException;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import java.io.FileNotFoundException;
import java.io.IOException;
//Add more imports if needed

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

    DoOpenFile(NetworkManager receiver) {
        super(Label.OPEN_FILE, receiver);
        addStringField("filename", Message.openFile());
    }

    @Override
    protected final void execute() throws CommandException {
      try {
          _receiver.load(stringField("filename"));
      } catch (UnavailableFileException | ClassNotFoundException | FileNotFoundException | ImportFileException e) {
          throw new FileOpenFailedException(e);
      }
    }
}
