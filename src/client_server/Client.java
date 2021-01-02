package client_server;

import java.net.Socket;
import java.io.*;
import javax.swing.*;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame{
	JLabel title = new JLabel("원");
	JTextField operand = new JTextField(10);
	String[] opExpression = {"선택", "달러", "비트코인", "위안"};
	JComboBox<String> opSelection = new JComboBox<String>(opExpression);
	JTextField txtResult = new JTextField(10);
	JButton btnClear = new JButton("다시입력");
	
	public Client() {
		Container cp = this.getContentPane();
		cp.setLayout(new FlowLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	private void startFrame(){
		opSelection.addActionListener(new SelectionHandler());
		btnClear.addActionListener(new SelectionHandler());
		this.setTitle("클라이언트 프로그램");
		this.add(operand);
		this.add(opSelection);
		this.add(txtResult);
		this.add(btnClear);
		this.setSize(700,200);
		
	}
	
	class SelectionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==opSelection){
				calculate();
			}else if(e.getSource()==btnClear){
			  init();
			}
			
		}
	}
	
	private void init(){
		operand.setText("");
		txtResult.setText("");
	}
	
	private void calculate(){
		float won=Integer.parseInt(operand.getText());
		String result=null;
		String operator=opSelection.getSelectedItem().toString();
		
		 InputStream is;
		 BufferedReader br;
		 BufferedWriter bw;
		 OutputStream os;
		 PrintWriter pw=null;
		 
		try{
			Socket s=new Socket("127.0.0.1", 23456);
			os = s.getOutputStream();
			is=s.getInputStream();
			System.out.println("전송데이터:"+won+","+operator);
			
			
			bw = new BufferedWriter(new OutputStreamWriter(os));
			pw=new PrintWriter(bw,true);
		    pw.println(won+","+operator);
		    
		    br=new BufferedReader(new InputStreamReader(is));
			result=br.readLine();
			System.out.println("클라이언트 수신 데이터:"+result);
			txtResult.setText(result);
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Client machine=new Client();
		machine.startFrame();
		
	}
}
