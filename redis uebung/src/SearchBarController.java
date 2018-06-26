import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import redis.clients.jedis.Jedis;

public class SearchBarController {
	Jedis connection;
	
	String[] names = new String[0];
	String[] businesses;
	JComboBox cb;
	JTextArea text;
	
	
	SearchBarController(Jedis connector, JComboBox comboBox, JTextArea textArea){
		this.connection = connector;
		this.cb = comboBox;
		this.text = textArea;

	}
	
	public void findNames() {
		JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
		
		String searchTerm = editor.getText()+"*";
		names = connection.keys(searchTerm).toArray(new String[connection.keys(searchTerm).size()]);
		DefaultComboBoxModel model = new DefaultComboBoxModel( names );
	    model.setSelectedItem(editor.getText());
	    cb.setModel( model );		
	}

	
	public void searchAttributes() {
		JTextField editor = (JTextField) cb.getEditor().getEditorComponent();
	
    	String searchTerm = editor.getText();
    	businesses = connection.smembers(searchTerm).toArray(new String[connection.smembers(searchTerm).size()]);
    	String[] attributes = new String[businesses.length];
    	
    	for(int i = 0; i<businesses.length; i++) {
    		attributes[i] = "business:"+businesses[i]+":categories";
    	}
    	
    	Set<String> output = connection.sunion(attributes);
    	System.out.println(attributes[0]);
    	System.out.println(output);
    	text.setText(output.toString());
	}
}
