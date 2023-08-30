package org.example.commit;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorCommit {

    public static void main(String[] args) throws IOException, GitAPIException {


        String caminhoGit = "C:/Users/Nova/Documents/Projetos/geradorCommit/.git";

        Repository repository = new FileRepositoryBuilder()
                .setGitDir(new File(caminhoGit))
                .readEnvironment()
                .findGitDir()
                .build();

        Git git = new Git(repository);

        int quantidadeCommit = 1;

        for (int i = 1; i < quantidadeCommit; i++) {

            String arquivoCommit = "file_" + i + ".txt";
            String conteudoArquivo = "Conteúdo do commit " + i;
            File file = new File(repository.getDirectory().getParent(), arquivoCommit);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(conteudoArquivo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            git.add().addFilepattern(arquivoCommit).call();

            PersonIdent autor = new PersonIdent("Ana-Ingrid", "ingridsantoscosta2003@gmail.com");
            PersonIdent commit = new PersonIdent("Ana-Ingrid", "ingridsantoscosta2003@gmail.com");

            git.commit()
                    .setAuthor(autor)
                    .setCommitter(commit)
                    .setMessage("Commit " + i)
                    .call();

            System.out.println("Commit " + i + " realizado.");
        }

        git.close();
        repository.close();
    }
}
