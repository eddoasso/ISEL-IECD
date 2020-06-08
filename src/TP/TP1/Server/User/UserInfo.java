package TP.TP1.Server.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserInfo {
	/**
	 * Classe basica que carrega as informacoes de utilizador para a memoria.
	 * 
	 * Nao é segura!!!!
	 * 
	 */

	private String userEncryptedPassword;
	private String userSalt;
	private final String userName;

	public UserInfo(String userName, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.userName = userName;
		this.setUserSalt(gerarSal());
		this.setUserEncryptedPassword(password);

	}
	// METODO NAO SEGURO, NAO USAR, PARA OUTRAS COISAS QUE NAO SEJAM
	// VERIFICACOES
	public UserInfo(String userName, String hash, String salt) {
		this.userName = userName;
		this.userEncryptedPassword = hash;
		this.userSalt = salt;
	}

	public static String encriptarPassWord(String password, String userSalt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		String algorithm = "PBKDF2WithHmacSHA1";
		int derivedKeyLength = 160; // for SHA1
		int iterations = 20000; // NIST specifies 10000

		byte[] saltBytes = Base64.getDecoder().decode(userSalt);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes,
				iterations, derivedKeyLength);
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

		byte[] encBytes = f.generateSecret(spec).getEncoded();
		return Base64.getEncoder().encodeToString(encBytes);
	}

	private String gerarSal() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// NIST recommends minimum 4 bytes. We use 8.
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public String getUserName() {
		return userName;
	}

	public String getUserEncryptedPassword() {
		return userEncryptedPassword;
	}

	public void setUserEncryptedPassword(String userEncryptedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		this.setUserSalt(gerarSal());
		this.userEncryptedPassword = encriptarPassWord(userEncryptedPassword,
				getUserSalt());
	}

	public String getUserSalt() {
		return userSalt;
	}

	public void setUserSalt(String userSalt) {
		this.userSalt = userSalt;
	}

	@Override
	public String toString() {
		return String.format("User: %s%nhash: %s%nSalt: %s%n", getUserName(),
				getUserEncryptedPassword(), getUserSalt());
	}
}
