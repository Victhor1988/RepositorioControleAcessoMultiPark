package com.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.model.Filial;

/**
 * @author Luiz
 * 
 */
public class FileUtil {

	private static String SERVIDOR = "184.107.67.241";
	private static String USUARIO = "multmovi";
	private static String SENHA = "muL03vi429";
	private static String DIRETORIO = "/public_ftp";
	
	public static void main2(String[] args) {  
        useFTP(SERVIDOR, DIRETORIO, "C:/Desenvolvimento/workspace/ControleAcessoMultiPark/WebContent/WEB-INF/arquivos/teste.txt",  
        		USUARIO, SENHA);  
    }  
  
    public static void useFTP(String ftpserver, String directoryName,  
        String filetoUpload, String username, String password) {  
        FTPClient ftpClient = new FTPClient();  
  
        try {  
            ftpClient.connect(ftpserver);  
            System.out.print(ftpClient.getReplyString());  
  
            // check reply code.  
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {  
                ftpClient.disconnect();  
                System.out.println("Connection refused.");  
                return;  
            }  
  
            ftpClient.login(username, password);  
            ftpClient.changeWorkingDirectory(directoryName);  
            
            System.out.println("Workdir >>" +  
                    ftpClient.printWorkingDirectory());  
  
            //Store file start  
            FileInputStream input = new FileInputStream(filetoUpload);  
            
            ftpClient.enterLocalActiveMode();
            ftpClient.enterLocalPassiveMode(); 
            
            if(ftpClient.storeFile( filetoUpload , input )){//enviando o arquivo
				System.out.println("sucesso");
			}else{
				System.out.println("erro");
			}
  
            //List all Files and directories  
            FTPFile[] files = ftpClient.listFiles();  
  
            if (files != null) {  
                for (int i = 0; i < files.length; i++) {  
                    //If file print name and size  
                    if (files[i].isFile()) {  
                        System.out.println("File >> " + files[i].getName() +  
                            "   Size >>" + files[i].getSize());  
                    }  
                }  
            }  
            ftpClient.logout();  
            ftpClient.disconnect();  
        } catch (IOException e) {  
            if (ftpClient.isConnected()) {  
                try {  
                    ftpClient.disconnect();  
                } catch (IOException f) {  
                    // do nothing  
                }  
            }  
  
            e.printStackTrace();  
        }  
    }  
	
	public static void main1(String[] args) {
		FTPClient ftp = new FTPClient();

		try {
			ftp.connect( SERVIDOR );
			ftp.enterLocalActiveMode();
			ftp.enterLocalPassiveMode(); 
            
			 //verifica se conectou com sucesso!  
            if( FTPReply.isPositiveCompletion( ftp.getReplyCode() ) ) {  
            	ftp.login( USUARIO, SENHA );  
            } else {  
                //erro ao se conectar  
                ftp.disconnect();  
                System.out.println("Conexão recusada");  
                System.exit(1);  
            }  

			ftp.changeWorkingDirectory ( DIRETORIO);
			
			ftp.setFileType( FTPClient.BINARY_FILE_TYPE );  
		    InputStream in = ftp.retrieveFileStream("RELATORIO_OPERACIONAL_PIER21.xlsx");  
		    
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static void main(String[] args) {
		Filial filial = new Filial();
		filial.setNomeArquivo("RELATORIO_OPERACIONAL_PIER21.xlsx");
		try {
			System.out.println(download(filial));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static InputStream  upload(Filial filial)
			throws MalformedURLException, IOException {
		InputStream inputStream = null;
		FTPClient ftp = new FTPClient();

		try {
			ftp.connect( SERVIDOR );
			ftp.enterLocalActiveMode();
			ftp.enterLocalPassiveMode(); 
            
			 //verifica se conectou com sucesso!  
            if( FTPReply.isPositiveCompletion( ftp.getReplyCode() ) ) {  
            	ftp.login( USUARIO, SENHA );  
            } else {  
                //erro ao se conectar  
                ftp.disconnect();  
                System.out.println("Conexão recusada");  
                System.exit(1);  
            }  

			ftp.changeWorkingDirectory ( DIRETORIO);
			ftp.setFileType( FTPClient.BINARY_FILE_TYPE );  
		    inputStream = ftp.retrieveFileStream(filial.getNomeArquivo());  
		    
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return inputStream;
	}

	public static String download(Filial filial)
			throws MalformedURLException, IOException {
		
		if (SERVIDOR != null) {
			
			StringBuffer sb = new StringBuffer("ftp://");
			sb.append(USUARIO);
			sb.append(':');
			sb.append(SENHA);
			sb.append('@');

			sb.append("ftp.multmovimentodiario.com.br/public_ftp/");
			sb.append(filial.getNomeArquivo());

			return sb.toString();
			
			/*
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			File destination = new File();
			try {
				URL url = new URL(sb.toString());
				URLConnection urlc = url.openConnection();

				bis = new BufferedInputStream(urlc.getInputStream());
				//bos = new BufferedOutputStream(new FileOutputStream(
				//		""));

				int i;
				while ((i = bis.read()) != -1) {
					bos.write(i);
				}
			} finally {
				if (bis != null)
					try {
						bis.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				if (bos != null)
					try {
						bos.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
			}
			*/
		} else {
			System.out.println("Input not available");
		}
		return null;
	}
	
	/**
	 * @param nomeArquivo
	 * @return
	 */
	private static String recuperarNomeArquivo(String nomeArquivo) {
		return recuperarDiretorioArquivo() + "/" + nomeArquivo;
	}
	
	/**
	 * @return
	 */
	private static String recuperarDiretorioArquivo() {
		//ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	
		return "/home/multmovi/public_html/luiz/";
		//return servletContext.getRealPath("WEB-INF/arquivos");
	}
}
