package dao;

import model.Equipe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {
    private List<Equipe> equipes;
    private int maxId = 0;

    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream outputFile;

    public int getMaxId() {
        return maxId;
    }

    public EquipeDAO(String filename) throws IOException {

        file = new File(filename);
        equipes = new ArrayList<Equipe>();
        if (file.exists()) {
            readFromFile();
        }

    }

    public void add(Equipe equipe) {
        try {
            equipes.add(equipe);
            this.maxId = (equipe.getId() > this.maxId) ? equipe.getId() : this.maxId;
            this.saveToFile();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar o cliente '" + equipe.getNomeEquipe() + "' no disco!");
        }
    }

    public Equipe get(int id) {
        for (Equipe equipe : equipes) {
            if (id == equipe.getId()) {
                return equipe;
            }
        }
        return null;
    }

    public void update(Equipe equipe) {
        int index = equipes.indexOf(equipe);
        if (index != -1) {
            equipes.set(index, equipe);
            this.saveToFile();
        }
    }

    public void remove(Equipe p) {
        int index = equipes.indexOf(p);
        if (index != -1) {
            equipes.remove(index);
            this.saveToFile();
        }
    }

    public List<Equipe> getAll() {
        return equipes;
    }

    private List<Equipe> readFromFile() {
        equipes.clear();
        Equipe equipe = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream inputFile = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                equipe = (Equipe) inputFile.readObject();
                equipes.add(equipe);
                maxId = (equipe.getId() > maxId) ? equipe.getId() : maxId;
            }
        } catch (Exception e) {
            System.out.println("ERRO ao gravar produto no disco!");
            e.printStackTrace();
        }
        return equipes;
    }

    private void saveToFile() {
        try {
            fos = new FileOutputStream(file, false);
            outputFile = new ObjectOutputStream(fos);

            for (Equipe equipe : equipes) {
                outputFile.writeObject(equipe);
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
