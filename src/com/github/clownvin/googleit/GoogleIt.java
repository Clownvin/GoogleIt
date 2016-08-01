package com.github.clownvin.googleit;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

public final class GoogleIt {

	public static final void main(String[] args) throws IOException, InterruptedException {
		Process xselProcess = Runtime.getRuntime().exec("xsel -o");
		xselProcess.waitFor();
		Scanner scanner = new Scanner(xselProcess.getInputStream());
		scanner.useDelimiter(""+(char)-1);
		URL searchURL = null;
		try {
			searchURL = new URL("https://www.google.com/#q=" + scanner.next().trim().replace(" ", "+"));
			openWebpage(searchURL.toURI());
		} catch (HeadlessException | IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
		}
		scanner.close();
	}

	public static final void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
