package ru.nsu.fit.trubinov.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class GitApi {
    private static final String githubLinkPrefix = "https://github.com/";
    private static final String githubLinkPostfix = ".git";
    private static final File saveClonedDirectory = new File(
            "./task_2_4_1/src/main/java/ru/nsu/fit/trubinov/git/cloned");

    public static void cloneRepository(String repositoryName) throws GitAPIException {
        try (Git git = Git.cloneRepository()
                .setURI(githubLinkPrefix + repositoryName + githubLinkPostfix)
                .setDirectory(saveClonedDirectory)
                .setCloneAllBranches(true)
                .call()) {
        }
    }
}
