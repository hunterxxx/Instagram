package Instagram_Praktikum.Instagram;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jinstagram.exceptions.InstagramException;

public class Main {
	public static void main(String[] args) throws MalformedURLException, IOException {
		NewInstagram newInstagram = new NewInstagram();
		newInstagram.likesCount();
	}
}
