package com.emh.view.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.emh.util.Utility;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class FileUploader implements StartedListener, Receiver, SucceededListener, FailedListener, ProgressListener {

	private static final long serialVersionUID = 1L;
	private File file;
	private Upload upload;
	private ProgressBar progressBar;
	private String PATH;
	private String SUBPATH;
	private boolean allowed = false;
	
	private List<String> mimeTypes = Arrays.asList("image/bmp", "image/jpeg", "image/png", "image/pjpeg");

	public FileUploader() {
	}

	public FileUploader(ProgressBar progressBar, String PATH, String SUBPATH) {
		this.progressBar = progressBar;
		this.PATH = PATH;
		this.SUBPATH = SUBPATH;
	}
	
	@Override
	public void uploadStarted(StartedEvent event) {
		mimeTypes.forEach(mimeType -> {
			if(mimeType.equalsIgnoreCase(event.getMIMEType())) {
				allowed = true;
				return;
			}
		});
		if(!allowed) {
			Notification.show("Error", "\nAllowed MIME: " + event.getMIMEType() + ", Please upload image again.", Type.ERROR_MESSAGE);
			upload.interruptUpload();
		} 
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
		try {
			String basePath = PATH + SUBPATH;
			Utility.checkFolder(basePath);
			file = new File(PATH + event.getFilename());
			Utility.writeImage(file, basePath, "abc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Notification.show("Upload " + event.getFilename() + " file Succeeded.");
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		OutputStream outputStream = null;
		try {
			Utility.checkFolder(PATH);
			file = new File(PATH + filename);
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

	public Upload getUpload() {
		return upload;
	}

	public void setUpload(Upload upload) {
		this.upload = upload;
	}
}
