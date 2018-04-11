package yashaswi.cartappandroid;

/**
 * Created by madan on 4/28/2016.
 */
public class User {
    private int _id;
    private String uname;
    private String pwd;
    private String fname;
    private String phoneNumer;
    private String gender;

    public User(int _id, String uname, String pwd, String fname, String phoneNumer, String gender) {
        this._id = _id;
        this.uname = uname;
        this.pwd = pwd;
        this.fname = fname;
        this.phoneNumer = phoneNumer;
        this.gender = gender;
    }

    public int get_id() {
        return _id;
    }

    public User() {
    }

    public User(String uname, String pwd, String fname, String phoneNumer, String gender) {
        this.uname = uname;
        this.pwd = pwd;
        this.fname = fname;
        this.phoneNumer = phoneNumer;
        this.gender = gender;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPhoneNumer() {
        return phoneNumer;
    }

    public void setPhoneNumer(String phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
