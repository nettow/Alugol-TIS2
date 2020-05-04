package dao;

import model.Cliente;
import model.Quadra;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuadraDAO {
    private List<Quadra> quadras;
    private int maxId = 0;

    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream outputFile;

    public int getMaxId() {
        return maxId;
    }

    public QuadraDAO(String filename) throws IOException {

        file = new File(filename);
        quadras = new ArrayList<Quadra>();
        if (file.exists()) {
            readFromFile();
        }

    }

    public void add(Quadra quadra) {
        try {
            quadras.add(quadra);
            this.maxId = (quadra.getId() > this.maxId) ? quadra.getId() : this.maxId;
            this.saveToFile();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar o cliente '" + quadra.getNomeQuadra() + "' no disco!");
        }
    }

    public Quadra get(int id) {
        for (Quadra quadra : quadras) {
            if (id == quadra.getId()) {
                return quadra;
            }
        }
        return null;
    }

    public void update(Quadra p) {
        int index = quadras.indexOf(p);
        if (index != -1) {
            quadras.set(index, p);
            this.saveToFile();
        }
    }

    public void remove(Quadra p) {
        int index = quadras.indexOf(p);
        if (index != -1) {
            quadras.remove(index);
            this.saveToFile();
        }
    }

    public List<Quadra> getAll() {
        return quadras;
    }

    private List<Quadra> readFromFile() {
        quadras.clear();
        Quadra quadra = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream inputFile = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                quadra = (Quadra) inputFile.readObject();
                quadras.add(quadra);
                maxId = (quadra.getId() > maxId) ? quadra.getId() : maxId;
            }
        } catch (Exception e) {
            System.out.println("ERRO ao gravar produto no disco!");
            e.printStackTrace();
        }
        return quadras;
    }

    private void saveToFile() {
        try {
            fos = new FileOutputStream(file, false);
            outputFile = new ObjectOutputStream(fos);

            for (Quadra quadra : quadras) {
                outputFile.writeObject(quadra);
            }
            outputFile.flush();
            this.close();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar produto no disco!");
            e.printStackTrace();
        }
    }

    private void close() throws IOException {
        outputFile.close();
        fos.close();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            this.saveToFile();
            this.close();
        } catch (Exception e) {
            System.out.println("ERRO ao salvar a base de dados no disco!");
            e.printStackTrace();
        }
    }
}
