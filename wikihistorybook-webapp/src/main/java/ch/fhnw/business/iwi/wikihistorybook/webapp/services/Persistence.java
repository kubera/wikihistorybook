package ch.fhnw.business.iwi.wikihistorybook.webapp.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import ch.fhnw.business.iwi.wikihistorybook.graph.DBProvider;
import ch.fhnw.business.iwi.wikihistorybook.graph.GraphData;

/**
 * Encapsulates the persistence singleton instance and the byte streams written
 * to the file system.
 * 
 * @author Stefan Wagner
 * 
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class Persistence implements Serializable {

    private final static Logger LOGGER = Logger.getLogger(Persistence.class.getName());

    private static final String WIKIHISTORYBOOK_HOME = ".wikihistorybook/";
    private static final String FILE_PERSISTENCE = "files/";

    private static final long serialVersionUID = 1L;

    private transient DBProvider dbProvider;

    public DBProvider getDBProvider() {
        if (dbProvider == null) {
            dbProvider = DBProvider.getInstance();
        }
        return dbProvider;
    }

    public void disconnect() {
        getDBProvider().closeConnection();
    }

    public void initFilePersistence() {
        closeFilePersistence();
        try {
            if (!getFilePersistenceDir().mkdir()) {
                throw new IOException();
            }
        } catch (IOException e) {
            LOGGER.error("file persistence can not be created");
        }
    }

    public void closeFilePersistence() {
        File filePersistence = getFilePersistenceDir();
        if (filePersistence.exists()) {
            try {
                FileUtils.deleteDirectory(filePersistence);
            } catch (IOException e) {
                LOGGER.error("persistence files can not be deleted");
            }
        }

    }

    public boolean fileStreamExists(String imageName) {
        return getFilePersistence(imageName).exists();
    }

    public ByteArrayOutputStream readFileStream(String fileName) {
        File file = getFilePersistence(fileName);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            FileUtils.copyFile(file, output);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return output;
    }

    public void readGraphData(String graphName, GraphData graphData) {
        File file = getFilePersistence(graphName, ".graphdata");
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            graphData.readObject(ois);

        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } finally {
            closeStream(ois);
        }
    }

    public void writeFileStream(String fileName, ByteArrayOutputStream stream) {
        File file = getFilePersistence(fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            stream.writeTo(outputStream);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            closeStream(outputStream);
        }
    }

    public void writeAbsoluteGraphData(String graphName, GraphData graphData) {
        File file = getFilePersistence(graphName, ".graphdata");
        OutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            graphData.writeObject(oos);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            closeStream(fos);
        }
    }

    private File getFilePersistenceDir() {
        return getFilePersistence("");
    }

    private File getFilePersistence(String fileName) {
        return getFilePersistence(fileName, "");
    }

    private File getFilePersistence(String fileName, String postfix) {
        return new File(WIKIHISTORYBOOK_HOME + FILE_PERSISTENCE + fileName + postfix);
    }

    private void closeStream(ObjectInputStream objectInputStream) {
        if (objectInputStream != null) {
            try {
                objectInputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void closeStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

}
