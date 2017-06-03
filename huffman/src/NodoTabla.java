


public class NodoTabla {
	private String Dato;
	private String ubicacion;
	private NodoTabla Siguiente;
	
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getDato() {
		return Dato;
	}
	public void setDato(String dato) {
		Dato = dato;
	}
	public NodoTabla getSiguiente() {
		return Siguiente;
	}
	public void setSiguiente(NodoTabla siguiente) {
		Siguiente = siguiente;
	}
	
	public NodoTabla(String valor,String ub){
		this.setDato(valor);
		this.setSiguiente(null);
		this.setUbicacion(ub);
	}

}
