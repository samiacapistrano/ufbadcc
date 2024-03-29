/**
 * 
 */
package br.com.webstore.features;

import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import br.com.webstore.facade.GenericFacade;
import br.com.webstore.model.Usuario;
import br.com.webstore.model.Perfil;
import br.com.webstore.model.StatusUsuario;


/**
 * @author webstore
 *
 */
public class UsuarioComumEdicao extends JPanel 
{
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -5464321145565350625L;

	public static final String NAME = "Cliente Edicao";
	
	private boolean editMode = false;
	private Integer Id;
	
	private JFormattedTextField loginFld;
	private JTextField nomeFld;
	private JPasswordField senhaFld;
	private JFormattedTextField emailFld;
	private JFormattedTextField dtNascimentoFld;
	private JFormattedTextField telefoneFld;

	private ActionListener doneEvent;
	private String tmpLogin;
	
	private JButton salvarBtn;
	
	public void setDoneEvent(ActionListener doneEvent) 
	{
		this.doneEvent = doneEvent;
	}
	
	public Usuario toModel() 
	{
		Usuario usuario = new Usuario();
		
		if (this.editMode) 
		{
			usuario.setId(this.Id);
		}
		else
		{
			Perfil perfil = new Perfil();
			perfil.setId(2);
			perfil.setDescricao("Cliente");
			
			StatusUsuario statusUsuario = new StatusUsuario();
			statusUsuario.setId(1);
			statusUsuario.setDescricao("Ativo");
			
			usuario.setPerfil(perfil);
			usuario.setStatusUsuario(statusUsuario);
			usuario.setDsLogin(this.loginFld.getText());
			usuario.setDataInclusao(new Date());
		}
		
		usuario.setNome(this.nomeFld.getText());
		usuario.setEmail(this.emailFld.getText());
		usuario.setSenha(new String(this.senhaFld.getPassword()));		
		usuario.setTelefone(this.telefoneFld.getText());
		
	    String pattern = "dd/MM/yyyy";
	    DateFormat df = new SimpleDateFormat(pattern);
	    
	    try 
	    {
	    	Date date = df.parse(this.dtNascimentoFld.getText());
	    	usuario.setDataNascimento(date);
	    }
	    catch(Exception e)
	    {
	    }
				
		return usuario;
	}
	
	public boolean validateFields() 
	{
		
		if ("".equals(this.nomeFld.getText())) 
		{
			JOptionPane.showMessageDialog(null, "Descricao deve ser preenchido");
			return false;
		}
		
		if ("".equals(this.emailFld.getText())) 
		{
			JOptionPane.showMessageDialog(null, "E-mail deve ser preenchido");
			return false;
		}
		
		Usuario usr = new GenericFacade().getUsuarioByLogin(this.loginFld.getText());
		
		if (usr!=null && !tmpLogin.equals(usr.getDsLogin()))
		{
			JOptionPane.showMessageDialog(null, "Login ja cadastrado.");
			return false;
		}
		
		return true;
	}
	
	public void EnviarEmail(Usuario usuario)
	{
		String message = "Parabens! \n\n  Seu cadastrado foi realizado com sucesso.";
		 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		final String userName = "dccreuso@gmail.com";
		final String password = "dccufba123";
		Session session = Session.getInstance(props, new javax.mail.Authenticator()
		{
		    protected PasswordAuthentication getPasswordAuthentication() 
		    {
		        return new PasswordAuthentication(userName, password);
		    }
		});
	    try 
	    {
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress("dccreuso@gmail.com"));
	        msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(usuario.getEmail()));
	        msg.setSubject("Contato");
	        msg.setSentDate(new Date());
	        msg.setText("De: "+"dccreuso@gmail.com"+"\n"+message);
	        Transport.send(msg);
	        JOptionPane.showMessageDialog(null, "Notificacao de cadastro enviada com sucesso.");
	    } 
	    catch (MessagingException mex) 
	    {
	        System.out.println("send failed, exception: " + mex);
	        JOptionPane.showMessageDialog(null, "Notificacao de cadastro nao pode ser enviada.");
	    }
	}
	
	/**
	 * Create the panel.
	 */
	public UsuarioComumEdicao() 
	{
		this.setLayout(null);
		
		// Login		
		JLabel loginLbl = new JLabel("Login");
		loginLbl.setBounds(7, 16, 100, 15);
		loginLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.add(loginLbl);
				
		this.loginFld = new JFormattedTextField();
		this.loginFld.setBounds(7, 29, 219, 25);
		this.loginFld.setToolTipText("Informe o Login.");
		this.loginFld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.loginFld.setColumns(10);
		this.add(this.loginFld);
				
		// Nome
		JLabel nomeLbl = new JLabel("Nome");
		nomeLbl.setBounds(7, 61, 100, 15);
		nomeLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.add(nomeLbl);
				
		this.nomeFld = new JTextField();
		this.nomeFld.setBounds(7, 74, 438, 25);
		this.nomeFld.setToolTipText("Informe o nome.");
		this.nomeFld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.nomeFld.setColumns(10);
		this.add(this.nomeFld);
			
		// Email
		JLabel emailLbl = new JLabel("E-mail");
		emailLbl.setBounds(7, 106, 100, 15);
		emailLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.add(emailLbl);
						
		this.emailFld = new JFormattedTextField();
		this.emailFld.setBounds(7, 119, 438, 25);
		this.emailFld.setToolTipText("Informe o E-mail.");
		this.emailFld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.emailFld.setColumns(10);
		this.add(this.emailFld);
				
		// Senha
		JLabel SenhaLbl = new JLabel("Senha");
		SenhaLbl.setBounds(7, 151, 100, 15);
		SenhaLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.add(SenhaLbl);
				
		this.senhaFld = new JPasswordField();
		this.senhaFld.setBounds(7, 164, 219, 25);
		this.senhaFld.setToolTipText("Informe o Senha.");
		this.senhaFld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.senhaFld.setColumns(10);
		this.add(this.senhaFld);

		// Data de Nascimento Label
		JLabel DtNascimentoLbl = new JLabel("Data de Nascimento");
		DtNascimentoLbl.setBounds(7, 196, 100, 15);
		DtNascimentoLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.add(DtNascimentoLbl);
		
		try
		{
		    javax.swing.text.MaskFormatter dataNascimento = new MaskFormatter("##/##/####");
		    this.dtNascimentoFld = new JFormattedTextField(dataNascimento);
		}
		catch(Exception e)
		{
		};
		
		// Data de Nascimento TextField
		this.dtNascimentoFld = new JFormattedTextField();
		this.dtNascimentoFld.setBounds(7, 209, 438, 25);
		this.dtNascimentoFld.setToolTipText("Informe a data de nascimento.");
		this.dtNascimentoFld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.dtNascimentoFld.setColumns(10);
		this.add(this.dtNascimentoFld);
		
		// Telfone Label
		JLabel TelefoneLbl = new JLabel("Telefone");
		TelefoneLbl.setBounds(7, 241, 100, 15);
		TelefoneLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.add(TelefoneLbl);
				
		try
		{
		    javax.swing.text.MaskFormatter telefone = new MaskFormatter("(##) ####-####");
		    this.telefoneFld = new JFormattedTextField(telefone);
		}
		catch(Exception e)
		{
		};    
		
		// Telefone TextField
		this.telefoneFld.setBounds(7, 258, 438, 25);
		this.telefoneFld.setToolTipText("Informe o telefone.");
		this.telefoneFld.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.telefoneFld.setColumns(10);
		this.add(this.telefoneFld);	
		
		// Botao Salvar
		this.salvarBtn = new JButton("Salvar");
		this.salvarBtn.setBounds(190, 400, 100, 32);
		this.salvarBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.salvarBtn.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (UsuarioComumEdicao.this.validateFields()) 
				{
					Usuario usuario = UsuarioComumEdicao.this.toModel();
					if (UsuarioComumEdicao.this.editMode) 
					{
						new GenericFacade().updateUsuario(usuario);
					}
					else
					{
						new GenericFacade().insertUsuario(usuario);
						EnviarEmail(usuario);
					}
					if (UsuarioComumEdicao.this.doneEvent != null) 
					{
						UsuarioComumEdicao.this.doneEvent.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, e.getActionCommand()));
					}
				}
			}
		});
		this.add(this.salvarBtn);
	}
	
	public UsuarioComumEdicao(Integer id) 
	{
		this();
		this.editMode = true;
		this.Id = id;
		Usuario usuario = new GenericFacade().getUsuarioById(this.Id);
		
		this.loginFld.setEnabled(false);
		this.nomeFld.setText(usuario.getNome());
		this.emailFld.setText(usuario.getEmail());
		this.senhaFld.setText(usuario.getSenha());
		
	    if (usuario.getDataNascimento() != null)
	    {
	    	try
	    	{
	    		String pattern = "dd/MM/yyyy";
		        DateFormat df = new SimpleDateFormat(pattern);
			    this.dtNascimentoFld.setText(df.format(usuario.getDataNascimento()));
		    }
	    	catch(Exception e)
	    	{
	    	}
	    }   	
		if (usuario.getTelefone()!=null)
		{
		     this.telefoneFld.setText(usuario.getTelefone());
		}	
	}
}
