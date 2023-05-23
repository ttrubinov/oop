package ru.nsu.fit.trubinov.git;

import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitApi {
    private static final String githubLinkPrefix = "https://github.com/";
    private static final String githubLinkPostfix = ".git";
    private static final File saveClonedDirectory = new File(
            "./task_2_4_1/src/main/java/ru/nsu/fit/trubinov/git/cloned");

    public static Git cloneRepository(String repositoryName) throws GitAPIException {
        try (Git git = Git.cloneRepository()
                .setURI(githubLinkPrefix + repositoryName + githubLinkPostfix)
                .setDirectory(saveClonedDirectory)
                .setCloneAllBranches(true)
                .call()) {
            return git;
        }
    }

    public static boolean isOnBranch(Git git, String branchName) {
        try {
            return git.getRepository()
                    .getFullBranch()
                    .equals(branchName);
        } catch (IOException e) {
            return false;
        }
    }

    public static void checkout(Git git, String branchName) throws GitAPIException {
        git.checkout()
                .setName(branchName)
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK)
                .setStartPoint("origin/" + branchName)
                .call();
    }
}
