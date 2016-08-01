package com.github.clownvin.googleit;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

public final class GoogleIt {

	public static final void main(String[] args) throws IOException, InterruptedException {
		if (args.length < 1) {
			openWebpage(getURIFromClipboard());
		} else {
			switch (args[0].toLowerCase()) {
			case "-p":
				if (args.length < 2) {
					openWebpage(getURIFromProgram("xsel -o"));
				} else {
					openWebpage(getURIFromProgram(args[1]));
				}
				break;
			case "-o":
				openWebpage(getURIFromOptionPane());
				break;
			default:
				System.out.println("Usage:");
				System.out.println("Use no arguments to have program get the search query from the clipboard.");
				System.out.println("-p [program [args]]    Gets the search query from specified program, or from xsel if none specified.");
				System.out.println("-o                     Gets the search query from a popup dialogue.");
				System.exit(2);
				break;
			}
		}
	}
	
	public static final URI getURIFromProgram(final String program) {
		Scanner scanner = null;
		try {
			Process process = Runtime.getRuntime().exec(program);
			process.waitFor();
			scanner = new Scanner(process.getInputStream());
			scanner.useDelimiter(""+(char)-1);
			URL searchURL = null;
			searchURL = new URL("https://www.google.com/#q=" + scanner.next().trim().replace(" ", "+"));
			return searchURL.toURI();
		} catch (HeadlessException | IOException | URISyntaxException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} finally {
			scanner.close();
		}
		return null;
	}
	
	public static final URI getURIFromOptionPane() {
		URL searchURL;
		try {
			searchURL = new URL("https://www.google.com/#q=" + JOptionPane.showInputDialog(null, "Please enter search query").trim().replace(" ", "+"));
			return searchURL.toURI();
		} catch (HeadlessException | IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return null;
	}
	
	public static final URI getURIFromClipboard() {
		URL searchURL;
		try {
			searchURL = new URL("https://www.google.com/#q=" + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().trim().replace(" ", "+"));
			return searchURL.toURI();
		} catch (HeadlessException | UnsupportedFlavorException | IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return null;
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
