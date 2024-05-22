package service;

public class Settings {
    String name;
    String tool;
    String workspace;
    String folder;
    boolean update;
    boolean commit;
    boolean prevWork;

    public Settings(String name, String tool, String workspace, String folder, boolean update, boolean commit, boolean prevWork) {
        this.name = name;
        this.tool = tool;
        this.workspace = workspace;
        this.folder = folder;
        this.update = update;
        this.commit = commit;
        this.prevWork = prevWork;
    }

    public String getName() {
        return name;
    }

    public String getTool() {
        return tool;
    }

    public String getWorkspace() {
        return workspace;
    }

    public String getFolder() {
        return folder;
    }

    public boolean getUpdate() {
        return update;
    }

    public boolean getCommit() {
        return commit;
    }

    public boolean getPrevWork() {
        return prevWork;
    }
}
