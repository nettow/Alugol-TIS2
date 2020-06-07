package dao;

import model.Aluguel;
import model.Cliente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AluguelDAO {
    private List<Aluguel> alugueis;
    private int maxId = 0;
    private File file;
    private FileOutputStream fos;
    private ObjectOutputStream outputFile;

    public int getMaxId() {
        return maxId;
    }

    public AluguelDAO(String filename) throws IOException {

        file = new File(filename);
        alugueis = new ArrayList<Aluguel>();
        if (file.exists()) {
            readFromFile();
        }

    }

    public void add(Aluguel aluguel) {
        try {
            alugueis.add(aluguel);
            this.maxId = (aluguel.getIdAluguel() > this.maxId) ? aluguel.getIdAluguel() : this.maxId;
            this.saveToFile();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar o cliente '" + aluguel.getIdAluguel() + "' no disco!");
        }
    }

    public Aluguel get(int id) {
        for (Aluguel aluguel : alugueis) {
            if (id == aluguel.getIdAluguel()) {
                return aluguel;
            }
        }
        return null;
    }

    public void update(Aluguel p) {
        int index = alugueis.indexOf(p);
        if (index != -1) {
            alugueis.set(index, p);
            this.saveToFile();
        }
    }

    public void remove(Aluguel p) {
        int index = alugueis.indexOf(p);
        if (index != -1) {
            alugueis.remove(index);
            this.saveToFile();
        }
    }

    public List<Aluguel> getAll() {
        return alugueis;
    }

    private List<Aluguel> readFromFile() {
        alugueis.clear();
        Aluguel aluguel = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream inputFile = new ObjectInputStream(fis)) {

            while (fis.available() > 0) {
                aluguel = (Aluguel) inputFile.readObject();
                alugueis.add(aluguel);
                maxId = (aluguel.getIdAluguel() > maxId) ? aluguel.getIdAluguel() : maxId;
            }
        } catch (Exception e) {
            System.out.println("ERRO ao gravar cliente no disco!");
            e.printStackTrace();
        }
        return alugueis;
    }

    private void saveToFile() {
        try {
            fos = new FileOutputStream(file, false);
            outputFile = new ObjectOutputStream(fos);

            for (Aluguel aluguel : alugueis) {
                outputFile.writeObject(aluguel);
            }

            outputFile.flush();
            this.close();
        } catch (Exception e) {
            System.out.println("ERRO ao gravar cliente no disco!");
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
