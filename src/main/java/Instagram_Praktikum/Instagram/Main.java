package Instagram_Praktikum.Instagram;

import org.jinstagram.exceptions.InstagramException;

public class Main {
	public static void main(String[] args) throws InstagramException {
		NewInstagram newInstagram = new NewInstagram();
		newInstagram.login();
	}
}
