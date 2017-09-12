package com.emh.view;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.emh.model.Role;
import com.emh.model.User;
import com.emh.repository.business.ClassBusiness;
import com.emh.util.Utility;
import com.emh.view.upload.FileUploader;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SignupView extends Window {

	private static final long serialVersionUID = 1L;

	private ApplicationContext applicationContext;
	
	private VerticalLayout vSignup;
	private FormLayout formLayout;
	private HorizontalLayout hSignup;
	private HorizontalLayout hBar;
	private Binder<User> binder;

	private TextField username;
	private PasswordField password;
	private PasswordField repeatPassword;
	private TextField telephone;
	private TextField email;
	private Button btnCreate;
	private Button btnCancel;
	private ComboBox<Role> cbRole;
	private Upload upload;
	private ProgressBar progressBar;

	public SignupView(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		Init();
	}

	private void Init() {
		setCaption("Create User");
		vSignup = new VerticalLayout();
		formLayout = new FormLayout();
		hSignup = new HorizontalLayout();
		hBar = new HorizontalLayout();
		binder = new Binder<>();
		progressBar = new ProgressBar();

		username = new TextField("UserName :");
		username.setIcon(VaadinIcons.USER);
		username.setRequiredIndicatorVisible(true);
		username.focus();
		binder.forField(username)
				.withValidator(username -> username.length() >= 2, "Full name must contain at least two characters")
				.bind(User::getUsername, User::setUsername);

		password = new PasswordField("Password :");
		password.setIcon(VaadinIcons.PASSWORD);
		password.setRequiredIndicatorVisible(true);
		binder.bind(password, User::getPassword, User::setPassword);

		repeatPassword = new PasswordField("Repeat Password :");
		repeatPassword.setIcon(VaadinIcons.PASSWORD);
		repeatPassword.setRequiredIndicatorVisible(true);
		repeatPassword.addBlurListener(new PasswordExitFocus());

		telephone = new TextField("Telephone :");
		telephone.setIcon(VaadinIcons.PHONE);
		// telephone.setRequiredIndicatorVisible(true);
		binder.bind(telephone, User::getTelephone, User::setTelephone);

		email = new TextField("Email :");
		email.setIcon(VaadinIcons.MAILBOX);
		email.setRequiredIndicatorVisible(true);
		email.addBlurListener(new EmailExitFocus());
		binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
				.bind(User::getEmail, User::setEmail);

		cbRole = new ComboBox<>();
		cbRole.setCaption("Access Role :");
		cbRole.setIcon(VaadinIcons.GROUP);
		cbRole.setRequiredIndicatorVisible(true);
		cbRole.setItems(Utility.getRoles(applicationContext));
		cbRole.setItemCaptionGenerator(Role::getRoleName);
		binder.bind(cbRole, User::getRole, User::setRole);

		btnCreate = new Button("Create");
		btnCreate.addStyleName("friendly");
		btnCreate.addClickListener(new CreateClickListener());

		btnCancel = new Button("Cancel");
		btnCancel.addStyleName("primary");
		
		btnCancel.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		
		upload = new Upload("Please Select File.", new FileUploader(progressBar));
		upload.setButtonCaption("Upload");
		FileUploader receiver = new FileUploader(progressBar);
		upload.addSucceededListener(receiver);
		upload.addProgressListener(receiver);
		upload.addFailedListener(receiver);
		
		hSignup.addComponents(btnCreate, btnCancel);
		hSignup.setComponentAlignment(btnCreate, Alignment.TOP_RIGHT);
		hSignup.setWidth("400px");
		hSignup.setSpacing(true);

		hBar.addComponents(progressBar);
		
		formLayout.addComponents(username, password, repeatPassword, telephone, email, cbRole, upload, hBar);
		vSignup.addComponents(formLayout, hSignup);
		progressBar.setVisible(false);
		// vSignup.setHeight("95%");

		setContent(vSignup);
		center();
		//setModal(true);
		//setResizable(false);

		setHeight("465px");
		setWidth("400px");
	}

	private final class PasswordExitFocus implements BlurListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void blur(BlurEvent event) {

			String tfPassword = password.getValue();
			String tfRepeatPassword = repeatPassword.getValue();

			if (!tfPassword.equals(tfRepeatPassword)) {
				Notification.show("Please fill the password again becuase the password is not match.",
						Type.HUMANIZED_MESSAGE);
				password.clear();
				repeatPassword.clear();
				password.focus();
			}
		}
	}

	private final class EmailExitFocus implements BlurListener {

		private static final long serialVersionUID = 1L;
		private Notification notification;

		@Override
		public void blur(BlurEvent event) {

			ClassBusiness classBusiness = (ClassBusiness) applicationContext
					.getBean(ClassBusiness.class.getSimpleName());
			notification = new Notification("Information", "The email has already Exist. Please choose another one.",
					Type.WARNING_MESSAGE);
			notification.setDelayMsec(500);
			notification.setStyleName("mynotificationstyle");

			List<User> users = classBusiness.selectAllEntity(User.class);

			if (users.size() > 0) {
				users.forEach(user -> {
					if (user.getEmail().equals(email.getValue())) {
						notification.show(Page.getCurrent());
						email.focus();
						setWidth("450px");
					}
				});
			}
		}
	}

	private final class CreateClickListener implements ClickListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {

			ClassBusiness classBusiness = (ClassBusiness) applicationContext
					.getBean(ClassBusiness.class.getSimpleName());

			User userbind = new User();
			try {
				binder.writeBean(userbind);
			} catch (ValidationException e) {
				e.printStackTrace();
			}

			User user = new User(null, username.getValue(), password.getValue(), telephone.getValue(),
					email.getValue());
			Role r = cbRole.getSelectedItem().get();
			if (user.getUsername() != null && user.getPassword() != null && r != null) {
				user.setRole(r);
				classBusiness.createEntity(user);
				Notification.show("The data save successfully in Data Source.", Type.HUMANIZED_MESSAGE);
				close();
				UI.getCurrent().getNavigator().navigateTo("mainpageview");
			} else {
				Notification.show("Please fill in the blank.", Type.HUMANIZED_MESSAGE);
			}
		}
	}
}
