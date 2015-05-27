package com.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.model.Filial;
import com.util.FileUtil;

@ViewScoped
@ManagedBean(name = "arquivoMB")
public class ArquivoMB extends AbstractMB implements Serializable {

	public static final String INJECTION_NAME = "#{arquivoMB}";
	private static final long serialVersionUID = 1L;
	public static final String EXTENCAO = ".xlsx";
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private UploadedFile arquivoUpload;
	private StreamedContent arquivoDownload;
	private Filial filial;
	
	/**
	 * @return
	 * @throws FileNotFoundException
	 */
	public void fazerDownload() throws FileNotFoundException{
		arquivoDownload = null;
		
		filial = userMB.getUser().getFilial_id();
		
		try {
			if(filial != null){
				String caminho = filial.getPathArquivo() + File.separator + filial.getNomeArquivo();
				
			    FileInputStream stream = new FileInputStream(caminho);      
			    arquivoDownload = new DefaultStreamedContent(stream, caminho, filial.getNomeArquivo()); 
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema realizar o download do arquivo. Favor tentar mais tarde.");
			e.printStackTrace();
		}
	}
	
	public void setArquivoUpload(UploadedFile arquivoUpload) {
		this.arquivoUpload = arquivoUpload;
		
		try {
			if (arquivoUpload != null && filial != null) {
				FileUtil.upload(filial);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fileUploadAction(FileUploadEvent event) {
		FileOutputStream saida = null;
		
		try {
        	
        	filial = userMB.getUser().getFilial_id();

        	String caminho = filial.getPathArquivo() + File.separator + filial.getNomeArquivo();
        	
            File file = new File(filial.getPathArquivo());
            if(!file.exists()){
            	file.mkdirs();
            }
            byte[] conteudo = event.getFile().getContents();
            
            saida = new FileOutputStream(caminho);
            saida.write(conteudo);

            String msg = "Arquivo recebido: " + filial.getNomeArquivo() +"</b>" + 
					"Tamanho do Arquivo: " + arquivoUpload.getSize() + ".";
			displayInfoMessageToUser(msg);
        } catch (Exception ex) {
        	keepDialogOpen();
			displayErrorMessageToUser("Desculpe, ocorreu um problema realizar o upload do arquivo. Favor tentar mais tarde.");
			ex.printStackTrace();
		}finally{
			// fechamos o arquivo
			try {
				saida.flush();
				saida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public UploadedFile getArquivoUpload() {
		return arquivoUpload;
	}

	public void setArquivoDownload(StreamedContent arquivoDownload) {
		this.arquivoDownload = arquivoDownload;
	}

	public StreamedContent getArquivoDownload() {
		if (arquivoDownload == null) {
			try {
				fazerDownload();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return arquivoDownload;
	}
	
	public void resetarArquivo(){
		arquivoUpload = null;
		arquivoDownload = null;
		filial = new Filial();
	}
	
	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

}

