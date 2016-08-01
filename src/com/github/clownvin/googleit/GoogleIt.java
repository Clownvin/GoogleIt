package com.github.clownvin.googleit;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

public final class GoogleIt {

	public static final void main(String[] args) throws IOException, InterruptedException {
		String query = "";
		if (args.length < 1) {
			query = getQueryFromClipboard();
		} else {
			switch (args[0].toLowerCase()) {
			case "-p":
				if (args.length < 2) {
					query = getQueryFromProgram("xsel -o");
				} else {
					query = getQueryFromProgram(args[1]);
				}
				break;
			case "-o":
				query = getQueryFromOptionPane();
				break;
			default:
				System.out.println("Usage:");
				System.out.println("Use no arguments to have program get the search query from the clipboard.");
				System.out.println("-p [program [args]]    Gets the search query from specified program, or from xsel if none specified.");
				System.out.println("-o                     Gets the search query from a popup dialogue.");
				System.exit(2);
			}
		}
		openWebpage(getGoogleSearchURI(query));
	}
	
	public static final String getQueryFromProgram(final String program) {
		Scanner scanner = null;
		try {
			Process process = Runtime.getRuntime().exec(program);
			process.waitFor();
			scanner = new Scanner(process.getInputStream());
			scanner.useDelimiter(""+(char)-1);
			return scanner.next().trim().replace(" ", "+");
		} catch (HeadlessException | IOException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} finally {
			scanner.close();
		}
		throw new RuntimeException("Something really bad happened...");
	}
	
	public static final String getQueryFromOptionPane() {
		return JOptionPane.showInputDialog(null, "Please enter search query").trim().replace(" ", "+");
	}
	
	public static final String getQueryFromClipboard() {
		try {
			return Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().trim().replace(" ", "+");
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		throw new RuntimeException("Something really bad happened...");
	}
	
	public static final URI getGoogleSearchURI(String query) {
		URL searchURL;
		try {
			searchURL = new URL("https://www.google.com/#q=" + query);
			return searchURL.toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		throw new RuntimeException("Something really bad happened...");
	}

	public static final void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
	}

}
