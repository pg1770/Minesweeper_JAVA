package minesweeper;

public abstract class Network {
	 	
		protected Control ctrl;

	    Network(Control c) {
	        ctrl = c;
	    }

	    abstract void connect(String ip, String name);
	    
	    abstract void disconnect();

	    abstract void send(Object p);
}
