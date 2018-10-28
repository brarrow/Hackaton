package androidapp.hackaton.hackaton;

public class Status {
    private int status;
    private String string;

    public Status(int status, String string) {
        this.status = status;
        this.string = string;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
