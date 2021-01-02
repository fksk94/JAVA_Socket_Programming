package client_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	// 2021.01.02 환율
	private static float USD_RATE = 1085.73F;
	private static float CNY_RATE = 166.19F;
	private static float BIT_RATE = 32467532.47F;
	
	public static void main(String[] args) {
		InputStream is;
		BufferedReader br;
		BufferedWriter bw;
		PrintWriter pw=null;
		OutputStream os;
		ServerSocket serverSocket;
		Socket s = null;
		try {
			serverSocket= new ServerSocket(23456);
			System.out.println("서버 실행 중");
			
			while(true){
				s= serverSocket.accept();
				is=s.getInputStream();
				os = s.getOutputStream();
				br=new BufferedReader(new InputStreamReader(is));
				String data=br.readLine();
				System.out.println("수신 데이터: "+data);
				String result=calculate(data);
				System.out.println(result);
				
				
				bw = new BufferedWriter(new OutputStreamWriter(os));
				pw=new PrintWriter(bw,true);
				pw.println(result);
				pw.close();
			}
		}catch(IOException ie) {
			ie.printStackTrace();
		}
		
	}
	
	private static  String calculate(String data){
		String []token=data.split(",");
		
		float won=Float.parseFloat(token[0]);
		String operator=token[1];
		String result=null;
		if(operator.equals("달러")){
			result=String.format("%.6f",won/USD_RATE);
		}else if(operator.equals("비트코인")){
			result=String.format("%.6f",won/BIT_RATE);
		}else if(operator.equals("위안")){
			result=String.format("%.6f",won/CNY_RATE);
		}
		
		return result;
			
	}
}
