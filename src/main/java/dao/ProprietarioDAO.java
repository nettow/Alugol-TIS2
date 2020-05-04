package dao;


import model.Cliente;
import model.Proprietario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {

        private List<Proprietario> proprietarios;
        private int maxId = 0;

        private File file;
        private FileOutputStream fos;
        private ObjectOutputStream outputFile;

        public int getMaxId() {
            return maxId;
        }

        public ProprietarioDAO(String filename) throws IOException {

            file = new File(filename);
            proprietarios = new ArrayList<Proprietario>();
            if (file.exists()) {
                readFromFile();
            }

        }

        public boolean verificaContaExiste(String email, String cpnj){
            for(Proprietario proprietario : proprietarios){
                if(proprietario.getEmail().equals(email)&&proprietario.getCPNJ().equals(cpnj)){
                    return true;
                }
            }
            return false;
    }
        public boolean verificaLogin(String email,String senha){
            for(Proprietario proprietario : proprietarios){
                if(proprietario.getEmail().equals(email)&&proprietario.getSenha().equals(senha)){
                return true;
                }
            }
            return false;
        }

        public int retornaID(String email, String senha){
            for(Proprietario proprietario : proprietarios){
                if(proprietario.getEmail().equals(email)&&proprietario.getSenha().equals(senha)){
                    return proprietario.getId();
                }
            }
            return 0;
        }

        public void add(Proprietario proprietario) {
            try {
                proprietarios.add(proprietario);
                this.maxId = (proprietario.getId() > this.maxId) ? proprietario.getId() : this.maxId;
                this.saveToFile();
            } catch (Exception e) {
                System.out.println("ERRO ao gravar o proprietario '" + proprietario.getRazaoSocial() + "' no disco!");
            }
        }

        public Proprietario get(int id) {
            for (Proprietario proprietario : proprietarios) {
                if (id == proprietario.getId()) {
                    return proprietario;
                }
            }
            return null;
        }

        public void update(Proprietario p) {
            int index = proprietarios.indexOf(p);
            if (index != -1) {
                proprietarios.set(index, p);
                this.saveToFile();
            }
        }

        public void remove(Proprietario p) {
            int index = proprietarios.indexOf(p);
            if (index != -1) {
                proprietarios.remove(index);
                this.saveToFile();
            }
        }

        public List<Proprietario> getAll() {
            return proprietarios;
        }

        private List<Proprietario> readFromFile() {
            proprietarios.clear();
            Proprietario proprietario = null;
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream inputFile = new ObjectInputStream(fis)) {

                while (fis.available() > 0) {
                    proprietario = (Proprietario) inputFile.readObject();
                    proprietarios.add(proprietario);
                    maxId = (proprietario.getId() > maxId) ? proprietario.getId() : maxId;
                }
            } catch (Exception e) {
                System.out.println("ERRO ao gravar produto no disco!");
                e.printStackTrace();
            }
            return proprietarios;
        }

        private void saveToFile() {
            try {
                fos = new FileOutputStream(file, false);
                outputFile = new ObjectOutputStream(fos);

                for (Proprietario proprietario : proprietarios) {
                    outputFile.writeObject(proprietario);
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


