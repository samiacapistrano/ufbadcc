//#if ${Produto} == "T"
package br.com.webstore.features;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import br.com.webstore.facade.GenericFacade;
import br.com.webstore.model.Faq;
import br.com.webstore.model.Produto;
import br.com.webstore.model.Usuario;

public class ProdutoPesquisa extends JPanel {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -5464321145565350625L;

	public static final String NAME = "Produtos";

	private JTextField textField;
	private JScrollPane scrollPane;
	// private static JTable tableList;

	private JTable table;

	/**
	 * Create the panel.
	 */
	
	public ProdutoPesquisa(final GenericFacade gfacade, final Usuario usuarioLogado) {
		this.setLayout(null);

		this.textField = new JTextField();
		this.textField.setToolTipText("Informe o nome da categoria.");
		this.textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		this.textField.setBounds(6, 53, 480, 20);
		this.add(this.textField);
		this.textField.setColumns(10);

		JLabel lblNomeDaCategoria = new JLabel("Nome do Produto");
		lblNomeDaCategoria.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNomeDaCategoria.setBounds(6, 24,106, 14);
		this.add(lblNomeDaCategoria);

		JLabel lblRelaoDeCategorias = new JLabel("Produtos");
		lblRelaoDeCategorias.setBounds(6, 140, 175, 14);
		this.add(lblRelaoDeCategorias);

		this.scrollPane = new JScrollPane();
		// this.scrollPane.setToolTipText("");
		this.scrollPane.setBounds(6, 165, 480, 99);

		final Vector<String> headers = new Vector<String>(3);
		headers.addElement(new String("Id"));
		headers.addElement(new String("Cod."));
		headers.addElement(new String("Nome"));
		headers.addElement(new String("Valor"));
		
		this.table = new JTable();
		
		this.scrollPane.setViewportView(this.table);
		// this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// this.table.setBounds(57, 165, 353, 99);
		this.add(this.scrollPane);
		//listar inicialmente o produto 
		List<Produto> lista = new GenericFacade().findProduto("");
		DefaultTableModel modelListProduto = new DefaultTableModel(headers, lista.size());				
		ProdutoPesquisa.this.table.setModel(modelListProduto);
		int row = 0;
		for (Produto produto : lista) {
			ProdutoPesquisa.this.table.getModel().setValueAt(produto.getId(), row, 0);
			ProdutoPesquisa.this.table.getModel().setValueAt(produto.getNumero(), row, 1);
			ProdutoPesquisa.this.table.getModel().setValueAt(produto.getDescricao(), row, 2);
			String valor = NumberFormat.getCurrencyInstance().format(produto.getValor());
			ProdutoPesquisa.this.table.getModel().setValueAt(valor, row, 3);
			row++;
		}
		ProdutoPesquisa.this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
		ProdutoPesquisa.this.table.getColumnModel().getColumn(1).setPreferredWidth(60);
		ProdutoPesquisa.this.table.getColumnModel().getColumn(2).setPreferredWidth(290);
		ProdutoPesquisa.this.table.getColumnModel().getColumn(3).setPreferredWidth(130);
		
	// fim do listar inicial 

		JButton btnCadastrp = new JButton("Pesquisar");
		btnCadastrp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Produto> lista = new GenericFacade().findProduto(ProdutoPesquisa.this.textField.getText());
				
				DefaultTableModel model = new DefaultTableModel(headers, lista.size());				
				ProdutoPesquisa.this.table.setModel(model);
				

				int row = 0;
				for (Produto produto : lista) {
					ProdutoPesquisa.this.table.getModel().setValueAt(produto.getId(), row, 0);
					ProdutoPesquisa.this.table.getModel().setValueAt(produto.getNumero(), row, 1);
					ProdutoPesquisa.this.table.getModel().setValueAt(produto.getDescricao(), row, 2);
					String valor = NumberFormat.getCurrencyInstance().format(produto.getValor());
					ProdutoPesquisa.this.table.getModel().setValueAt(valor, row, 3);
					row++;
				}
				ProdutoPesquisa.this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
				ProdutoPesquisa.this.table.getColumnModel().getColumn(1).setPreferredWidth(60);
				ProdutoPesquisa.this.table.getColumnModel().getColumn(2).setPreferredWidth(290);
				ProdutoPesquisa.this.table.getColumnModel().getColumn(3).setPreferredWidth(130);
			
			}
		});
		btnCadastrp.setBounds(312, 84, 96, 23);		
		this.add(btnCadastrp);
		
		if(usuarioLogado.getPerfil().getDescricao().equals("Admin")){
		
			JButton btnNewButton = new JButton("Cadastro");
			btnNewButton.setBounds(80, 84, 89, 23);
			btnNewButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					ProdutoEdit pe = new ProdutoEdit();				
					final JDialog frame = new JDialog();				
					pe.setDoneEvent(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							frame.dispose();
							List<Produto> lista = new GenericFacade().findProduto(ProdutoPesquisa.this.textField.getText());
							
							DefaultTableModel model = new DefaultTableModel(headers, lista.size());				
							ProdutoPesquisa.this.table.setModel(model);
							
	
							int row = 0;
							for (Produto produto : lista) {
								ProdutoPesquisa.this.table.getModel().setValueAt(produto.getId(), row, 0);
								ProdutoPesquisa.this.table.getModel().setValueAt(produto.getNumero(), row, 1);
								ProdutoPesquisa.this.table.getModel().setValueAt(produto.getDescricao(), row, 2);
								String valor = NumberFormat.getCurrencyInstance().format(produto.getValor());
								ProdutoPesquisa.this.table.getModel().setValueAt(valor, row, 3);
								row++;
							}	
							ProdutoPesquisa.this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
							ProdutoPesquisa.this.table.getColumnModel().getColumn(1).setPreferredWidth(60);
							ProdutoPesquisa.this.table.getColumnModel().getColumn(2).setPreferredWidth(290);
							ProdutoPesquisa.this.table.getColumnModel().getColumn(3).setPreferredWidth(130);
						}
					});				
					frame.setModal(true);
					frame.setResizable(false);
					frame.setBounds(400, 200, 460, 320);
					frame.getContentPane().add(pe);
					frame.setVisible(true);
				}
	
			});
	
			this.add(btnNewButton);
	
			JButton btnEditar = new JButton("Editar");
			btnEditar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ListSelectionModel lsm = ProdutoPesquisa.this.table.getSelectionModel();
					int index = lsm.getLeadSelectionIndex();
					
					if (index == -1) {
						JOptionPane.showMessageDialog(null, "Selecione um item antes de editar.");
					} else {
						Integer id = (Integer) ProdutoPesquisa.this.table.getValueAt(index, 0);
						
						ProdutoEdit pe = new ProdutoEdit(id);
						
						final JDialog frame = new JDialog();
						
						pe.setDoneEvent(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								frame.dispose();
								List<Produto> lista = new GenericFacade().findProduto(ProdutoPesquisa.this.textField.getText());
								
								DefaultTableModel model = new DefaultTableModel(headers, lista.size());				
								ProdutoPesquisa.this.table.setModel(model);
								
	
								int row = 0;
								for (Produto produto : lista) {
									ProdutoPesquisa.this.table.getModel().setValueAt(produto.getId(), row, 0);
									ProdutoPesquisa.this.table.getModel().setValueAt(produto.getNumero(), row, 1);
									ProdutoPesquisa.this.table.getModel().setValueAt(produto.getDescricao(), row, 2);
									String valor = NumberFormat.getCurrencyInstance().format(produto.getValor());
									ProdutoPesquisa.this.table.getModel().setValueAt(valor, row, 3);
									row++;
								}					
								ProdutoPesquisa.this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
								ProdutoPesquisa.this.table.getColumnModel().getColumn(1).setPreferredWidth(60);
								ProdutoPesquisa.this.table.getColumnModel().getColumn(2).setPreferredWidth(290);
								ProdutoPesquisa.this.table.getColumnModel().getColumn(3).setPreferredWidth(130);
								
							}
						});
						
						frame.setModal(true);
						frame.setResizable(false);
						frame.setBounds(400, 200, 460, 320);
						frame.getContentPane().add(pe);
						frame.setVisible(true);
					}
					
				}
			});
			btnEditar.setBounds(430, 85, 79, 23);
			this.add(btnEditar);
	
			JButton btnExcluir = new JButton("Excluir");
			btnExcluir.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ListSelectionModel lsm = ProdutoPesquisa.this.table.getSelectionModel();
					int index = lsm.getLeadSelectionIndex();
					
					if (index == -1) {
						JOptionPane.showMessageDialog(null, "Selecione um item antes de excluir.");
					} else {
						
						if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Excluir item selecionado?", "Excluir?", JOptionPane.YES_NO_OPTION)) {
							Integer id = (Integer) ProdutoPesquisa.this.table.getValueAt(index, 0);
							gfacade.removerProduto(id);
							List<Produto> lista = new GenericFacade().findProduto(ProdutoPesquisa.this.textField.getText());
							
							DefaultTableModel model = new DefaultTableModel(headers, lista.size());				
							ProdutoPesquisa.this.table.setModel(model);
							
	
							int row = 0;
							for (Produto produto : lista) {
								ProdutoPesquisa.this.table.getModel().setValueAt(produto.getId(), row, 0);
								ProdutoPesquisa.this.table.getModel().setValueAt(produto.getNumero(), row, 1);
								ProdutoPesquisa.this.table.getModel().setValueAt(produto.getDescricao(), row, 2);
								String valor = NumberFormat.getCurrencyInstance().format(produto.getValor());
								ProdutoPesquisa.this.table.getModel().setValueAt(valor, row, 3);
								row++;
							}
							ProdutoPesquisa.this.table.getColumnModel().getColumn(0).setPreferredWidth(40);
							ProdutoPesquisa.this.table.getColumnModel().getColumn(1).setPreferredWidth(60);
							ProdutoPesquisa.this.table.getColumnModel().getColumn(2).setPreferredWidth(290);
							ProdutoPesquisa.this.table.getColumnModel().getColumn(3).setPreferredWidth(130);
						}
	
					}
				}
			});
			btnExcluir.setBounds(195, 84, 89, 23);
			this.add(btnExcluir);
		
		}
	}
}
//#endif
