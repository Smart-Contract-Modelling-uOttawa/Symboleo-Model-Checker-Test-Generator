package test_generator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Gui extends JFrame {
	Test test;
	private final JLabel lblNewLabel_3 = new JLabel("SMV output folder:");
	private final JTextField txt_out = new JTextField();
	private final JButton btn_submit = new JButton("Submit");
	private final JButton btn_cancel = new JButton("Cancel");
	private final JLabel lblNewLabel = new JLabel("nuXMV_file:");
	private final JTextField txt_nuxmv = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		txt_nuxmv.setBounds(270, 54, 163, 20);
		txt_nuxmv.setColumns(10);
		test = new Test();
		txt_out.setBounds(272, 21, 161, 20);
		txt_out.setColumns(10);
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 150);
		getContentPane().setLayout(null);
		
		lblNewLabel_3.setBounds(21, 21, 189, 14);
		
		getContentPane().add(lblNewLabel_3);
		
		getContentPane().add(txt_out);
		btn_submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				test.set_output_folder(txt_out.getText());
				test.set_nuxmv_file(txt_nuxmv.getText());
				
				try {
					test.generate();					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					test.run();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_submit.setBounds(10, 82, 89, 23);
		
		getContentPane().add(btn_submit);
		btn_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		btn_cancel.setBounds(115, 82, 89, 23);
		
		getContentPane().add(btn_cancel);
		lblNewLabel.setBounds(22, 57, 89, 14);
		
		getContentPane().add(lblNewLabel);
		
		getContentPane().add(txt_nuxmv);
	}
}
