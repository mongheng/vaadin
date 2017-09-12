package com.emh.view.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class FileUploader implements Receiver, SucceededListener, FailedListener, ProgressListener {

	private static final long serialVersionUID = 1L;
	private File file;
	private ProgressBar progressBar;

	public FileUploader() {
	}

	public FileUploader(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		UI.getCurrent().access(new Runnable() {

			@Override
			public void run() {
				progressBar.setVisible(true);
				if (contentLength == -1) {
					progressBar.setIndeterminate(true);
				} else {
					float current = (float) readBytes / (float) contentLength;
					System.out.println("Current = " + current);
					progressBar.setIndeterminate(false);
					if (current <= 1.0f) {
						progressBar.setValue(current);
					}
					System.out.println(progressBar.getValue());
					progressBar.setCaption(((Integer) Math.round(current * 100)).toString() + "%");
				}
			}
		});

	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		Notification.show("Upload " + event.getFilename() + " file Succeeded.");
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		OutputStream outputStream = null;
		try {
			file = new File("C:/test/" + filename);
			outputStream = new FileOutputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return outputStream;
	}

	@Override
	public void uploadFailed(FailedEvent event) {
		Notification.show("Upload file failed : " + event.getFilename(), Type.ERROR_MESSAGE);
	}
}
