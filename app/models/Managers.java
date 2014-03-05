package models;

import javax.persistence.*;

import play.db.jpa.GenericModel;
import play.mvc.Http;
import utils.Encryption;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Entity
@Table(name="managers")
public class Managers extends GenericModel {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="user_id")
	public Long userId;
	@Column(name="last_login_pc_ip")
	public String lastLoginPcIp;
	@Column(name="last_login_pc_name")
	public String lastLoginPcName;
	@Column(name="user_name")
	public String userName;
	@Column(name="user_account")
	public String userAccount;
	@Column(name="user_pass")
	public String userPass;
	@Column(name="user_pass_salt")
	public String userPassSalt;

    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name = "t_roles_managers", inverseJoinColumns = @JoinColumn(name = "role_id"), joinColumns = @JoinColumn(name = "manager_id"))
    public List<ManagerRole> roles;
	
	private boolean verifyUserPass(String pass) {
		return Encryption.instance().validEncryptedChar(pass, this.userPass, this.userPassSalt);
	}
	
	public static Managers validateUser(String account,String pass){
		Managers u=Managers.find("userAccount=?", account).first();
		if(u!=null){
			if(u.verifyUserPass(pass)){
				return u;
			}else{
				return null;
			}
		}
		return null;
	}

    public void addRole(ManagerRole role) {
        if (!this.roles.contains(role)) {
            this.roles.add(role);
        }
    }

    public void removeRole(ManagerRole role) {
        if (this.roles.contains(role)) {
            this.roles.remove(role);
        }
    }

    public void generatorPass(String pass) {
        byte[] salt = Encryption.instance().nextSalt();
        this.userPass = Encryption.instance().getEncryptedChar(pass, salt);
        this.userPassSalt = Encryption.instance().byteToHexString(salt);
    }
}
