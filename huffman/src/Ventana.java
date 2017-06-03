

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class Ventana extends JFrame implements ActionListener {

	
	private Huffman huffman;
	private JTextArea entrada;
	private JTextArea salida;
	private JLabel vista;
	private JLabel res;
	private JButton Comprimir;
	private JButton Descomprimir;
	private JButton Limpiar;
	private JButton abrir;
    JFileChooser abrirArchivo;

	public Ventana(){
		super("HUFFMAN");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try{
			  
			  this.setDefaultLookAndFeelDecorated(true);
			  UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			}
			catch (Exception e)
			 {
			  e.printStackTrace();
			 }
		
		
		this.setBounds(400, 70, 550, 370);
		vista = new JLabel("Texto: ");
		res = new JLabel("Resultado: ");
		Comprimir = new JButton("Encriptar");
		Descomprimir = new JButton("Desencriptar");
		Limpiar = new JButton("Reiniciar");
		abrir = new JButton ("Abrir");
		JPanel todo=(JPanel)this.getContentPane();
		todo.setLayout(null);
		entrada=new JTextArea();
		entrada.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		salida=new JTextArea();
		salida.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));		
		JScrollPane s = new JScrollPane(salida);
		JScrollPane s2 = new JScrollPane(entrada);
		s2.setBounds(300, 40, 200, 100);
		s.setBounds(300, 170, 200, 150);
		res.setBounds(300, 140, 80, 20);
		vista.setBounds(300, 10, 80, 20);
		abrir.addActionListener(this);
		abrir.setBounds(0, 40, 100, 30);
		Comprimir.addActionListener(this);
		Comprimir.setBounds(0, 70, 100, 30);
		Descomprimir.addActionListener(this);
		Descomprimir.setBounds(120, 40, 120, 30);
		Limpiar.addActionListener(this);
		Limpiar.setBounds(120, 70, 100, 30);
		JPanel panel=new JPanel();
		panel.setLayout(null);
		panel.add(s2);
		panel.add(s);
		panel.add(Comprimir);
		panel.add(Descomprimir);
		panel.add(Limpiar);
		panel.add(vista);
		panel.add(abrir);
		panel.add(res);
		panel.setBounds(0, 0, 800, 370);
		todo.add(panel);
		huffman=new Huffman();
		
	}
	
	
	
    public String getArchivo( String ruta ){
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el contenido del archivo
        String contenido = "";
        try
        {
            //ruta puede ser de tipo String o tipo File
            fr = new FileReader( ruta );
            br = new BufferedReader( fr );
 
            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while( ( linea = br.readLine() ) != null ){ 
                contenido += linea + "\n";
            }
 
        }catch( Exception e ){  }
        //finally se utiliza para que si todo ocurre correctamente o si ocurre 
        //algun error se cierre el archivo que anteriormente abrimos
        finally
        {
            try{
                br.close();
            }catch( Exception ex ){}
        }
        return contenido;
    }
	
	
	
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if(e.getSource()== abrir){
				 if( abrirArchivo == null ) abrirArchivo = new JFileChooser();
		            //Con esto solamente podamos abrir archivos
		            abrirArchivo.setFileSelectionMode( JFileChooser.FILES_ONLY );
		 
		            int seleccion = abrirArchivo.showOpenDialog( this );
		 
		            if( seleccion == JFileChooser.APPROVE_OPTION )
		            {
		                File f = abrirArchivo.getSelectedFile();
		                try
		                {
		                    String nombre = f.getName();
		                    String path = f.getAbsolutePath();
		                    String contenido = getArchivo( path );
		                    this.setTitle( nombre );
		                    entrada.setText( contenido );
		 
		                }catch( Exception exp){}
		            }
			}
			
			if(e.getSource() == Comprimir){
				String texto=entrada.getText();
				if(texto.length()==0){
					JOptionPane.showMessageDialog(null, "Error. No hay datos que comprimir","Error",JOptionPane.ERROR_MESSAGE);
					throw new NombreNulo();
				}
				String[] cadenas=huffman.Comprimir(texto);
				salida.setText("\nEnriptacion: "+cadenas[1]);
			
			} if(e.getSource() == Descomprimir){
				entrada.setText("");
				salida.setText("El mensaje original es: "+huffman.Descomprimir());
			
			} if(e.getSource() == Limpiar) {
				salida.setText("");
				entrada.setText("");
			}			
			
			}catch (RangoNulo e1) {
				e1.printStackTrace();
			} catch (ListaVacia e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (NombreNulo e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}

}
