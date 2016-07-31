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

import javax.swing.JOptionPane;

public final class GoogleIt {

	public static final void main(String[] args) {
		URL searchURL = null;
		try {
			searchURL = new URL("https://www.google.com/#q=" + Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().trim().replace(" ", "+"));
			openWebpage(searchURL.toURI());
		} catch (HeadlessException | UnsupportedFlavorException | IOException | URISyntaxException e) {
			JOptionPane.showMessageDialog(null, "The following exception has occured:\n\n "+e.getClass().getSimpleName(), "Exception Occured", JOptionPane.ERROR_MESSAGE);
		}
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
